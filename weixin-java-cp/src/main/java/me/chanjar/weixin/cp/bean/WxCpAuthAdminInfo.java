package me.chanjar.weixin.cp.bean;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import me.chanjar.weixin.cp.util.json.WxCpGsonBuilder;

import java.util.List;

@Data
public class WxCpAuthAdminInfo {

  @SerializedName(value = "admin")
  private List<AuthAdminUser> admin;

  public static WxCpAuthAdminInfo fromJson(String json) {
    return WxCpGsonBuilder.create().fromJson(json, WxCpAuthAdminInfo.class);
  }

  public String toJson() {
    return WxCpGsonBuilder.create().toJson(this);
  }

  @Data
  @AllArgsConstructor
  public static class AuthAdminUser {
    @SerializedName(value = "userid")
    private String userId;

    @SerializedName(value = "auth_type")
    private String authType;
  }
}
