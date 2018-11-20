package me.chanjar.weixin.cp.bean;


import com.google.gson.annotations.SerializedName;
import lombok.Data;
import me.chanjar.weixin.cp.util.json.WxCpGsonBuilder;

import java.io.Serializable;

/**
 * "auth_corp_info;  //
 * {
 * "corpid;  // "xxxx",
 * "corp_name;  // "name",
 * "corp_type;  // "verified",
 * "corp_square_logo_url;  // "yyyyy",
 * "corp_user_max;  // 50,
 * "corp_agent_max;  // 30,
 * "corp_full_name;  //"full_name",
 * "verified_end_time;  //1431775834,
 * "subject_type;  // 1，
 * "corp_wxqrcode;  // "zzzzz",
 * "corp_scale;  // "1-50人",
 * "corp_industry;  // "IT服务",
 * "corp_sub_industry;  // "计算机软件/硬件/信息服务"
 * },
 */
@Data
public class WxCpAuthCorpInfo implements Serializable {

  private static final long serialVersionUID = 1L;

  @SerializedName(value = "corpid")
  private String corpId;

  @SerializedName(value = "corp_name")
  private String corpName;

  @SerializedName(value = "corp_type")
  private String corpType;

  @SerializedName(value = "corp_square_logo_url")
  private String corpSquareLogoUrl;

  @SerializedName(value = "corp_user_max")
  private Integer corpUserMax;

  @SerializedName(value = "corp_agent_max")
  private Integer corpAgentMax;

  @SerializedName(value = "corp_full_name")
  private String corpFullName;

  @SerializedName(value = "verified_end_time")
  private Long verifiedEndTime;

  @SerializedName(value = "subject_type")
  private Integer subjectType;

  @SerializedName(value = "corp_wxqrcode")
  private String corpWxqrcode;

  @SerializedName(value = "corp_scale")
  private String corpScale;

  @SerializedName(value = "corp_industry")
  private String corpIndustry;

  /** "计算机软件/硬件/信息服务"*/
  @SerializedName(value = "corp_sub_industry")
  private String corpSubIndustry;

  public static WxCpUser fromJson(String json) {
    return WxCpGsonBuilder.INSTANCE.create().fromJson(json, WxCpUser.class);
  }

  public String toJson() {
    return WxCpGsonBuilder.INSTANCE.create().toJson(this);
  }

}
