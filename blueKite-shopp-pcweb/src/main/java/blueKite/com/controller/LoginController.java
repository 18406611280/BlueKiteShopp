package blueKite.com.controller;

import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import blueKite.com.base.baseEntity.BaseResponse;
import blueKite.com.base.baseEntity.Constants;
import blueKite.com.base.baseUtils.CookieUtil;
import blueKite.com.entity.UserEntity;
import blueKite.com.feign.FeignUserService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class LoginController {
	
	private String LOGIN = "login";
	private String INDEX = "redirect:/";
	@Autowired
	private FeignUserService userService;
	
	@RequestMapping(value = "/loginUI", method = RequestMethod.GET)
	public String loginUI() {
		return LOGIN;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(UserEntity user,HttpServletRequest request, HttpServletResponse response) {
		if(user.getUsername() == null) {
			request.setAttribute("error", "账号不能为空");
			return LOGIN;
		}
		if(user.getPassword() == null) {
			request.setAttribute("error", "密码不能为空");
			return LOGIN;
		}
		BaseResponse baseResponse = userService.login(user);
		int code = baseResponse.getCode();
		if(code != Constants.HTTP_RES_CODE_200) {
			request.setAttribute("error", "账号或者密码错误!");
			return LOGIN;
		}
		LinkedHashMap map = (LinkedHashMap) baseResponse.getData();
		String token = (String) map.get("token");
		CookieUtil.addCookie(response, Constants.TOKEN, token, Constants.COOKIE_TOKEN_TIME);
		log.info("获取token成功:{}",token);
		return INDEX;
	}

}
