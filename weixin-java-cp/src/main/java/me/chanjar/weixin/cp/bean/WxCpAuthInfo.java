package me.chanjar.weixin.cp.bean;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import me.chanjar.weixin.cp.util.json.WxCpGsonBuilder;

@Data
public class WxCpAuthInfo {

  @SerializedName(value = "access_token")
  private String accessToken;

  @SerializedName(value = "permanent_code")
  private String permanentCode;

  @SerializedName(value = "auth_corp_info")
  private WxCpAuthCorpInfo authCorpInfo;

  @SerializedName(value = "auth_info")
  private WxCpAgentAuthInfo authInfo;

  @SerializedName(value = "auth_user_info")
  private WxCpUser authUserInfo;


  public static WxCpAuthInfo fromJson(String json) {
    return WxCpGsonBuilder.create().fromJson(json, WxCpAuthInfo.class);
  }

  public String toJson() {
    return WxCpGsonBuilder.create().toJson(this);
  }

}
