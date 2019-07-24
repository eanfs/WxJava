package me.chanjar.weixin.cp.config;

import me.chanjar.weixin.common.bean.WxAccessToken;
import me.chanjar.weixin.common.util.http.apache.ApacheHttpClientBuilder;

/**
 * 企业微信第三方应用配置存储
 *
 * @author lirichen
 * @date 2019-07-03
 */
public interface WxCpSuiteConfigStorage {


  String getProviderAccessToken();

  boolean isProviderAccessTokenExpired();

  void updateProviderAccessToken(String accessToken, int expiresInSeconds);

  String getSuiteAccessToken();

  boolean isSuiteAccessTokenExpired();

  String getSuiteVerifyTicket();

  void updateSuiteVerifyTicket(String ticket, int expiresInSeconds);

  void updateSuiteAccessToken(String accessToken, int expiresInSeconds);

  /**
   * 强制将suite access token过期掉
   */
  void expireSuiteAccessToken();

  void updateSuiteAccessToken(WxAccessToken accessToken);

  boolean isSuiteVerifyTicketExpired();


  String getCorpId();

  String getCorpSecret();

  String getProviderSecret();

  String getSuiteId();

  String getSuiteSecret();

  String getSuiteToken();

  String getSuiteAesKey();

  String getHttpProxyHost();

  int getHttpProxyPort();

  String getHttpProxyUsername();

  String getHttpProxyPassword();

  String getAuthCorpAccessToken(String authCorpId);

  void updateAuthCorpAccessToken(String authCorpId, String accessToken, int expiresInSeconds);

  boolean isAuthCorpAccessTokenExpired(String authCorpId);

  String getAuthCorpPermanentCode(String authCorpId);

  void updateAuthCorpPermanentCode(String authCorpId, String permanentCode);

  WxCpConfigStorage getWxCpConfigStorage(String authCorpId);

  String getOauth2redirectUri();

  /**
   * http client builder
   *
   * @return ApacheHttpClientBuilder
   */
  ApacheHttpClientBuilder getApacheHttpClientBuilder();
}
