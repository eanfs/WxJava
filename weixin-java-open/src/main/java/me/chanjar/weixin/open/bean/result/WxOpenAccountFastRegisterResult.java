package me.chanjar.weixin.open.bean.result;

import lombok.Data;
import me.chanjar.weixin.open.bean.auth.WxOpenAuthorizationInfo;

import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="https://github.com/007gzs">007</a>
 */
@Data
public class WxOpenAccountFastRegisterResult implements Serializable {
  private static final long serialVersionUID = 2394736235020206855L;

  private String appId;
  private String authorizationCode;
  private String isWxVerifySucc;
  private String isLinkSucc;
}
