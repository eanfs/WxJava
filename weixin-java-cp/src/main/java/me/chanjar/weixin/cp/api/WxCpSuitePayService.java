package me.chanjar.weixin.cp.api;


import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.bean.pay.WxCpPayOrderResult;
import me.chanjar.weixin.cp.bean.pay.WxCpPayOrders;

/**
 * <pre>
 * 微信支付相关接口.
 * Created by Binary Wang on 2016/7/28.
 * </pre>
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
public interface WxCpSuitePayService {

  /**
   * <pre>
   * 获取订单详情.
   * 详见https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_2
   * 请求方式： POST（HTTPS）
   * 请求地址： https://qyapi.weixin.qq.com/cgi-bin/service/get_order?suite_access_token=SUITE_ACCESS_TOKEN
   * </pre>
   *
   * @param orderId 微信订单号
   * @return the wx pay order query result
   * @throws me.chanjar.weixin.common.error.WxErrorException the wx pay exception
   */
  WxCpPayOrderResult getOrder(String orderId) throws WxErrorException;

  /**
   * <pre>
   * 获取订单列表
   *
   * 请求方式： POST（HTTPS）
   * 请求地址： https://qyapi.weixin.qq.com/cgi-bin/service/get_order_list?suite_access_token=SUITE_ACCESS_TOKEN
   * </pre>
   *
   * @param startTime 查询订单请求对象
   * @return the wx pay order query result
   * @throws WxErrorException the wx pay exception
   */
  WxCpPayOrders findOrder(Long startTime, Long endTime, Integer testMode) throws WxErrorException;

}
