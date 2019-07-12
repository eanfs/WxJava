package me.chanjar.weixin.cp.bean.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.ToString;
import me.chanjar.weixin.common.util.xml.XStreamCDataConverter;
import me.chanjar.weixin.cp.config.WxCpSuiteConfigStorage;
import me.chanjar.weixin.cp.util.crypto.WxCpCryptUtil;
import me.chanjar.weixin.cp.util.xml.XStreamTransformer;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

/**
 * <pre>
 * 微信推送过来的消息，也是同步回复给用户的消息，xml格式
 * 相关字段的解释看微信开发者文档：
 * http://mp.weixin.qq.com/wiki/index.php?title=接收普通消息
 * http://mp.weixin.qq.com/wiki/index.php?title=接收事件推送
 * http://mp.weixin.qq.com/wiki/index.php?title=接收语音识别结果
 * </pre>
 *
 * @author Daniel Qian
 */
@XStreamAlias("xml")
public class WxCpSuiteXmlMessage implements Serializable {

  public final Logger log = LoggerFactory.getLogger(WxCpSuiteXmlMessage.class);

  private static final long serialVersionUID = -1042994982179476410L;

  @XStreamAlias("MsgId")
  private Long msgId;

  ///////////////////////
  // 以下都是微信推送过来的消息的xml的element所对应的属性
  ///////////////////////

  @XStreamAlias("SuiteId")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String suiteId;

  @XStreamAlias("InfoType")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String infoType;

  @XStreamAlias("SuiteTicket")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String suiteTicket;

  @XStreamAlias("AuthCode")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String authCode;

  @XStreamAlias("AuthCorpId")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String authCorpId;

  @XStreamAlias("TimeStamp")
  private Long timeStamp;

  @XStreamAlias("UserID")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String userId;

  @XStreamAlias("Name")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String name;

  @XStreamAlias("Department")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String department;

  @XStreamAlias("Mobile")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String mobile;

  @XStreamAlias("Position")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String position;

  @XStreamAlias("Gender")
  private String gender;

  @XStreamAlias("Email")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String email;

  @XStreamAlias("Avatar")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String avatar;

  @XStreamAlias("Alias")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String alias;

  @XStreamAlias("Telephone")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String telephone;

  public static WxCpSuiteXmlMessage fromXml(String xml) {
    //修改微信变态的消息内容格式，方便解析
    return XStreamTransformer.fromXml(WxCpSuiteXmlMessage.class, xml);
  }

  protected static WxCpSuiteXmlMessage fromXml(InputStream is) {
    return XStreamTransformer.fromXml(WxCpSuiteXmlMessage.class, is);
  }

  /**
   * 从加密字符串转换.
   */
  public static WxCpSuiteXmlMessage fromEncryptedXml(
    String encryptedXml,
    WxCpSuiteConfigStorage wxCpConfigStorage,
    String timestamp, String nonce, String msgSignature) {
    WxCpCryptUtil cryptUtil = new WxCpCryptUtil(wxCpConfigStorage);
    String plainText = cryptUtil.decrypt(msgSignature, timestamp, nonce, encryptedXml);
    return fromXml(plainText);
  }

  public static WxCpSuiteXmlMessage fromEncryptedXml(
    InputStream is,
    WxCpSuiteConfigStorage wxCpConfigStorage,
    String timestamp, String nonce, String msgSignature) {
    try {
      return fromEncryptedXml(IOUtils.toString(is, "UTF-8"), wxCpConfigStorage, timestamp, nonce, msgSignature);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }


  public Long getMsgId() {
    return msgId;
  }

  public void setMsgId(Long msgId) {
    this.msgId = msgId;
  }

  public String getSuiteId() {
    return suiteId;
  }

  public void setSuiteId(String suiteId) {
    this.suiteId = suiteId;
  }

  public String getInfoType() {
    return infoType;
  }

  public void setInfoType(String infoType) {
    this.infoType = infoType;
  }

  public String getSuiteTicket() {
    return suiteTicket;
  }

  public void setSuiteTicket(String suiteTicket) {
    this.suiteTicket = suiteTicket;
  }

  public Long getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(Long timeStamp) {
    this.timeStamp = timeStamp;
  }

  public String getAuthCode() {
    return authCode;
  }

  public void setAuthCode(String authCode) {
    this.authCode = authCode;
  }

  public String getAuthCorpId() {
    return authCorpId;
  }

  public void setAuthCorpId(String authCorpId) {
    this.authCorpId = authCorpId;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDepartment() {
    return department;
  }

  public void setDepartment(String department) {
    this.department = department;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public String getAlias() {
    return alias;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }

  public String getTelephone() {
    return telephone;
  }

  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }
}

