package me.chanjar.weixin.cp.bean;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.chanjar.weixin.cp.util.json.WxCpGsonBuilder;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * <pre>
 * 企业号应用信息.
 * Created by huansinho on 2018/4/13.
 * </pre>
 *
 * @author <a href="https://github.com/huansinho">huansinho</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WxCpAgentScope implements Serializable {

  private static final long serialVersionUID = 1526852681831426958L;

  @SerializedName("agentid")
  private Integer agentId;

  @SerializedName("allow_user")
  private List<String> allowUser;

  @SerializedName("allow_party")
  private List<Integer> allowParties;

  @SerializedName("allow_tag")
  private List<Integer> allowTags;


  public static WxCpAgent fromJson(String json) {
    return WxCpGsonBuilder.create().fromJson(json, WxCpAgent.class);
  }

  public String toJson() {
    return WxCpGsonBuilder.create().toJson(this);
  }

}
