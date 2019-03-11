package me.chanjar.weixin.cp.api;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.bean.WxCpAgent;
import me.chanjar.weixin.cp.bean.WxCpAgentScope;
import me.chanjar.weixin.cp.bean.WxCpMessageSendResult;

import java.util.List;

/**
 * <pre>
 *  管理企业号应用
 *  文档地址：https://work.weixin.qq.com/api/doc#10087
 *  Created by huansinho on 2018/4/13.
 * </pre>
 *
 * @author <a href="https://github.com/huansinho">huansinho</a>
 */
public interface WxCpAgentService {
  /**
   * <pre>
   * 获取企业号应用信息
   * 该API用于获取企业号某个应用的基本信息，包括头像、昵称、帐号类型、认证类型、可见范围等信息
   * 详情请见: https://work.weixin.qq.com/api/doc#10087
   * </pre>
   *
   * @param agentId 企业应用的id
   * @return 部门id
   */
  WxCpAgent get(Integer agentId) throws WxErrorException;

  /**
   * <pre>
   * 设置应用.
   * 仅企业可调用，可设置当前凭证对应的应用；第三方不可调用。
   * 详情请见: https://work.weixin.qq.com/api/doc#10088
   * </pre>
   *
   * @param agentInfo 应用信息
   */
  void set(WxCpAgent agentInfo) throws WxErrorException;

  /**
   * <pre>
   * 设置授权应用可见范围.
   * 调用该接口前提是开启通讯录迁移，收到授权成功通知后可调用。企业注册初始化安装应用后，应用默认可见范围为根部门。如需修改应用可见范围，服务商可以调用该接口设置授权应用的可见范围。该接口只能使用注册完成回调事件或者查询注册状态返回的access_token，调用设置通讯录同步完成后或者access_token超过30分钟失效（即解除通讯录锁定状态）则不能继续调用该接口。
   * 详情请见: https://open.work.weixin.qq.com/api/doc#11729/
   * </pre>
   *
   * @param agentScope 应用信息
   */
  WxCpMessageSendResult setScope(WxCpAgentScope agentScope) throws WxErrorException;

  /**
   * <pre>
   * 获取应用列表.
   * 企业仅可获取当前凭证对应的应用；第三方仅可获取被授权的应用。
   * 详情请见: https://work.weixin.qq.com/api/doc#11214
   * </pre>
   *
   */
  List<WxCpAgent> list() throws WxErrorException;

}
