package blueKite.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import blueKite.com.base.baseEntity.BaseResponse;
import blueKite.com.base.baseEntity.Constants;
import blueKite.com.base.baseService.BaseService;
import blueKite.com.base.baseUtils.MD5Util;
import blueKite.com.base.baseUtils.TokenUtils;
import blueKite.com.dao.UserDao;
import blueKite.com.entity.UserEntity;
import tk.mybatis.mapper.util.StringUtil;

@RestController
public class QQLoginServiceImpl extends BaseService implements QQloginService{
	
	@Autowired
	private UserDao userDao;

	@Override
	public BaseResponse qqLoginByOpenId(String openId) {
		if(StringUtil.isEmpty(openId)) {
			return setResultMsgError("openId为空!");
		}
		UserEntity userEntity = userDao.findByOpenId(openId);
		if(userEntity == null) {
			return setResultResMsgError(Constants.HTTP_RES_CODE_201, "未关联用户!");
		}
		Integer userId = userEntity.getId();
		String token = TokenUtils.getToken();
		baseRedisService.setString(token, String.valueOf(userId), Constants.REDIS_TOKEN_TIME);
		JSONObject Token = new JSONObject();
		Token.put(Constants.TOKEN, token);
		return setResultDataSuccess(Token);
	}

	@Override
	public BaseResponse relatedLogin(@RequestBody UserEntity user) {
		String openId = user.getOpenId();
		if(openId ==null) {
			return setResultMsgError("openId为空!");
		}
		String password = user.getPassword();
		String newPassword = MD5Util.MD5(password);
		String username = user.getUsername();
		UserEntity userEntity = userDao.login(username, newPassword);
		if(userEntity==null) {
			return setResultMsgError("账号或者密码错误!");
		}
		userEntity.setOpenId(openId);
		Integer code = userDao.updateOpenId(userEntity);
		if(code < 0) {
			return setResultMsgError("关联QQ账号失败!");
		}
		Integer userId = userEntity.getId();
		String token = TokenUtils.getToken();
		baseRedisService.setString(token, String.valueOf(userId), Constants.REDIS_TOKEN_TIME);
		JSONObject Token = new JSONObject();
		Token.put(Constants.TOKEN, token);
		return setResultDataSuccess(Token);
	}

}
