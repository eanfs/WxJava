package me.chanjar.weixin.cp.bean.messagebuilder;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.cp.bean.WxCpMessage;
import me.chanjar.weixin.cp.bean.article.MpContentItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * mpnews类型的图文消息builder
 * <pre>
 *
 *     "appId": "wx123123123123123",
 *         "page": "pages/index?userid=zhangsan&orderid=123123123",
 *         "title": "会议室预订成功通知",
 *         "description": "4月27日 16:16",
 *         "emphasis_first_item": true,
 *
 * 用法:
 * WxCustomMessage m = WxCustomMessage.MPNEWS().addArticle(article).toUser(...).build();
 * </pre>
 *
 * @author lirichen
 */
public final class MiniAppNoticeBuilder extends BaseBuilder<MiniAppNoticeBuilder> {
  private List<MpContentItem> contentItem = new ArrayList<>();

  private String appId;

  private String page;

  private String title;

  private String description;

  private Boolean emphasisFirstItem;

  public MiniAppNoticeBuilder() {
    this.msgType = WxConsts.KefuMsgType.MINIPROGRAMNOTICE;
  }

  public MiniAppNoticeBuilder appid(String appid) {
    this.appId = appid;
    return this;
  }

  public MiniAppNoticeBuilder page(String page) {
    this.page = page;
    return this;
  }

  public MiniAppNoticeBuilder title(String title) {
    this.title = title;
    return this;
  }

  public MiniAppNoticeBuilder description(String description) {
    this.description = description;
    return this;
  }

  public MiniAppNoticeBuilder emphasisFirstItem(Boolean emphasisFirstItem) {
    this.emphasisFirstItem = emphasisFirstItem;
    return this;
  }


  public MiniAppNoticeBuilder addContentItem(MpContentItem... items) {
    Collections.addAll(this.contentItem, items);
    return this;
  }

  public MiniAppNoticeBuilder addContentItem(List<MpContentItem> items) {
    this.contentItem = items;
    return this;
  }

  public List<MpContentItem> getContentItem() {
    return contentItem;
  }

  public void setContentItem(List<MpContentItem> contentItem) {
    this.contentItem = contentItem;
  }

  public String getAppId() {
    return appId;
  }

  public void setAppId(String appId) {
    this.appId = appId;
  }

  public String getPage() {
    return page;
  }

  public void setPage(String page) {
    this.page = page;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Boolean getEmphasisFirstItem() {
    return emphasisFirstItem;
  }

  public void setEmphasisFirstItem(Boolean emphasisFirstItem) {
    this.emphasisFirstItem = emphasisFirstItem;
  }

  @Override
  public WxCpMessage build() {
    WxCpMessage m = super.build();
    m.setAppId(this.appId);
    m.setPage(this.page);
    m.setTitle(this.title);
    m.setDescription(this.description);
    m.setEmphasisFirstItem(this.emphasisFirstItem);
    m.setContentItems(this.contentItem);

    return m;
  }
}
