package blueKite.com.service;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import blueKite.com.base.baseEntity.BaseResponse;
import blueKite.com.entity.UserEntity;

@RequestMapping("/member")
public interface UserService {
	
	@RequestMapping("/registUser")
	public BaseResponse regist(@RequestBody UserEntity user);//表示用json传参
	
	@RequestMapping("/login")
	public BaseResponse login(@RequestBody UserEntity user);
	
	@RequestMapping("/getUserById")
	public BaseResponse getUserById(@RequestParam("id") Integer id);
	
	@RequestMapping("/getUserByToken")
	public BaseResponse getUserByToken(@RequestParam("token") String token);
	
}
