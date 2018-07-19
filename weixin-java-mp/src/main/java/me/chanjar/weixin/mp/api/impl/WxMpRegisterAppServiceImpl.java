package me.chanjar.weixin.mp.api.impl;

import com.google.gson.JsonObject;
import me.chanjar.weixin.common.error.WxError;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpRegisterAppService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpFastRegisterResult;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by lirichen on 2017/7/11.
 *
 * @author lirichen
 */
public class WxMpRegisterAppServiceImpl implements WxMpRegisterAppService {

  private static final String API_URL_PREFIX = "https://api.weixin.qq.com/cgi-bin/account";

  private static final String API_ACCOUNT_FAST_REGISTER_URL = "/fastregister";

  private WxMpService wxMpService;

  public WxMpRegisterAppServiceImpl(WxMpService wxMpService) {
    this.wxMpService = wxMpService;
  }


  @Override
  public WxMpFastRegisterResult fastRegisterWxApp(String ticket) throws WxErrorException {
    if (StringUtils.isBlank(ticket)) {
      throw new WxErrorException(WxError.builder().errorCode(-1).errorMsg("公众号扫码授权的凭证值不能为空！").build());
    }

    String url = API_URL_PREFIX + API_ACCOUNT_FAST_REGISTER_URL;
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("ticket", ticket);
    String responseContent = this.wxMpService.post(url, jsonObject.toString());
    return WxMpFastRegisterResult.fromJson(responseContent);

  }

}
