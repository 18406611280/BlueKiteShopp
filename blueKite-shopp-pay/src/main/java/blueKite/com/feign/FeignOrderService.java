package blueKite.com.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import blueKite.com.service.OrderService;

@FeignClient("order")
public interface FeignOrderService extends OrderService{

}
