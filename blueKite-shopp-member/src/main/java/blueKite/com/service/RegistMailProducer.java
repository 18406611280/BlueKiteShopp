package blueKite.com.service;

import javax.jms.Destination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class RegistMailProducer {
	
	@Autowired
	private JmsMessagingTemplate jmsTemplate;
	
	public void sendMailMessage(Destination destination, String json) {
		jmsTemplate.convertAndSend(destination, json);
	}

}
