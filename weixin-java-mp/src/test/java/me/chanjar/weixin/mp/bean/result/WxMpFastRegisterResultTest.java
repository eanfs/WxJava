package me.chanjar.weixin.mp.bean.result;

import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * <pre>
 * Created by Binary Wang on 2017-7-8.
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 * </pre>
 */
public class WxMpFastRegisterResultTest {
  @Test
  public void testFromJson() throws Exception {
    String json = "{\"errcode\":0,\"errmsg\":\"ok\",\"appid\":\"wxe2240df33a2cd06a\",\"authorization_code\":\"queryauthcode@@@158SLrxEPzAm0Yqx2l1vcfeGX-M1W9ePs1M3ViYp2S1ccewHOWYyurqZtRyPNGzaFMDHRY6zAdqYUoztXXhFhw\",\"is_wx_verify_succ\":true,\"is_link_succ\":true}";

    WxMpFastRegisterResult wxMpFastRegisterResult = WxMpFastRegisterResult.fromJson(json);

    assertNotNull(wxMpFastRegisterResult);
    assertNotNull(wxMpFastRegisterResult.getAppId());
    assertNotNull(wxMpFastRegisterResult.getAuthorizationCode());
    assertNotNull(wxMpFastRegisterResult.getIsWxVerifySucc());
    assertNotNull(wxMpFastRegisterResult.getIsLinkSucc());

    System.out.println(wxMpFastRegisterResult);
  }

}
