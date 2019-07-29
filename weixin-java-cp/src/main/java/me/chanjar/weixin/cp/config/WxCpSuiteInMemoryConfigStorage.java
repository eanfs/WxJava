package me.chanjar.weixin.cp.config;

import me.chanjar.weixin.common.bean.WxAccessToken;
import me.chanjar.weixin.common.util.http.apache.ApacheHttpClientBuilder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 基于内存的微信配置provider，在实际生产环境中应该将这些配置持久化
 *
 * @author Daniel Qian
 */
public class WxCpSuiteInMemoryConfigStorage implements WxCpSuiteConfigStorage {

  private volatile String suiteId;
  private volatile String suiteSecret;
  private volatile String suiteAccessToken;
  private volatile long suiteAccessTokenExpiresTime;


  private volatile String providerAccessToken;
  private volatile long providerAccessTokenExpiresTime;

  private volatile String suiteVerifyTicket;
  private volatile long suiteVerifyTicketExpiresTime;

  private volatile String corpId;
  private volatile String corpSecret;
  private volatile String providerSecret;
  private volatile String token;
  private volatile String aesKey;

  private volatile String oauth2redirectUri;

  private Map<String, Token> authCorpAccessTokens = new ConcurrentHashMap<>();
  private Map<String, String> authCorpPermanentCodes = new ConcurrentHashMap<>();

  private String httpProxyHost;
  private int httpProxyPort;
  private String httpProxyUsername;
  private String httpProxyPassword;
  private ApacheHttpClientBuilder apacheHttpClientBuilder;


  @Override
  public String getSuiteId() {
    return suiteId;
  }

  @Override
  public String getSuiteSecret() {
    return this.suiteSecret;
  }

  @Override
  public String getSuiteToken() {
    return this.token;
  }

  @Override
  public String getSuiteAesKey() {
    return this.aesKey;
  }

  public void setSuiteId(String suiteId) {
    this.suiteId = suiteId;
  }

  public void setSuiteSecret(String suiteSecret) {
    this.suiteSecret = suiteSecret;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getAesKey() {
    return aesKey;
  }

  public void setAesKey(String aesKey) {
    this.aesKey = aesKey;
  }

  @Override
  public String getProviderAccessToken(){
    return this.providerAccessToken;
  }

  @Override
  public boolean isProviderAccessTokenExpired(){
    return System.currentTimeMillis() > providerAccessTokenExpiresTime;
  }

  @Override
  public void updateProviderAccessToken(String accessToken, int expiresInSeconds){
    this.providerAccessToken = accessToken;
    this.providerAccessTokenExpiresTime = System.currentTimeMillis() + (expiresInSeconds - 200) * 1000L;
  }

  @Override
  public String getSuiteAccessToken() {
    return this.suiteAccessToken;
  }

  @Override
  public boolean isSuiteAccessTokenExpired() {
    return System.currentTimeMillis() > suiteAccessTokenExpiresTime;
  }

  @Override
  public String getSuiteVerifyTicket() {
    return this.suiteVerifyTicket;
  }

  @Override
  public void updateSuiteVerifyTicket(String ticket, int expiresInSeconds) {
    this.suiteVerifyTicket = ticket;
    this.suiteVerifyTicketExpiresTime = System.currentTimeMillis() + (expiresInSeconds - 200) * 1000L;

  }

  @Override
  public void expireSuiteAccessToken() {
    this.suiteAccessTokenExpiresTime = 0L;

  }

  @Override
  public void updateSuiteAccessToken(WxAccessToken accessToken) {
    this.suiteAccessToken = accessToken.getAccessToken();
    this.suiteAccessTokenExpiresTime = System.currentTimeMillis() + (accessToken.getExpiresIn() - 200) * 1000L;
  }

  @Override
  public void updateSuiteAccessToken(String accessToken, int expiresInSeconds) {
    this.suiteAccessToken = accessToken;
    this.suiteAccessTokenExpiresTime = System.currentTimeMillis() + (expiresInSeconds - 200) * 1000L;
  }

  @Override
  public boolean isSuiteVerifyTicketExpired() {
    return System.currentTimeMillis() > suiteVerifyTicketExpiresTime;
  }

  @Override
  public String getCorpId() {
    return this.corpId;
  }

  public void setCorpId(String corpId) {
    this.corpId = corpId;
  }

  @Override
  public String getCorpSecret() {
    return this.corpSecret;
  }

  public void setCorpSecret(String corpSecret) {
    this.corpSecret = corpSecret;
  }


  @Override
  public String getProviderSecret() {
    return providerSecret;
  }

  public void setProviderSecret(String providerSecret) {
    this.providerSecret = providerSecret;
  }

  @Override
  public String getAuthCorpAccessToken(String authCorpId) {
    return getTokenString(authCorpAccessTokens, authCorpId);
  }

  @Override
  public void updateAuthCorpAccessToken(String authCorpId, String accessToken, int expiresInSeconds) {
    updateToken(authCorpAccessTokens, authCorpId, accessToken, expiresInSeconds);

  }

  @Override
  public void expireAuthCorpAccessToken(String authCorpId) {
    expireToken(authCorpAccessTokens, authCorpId);

  }

  @Override
  public boolean isAuthCorpAccessTokenExpired(String authCorpId) {
    return getTokenString(authCorpAccessTokens, authCorpId) == null;
  }

  @Override
  public String getAuthCorpPermanentCode(String authCorpId) {
    return authCorpPermanentCodes.get(authCorpId);
  }

  @Override
  public void updateAuthCorpPermanentCode(String authCorpId, String permanentCode) {
    authCorpPermanentCodes.put(authCorpId, permanentCode);
  }

  @Override
  public String getOauth2redirectUri() {
    return oauth2redirectUri;
  }

  public void setOauth2redirectUri(String oauth2redirectUri) {
    this.oauth2redirectUri = oauth2redirectUri;
  }

  @Override
  public WxCpConfigStorage getWxCpConfigStorage(String authCorpId) {
    return new WxCpInnerConfigStorage(this, authCorpId);
  }

  @Override
  public String getHttpProxyHost() {
    return this.httpProxyHost;
  }

  public void setHttpProxyHost(String httpProxyHost) {
    this.httpProxyHost = httpProxyHost;
  }

  @Override
  public int getHttpProxyPort() {
    return this.httpProxyPort;
  }

  public void setHttpProxyPort(int httpProxyPort) {
    this.httpProxyPort = httpProxyPort;
  }

  @Override
  public String getHttpProxyUsername() {
    return this.httpProxyUsername;
  }

  public void setHttpProxyUsername(String httpProxyUsername) {
    this.httpProxyUsername = httpProxyUsername;
  }

  @Override
  public String getHttpProxyPassword() {
    return this.httpProxyPassword;
  }

  public void setHttpProxyPassword(String httpProxyPassword) {
    this.httpProxyPassword = httpProxyPassword;
  }


  @Override
  public ApacheHttpClientBuilder getApacheHttpClientBuilder() {
    return this.apacheHttpClientBuilder;
  }

  public void setApacheHttpClientBuilder(ApacheHttpClientBuilder apacheHttpClientBuilder) {
    this.apacheHttpClientBuilder = apacheHttpClientBuilder;
  }


  private String getTokenString(Map<String, Token> map, String key) {
    Token token = map.get(key);
    if (token == null || (token.expiresTime != null && System.currentTimeMillis() > token.expiresTime)) {
      return null;
    }
    return token.token;
  }

  private void expireToken(Map<String, Token> map, String key) {
    Token token = map.get(key);
    if (token != null) {
      token.expiresTime = 0L;
    }
  }

  private void updateToken(Map<String, Token> map, String key, String tokenString, Integer expiresInSeconds) {
    Token token = map.get(key);
    if (token == null) {
      token = new Token();
      map.put(key, token);
    }
    token.token = tokenString;
    if (expiresInSeconds != null) {
      token.expiresTime = System.currentTimeMillis() + (expiresInSeconds - 200) * 1000L;
    }
  }

  private static class Token {
    private String token;
    private Long expiresTime;
  }
}
