package me.chanjar.weixin.cp.config;

import me.chanjar.weixin.common.bean.WxAccessToken;
import me.chanjar.weixin.common.util.http.apache.ApacheHttpClientBuilder;

import java.io.File;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class WxCpInnerConfigStorage implements WxCpConfigStorage {


  private WxCpSuiteConfigStorage wxCpSuiteConfigStorage;
  private String authCorpId;
  private Lock accessTokenLock = new ReentrantLock();

  protected WxCpInnerConfigStorage(WxCpSuiteConfigStorage wxCpSuiteConfigStorage, String authCorpId) {
    this.wxCpSuiteConfigStorage = wxCpSuiteConfigStorage;
    this.authCorpId = authCorpId;
  }

  @Override
  public void setBaseApiUrl(String baseUrl) {

  }

  @Override
  public String getApiUrl(String path) {
    return null;
  }

  @Override
  public String getSuiteVerifyTicket(String suiteId) {
    return null;
  }

  @Override
  public String getSuiteAccessToken(String suiteId) {
    return null;
  }

  @Override
  public void updateSuiteVerifyTicket(String suiteId, String ticket, int expiresIn) {

  }

  @Override
  public void updateSuiteAccessToken(String authCorpId, String accessToken, int expiresIn) {

  }

  @Override
  public String getAccessToken() {
    return wxCpSuiteConfigStorage.getAuthCorpAccessToken(authCorpId);
  }

  public Lock getAccessTokenLock() {
    return this.accessTokenLock;
  }


  @Override
  public boolean isAccessTokenExpired() {
    return wxCpSuiteConfigStorage.isAuthCorpAccessTokenExpired(authCorpId);

  }

  @Override
  public void expireAccessToken() {
    wxCpSuiteConfigStorage.expireAuthCorpAccessToken(authCorpId);
  }

  @Override
  public void updateAccessToken(String accessToken, int expiresInSeconds) {
    wxCpSuiteConfigStorage.updateAuthCorpAccessToken(authCorpId, accessToken, expiresInSeconds);

  }

  @Override
  public void updateAccessToken(Integer agentId, WxAccessToken accessToken) {
    wxCpSuiteConfigStorage.updateAuthCorpAccessToken(authCorpId, accessToken.getAccessToken(), accessToken.getExpiresIn());
  }

  @Override
  public void updateAccessToken(Integer agentId, String accessToken, int expiresIn) {
    wxCpSuiteConfigStorage.updateAuthCorpAccessToken(authCorpId, accessToken, expiresIn);

  }

  @Override
  public String getJsapiTicket() {
    return null;
  }

  @Override
  public boolean isJsapiTicketExpired() {
    return false;
  }

  @Override
  public void expireJsapiTicket() {

  }

  @Override
  public void updateJsapiTicket(String jsapiTicket, int expiresInSeconds) {

  }

  @Override
  public String getAgentJsapiTicket() {
    return null;
  }

  @Override
  public boolean isAgentJsapiTicketExpired() {
    return false;
  }

  @Override
  public void expireAgentJsapiTicket() {

  }

  @Override
  public void updateAgentJsapiTicket(String jsapiTicket, int expiresInSeconds) {

  }

  @Override
  public String getCorpId() {
    return null;
  }

  @Override
  public String getCorpSecret() {
    return null;
  }

  @Override
  public Integer getAgentId() {
    return null;
  }

  @Override
  public String getToken() {
    return null;
  }

  @Override
  public String getAesKey() {
    return null;
  }

  @Override
  public long getExpiresTime() {
    return 0;
  }

  @Override
  public String getOauth2redirectUri() {
    return null;
  }

  @Override
  public String getHttpProxyHost() {
    return null;
  }

  @Override
  public int getHttpProxyPort() {
    return 0;
  }

  @Override
  public String getHttpProxyUsername() {
    return null;
  }

  @Override
  public String getHttpProxyPassword() {
    return null;
  }

  @Override
  public File getTmpDirFile() {
    return null;
  }

  @Override
  public ApacheHttpClientBuilder getApacheHttpClientBuilder() {
    return null;
  }

  @Override
  public String toString() {
    return "WxCpInnerConfigStorage{}";
  }
}
