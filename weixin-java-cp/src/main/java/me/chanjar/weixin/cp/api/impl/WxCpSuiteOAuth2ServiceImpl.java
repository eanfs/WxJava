package me.chanjar.weixin.cp.api.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxError;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.util.http.URIUtil;
import me.chanjar.weixin.common.util.json.GsonHelper;
import me.chanjar.weixin.cp.api.WxCpOAuth2Service;
import me.chanjar.weixin.cp.api.WxCpSuiteService;
import me.chanjar.weixin.cp.bean.WxCpProviderAuthInfo;
import me.chanjar.weixin.cp.bean.WxCpUserDetail;
import me.chanjar.weixin.cp.util.json.WxCpGsonBuilder;

/**
 * <pre>
 *
 * Created by Binary Wang on 2017-6-25.
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 *
 * @author Binary Wang
 * </pre>
 */
public class WxCpSuiteOAuth2ServiceImpl implements WxCpOAuth2Service {
  private WxCpSuiteService mainService;

  public WxCpSuiteOAuth2ServiceImpl(WxCpSuiteService mainService) {
    this.mainService = mainService;
  }

  @Override
  public String buildAuthorizationUrl(String state) {
    return this.buildAuthorizationUrl(
      this.mainService.getWxCpSuiteConfigStorage().getOauth2redirectUri(),
      state
    );
  }

  @Override
  public String buildAuthorizationUrl(String redirectUri, String state) {
    return this.buildAuthorizationUrl(redirectUri, state, WxConsts.OAuth2Scope.SNSAPI_BASE);
  }

  @Override
  public String buildAuthorizationUrl(String redirectUri, String state, String scope) {
    StringBuilder url = new StringBuilder("https://open.weixin.qq.com/connect/oauth2/authorize?");
    url.append("appid=").append(this.mainService.getWxCpSuiteConfigStorage().getSuiteId());
    url.append("&redirect_uri=").append(URIUtil.encodeURIComponent(redirectUri));
    url.append("&response_type=code");
    url.append("&scope=").append(scope);

    if (state != null) {
      url.append("&state=").append(state);
    }
    url.append("#wechat_redirect");
    return url.toString();
  }


  @Override
  public String buildQRConnectUrl(String state) {
    return this.buildQRConnectUrl(
      this.mainService.getWxCpSuiteConfigStorage().getOauth2redirectUri(),
      state
    );
  }

  @Override
  public String buildQRConnectUrl(String redirectUri, String state) {
    return this.buildQRConnectUrl(
      this.mainService.getWxCpSuiteConfigStorage().getOauth2redirectUri(),
      state, "member"
    );
  }

  public String buildQRConnectUrl(String redirectUri, String state, String userType) {
    StringBuilder url = new StringBuilder("https://open.work.weixin.qq.com/wwopen/sso/3rd_qrConnect?");
    url.append("appid=").append(this.mainService.getWxCpSuiteConfigStorage().getCorpId());
    url.append("&usertype=").append(userType);
    url.append("&redirect_uri=").append(URIUtil.encodeURIComponent(redirectUri));

    if (state != null) {
      url.append("&state=").append(state);
    }
    return url.toString();
  }

  @Override
  public String[] getUserInfo(String code) throws WxErrorException {
    String url = String.format("https://qyapi.weixin.qq.com/cgi-bin/service/getuserinfo3rd?code=%s&access_token=%s",
      code, this.mainService.getWxCpSuiteComponentService().getSuiteAccessToken(false));
    String responseText = this.mainService.get(url, null);
    JsonElement je = new JsonParser().parse(responseText);
    JsonObject jo = je.getAsJsonObject();
    return new String[]{GsonHelper.getString(jo, "CorpId"),
      GsonHelper.getString(jo, "UserId"),
      GsonHelper.getString(jo, "DeviceId"),
      GsonHelper.getString(jo, "OpenId"),
      GsonHelper.getString(jo, "user_ticket"),
      GsonHelper.getString(jo, "expires_in")
    };
  }

  @Override
  public String[] getUserInfo(Integer agentId, String code) throws WxErrorException {
    throw new WxErrorException(WxError.builder().errorMsg("Suite 应用不实现").build());
  }

  @Override
  public WxCpUserDetail getUserDetail(String userTicket) throws WxErrorException {
    String url = String.format("https://qyapi.weixin.qq.com/cgi-bin/service/getuserdetail3rd?access_token=%s",
      this.mainService.getWxCpSuiteComponentService().getSuiteAccessToken(false));
    JsonObject param = new JsonObject();
    param.addProperty("user_ticket", userTicket);
    String responseText = this.mainService.post(url, param.toString());
    return WxCpGsonBuilder.create().fromJson(responseText, WxCpUserDetail.class);
  }

  @Override
  public WxCpProviderAuthInfo getAuthUserInfo(String authCode) throws WxErrorException {

    String url = String.format("https://qyapi.weixin.qq.com/cgi-bin/service/get_login_info?access_token=%s",
      this.mainService.getWxCpSuiteComponentService().getProviderAccessToken(false));
    JsonObject param = new JsonObject();
    param.addProperty("auth_code", authCode);
    String responseText = this.mainService.post(url, param.toString());
    JsonElement je = new JsonParser().parse(responseText);
    JsonObject jo = je.getAsJsonObject();
    return WxCpGsonBuilder.create().fromJson(responseText, WxCpProviderAuthInfo.class);
  }
}
