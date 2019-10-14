package me.chanjar.weixin.cp.api.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpSuitePayService;
import me.chanjar.weixin.cp.api.WxCpSuiteService;
import me.chanjar.weixin.cp.bean.pay.WxCpPayOrderResult;
import me.chanjar.weixin.cp.bean.pay.WxCpPayOrders;
import me.chanjar.weixin.cp.util.json.WxCpGsonBuilder;

/**
 * <pre>
 *
 * Created by Binary Wang on 2017-6-25.
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 *
 * @author Binary Wang
 * </pre>
 */
public class WxCpSuitePayServiceImpl implements WxCpSuitePayService {
  private WxCpSuiteService mainService;

  public WxCpSuitePayServiceImpl(WxCpSuiteService mainService) {
    this.mainService = mainService;
  }

  @Override
  public WxCpPayOrderResult getOrder(String orderId) throws WxErrorException {
    String url = String.format("https://qyapi.weixin.qq.com/cgi-bin/service/get_order?suite_access_token==%s",
      this.mainService.getWxCpSuiteComponentService().getSuiteAccessToken(false));
    JsonObject param = new JsonObject();
    param.addProperty("orderid", orderId);
    String responseText = this.mainService.post(url, param.toString());
    JsonElement je = new JsonParser().parse(responseText);
    JsonObject jo = je.getAsJsonObject();
    return WxCpGsonBuilder.create().fromJson(responseText, WxCpPayOrderResult.class);
  }

  @Override
  public WxCpPayOrders findOrder(Long startTime, Long endTime, Integer testMode) throws WxErrorException {
    String url = String.format("https://qyapi.weixin.qq.com/cgi-bin/service/get_order_list?suite_access_token=%s",
      this.mainService.getWxCpSuiteComponentService().getSuiteAccessToken(false));
    JsonObject param = new JsonObject();
    param.addProperty("start_time", startTime);
    param.addProperty("end_time", endTime);
    param.addProperty("test_mode", testMode);
    String responseText = this.mainService.post(url, param.toString());
    JsonElement je = new JsonParser().parse(responseText);
    JsonObject jo = je.getAsJsonObject();
    return WxCpGsonBuilder.create().fromJson(responseText, WxCpPayOrders.class);
  }

}
