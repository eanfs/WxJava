package me.chanjar.weixin.cp.api.impl;

import com.google.gson.JsonObject;
import me.chanjar.weixin.common.WxType;
import me.chanjar.weixin.common.error.WxError;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.WxCpConsts;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.WxCpSuiteComponentService;
import me.chanjar.weixin.cp.api.WxCpSuiteService;
import me.chanjar.weixin.cp.bean.WxCpAuthCorpToken;
import me.chanjar.weixin.cp.bean.WxCpAuthInfo;
import me.chanjar.weixin.cp.bean.WxCpPreAuth;
import me.chanjar.weixin.cp.bean.WxCpSuiteAccessToken;
import me.chanjar.weixin.cp.bean.message.SuiteTicketXmlMessage;
import me.chanjar.weixin.cp.config.WxCpSuiteConfigStorage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <pre>
 *  Created by lirichen on 2019-7-3.
 * </pre>
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
public class WxCpSuiteComponentServiceImpl implements WxCpSuiteComponentService {
  protected final Logger log = LoggerFactory.getLogger(this.getClass());

  private final WxCpSuiteService mainService;
  private static final Map<String, WxCpService> WX_CP_SERVICE_MAP = new ConcurrentHashMap<>();


  /**
   * 全局的是否正在刷新access token的锁
   */
  protected final Object globalAccessTokenRefreshLock = new Object();

  public WxCpSuiteComponentServiceImpl(WxCpSuiteService mainService) {
    this.mainService = mainService;
  }


  @Override
  public WxCpSuiteConfigStorage getWxCpSuiteConfigStorage() {
    return mainService.getWxCpSuiteConfigStorage();
  }

  @Override
  public String route(final SuiteTicketXmlMessage message) throws WxErrorException {
    if (message == null) {
      throw new NullPointerException("message is empty");
    }
    if (StringUtils.equals(message.getInfoType(), WxCpConsts.WX_MESSAGE_TYPE_SUITE_TICKET)) {
      log.debug("handleSuiteMessage exec getPermanentCode getSuiteTicket: {} suiteId: {}", message.getSuiteTicket(), message.getSuiteId());

      // 企业微信服务器会定时（每十分钟）推送ticket。ticket会实时变更，并用于后续接口的调用。
      getWxCpSuiteConfigStorage().updateSuiteVerifyTicket(message.getSuiteTicket(), 1800);
    }
    return "success";
  }


  @Override
  public String getSuiteAccessToken(boolean forceRefresh) throws WxErrorException {

    synchronized (this.globalAccessTokenRefreshLock) {
      if (this.getWxCpSuiteConfigStorage().isSuiteAccessTokenExpired()) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("suite_id", this.getWxCpSuiteConfigStorage().getSuiteId());
        jsonObject.addProperty("suite_secret", this.getWxCpSuiteConfigStorage().getSuiteSecret());
        jsonObject.addProperty("suite_ticket", this.getWxCpSuiteConfigStorage().getSuiteVerifyTicket());
        String resultContent = mainService.get(SUITE_AUTH_URL, jsonObject.getAsString());
        WxError error = WxError.fromJson(resultContent, WxType.CP);
        if (error.getErrorCode() != 0) {
          throw new WxErrorException(error);
        }
        WxCpSuiteAccessToken accessToken = WxCpSuiteAccessToken.fromJson(resultContent);
        this.getWxCpSuiteConfigStorage().updateSuiteAccessToken(
          accessToken.getSuiteAccessToken(), 1800);

      }
    }
    return this.getWxCpSuiteConfigStorage().getSuiteAccessToken();
  }

  @Override
  public String getPreAuthCode() throws WxErrorException {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("suite_access_token", this.getWxCpSuiteConfigStorage().getSuiteAccessToken());
    String resultContent = mainService.get(PRE_AUTH_CODE_URL, jsonObject.getAsString());
    WxError error = WxError.fromJson(resultContent, WxType.CP);
    if (error.getErrorCode() != 0) {
      throw new WxErrorException(error);
    }
    WxCpPreAuth preAuth = WxCpPreAuth.fromJson(resultContent);
    return preAuth.getPreAuthCode();
  }

  @Override
  public String getSessionInfo() throws WxErrorException {
    return null;
  }

  @Override
  public WxCpAuthInfo getPermanentCode(String authCode) throws WxErrorException {
    String url = PERMANENT_CODE_URL
      + "?suite_access_token=" + this.getWxCpSuiteConfigStorage().getSuiteAccessToken();
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
  public WxCpAuthInfo getAuthInfo(String authCorpId, String permanentCode) throws WxErrorException {
    String url = AUTH_INFO_URL
      + "?suite_access_token=" + this.getWxCpSuiteConfigStorage().getSuiteAccessToken();
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
  public String getCorpToken(String authCorpId, String permanentCode) throws WxErrorException {
    String url = CORP_TOKEN_URL
      + "?suite_access_token=" + this.getWxCpSuiteConfigStorage().getSuiteAccessToken();
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

  @Override
  public WxCpService getWxCpServiceByAuthCorpId(String authCorpId) {
    WxCpService wxCpService = WX_CP_SERVICE_MAP.get(authCorpId);
    if (wxCpService == null) {
      synchronized (WX_CP_SERVICE_MAP) {
        wxCpService = WX_CP_SERVICE_MAP.get(authCorpId);
        if (wxCpService == null) {
          wxCpService = new WxCpOpenServiceImpl(this, authCorpId, getWxCpSuiteConfigStorage().getWxCpConfigStorage(authCorpId));

          WX_CP_SERVICE_MAP.put(authCorpId, wxCpService);
        }
      }
    }
    return wxCpService;
  }

  @Override
  public String getAuthCorpAccessToken(String authCorpId, boolean forceRefresh) throws WxErrorException {
    if (this.getWxCpSuiteConfigStorage().isAuthCorpAccessTokenExpired(authCorpId) || forceRefresh) {
      String url = CORP_TOKEN_URL
        + "?suite_access_token=" + this.getWxCpSuiteConfigStorage().getSuiteAccessToken();
      String permanentCode = this.getWxCpSuiteConfigStorage().getAuthCorpPermanentCode(authCorpId);
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("auth_corpid", authCorpId);
      jsonObject.addProperty("permanent_code", permanentCode);
      String resultContent = mainService.post(url, jsonObject.getAsString());
      WxError error = WxError.fromJson(resultContent, WxType.CP);
      if (error.getErrorCode() != 0) {
        throw new WxErrorException(error);
      }
      WxCpAuthCorpToken authInfo = WxCpAuthCorpToken.fromJson(resultContent);

      String authCorpAccessToken = authInfo.getAccessToken();

      getWxCpSuiteConfigStorage().updateAuthCorpAccessToken(authCorpId, authCorpAccessToken, authInfo.getExpiresIn());
    }
    return this.getWxCpSuiteConfigStorage().getAuthCorpAccessToken(authCorpId);
  }
}
