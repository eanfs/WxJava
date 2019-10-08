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
public class WxCpPayDealerCorpInfo implements Serializable {

  @SerializedName("corpid")
  private String corpId;

  @SerializedName("corp_name")
  private String corpName;

}
