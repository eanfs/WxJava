package me.chanjar.weixin.cp.bean;

import lombok.Data;
import me.chanjar.weixin.cp.util.json.WxCpGsonBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * "auth_info":
 * {
 * "agent" :
 * [
 * {
 * "agentid":1,
 * "name":"NAME",
 * "round_logo_url":"xxxxxx",
 * "square_logo_url":"yyyyyy",
 * "appid":1,
 * "privilege":
 * {
 * "level":1,
 * "allow_party":[1,2,3],
 * "allow_user":["zhansan","lisi"],
 * "allow_tag":[1,2,3],
 * "extra_party":[4,5,6],
 * "extra_user":["wangwu"],
 * "extra_tag":[4,5,6]
 * }
 * },
 * {
 * "agentid":2,
 * "name":"NAME2",
 * "round_logo_url":"xxxxxx",
 * "square_logo_url":"yyyyyy",
 * "appid":5
 * }
 * ]
 * },
 */
@Data
public class WxCpAgentAuthInfo implements Serializable {

  private static final long serialVersionUID = 1L;

  private List<WxCpAgentBasic> agent;

  public List<WxCpAgentBasic> getAgent() {
    return agent;
  }

  public void setAgent(List<WxCpAgentBasic> agent) {
    this.agent = agent;
  }


  public static WxCpAgentBasic fromJson(String json) {
    return WxCpGsonBuilder.create().fromJson(json, WxCpAgentBasic.class);
  }

  public String toJson() {
    return WxCpGsonBuilder.create().toJson(this);
  }
}
