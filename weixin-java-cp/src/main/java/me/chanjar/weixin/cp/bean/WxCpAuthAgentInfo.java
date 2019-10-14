package me.chanjar.weixin.cp.bean;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

@Data
public class WxCpAuthAgentInfo implements Serializable {

  @SerializedName(value = "agentid")
  private String agentId;

  @SerializedName(value = "edition_id")
  private String editionId;

  @SerializedName(value = "edition_name")
  private String editionName;

  /**
   * 付费状态。
   * 0-没有付费;
   * 1-限时试用;
   * 2-试用过期;
   * 3-购买期内;
   * 4-购买过期;
   * 5-不限时试用;
   * 6-购买期内，但是人数超标, 注意，超标后还可以用7天;
   * 7-购买期内，但是人数超标, 且已经超标试用7天
   * */
  @SerializedName(value = "app_status")
  private String appStatus;

  /**
   * 用户上限。
   * 特别注意, 以下情况该字段无意义，可以忽略：
   * 1. 固定总价购买
   * 2. app_status = 限时试用/试用过期/不限时试用
   * 3. 在第2条“app_status=不限时试用”的情况下，如果该应用的配置为“小企业无使用限制”，user_limit有效，且为限制的人数
   * */
  @SerializedName(value = "user_limit")
  private String userLimit;

  /**
   * 版本到期时间（根据购买版本，可能是试用到期时间或付费使用到期时间）。
   * 特别注意，以下情况该字段无意义，可以忽略：
   * 1. app_status = 不限时试用
   * */
  @SerializedName(value = "expired_time")
  private String expiredTime;

}
