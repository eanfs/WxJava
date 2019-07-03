package me.chanjar.weixin.cp.api.impl;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpSuiteComponentService;
import me.chanjar.weixin.cp.config.WxCpConfigStorage;

/**
 * <pre>
 *  默认接口实现类，使用apache httpclient实现
 * Created by lirichen on 2019-7-3.
 * </pre>
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
public class WxCpOpenServiceImpl extends WxCpServiceImpl {
  private WxCpSuiteComponentService wxCpSuiteComponentService;
  private WxCpConfigStorage wxCpConfigStorage;
  private String authCorpId;

  public WxCpOpenServiceImpl(WxCpSuiteComponentService wxCpSuiteComponentService, String authCorpId, WxCpConfigStorage wxCpConfigStorage) {
    this.wxCpSuiteComponentService = wxCpSuiteComponentService;
    this.wxCpConfigStorage = wxCpConfigStorage;
    this.authCorpId = authCorpId;
    initHttp();
  }

  @Override
  public String getAccessToken(boolean forceRefresh) throws WxErrorException {
    return wxCpSuiteComponentService.getAuthCorpAccessToken(authCorpId, forceRefresh);
  }

  @Override
  public WxCpConfigStorage getWxCpConfigStorage() {
    return this.wxCpConfigStorage;
  }
}
