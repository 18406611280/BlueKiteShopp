package blueKite.com.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import blueKite.com.service.UserService;

@FeignClient("member")
@Component
public interface FeignUserService extends UserService {

}
