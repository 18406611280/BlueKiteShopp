package blueKite.com.controller;

import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import blueKite.com.base.baseEntity.BaseResponse;
import blueKite.com.base.baseEntity.Constants;
import blueKite.com.base.baseUtils.CookieUtil;
import blueKite.com.feign.FeignUserService;
import tk.mybatis.mapper.util.StringUtil;

@Controller
public class IndexController {
	
	private String INDEX = "index";
	
	@Autowired
	private FeignUserService userService;
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/")
	public String index(HttpServletRequest request) {
		String token = CookieUtil.getUid(request, Constants.TOKEN);
		if(!StringUtil.isEmpty(token)) {
			BaseResponse baseResponse = userService.getUserByToken(token);
			int code = baseResponse.getCode();
			if(code == Constants.HTTP_RES_CODE_200) {
				LinkedHashMap map = (LinkedHashMap) baseResponse.getData();
				String username = (String) map.get("username");
				request.setAttribute("username", username);
			}
		}
		return INDEX;
	}

}
