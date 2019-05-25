package blueKite.com.controller;

import java.util.LinkedHashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;
import blueKite.com.base.baseEntity.BaseResponse;
import blueKite.com.base.baseEntity.Constants;
import blueKite.com.base.baseUtils.CookieUtil;
import blueKite.com.entity.UserEntity;
import blueKite.com.feign.FeignQQLoginService;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.util.StringUtil;

@Controller
@Slf4j
public class QQLoginController {
	
	@Autowired
	private FeignQQLoginService qqLoginService;
	
	private String INDEX = "redirect:/";
	private String QQRELATION = "qqrelation";
	private String LOGIN = "login";
	private String ERROR = "error";
	
	//获取授权码后的回调地址,然后获取openID进行登录
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/qqLoginCallBack", method = RequestMethod.GET)
	public String qqCallBack(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) throws QQConnectException {
		AccessToken accessTokenOj = new Oauth().getAccessTokenByRequest(request);
		if (accessTokenOj == null) {
			request.setAttribute("error", "QQ授权失败");
			return ERROR;
		}
		String accessToken = accessTokenOj.getAccessToken();
		if (accessToken == null) {
			request.setAttribute("error", "accessToken为空");
			return ERROR;
		}
		// 3.使用accessToken获取openid
		OpenID openidOj = new OpenID(accessToken);
		String openId = openidOj.getUserOpenID();
		UserInfo qzoneUserInfo = new UserInfo(accessToken, openId);
		UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
		String nickName = userInfoBean.getNickname();
		httpSession.setAttribute("nickName", nickName);
		String gender = userInfoBean.getGender();
		httpSession.setAttribute("gender", gender);
	    String avatarUrl = userInfoBean.getAvatar().getAvatarURL30(); 
	    httpSession.setAttribute("avatarUrl", avatarUrl);
	    BaseResponse baseResponse = qqLoginService.qqLoginByOpenId(openId);
		Integer code = baseResponse.getCode();
		if(code.equals(Constants.HTTP_RES_CODE_201)) {
			httpSession.setAttribute("openId", openId);
			return QQRELATION;
		}
		LinkedHashMap map = (LinkedHashMap) baseResponse.getData();
		String token = (String) map.get("token");
		CookieUtil.addCookie(response, Constants.TOKEN, token, Constants.COOKIE_TOKEN_TIME);
		return INDEX;
	}
	
	@RequestMapping(value ="/locaQQLogin", method = RequestMethod.GET)
	public String qqLogin(HttpServletRequest request) throws QQConnectException {
		//1.生成授权码连接
		String authorizeURL = new Oauth().getAuthorizeURL(request);
		log.info("授权码地址:{}",authorizeURL);
		return "redirect:"+authorizeURL;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/qqRelatedLogin" ,method = RequestMethod.POST)
	public String qqRelatedLogin(UserEntity user, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		String password = user.getPassword();
		if(StringUtil.isEmpty(password)) {
			request.setAttribute("error", "密码不能为空!");
			return LOGIN;
		}
		String openId = (String) httpSession.getAttribute("openId");
		user.setOpenId(openId);
		BaseResponse baseResponse = qqLoginService.relatedLogin(user);
		Integer code = baseResponse.getCode();
		if(!code.equals(Constants.HTTP_RES_CODE_200)) {
			request.setAttribute("error", "关联登录失败!");
			log.info("关联登录失败!");
			return LOGIN;
		}
		LinkedHashMap map = (LinkedHashMap) baseResponse.getData();
		String token = (String) map.get("token");
		CookieUtil.addCookie(response, Constants.TOKEN, token, Constants.COOKIE_TOKEN_TIME);
		return INDEX;
	}
	

}
