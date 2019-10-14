package me.chanjar.weixin.cp.bean.pay;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WxCpPayOrderResult implements Serializable {

  @SerializedName("errcode")
  private Integer errCode;

  @SerializedName("errmsg")
  private String errMsg;

  @SerializedName(value = "orderid")
  private String orderid;

  @SerializedName("order_status")
  private Integer orderStatus;

  @SerializedName("order_type")
  private Integer orderType;

  @SerializedName("paid_corpid")
  private String paidCorpId;

  @SerializedName("operator_id")
  private String operatorId;

  @SerializedName("suiteid")
  private String suiteId;

  @SerializedName("appid")
  private Long appId;

  @SerializedName("edition_id")
  private String editionId;

  @SerializedName("edition_name")
  private String editionName;

  @SerializedName("price")
  private Double price;

  @SerializedName("user_count")
  private Long userCount;

  @SerializedName("order_period")
  private Long orderPeriod;

  @SerializedName("order_time")
  private Long orderTime;

  @SerializedName("paid_time")
  private Long paidTime;

  @SerializedName("begin_time")
  private Long beginTime;

  @SerializedName("end_time")
  private Long endTime;

  @SerializedName("order_from")
  private Long orderFrom;

  @SerializedName("operator_corpid")
  private String operatorCorpId;

  @SerializedName("service_share_amount")
  private Long serviceShareAmount;

  @SerializedName("platform_share_amount")
  private Long platformShareAmount;

  @SerializedName("dealer_share_amount")
  private Long dealerShareAmount;

  @SerializedName("dealer_corp_info")
  private WxCpPayDealerCorpInfo DealerCorpInfo;

}
