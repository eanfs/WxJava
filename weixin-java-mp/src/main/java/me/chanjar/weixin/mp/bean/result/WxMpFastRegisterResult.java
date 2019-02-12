package me.chanjar.weixin.mp.bean.result;

import lombok.Data;
import me.chanjar.weixin.mp.util.json.WxMpGsonBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import me.chanjar.weixin.common.util.ToStringUtils;
import me.chanjar.weixin.mp.util.json.WxMpGsonBuilder;

import java.io.Serializable;

/**
 * @author <a href="https://github.com/007gzs">007</a>
 */
@Data
public class WxMpFastRegisterResult implements Serializable {
  private static final long serialVersionUID = 2394736235020206855L;

  private String appId;
  private String authorizationCode;
  private String isWxVerifySucc;
  private String isLinkSucc;


  public static WxMpFastRegisterResult fromJson(String json) {
    return WxMpGsonBuilder.create().fromJson(json, WxMpFastRegisterResult.class);
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }
}
