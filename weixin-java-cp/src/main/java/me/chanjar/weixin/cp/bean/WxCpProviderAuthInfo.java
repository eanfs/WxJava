package me.chanjar.weixin.cp.bean;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import me.chanjar.weixin.cp.util.json.WxCpGsonBuilder;

import java.util.List;

@Data
public class WxCpProviderAuthInfo {

  @SerializedName(value = "usertype")
  private String userType;

  @SerializedName(value = "corp_info")
  private WxCpAuthCorpInfo authCorpInfo;

  @SerializedName(value = "agent")
  private List<WxCpAgentBasic> agent;

  @SerializedName(value = "user_info")
  private WxCpUser authUserInfo;


  public static WxCpProviderAuthInfo fromJson(String json) {
    return WxCpGsonBuilder.create().fromJson(json, WxCpProviderAuthInfo.class);
  }

  public String toJson() {
    return WxCpGsonBuilder.create().toJson(this);
  }

}
