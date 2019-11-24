package me.chanjar.weixin.cp.api.impl;

import com.google.common.base.Joiner;
import com.google.gson.JsonObject;
import me.chanjar.weixin.common.WxType;
import me.chanjar.weixin.common.error.WxError;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.WxCpSuiteComponentService;
import me.chanjar.weixin.cp.api.WxCpSuiteService;
import me.chanjar.weixin.cp.bean.*;
import me.chanjar.weixin.cp.config.WxCpConfigStorage;
import me.chanjar.weixin.cp.config.WxCpSuiteConfigStorage;
import me.chanjar.weixin.cp.constant.WxCpConsts;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
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

  protected final Object globalProviderAccessTokenRefreshLock = new Object();

  protected final Object globalAccessTokenRefreshLock = new Object();

  protected final Object globalPermanentCodeLock = new Object();

  protected final Object globalAuthCorpAccesstokenLock = new Object();

  public WxCpSuiteComponentServiceImpl(WxCpSuiteService mainService) {
    this.mainService = mainService;
  }


  @Override
  public WxCpSuiteConfigStorage getWxCpSuiteConfigStorage() {
    return mainService.getWxCpSuiteConfigStorage();
  }

  @Override
  public String route(final WxCpXmlMessage message) throws WxErrorException {
    if (message == null) {
      throw new NullPointerException("message is empty");
    }
    if (StringUtils.equals(message.getInfoType(), WxCpConsts.SuiteEventType.SUITE_TICKET)) {
      log.debug("handleSuiteMessage exec getPermanentCode getSuiteTicket: {} suiteId: {}", message.getSuiteTicket(), message.getSuiteId());

      // 企业微信服务器会定时（每十分钟）推送ticket。ticket会实时变更，并用于后续接口的调用。
      getWxCpSuiteConfigStorage().updateSuiteVerifyTicket(message.getSuiteTicket(), 1800);
    }
    return "success";
  }


  @Override
  public String getProviderAccessToken(boolean forceRefresh) throws WxErrorException {

    synchronized (this.globalProviderAccessTokenRefreshLock) {
      if (this.getWxCpSuiteConfigStorage().isProviderAccessTokenExpired() || forceRefresh) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("corpid", this.getWxCpSuiteConfigStorage().getCorpId());
        jsonObject.addProperty("provider_secret", this.getWxCpSuiteConfigStorage().getProviderSecret());
        String resultContent = this.post(GET_PROVIDER_ACCESS_TOKEN, jsonObject.toString());
        WxError error = WxError.fromJson(resultContent, WxType.CP);
        if (error.getErrorCode() != 0) {
          throw new WxErrorException(error);
        }
        WxCpProviderAccessToken accessToken = WxCpProviderAccessToken.fromJson(resultContent);
        this.getWxCpSuiteConfigStorage().updateProviderAccessToken(
          accessToken.getProviderAccessToken(), accessToken.getExpiresIn());

      }
    }
    return this.getWxCpSuiteConfigStorage().getProviderAccessToken();
  }


  @Override
  public String getSuiteAccessToken(boolean forceRefresh) throws WxErrorException {

    synchronized (this.globalAccessTokenRefreshLock) {
      if (this.getWxCpSuiteConfigStorage().isSuiteAccessTokenExpired() || forceRefresh) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("suite_id", this.getWxCpSuiteConfigStorage().getSuiteId());
        jsonObject.addProperty("suite_secret", this.getWxCpSuiteConfigStorage().getSuiteSecret());
        jsonObject.addProperty("suite_ticket", this.getWxCpSuiteConfigStorage().getSuiteVerifyTicket());
        String resultContent = this.mainService.post(SUITE_AUTH_URL, jsonObject.toString());
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
//    JsonObject jsonObject = new JsonObject();
//    jsonObject.addProperty("suite_access_token", this.getWxCpSuiteConfigStorage().getProviderAccessToken());
    String resultContent = get(PRE_AUTH_CODE_URL);
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
  public WxCpAuthInfo getPermanentCode(String authCorpId, String preAuthCode) throws WxErrorException {
    WxCpAuthInfo authInfo;
    synchronized (this.globalPermanentCodeLock) {
      String permanentCode = null;

      if (StringUtils.isNotEmpty(authCorpId)) {
        permanentCode = this.getWxCpSuiteConfigStorage().getAuthCorpPermanentCode(authCorpId);
      }
      if (StringUtils.isEmpty(permanentCode)) {
        final String url = PERMANENT_CODE_URL;
//      + "?suite_access_token=" + this.getWxCpSuiteConfigStorage().getProviderAccessToken();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("auth_code", preAuthCode);
        String resultContent = post(url, jsonObject.toString());
        WxError error = WxError.fromJson(resultContent, WxType.CP);
        if (error.getErrorCode() != 0) {
          throw new WxErrorException(error);
        }
        authInfo = WxCpAuthInfo.fromJson(resultContent);
        this.getWxCpSuiteConfigStorage().updateAuthCorpPermanentCode(authInfo.getAuthCorpInfo().getCorpId(), authInfo.getPermanentCode());
      } else {
        authInfo = getAuthInfo(authCorpId, permanentCode);
      }
    }

    return authInfo;
  }

  public void updatePermanentCode(String authCorpId, String permanentCode) throws WxErrorException {
    synchronized (this.globalPermanentCodeLock) {
      if (StringUtils.isNotEmpty(authCorpId) && StringUtils.isNotEmpty(permanentCode)) {
        this.getWxCpSuiteConfigStorage().updateAuthCorpPermanentCode(authCorpId, permanentCode);
      }
    }
  }


  @Override
  public WxCpAuthInfo getAuthInfo(String authCorpId, String permanentCode) throws WxErrorException {
    final String url = AUTH_INFO_URL;
//      + "?suite_access_token=" + this.getWxCpSuiteConfigStorage().getProviderAccessToken();
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("auth_corpid", authCorpId);
    jsonObject.addProperty("permanent_code", permanentCode);
    String resultContent = post(url, jsonObject.toString());
    WxError error = WxError.fromJson(resultContent, WxType.CP);
    if (error.getErrorCode() != 0) {
      throw new WxErrorException(error);
    }
    WxCpAuthInfo authInfo = WxCpAuthInfo.fromJson(resultContent);
    return authInfo;
  }

  @Override
  public String getAuthCorpAccessToken(String authCorpId) throws WxErrorException {
    return getAuthCorpAccessToken(authCorpId, false);
  }

  @Override
  public WxCpService getWxCpServiceByAuthCorpId(String authCorpId) {
    WxCpService wxCpService = WX_CP_SERVICE_MAP.get(authCorpId);
    if (wxCpService == null) {
      synchronized (WX_CP_SERVICE_MAP) {
        wxCpService = WX_CP_SERVICE_MAP.get(authCorpId);
        if (wxCpService == null) {
          WxCpConfigStorage cpConfigStorage = this.getWxCpSuiteConfigStorage().getWxCpConfigStorage(authCorpId);
          wxCpService = new WxCpOpenServiceImpl(this, authCorpId, cpConfigStorage);

          WX_CP_SERVICE_MAP.put(authCorpId, wxCpService);
        }
      }
    }
    return wxCpService;
  }

  @Override
  public String getAuthCorpAccessToken(String authCorpId, boolean forceRefresh) throws WxErrorException {
    if (this.getWxCpSuiteConfigStorage().isAuthCorpAccessTokenExpired(authCorpId) || forceRefresh) {
      final String url = CORP_TOKEN_URL;
      String permanentCode = this.getWxCpSuiteConfigStorage().getAuthCorpPermanentCode(authCorpId);
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("auth_corpid", authCorpId);
      jsonObject.addProperty("permanent_code", permanentCode);
      String resultContent = post(url, jsonObject.toString());
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

  @Override
  public WxCpAuthAdminInfo getAuthCorpAdmin(String authCorpId, Integer agentId) throws WxErrorException {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("auth_corpid", authCorpId);
    jsonObject.addProperty("agentid", agentId);
    String result = post(CORP_ADMINISTRATOR_URL, jsonObject.toString());

    WxError error = WxError.fromJson(result, WxType.CP);
    if (error.getErrorCode() != 0) {
      throw new WxErrorException(error);
    }
    return WxCpAuthAdminInfo.fromJson(result);
  }

  @Override
  public void dialAuthCorp(String authCorpId, String caller, String callee) throws WxErrorException {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("auth_corpid", authCorpId);
    jsonObject.addProperty("caller", caller);
    jsonObject.addProperty("callee", callee);
    String result = post(AUTH_CORP_DIAL_URL, jsonObject.toString(), "provider_access_token");

    WxError error = WxError.fromJson(result, WxType.CP);
    if (error.getErrorCode() != 0) {
      throw new WxErrorException(error);
    }
  }


  @Override
  public WxCpMaJsCode2SessionResult jsCode2Session(String jsCode) throws WxErrorException {
    Map<String, String> params = new HashMap<>(2);
    params.put("js_code", jsCode);
    params.put("grant_type", "authorization_code");

    String result = this.get(MINIAPP_JSCODE_2_SESSION + "?" + Joiner.on("&").withKeyValueSeparator("=").join(params));
    return WxCpMaJsCode2SessionResult.fromJson(result);
  }


  public WxCpSuiteService getWxCpSuiteService() {
    return this.mainService;
  }

  public String post(String uri, String postData) throws WxErrorException {
    return post(uri, postData, "suite_access_token");
  }

  private String post(String uri, String postData, String accessTokenKey) throws WxErrorException {
    String componentAccessToken = getSuiteAccessToken(false);
    String uriWithComponentAccessToken = uri + (uri.contains("?") ? "&" : "?") + accessTokenKey + "=" + componentAccessToken;
    try {
      return getWxCpSuiteService().post(uriWithComponentAccessToken, postData);
    } catch (WxErrorException e) {
      WxError error = e.getError();
      /*
       * 发生以下情况时尝试刷新access_token
       * 40001 获取access_token时AppSecret错误，或者access_token无效
       * 42001 access_token超时
       * 40014 不合法的access_token，请开发者认真比对access_token的有效性（如是否过期），或查看是否正在为恰当的公众号调用接口
       */
      if (error.getErrorCode() == 42001 || error.getErrorCode() == 40001 || error.getErrorCode() == 40014) {
        // 强制设置wxMpConfigStorage它的access token过期了，这样在下一次请求里就会刷新access token
        this.getWxCpSuiteConfigStorage().expireSuiteAccessToken();
        return this.post(uri, postData, accessTokenKey);

      }
      if (error.getErrorCode() != 0) {
        throw new WxErrorException(error, e);
      }
      return null;
    }
  }

  public String get(String uri) throws WxErrorException {
    return get(uri, "suite_access_token");
  }

  private String get(String uri, String accessTokenKey) throws WxErrorException {
    String componentAccessToken = getSuiteAccessToken(false);
    if (StringUtils.equals(accessTokenKey, "provider_access_token")) {
      componentAccessToken = getProviderAccessToken(false);
    }
    String uriWithComponentAccessToken = uri + (uri.contains("?") ? "&" : "?") + accessTokenKey + "=" + componentAccessToken;
    try {
      return getWxCpSuiteService().get(uriWithComponentAccessToken, null);
    } catch (WxErrorException e) {
      WxError error = e.getError();
      /*
       * 发生以下情况时尝试刷新access_token
       * 40001 获取access_token时AppSecret错误，或者access_token无效
       * 42001 access_token超时
       * 40014 不合法的access_token，请开发者认真比对access_token的有效性（如是否过期），或查看是否正在为恰当的公众号调用接口
       */
      if (error.getErrorCode() == 42001 || error.getErrorCode() == 40001 || error.getErrorCode() == 40014) {
        // 强制设置wxMpConfigStorage它的access token过期了，这样在下一次请求里就会刷新access token
        this.getWxCpSuiteConfigStorage().expireSuiteAccessToken();
        return this.get(uri, accessTokenKey);

      }
      if (error.getErrorCode() != 0) {
        throw new WxErrorException(error, e);
      }
      return null;
    }
  }
}
