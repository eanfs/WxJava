package me.chanjar.weixin.cp.bean;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import me.chanjar.weixin.common.util.json.WxGsonBuilder;

import java.io.Serializable;

/**
 * 获取第三方应用凭证
 */
@Data
public class WxCpProviderAccessToken implements Serializable {

  private static final long serialVersionUID = 1L;

  @SerializedName("expires_in")
  private Integer expiresIn;

  @SerializedName("provider_access_token")
  private String providerAccessToken;

  public String getProviderAccessToken() {
    return providerAccessToken;
  }

  public void setProviderAccessToken(String providerAccessToken) {
    this.providerAccessToken = providerAccessToken;
  }


  public static WxCpProviderAccessToken fromJson(String json) {
    return WxGsonBuilder.create().fromJson(json, WxCpProviderAccessToken.class);
  }
}
