package blueKite.com.adapter.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import blueKite.com.adapter.MessageAdapter;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EmailMessageAdapter implements MessageAdapter{

	@Value("${msg.subject}")
	private String subject;
	@Value("${msg.text}")
	private String text;
	@Value("${spring.mail.username}")
	private String fromEmail;
	@Autowired
	private JavaMailSender mailSender; // 自动注入的Bean

	@Override
	public void sendMsg(JSONObject body) {
		String mail = body.getString("email");
		if (StringUtils.isEmpty(mail)) {
			return;
		}
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		// 发送
		simpleMailMessage.setFrom(fromEmail);
		simpleMailMessage.setTo(mail);
		// 标题
		simpleMailMessage.setSubject(subject);
		// 内容
		simpleMailMessage.setText(text.replace("{}", mail));
		mailSender.send(simpleMailMessage);
		log.info("发送email邮件成功,邮箱:{}",mail);
	}
		

}
