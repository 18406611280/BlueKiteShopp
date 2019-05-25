package blueKite.com.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

import blueKite.com.service.AlipayCallBackService;

@FeignClient("pay")
public interface FeignAlipayCallBackService extends AlipayCallBackService{

}
