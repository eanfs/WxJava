package me.chanjar.weixin.cp.bean;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class WxCpAuthEditionInfo implements Serializable {

  @SerializedName(value = "agent")
  private List<WxCpAuthAgentInfo> agent;

}
