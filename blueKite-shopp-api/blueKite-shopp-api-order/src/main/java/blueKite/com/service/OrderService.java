package blueKite.com.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import blueKite.com.base.baseEntity.BaseResponse;

@RequestMapping("/order")
public interface OrderService {
	
	@RequestMapping("/updateOrderState")
	public BaseResponse updateOrderState(@RequestParam("orderId") String orderId, @RequestParam("payId") String payId, @RequestParam("isPay") Integer isPay);

}
