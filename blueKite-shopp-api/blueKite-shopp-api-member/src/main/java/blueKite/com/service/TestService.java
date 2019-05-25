package blueKite.com.service;


import org.springframework.web.bind.annotation.RequestMapping;

import blueKite.com.base.baseEntity.BaseResponse;

@RequestMapping("/test")
public interface TestService {
	
	@RequestMapping("/getString")
	public BaseResponse getStringById(Integer id);
	
	@RequestMapping("/setValue")
	public BaseResponse setRedisValue(String key, String value);

	@RequestMapping("/getValue")
	public BaseResponse getRedisValue(String key);
}
