package blueKite.com.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import blueKite.com.base.baseEntity.BaseResponse;
import blueKite.com.base.baseEntity.Constants;
import blueKite.com.entity.UserEntity;
import blueKite.com.feign.FeignUserService;

@Controller
public class UserController {
	
	private String REGIST = "register";
	private String LOGIN = "login";
	
	@Autowired
	private FeignUserService userService;
	
	@RequestMapping(value = "/registUI", method = RequestMethod.GET)
	public String registUI() {
		return REGIST;
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String regist(UserEntity user,HttpServletRequest request) {
		BaseResponse response = userService.regist(user);
		if(!response.getCode().equals(Constants.HTTP_RES_CODE_200)) {
			request.setAttribute("msg", "注册失败!");
			return REGIST;
		}
		return LOGIN;
	}
	

}
