package me.chanjar.weixin.cp.bean;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import me.chanjar.weixin.cp.util.json.WxCpGsonBuilder;

import java.io.Serializable;
import java.util.Map;

/**
 * "auth_info;  //
 * {
 * "agent" :
 * [
 * {
 * "agentid;  //1,
 * "name;  //"NAME",
 * "round_logo_url;  //"xxxxxx",
 * "square_logo_url;  //"yyyyyy",
 * "appid;  //1,
 * "privilege;  //
 * {
 * "level;  //1,
 * "allow_party;  //[1,2,3],
 * "allow_user;  //["zhansan","lisi"],
 * "allow_tag;  //[1,2,3],
 * "extra_party;  //[4,5,6],
 * "extra_user;  //["wangwu"],
 * "extra_tag;  //[4,5,6]
 * }
 * },
 * {
 * "agentid;  //2,
 * "name;  //"NAME2",
 * "round_logo_url;  //"xxxxxx",
 * "square_logo_url;  //"yyyyyy",
 * "appid;  //5
 * }
 * ]
 * },
 */
@Data
public class WxCpAgentBasic implements Serializable {

  private static final long serialVersionUID = 1L;


  @SerializedName(value = "agentid")
  private String agentId;

  @SerializedName(value = "name")
  private String name;

  @SerializedName(value = "round_logo_url")
  private String roundLogoUrl;

  @SerializedName(value = "square_logo_url")
  private String squareLogoUrl;

  @SerializedName(value = "appid")
  private String appId;

  @SerializedName(value = "privilege")
  private Map<String, Object> privilege;


  public static WxCpAgentBasic fromJson(String json) {
    return WxCpGsonBuilder.INSTANCE.create().fromJson(json, WxCpAgentBasic.class);
  }

  public String toJson() {
    return WxCpGsonBuilder.INSTANCE.create().toJson(this);
  }

}
