package me.chanjar.weixin.cp.bean;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.Data;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.cp.bean.article.MpContentItem;
import me.chanjar.weixin.cp.bean.article.MpnewsArticle;
import me.chanjar.weixin.cp.bean.article.NewArticle;
import me.chanjar.weixin.cp.bean.messagebuilder.*;
import me.chanjar.weixin.cp.util.json.WxCpGsonBuilder;
import me.chanjar.weixin.common.api.WxConsts.KefuMsgType;
import me.chanjar.weixin.cp.bean.article.MpnewsArticle;
import me.chanjar.weixin.cp.bean.article.NewArticle;
import me.chanjar.weixin.cp.bean.messagebuilder.*;
import me.chanjar.weixin.cp.bean.taskcard.TaskCardButton;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 消息.
 *
 * @author Daniel Qian
 */
@Data
public class WxCpMessage implements Serializable {
  private static final long serialVersionUID = -2082278303476631708L;

  private String toUser;
  private String toParty;
  private String toTag;
  private Integer agentId;
  private String msgType;
  private String content;
  private String mediaId;
  private String thumbMediaId;
  private String title;
  private String description;
  private String appId;
  private String page;
  private Boolean emphasisFirstItem;
  private String musicUrl;
  private String hqMusicUrl;
  private String safe;
  private String url;
  private String btnTxt;
  private List<NewArticle> articles = new ArrayList<>();
  private List<MpnewsArticle> mpnewsArticles = new ArrayList<>();
  private List<MpContentItem> contentItems = new ArrayList<>();

  /**
   * 任务卡片特有的属性
   */
  private String taskId;
  private List<TaskCardButton> taskButtons = new ArrayList<>();

  /**
   * 获得文本消息builder.
   */
  public static TextBuilder TEXT() {
    return new TextBuilder();
  }

  /**
   * 获得文本卡片消息builder.
   */
  public static TextCardBuilder TEXTCARD() {
    return new TextCardBuilder();
  }

  /**
   * 获得图片消息builder.
   */
  public static ImageBuilder IMAGE() {
    return new ImageBuilder();
  }

  /**
   * 获得语音消息builder.
   */
  public static VoiceBuilder VOICE() {
    return new VoiceBuilder();
  }

  /**
   * 获得视频消息builder.
   */
  public static VideoBuilder VIDEO() {
    return new VideoBuilder();
  }

  /**
   * 获得图文消息builder.
   */
  public static NewsBuilder NEWS() {
    return new NewsBuilder();
  }

  /**
   * 获得mpnews图文消息builder.
   */
  public static MpnewsBuilder MPNEWS() {
    return new MpnewsBuilder();
  }

  /**
   * 获得markdown消息builder.
   */
  public static MarkdownMsgBuilder MARKDOWN() {
    return new MarkdownMsgBuilder();
  }

  /**
   * 获得文件消息builder.
   */
  public static FileBuilder FILE() {
    return new FileBuilder();
  }

  /**
   * 获得小程序消息 builder.
   */
  public static MiniAppNoticeBuilder MPNOTICE() {
    return new MiniAppNoticeBuilder();
  }

  /*
   * 获得任务卡片消息builder.
   */
  public static TaskCardBuilder TASKCARD() {
    return new TaskCardBuilder();
  }


  /**
   * <pre>
   * 请使用
   * {@link WxConsts.KefuMsgType#TEXT}
   * {@link WxConsts.KefuMsgType#IMAGE}
   * {@link WxConsts.KefuMsgType#VOICE}
   * {@link WxConsts.KefuMsgType#MUSIC}
   * {@link WxConsts.KefuMsgType#VIDEO}
   * {@link WxConsts.KefuMsgType#NEWS}
   * {@link WxConsts.KefuMsgType#MPNEWS}
   * {@link WxConsts.KefuMsgType#MINIPROGRAMNOTICE}
   * {@link KefuMsgType#MARKDOWN}
   * {@link KefuMsgType#TASKCARD}
   * </pre>
   *
   * @param msgType 消息类型
   */
  public void setMsgType(String msgType) {
    this.msgType = msgType;
  }

  public String toJson() {
    JsonObject messageJson = new JsonObject();
    if (this.getAgentId() != null) {
      messageJson.addProperty("agentid", this.getAgentId());
    }

    if (StringUtils.isNotBlank(this.getToUser())) {
      messageJson.addProperty("touser", this.getToUser());
    }

    messageJson.addProperty("msgtype", this.getMsgType());

    if (StringUtils.isNotBlank(this.getToParty())) {
      messageJson.addProperty("toparty", this.getToParty());
    }

    if (StringUtils.isNotBlank(this.getToTag())) {
      messageJson.addProperty("totag", this.getToTag());
    }

    this.handleMsgType(messageJson);

    if (StringUtils.isNotBlank(this.getSafe())) {
      messageJson.addProperty("safe", this.getSafe());
    }

    return messageJson.toString();
  }

  private void handleMsgType(JsonObject messageJson) {
    switch (this.getMsgType()) {
      case KefuMsgType.TEXT: {
        JsonObject text = new JsonObject();
        text.addProperty("content", this.getContent());
        messageJson.add("text", text);
        break;
      }
      case KefuMsgType.MARKDOWN: {
        JsonObject text = new JsonObject();
        text.addProperty("content", this.getContent());
        messageJson.add("markdown", text);
        break;
      }
      case KefuMsgType.TEXTCARD: {
        JsonObject text = new JsonObject();
        text.addProperty("title", this.getTitle());
        text.addProperty("description", this.getDescription());
        text.addProperty("url", this.getUrl());
        text.addProperty("btntxt", this.getBtnTxt());
        messageJson.add("textcard", text);
        break;
      }
      case KefuMsgType.IMAGE: {
        JsonObject image = new JsonObject();
        image.addProperty("media_id", this.getMediaId());
        messageJson.add("image", image);
        break;
      }
      case KefuMsgType.FILE: {
        JsonObject image = new JsonObject();
        image.addProperty("media_id", this.getMediaId());
        messageJson.add("file", image);
        break;
      }
      case KefuMsgType.VOICE: {
        JsonObject voice = new JsonObject();
        voice.addProperty("media_id", this.getMediaId());
        messageJson.add("voice", voice);
        break;
      }
      case KefuMsgType.VIDEO: {
        JsonObject video = new JsonObject();
        video.addProperty("media_id", this.getMediaId());
        video.addProperty("thumb_media_id", this.getThumbMediaId());
        video.addProperty("title", this.getTitle());
        video.addProperty("description", this.getDescription());
        messageJson.add("video", video);
        break;
      }
      case KefuMsgType.NEWS: {
        JsonObject newsJsonObject = new JsonObject();
        JsonArray articleJsonArray = new JsonArray();
        for (NewArticle article : this.getArticles()) {
          JsonObject articleJson = new JsonObject();
          articleJson.addProperty("title", article.getTitle());
          articleJson.addProperty("description", article.getDescription());
          articleJson.addProperty("url", article.getUrl());
          articleJson.addProperty("picurl", article.getPicUrl());
          articleJsonArray.add(articleJson);
        }
        newsJsonObject.add("articles", articleJsonArray);
        messageJson.add("news", newsJsonObject);
        break;
      }
      case KefuMsgType.MPNEWS: {
        JsonObject newsJsonObject = new JsonObject();
        if (this.getMediaId() != null) {
          newsJsonObject.addProperty("media_id", this.getMediaId());
        } else {
          JsonArray articleJsonArray = new JsonArray();
          for (MpnewsArticle article : this.getMpnewsArticles()) {
            JsonObject articleJson = new JsonObject();
            articleJson.addProperty("title", article.getTitle());
            articleJson.addProperty("thumb_media_id", article.getThumbMediaId());
            articleJson.addProperty("author", article.getAuthor());
            articleJson.addProperty("content_source_url", article.getContentSourceUrl());
            articleJson.addProperty("content", article.getContent());
            articleJson.addProperty("digest", article.getDigest());
            articleJson.addProperty("show_cover_pic", article.getShowCoverPic());
            articleJsonArray.add(articleJson);
          }

          newsJsonObject.add("articles", articleJsonArray);
        }
        messageJson.add("mpnews", newsJsonObject);
        break;
      }
      case KefuMsgType.TASKCARD: {
        JsonObject text = new JsonObject();
        text.addProperty("title", this.getTitle());
        text.addProperty("description", this.getDescription());

        if (StringUtils.isNotBlank(this.getUrl())) {
          text.addProperty("url", this.getUrl());
        }

        text.addProperty("task_id", this.getTaskId());

        JsonArray buttonJsonArray = new JsonArray();
        for (TaskCardButton button : this.getTaskButtons()) {
          JsonObject buttonJson = new JsonObject();
          buttonJson.addProperty("key", button.getKey());
          buttonJson.addProperty("name", button.getName());

          if (StringUtils.isNotBlank(button.getReplaceName())) {
            buttonJson.addProperty("replace_name", button.getReplaceName());
          }

          if (StringUtils.isNotBlank(button.getColor())) {
            buttonJson.addProperty("color", button.getColor());
          }

          if (button.getBold() != null) {
            buttonJson.addProperty("is_bold", button.getBold());
          }

          buttonJsonArray.add(buttonJson);
        }
        text.add("btn", buttonJsonArray);

        messageJson.add("taskcard", text);
        break;
      }
      default: {
        // do nothing
      }
    }
  }

}
