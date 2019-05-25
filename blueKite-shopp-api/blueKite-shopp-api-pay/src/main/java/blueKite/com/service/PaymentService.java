package blueKite.com.service;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import blueKite.com.base.baseEntity.BaseResponse;
import blueKite.com.entity.PaymentInfo;

@RequestMapping("/pay")
public interface PaymentService {
	
	@RequestMapping("/createPayToken")
	public BaseResponse createPayToken(@RequestBody PaymentInfo paymentInfo);
	
	@RequestMapping("/getPaymentByToken")
	public BaseResponse getPayHtmlByToken(@RequestParam("token") String token);
	
}
