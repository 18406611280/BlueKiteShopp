package blueKite.com.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

import blueKite.com.service.PaymentService;

@FeignClient("pay")
public interface FeignPayService extends PaymentService{

}
