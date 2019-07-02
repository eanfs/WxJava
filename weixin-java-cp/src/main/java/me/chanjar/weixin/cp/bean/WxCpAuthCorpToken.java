package me.chanjar.weixin.cp.bean;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import me.chanjar.weixin.cp.util.json.WxCpGsonBuilder;

import java.io.Serializable;

/**
 * 获取预授权码
 */
@Data
public class WxCpAuthCorpToken implements Serializable {

  private static final long serialVersionUID = 1L;

  @SerializedName(value = "access_token")
  private String accessToken;


  public static WxCpAuthCorpToken fromJson(String json) {
    return WxCpGsonBuilder.create().fromJson(json, WxCpAuthCorpToken.class);
  }

  public String toJson() {
    return WxCpGsonBuilder.create().toJson(this);
  }
}
