package me.chanjar.weixin.cp.bean;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import me.chanjar.weixin.common.bean.WxAccessToken;
import me.chanjar.weixin.common.util.json.WxGsonBuilder;

import java.io.Serializable;

/**
 * 获取第三方应用凭证
 */
@Data
public class WxCpSuiteAccessToken implements Serializable {

  private static final long serialVersionUID = 1L;


  @SerializedName("suite_access_token")
  private String suiteAccessToken;

  public String getSuiteAccessToken() {
    return suiteAccessToken;
  }

  public void setSuiteAccessToken(String suiteAccessToken) {
    this.suiteAccessToken = suiteAccessToken;
  }


  public static WxCpSuiteAccessToken fromJson(String json) {
    return WxGsonBuilder.create().fromJson(json, WxCpSuiteAccessToken.class);
  }
}
