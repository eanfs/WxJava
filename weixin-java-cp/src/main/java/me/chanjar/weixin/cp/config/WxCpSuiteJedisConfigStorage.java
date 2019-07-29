package me.chanjar.weixin.cp.config;

import me.chanjar.weixin.common.bean.WxAccessToken;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.util.Pool;

/**
 * <pre>
 *    使用说明：本实现仅供参考，并不完整.
 *    比如为减少项目依赖，未加入redis分布式锁的实现，如有需要请自行实现。
 * </pre>
 *
 * @author gaigeshen
 */
public class WxCpSuiteJedisConfigStorage extends WxCpSuiteInMemoryConfigStorage {
  private static final String SUITE_ACCESS_TOKEN_KEY = "wx_cp_suite_access_token:";
  private static final String SUITE_VERIFY_TICKET_KEY = "wx_cp_suite_verify_ticket:";
  private static final String AUTH_CORP_PERMANENT_CODE_KEY = "wx_cp_auth_corp_permanent_code:";
  private static final String AUTH_CORP_ACCESS_TOKEN_KEY = "wx_cp_auth_corp_access_token:";

  private String keyPrefix;


  private String suiteVerifyTicketKey;
  private String suiteAccessTokenKey;
  private String authCorpPermanentCodeKey;
  private String authCorpAccessTokenKey;

  protected final Pool<Jedis> jedisPool;

  public WxCpSuiteJedisConfigStorage(JedisPool jedisPool) {
    this.jedisPool = jedisPool;
  }

  public WxCpSuiteJedisConfigStorage(JedisPool jedisPool, String keyPrefix) {
    this.jedisPool = jedisPool;
    this.keyPrefix = keyPrefix;
  }


  public WxCpSuiteJedisConfigStorage(Pool<Jedis> jedisPool) {
    this.jedisPool = jedisPool;
  }

  public WxCpSuiteJedisConfigStorage(Pool<Jedis> jedisPool, String keyPrefix) {
    this.jedisPool = jedisPool;
    this.keyPrefix = keyPrefix;
  }

  /**
   * This method will be destroy jedis pool
   */
  public void destroy() {
    this.jedisPool.destroy();
  }

  @Override
  public void setSuiteId(String suiteId) {
    super.setSuiteId(suiteId);
    String prefix = StringUtils.isBlank(keyPrefix) ? "" :
      (StringUtils.endsWith(keyPrefix, ":") ? keyPrefix : (keyPrefix + ":"));
    suiteVerifyTicketKey = prefix + SUITE_VERIFY_TICKET_KEY.concat(suiteId);
    suiteAccessTokenKey = prefix + SUITE_ACCESS_TOKEN_KEY.concat(suiteId);
    authCorpPermanentCodeKey = prefix + AUTH_CORP_PERMANENT_CODE_KEY.concat(suiteId);
    authCorpAccessTokenKey = prefix + AUTH_CORP_ACCESS_TOKEN_KEY.concat(suiteId);
  }

  @Override
  public String getSuiteAccessToken() {
    try (Jedis jedis = this.jedisPool.getResource()) {
      return jedis.get(this.suiteAccessTokenKey);
    }
  }

  @Override
  public boolean isSuiteAccessTokenExpired() {
    try (Jedis jedis = this.jedisPool.getResource()) {
      return jedis.ttl(this.suiteAccessTokenKey) < 2;

    }
  }

  @Override
  public void expireSuiteAccessToken() {
    try (Jedis jedis = this.jedisPool.getResource()) {
      jedis.expire(this.suiteAccessTokenKey, 0);
    }
  }

  @Override
  public synchronized void updateSuiteAccessToken(WxAccessToken accessToken) {
    this.updateSuiteAccessToken(accessToken.getAccessToken(), accessToken.getExpiresIn());
  }

  @Override
  public synchronized void updateSuiteAccessToken(String accessToken, int expiresInSeconds) {
    try (Jedis jedis = this.jedisPool.getResource()) {
      jedis.setex(this.suiteAccessTokenKey, expiresInSeconds - 200, accessToken);
    }
  }


  @Override
  public String getSuiteVerifyTicket() {
    try (Jedis jedis = this.jedisPool.getResource()) {
      return jedis.get(this.suiteVerifyTicketKey);
    }
  }

  @Override
  public void updateSuiteVerifyTicket(String ticket, int expiresInSeconds) {
    try (Jedis jedis = this.jedisPool.getResource()) {
      jedis.setex(this.suiteVerifyTicketKey, expiresInSeconds - 200, ticket);
    }
  }

  @Override
  public String getAuthCorpAccessToken(String authCorpId) {
    try (Jedis jedis = this.jedisPool.getResource()) {
      return jedis.get(this.getKey(this.authCorpAccessTokenKey, authCorpId));
    }
  }


  @Override
  public boolean isAuthCorpAccessTokenExpired(String appId) {
    try (Jedis jedis = this.jedisPool.getResource()) {
      return jedis.ttl(this.getKey(this.authCorpAccessTokenKey, appId)) < 2;
    }
  }

  @Override
  public void expireAuthCorpAccessToken(String authCorpId) {
    try (Jedis jedis = this.jedisPool.getResource()) {
      jedis.expire(this.getKey(this.authCorpAccessTokenKey, authCorpId), 0);
    }
  }

  @Override
  public void updateAuthCorpAccessToken(String authCorpId, String accessToken, int expiresInSeconds) {
    try (Jedis jedis = this.jedisPool.getResource()) {
      jedis.setex(this.getKey(this.authCorpAccessTokenKey, authCorpId), expiresInSeconds - 200, accessToken);
    }
  }

  @Override
  public String getAuthCorpPermanentCode(String authCorpId) {
    try (Jedis jedis = this.jedisPool.getResource()) {
      return jedis.get(this.getKey(this.authCorpPermanentCodeKey, authCorpId));
    }
  }

  @Override
  public void updateAuthCorpPermanentCode(String authCorpId, String permanentCode) {
    try (Jedis jedis = this.jedisPool.getResource()) {
      jedis.set(this.getKey(this.authCorpPermanentCodeKey, authCorpId), permanentCode);
    }
  }

  private String getKey(String prefix, String appId) {
    return prefix.endsWith(":") ? prefix.concat(appId) : prefix.concat(":").concat(appId);
  }
}
