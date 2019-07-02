package me.chanjar.weixin.cp.bean.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import me.chanjar.weixin.common.util.xml.XStreamCDataConverter;
import me.chanjar.weixin.cp.config.WxCpConfigStorage;
import me.chanjar.weixin.cp.util.crypto.WxCpCryptUtil;
import me.chanjar.weixin.cp.util.xml.XStreamTransformer;
import org.apache.commons.io.IOUtils;
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
public class SuiteTicketXmlMessage implements Serializable {

    public final Logger log = LoggerFactory.getLogger(SuiteTicketXmlMessage.class);

    private static final long serialVersionUID = -1042994982179476410L;

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


    public static SuiteTicketXmlMessage fromXml(String xml) {
        //修改微信变态的消息内容格式，方便解析
        return XStreamTransformer.fromXml(SuiteTicketXmlMessage.class, xml);
    }

    protected static SuiteTicketXmlMessage fromXml(InputStream is) {
        return XStreamTransformer.fromXml(SuiteTicketXmlMessage.class, is);
    }

    /**
     * 从加密字符串转换.
     */
    public static SuiteTicketXmlMessage fromEncryptedXml(
        String encryptedXml,
        WxCpConfigStorage wxCpConfigStorage,
        String timestamp, String nonce, String msgSignature) {
        WxCpCryptUtil cryptUtil = new WxCpCryptUtil(wxCpConfigStorage);
        String plainText = cryptUtil.decrypt(msgSignature, timestamp, nonce, encryptedXml);
        return fromXml(plainText);
    }

    public static SuiteTicketXmlMessage fromEncryptedXml(
        InputStream is,
        WxCpConfigStorage wxCpConfigStorage,
        String timestamp, String nonce, String msgSignature) {
        try {
            return fromEncryptedXml(IOUtils.toString(is, "UTF-8"), wxCpConfigStorage, timestamp, nonce, msgSignature);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
}

