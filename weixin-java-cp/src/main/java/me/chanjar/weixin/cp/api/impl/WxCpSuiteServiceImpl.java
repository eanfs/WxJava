package me.chanjar.weixin.cp.api.impl;

import com.google.gson.JsonObject;
import me.chanjar.weixin.common.WxType;
import me.chanjar.weixin.common.bean.WxAccessToken;
import me.chanjar.weixin.common.error.WxError;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.WxCpConsts;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.WxCpSuiteService;
import me.chanjar.weixin.cp.bean.WxCpAuthCorpToken;
import me.chanjar.weixin.cp.bean.WxCpAuthInfo;
import me.chanjar.weixin.cp.bean.WxCpPreAuth;
import me.chanjar.weixin.cp.bean.WxCpSuiteAccessToken;
import me.chanjar.weixin.cp.bean.message.SuiteTicketXmlMessage;
import me.chanjar.weixin.cp.config.WxCpConfigStorage;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 *  Created by BinaryWang on 2017/6/24.
 * </pre>
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
public class WxCpSuiteServiceImpl implements WxCpSuiteService {
  protected final Logger log = LoggerFactory.getLogger(this.getClass());

  private final WxCpService mainService;

  protected final WxCpConfigStorage configStorage;

  /**
   * 全局的是否正在刷新access token的锁
   */
  protected final Object globalAccessTokenRefreshLock = new Object();

  public WxCpSuiteServiceImpl(WxCpService mainService, WxCpConfigStorage configStorage) {
    this.mainService = mainService;
    this.configStorage = configStorage;
  }

  @Override
  public String route(final SuiteTicketXmlMessage message) throws WxErrorException {
    if (message == null) {
      throw new NullPointerException("message is empty");
    }
    if(StringUtils.equals(message.getInfoType(), WxCpConsts.WX_MESSAGE_TYPE_SUITE_TICKET)) {
      log.debug("handleSuiteMessage exec getPermanentCode getSuiteTicket: {} suiteId: {}", message.getSuiteTicket(), message.getSuiteId());

      // 企业微信服务器会定时（每十分钟）推送ticket。ticket会实时变更，并用于后续接口的调用。
      configStorage.updateSuiteVerifyTicket(message.getSuiteId(), message.getSuiteTicket(), 1800);
    }
    return "success";
  }


  @Override
  public String getSuiteAccessToken(boolean forceRefresh) throws WxErrorException {

      synchronized (this.globalAccessTokenRefreshLock) {
        if (this.configStorage.isAccessTokenExpired()) {

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("suite_id", this.configStorage.getCorpId());
            jsonObject.addProperty("suite_secret", this.configStorage.getCorpSecret());
            jsonObject.addProperty("suite_ticket", this.configStorage.getJsapiTicket());
            String resultContent = mainService.get(SUITE_AUTH_URL, jsonObject.getAsString());
            WxError error = WxError.fromJson(resultContent, WxType.CP);
            if (error.getErrorCode() != 0) {
              throw new WxErrorException(error);
            }
            WxCpSuiteAccessToken accessToken = WxCpSuiteAccessToken.fromJson(resultContent);
            this.configStorage.updateSuiteAccessToken(this.configStorage.getCorpId(),
              accessToken.getSuiteAccessToken(), 1800);

        }
      }
    return this.configStorage.getAccessToken();
  }

  @Override
  public String getPreAuthCode(String suiteAccessToken) throws WxErrorException {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("suite_access_token", suiteAccessToken);
    String resultContent = mainService.get(PRE_AUTH_CODE_URL, jsonObject.getAsString());
    WxError error = WxError.fromJson(resultContent, WxType.CP);
    if (error.getErrorCode() != 0) {
      throw new WxErrorException(error);
    }
    WxCpPreAuth preAuth = WxCpPreAuth.fromJson(resultContent);
    return preAuth.getPreAuthCode();
  }

  @Override
  public String getSessionInfo(String suiteAccessToken) throws WxErrorException {
    return null;
  }

  @Override
  public WxCpAuthInfo getPermanentCode(String suiteAccessToken, String authCode) throws WxErrorException {
    String url = PERMANENT_CODE_URL
    + "?suite_access_token=" + suiteAccessToken;
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("auth_code", authCode);
    String resultContent = mainService.post(url, jsonObject.getAsString());
    WxError error = WxError.fromJson(resultContent, WxType.CP);
    if (error.getErrorCode() != 0) {
      throw new WxErrorException(error);
    }
    WxCpAuthInfo authInfo = WxCpAuthInfo.fromJson(resultContent);
    return authInfo;
  }

  @Override
  public WxCpAuthInfo getAuthInfo(String suiteAccessToken, String authCorpId, String permanentCode) throws WxErrorException {
    String url = AUTH_INFO_URL
      + "?suite_access_token=" + suiteAccessToken;
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("auth_corpid", authCorpId);
    jsonObject.addProperty("permanent_code", permanentCode);
    String resultContent = mainService.post(url, jsonObject.getAsString());
    WxError error = WxError.fromJson(resultContent, WxType.CP);
    if (error.getErrorCode() != 0) {
      throw new WxErrorException(error);
    }
    WxCpAuthInfo authInfo = WxCpAuthInfo.fromJson(resultContent);
    return authInfo;
  }

  @Override
  public String getCorpToken(String suiteAccessToken, String authCorpId, String permanentCode) throws WxErrorException {
    String url = CORP_TOKEN_URL
      + "?suite_access_token=" + suiteAccessToken;
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("auth_corpid", authCorpId);
    jsonObject.addProperty("permanent_code", permanentCode);
    String resultContent = mainService.post(url, jsonObject.getAsString());
    WxError error = WxError.fromJson(resultContent, WxType.CP);
    if (error.getErrorCode() != 0) {
      throw new WxErrorException(error);
    }
    WxCpAuthCorpToken authInfo = WxCpAuthCorpToken.fromJson(resultContent);
    return authInfo.getAccessToken();
  }
}
