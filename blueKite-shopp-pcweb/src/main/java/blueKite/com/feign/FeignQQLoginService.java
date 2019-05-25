package blueKite.com.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

import blueKite.com.service.QQloginService;

@FeignClient("member")
public interface FeignQQLoginService extends QQloginService{

}
