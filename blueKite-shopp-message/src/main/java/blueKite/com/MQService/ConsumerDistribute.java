package blueKite.com.MQService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.ctc.wstx.util.StringUtil;

import blueKite.com.adapter.MessageAdapter;
import blueKite.com.adapter.impl.EmailMessageAdapter;
import lombok.extern.slf4j.Slf4j;

//MQ消费者
@Component
@Slf4j
public class ConsumerDistribute {
	
	@Autowired
	private EmailMessageAdapter emailMessageAdapter;
	private MessageAdapter messageAdapter;
	
	@JmsListener(destination = "email_queue")
	public void distribute(String json) {
		if(StringUtils.isEmpty(json)) {
			return;
		}
		log.info("接收到消息信息:"+json);
		JSONObject jsonObj = JSONObject.parseObject(json);
		JSONObject header = jsonObj.getJSONObject("header");
		String interfaceType = header.getString("interfaceType");
		if("messEmail".equals(interfaceType)){
			messageAdapter = emailMessageAdapter;
		}
		if(messageAdapter ==null) {
			return;
		}
		JSONObject content = jsonObj.getJSONObject("content");
		messageAdapter.sendMsg(content);
	}

}
