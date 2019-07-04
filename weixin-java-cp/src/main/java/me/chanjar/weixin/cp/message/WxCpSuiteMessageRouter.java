package me.chanjar.weixin.cp.message;

import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.WxCpSuiteService;
import me.chanjar.weixin.cp.bean.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutMessage;

import java.util.HashMap;
import java.util.Map;

public class WxCpSuiteMessageRouter extends WxCpMessageRouter {
  private WxCpSuiteService wxCpSuiteService;

  public WxCpSuiteMessageRouter(WxCpSuiteService wxCpSuiteService) {
    super(null);
    this.wxCpSuiteService = wxCpSuiteService;
  }

  public WxCpXmlOutMessage route(final WxCpXmlMessage wxMessage, String authCorpId) {
    return route(wxMessage, new HashMap<String, Object>(), authCorpId);
  }

  public WxCpXmlOutMessage route(final WxCpXmlMessage wxMessage, final Map<String, Object> context, String authCorpId) {
    WxCpService wxCpService = wxCpSuiteService.getWxCpSuiteComponentService().getWxCpServiceByAuthCorpId(authCorpId);
    return route(wxMessage, context, wxCpService);
  }
}
