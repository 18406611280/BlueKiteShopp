package blueKite.com.service;

import org.springframework.web.bind.annotation.RestController;
import blueKite.com.base.baseEntity.BaseResponse;
import blueKite.com.base.baseService.BaseService;
import lombok.extern.slf4j.Slf4j; 

@RestController
@Slf4j
public class TestServiceImpl extends BaseService implements TestService{
	
	@Override
	public BaseResponse getStringById(Integer id) {
		return setResultDataSuccess("I am iron Man");
	}

	@Override
	public BaseResponse setRedisValue(String key, String value) {
		try {
			baseRedisService.setString(key, value, 2000L);
			log.info(value+"成功存入redis!");
			return setResultSuccess();
		} catch (Exception e) {
			return setResultMsgError("保存redis失败");
		}
	}

	@Override
	public BaseResponse getRedisValue(String key) {
		String value = (String) baseRedisService.getString(key);
		return setResultDataSuccess(value);
	}

}
