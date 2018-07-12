package me.chanjar.weixin.mp.api;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpFastRegisterResult;

/**
 * 摇一摇周边的相关接口
 *
 * @author rememberber
 */
public interface WxMpRegisterAppService {

  /**
   * <pre>
   * 第三方平台调用快速注册API完成注册<br/>
   * 快速注册接口使用流程说明：
   * 步骤一：从第三方平台页面发起，并跳转至微信公众平台指定授权注册页面。
   * 步骤二：公众号管理员扫码确认复用公众号资质快速注册小程序。
   * 步骤三：管理员扫码验证通过后跳转至第三方平台页面，微信将注册结果返回给第三方平台。
   * 详情请见: https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=21521706765hLoMO&token=&lang=zh_CN
   * http请求方式: POST（请使用https协议）
   * 接口地址：https://api.weixin.qq.com/cgi-bin/account/fastregister?access_token=TOKEN
   * </pre>
   *
   * @param ticket 公众号扫码授权的凭证(公众平台扫码页面回跳到第三方平台时携带)
   */
  WxMpFastRegisterResult fastRegisterWxApp(String ticket) throws WxErrorException;

}
