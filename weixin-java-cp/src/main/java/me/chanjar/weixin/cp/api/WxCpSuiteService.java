package me.chanjar.weixin.cp.api;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.bean.WxCpAuthInfo;
import me.chanjar.weixin.cp.bean.WxCpUserDetail;
import me.chanjar.weixin.cp.bean.message.SuiteTicketXmlMessage;

/**
 * <pre>
 *  企业微信 第三方应用接口
 *  Created by Lirichen on 2018/11/19.
 * </pre>
 *
 * @author <a href="https://github.com/eanfs">Binary Wang</a>
 */
public interface WxCpSuiteService {

  String SUITE_AUTH_URL = "https://qyapi.weixin.qq.com/cgi-bin/service/get_suite_token";
  String PRE_AUTH_CODE_URL = "https://qyapi.weixin.qq.com/cgi-bin/service/get_pre_auth_code";
  String SESSEION_INFO_URL = "https://qyapi.weixin.qq.com/cgi-bin/service/set_session_info";
  String PERMANENT_CODE_URL = "https://qyapi.weixin.qq.com/cgi-bin/service/get_permanent_code";
  String AUTH_INFO_URL = "https://qyapi.weixin.qq.com/cgi-bin/service/get_auth_info";
  String CORP_TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/service/get_corp_token";

  String route(SuiteTicketXmlMessage wxMessage) throws WxErrorException;


  String getSuiteAccessToken(boolean forceRefresh) throws WxErrorException;

  String getPreAuthCode(String suiteAccessToken) throws WxErrorException;

  String getSessionInfo(String suiteAccessToken) throws WxErrorException;

  WxCpAuthInfo getPermanentCode(String suiteAccessToken, String authCode) throws WxErrorException;

  WxCpAuthInfo getAuthInfo(String suiteAccessToken, String authCorpId, String permanentCode) throws WxErrorException;

  String getCorpToken(String suiteAccessToken, String authCorpId, String permanentCode) throws WxErrorException;

}
