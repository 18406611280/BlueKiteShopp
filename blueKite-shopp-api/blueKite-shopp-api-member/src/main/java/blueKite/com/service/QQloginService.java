package blueKite.com.service;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import blueKite.com.base.baseEntity.BaseResponse;
import blueKite.com.entity.UserEntity;

@RequestMapping("/member")
public interface QQloginService {
	
	@RequestMapping("/qqLoginByOpenId")
	public BaseResponse qqLoginByOpenId(@RequestParam("openId") String openId);
	
	//关联账号登录
	@RequestMapping("/relatedLogin")
	public BaseResponse relatedLogin(@RequestBody UserEntity user);

}
