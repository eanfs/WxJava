package me.chanjar.weixin.cp.message;

import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.WxCpSuiteService;
import me.chanjar.weixin.cp.bean.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class WxCpSuiteMessageRouter extends WxCpMessageRouter {
  private WxCpSuiteService wxCpSuiteService;

  public WxCpSuiteMessageRouter(WxCpSuiteService wxCpSuiteService) {
    super(null);
    this.wxCpSuiteService = wxCpSuiteService;
  }

  public WxCpXmlOutMessage route(final WxCpXmlMessage wxMessage, String authCorpId) {
    return route(wxMessage, new HashMap<String, Object>(), authCorpId);
  }

  public WxCpXmlOutMessage route(final WxCpXmlMessage wxMessage, final Map<String, Object> context, String authCorpId) {
    WxCpService wxCpService = wxCpSuiteService.getWxCpSuiteComponentService().getWxCpServiceByAuthCorpId(authCorpId);
    return route(wxMessage, context, wxCpService);
  }

  public WxCpXmlOutMessage route(final WxCpXmlMessage wxMessage, WxCpSuiteService wxCpSuiteService) {
    return route(wxMessage, new HashMap<String, Object>(), wxCpSuiteService);
  }
  /**
   * 处理微信消息
   *
   * @param wxMessage
   * @param context
   */
  public WxCpXmlOutMessage route(final WxCpXmlMessage wxMessage, final Map<String, Object> context, WxCpSuiteService wxCpSuiteService) {

    final WxCpSuiteService mpService = wxCpSuiteService;
    if (isDuplicateMessage(wxMessage)) {
      // 如果是重复消息，那么就不做处理
      return null;
    }

    final List<WxCpMessageRouterRule> matchRules = new ArrayList<>();
    // 收集匹配的规则
    for (final WxCpMessageRouterRule rule : this.getRules()) {
      if (rule.test(wxMessage)) {
        matchRules.add(rule);
        if (!rule.isReEnter()) {
          break;
        }
      }
    }

    if (matchRules.size() == 0) {
      return null;
    }

    WxCpXmlOutMessage res = null;
    final List<Future> futures = new ArrayList<>();
    for (final WxCpMessageRouterRule rule : matchRules) {
      // 返回最后一个非异步的rule的执行结果
      if (rule.isAsync()) {
        futures.add(
          this.executorService.submit(new Runnable() {
            @Override
            public void run() {
              rule.service(wxMessage, context, mpService, sessionManager, exceptionHandler);
            }
          })
        );
      } else {
        res = rule.service(wxMessage, context, mpService, this.sessionManager, this.exceptionHandler);
        // 在同步操作结束，session访问结束
        this.log.debug("End session access: async=false, sessionId={}", wxMessage.getFromUserName());
        sessionEndAccess(wxMessage);
      }
    }

    if (futures.size() > 0) {
      this.executorService.submit(new Runnable() {
        @Override
        public void run() {
          for (Future future : futures) {
            try {
              future.get();
              log.debug("End session access: async=true, sessionId={}", wxMessage.getFromUserName());
              // 异步操作结束，session访问结束
              sessionEndAccess(wxMessage);
            } catch (InterruptedException e) {
              log.error("Error happened when wait task finish", e);
              Thread.currentThread().interrupt();
            } catch (ExecutionException e) {
              log.error("Error happened when wait task finish", e);
            }
          }
        }
      });
    }
    return res;
  }

}
