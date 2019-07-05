package me.chanjar.weixin.cp.api;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.bean.WxCpAuthInfo;
import me.chanjar.weixin.cp.bean.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.message.WxCpSuiteXmlMessage;
import me.chanjar.weixin.cp.config.WxCpSuiteConfigStorage;

/**
 * <pre>
 *  企业微信 第三方应用接口
 *  Created by Lirichen on 2018/11/19.
 * </pre>
 *
 * @author <a href="https://github.com/eanfs">Binary Wang</a>
 */
public interface WxCpSuiteComponentService {

  String SUITE_AUTH_URL = "https://qyapi.weixin.qq.com/cgi-bin/service/get_suite_token";
  String PRE_AUTH_CODE_URL = "https://qyapi.weixin.qq.com/cgi-bin/service/get_pre_auth_code";
  String SESSEION_INFO_URL = "https://qyapi.weixin.qq.com/cgi-bin/service/set_session_info";
  String PERMANENT_CODE_URL = "https://qyapi.weixin.qq.com/cgi-bin/service/get_permanent_code";
  String AUTH_INFO_URL = "https://qyapi.weixin.qq.com/cgi-bin/service/get_auth_info";
  String CORP_TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/service/get_corp_token";

  String route(WxCpSuiteXmlMessage wxMessage) throws WxErrorException;

  WxCpSuiteConfigStorage getWxCpSuiteConfigStorage();

  String getSuiteAccessToken(boolean forceRefresh) throws WxErrorException;

  String getPreAuthCode() throws WxErrorException;

  String getSessionInfo() throws WxErrorException;

  WxCpAuthInfo getPermanentCode(String authCorpId) throws WxErrorException;

  WxCpAuthInfo getAuthInfo(String authCorpId, String permanentCode) throws WxErrorException;

  String getAuthCorpAccessToken(String authCorpId) throws WxErrorException;

  WxCpService getWxCpServiceByAuthCorpId(String authCorpId);

  String getAuthCorpAccessToken(String authCorpId, boolean forceRefresh) throws WxErrorException;


}
