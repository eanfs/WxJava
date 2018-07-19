package me.chanjar.weixin.mp.util.json;

import com.google.gson.*;
import me.chanjar.weixin.common.util.json.GsonHelper;
import me.chanjar.weixin.mp.bean.result.WxMpFastRegisterResult;

import java.lang.reflect.Type;

public class WxMpFastRegisterAdapter implements JsonDeserializer<WxMpFastRegisterResult> {

  @Override
  public WxMpFastRegisterResult deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    JsonObject jsonObject = json.getAsJsonObject();
    WxMpFastRegisterResult fastRegisterResult = new WxMpFastRegisterResult();

    if (jsonObject.get("appid") != null && !jsonObject.get("appid").isJsonNull()) {
      fastRegisterResult.setAppId(GsonHelper.getAsString(jsonObject.get("appid")));
    }
    if (jsonObject.get("authorization_code") != null && !jsonObject.get("authorization_code").isJsonNull()) {
      fastRegisterResult.setAuthorizationCode(GsonHelper.getAsString(jsonObject.get("authorization_code")));
    }
    if (jsonObject.get("is_wx_verify_succ") != null && !jsonObject.get("is_wx_verify_succ").isJsonNull()) {
      fastRegisterResult.setIsWxVerifySucc(GsonHelper.getAsString(jsonObject.get("is_wx_verify_succ")));
    }
    if (jsonObject.get("is_link_succ") != null && !jsonObject.get("is_link_succ").isJsonNull()) {
      fastRegisterResult.setIsLinkSucc(GsonHelper.getAsString(jsonObject.get("is_link_succ")));
    }
    return fastRegisterResult;
  }

}
