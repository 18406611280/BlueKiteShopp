package blueKite.com.handler;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import blueKite.com.base.baseUtils.HttpClientUtil;
import blueKite.com.builder.TextBuilder;
import blueKite.com.util.JsonUtils;

import java.util.Map;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Component
public class MsgHandler extends AbstractHandler {
	
	private static final String REQEST_HTTP = "http://api.qingyunke.com/api.php?key=free&appid=0&msg=";

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) {

        if (!wxMessage.getMsgType().equals(WxConsts.XML_MSG_EVENT)) {
            //TODO 可以选择将消息保存到本地
        }

        //当用户输入关键词如“你好”，“客服”等，并且有客服在线时，把消息转发给在线客服
        try {
            if (StringUtils.startsWithAny(wxMessage.getContent(), "你好", "客服")
                    && weixinService.getKefuService().kfOnlineList()
                    .getKfOnlineList().size() > 0) {
                return WxMpXmlOutMessage.TRANSFER_CUSTOMER_SERVICE()
                        .fromUser(wxMessage.getToUser())
                        .toUser(wxMessage.getFromUser()).build();
            }
        } catch (WxErrorException e) {
            e.printStackTrace();
        }

        //TODO 组装回复消息
        String responseText = "";
        //微信推送过来的消息
        String content = wxMessage.getContent();
        String msgType = wxMessage.getMsgType();
		switch (msgType) {
		case "text":
			String requestResultJson = HttpClientUtil.doGet(REQEST_HTTP + content);
			JSONObject jsonObject = JSONObject.parseObject(requestResultJson);
			String result = jsonObject.getString("result");
			if (result.equals("0")) {
				responseText = jsonObject.getString("content");
			} else {
				responseText = "我也不知道回答什么！";
			}
			break;

		default:
			break;
		}

        return new TextBuilder().build(responseText, wxMessage, weixinService);

    }

}
