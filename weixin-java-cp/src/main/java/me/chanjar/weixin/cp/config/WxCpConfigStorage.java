package me.chanjar.weixin.cp.config;

import me.chanjar.weixin.common.bean.WxAccessToken;
import me.chanjar.weixin.common.util.http.apache.ApacheHttpClientBuilder;

import java.io.File;

/**
 * 微信客户端配置存储
 *
 * @author Daniel Qian
 */
public interface WxCpConfigStorage {

  String getSuiteVerifyTicket(String suiteId);

  String getSuiteAccessToken(String suiteId);

  void updateSuiteVerifyTicket(String suiteId, String ticket, int expiresIn);

  void updateSuiteAccessToken(String authCorpId, String accessToken, int expiresIn);

  String getAccessToken();

  boolean isAccessTokenExpired();

  /**
   * 强制将access token过期掉
   */
  void expireAccessToken();

  void updateAccessToken(Integer agentId, WxAccessToken accessToken);

  void updateAccessToken(Integer agentId, String accessToken, int expiresIn);

  String getJsapiTicket();

  boolean isJsapiTicketExpired();

  /**
   * 强制将jsapi ticket过期掉
   */
  void expireJsapiTicket();

  /**
   * 应该是线程安全的
   *
   * @param jsapiTicket
   */
  void updateJsapiTicket(String jsapiTicket, int expiresInSeconds);

  String getCorpId();

  String getCorpSecret();

  Integer getAgentId();

  String getToken();

  String getAesKey();

  long getExpiresTime();

  String getOauth2redirectUri();

  String getHttpProxyHost();

  int getHttpProxyPort();

  String getHttpProxyUsername();

  String getHttpProxyPassword();

  File getTmpDirFile();



  /**
   * http client builder
   *
   * @return ApacheHttpClientBuilder
   */
  ApacheHttpClientBuilder getApacheHttpClientBuilder();
}
