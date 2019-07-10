package me.chanjar.weixin.cp.api;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.config.WxCpSuiteConfigStorage;

public interface WxCpSuiteService {

  WxCpSuiteComponentService getWxCpSuiteComponentService();

  WxCpSuiteConfigStorage getWxCpSuiteConfigStorage();

  void setWxCpSuiteConfigStorage(WxCpSuiteConfigStorage wxCpSuiteConfigStorage);

  boolean checkSignature(String msgSignature, String timestamp, String nonce, String data);

  /**
   * 当本Service没有实现某个API的时候，可以用这个，针对所有微信API中的GET请求
   */
  String get(String url, String queryParam) throws WxErrorException;

  /**
   * 当本Service没有实现某个API的时候，可以用这个，针对所有微信API中的POST请求
   */
  String post(String url, String postData) throws WxErrorException;
}
