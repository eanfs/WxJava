package me.chanjar.weixin.cp.bean;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.chanjar.weixin.cp.util.json.WxCpGsonBuilder;

import java.io.Serializable;

/**
 * <pre>
 * 登录凭证校验
 * 临时登录凭证校验接口是一个后台HTTPS 接口，开发者服务器使用 临时登录凭证code 获取 session_key 和 用户userid 和用户所在企业的corpid 等。
 * 文档地址：https://work.weixin.qq.com/api/doc#14276
 * 微信返回报文：{
 *       "corpid": "CORPID",
 *       "userid": "USERID",
 *       "session_key": "kJtdi6RF+Dv67QkbLlPGjw==",
 *       "errcode": 0,
 *       "errmsg": "ok"
 * }
 * </pre>
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WxCpJscode2SessionResult implements Serializable {
  private static final long serialVersionUID = -1060216618475607933L;

  @SerializedName("session_key")
  private String sessionKey;

  @SerializedName("corpid")
  private String corpId;

  @SerializedName("userid")
  private String userId;

  public static WxCpJscode2SessionResult fromJson(String json) {
    return WxCpGsonBuilder.create().fromJson(json, WxCpJscode2SessionResult.class);
  }

}
