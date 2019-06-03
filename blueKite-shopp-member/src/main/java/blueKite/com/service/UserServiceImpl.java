package blueKite.com.service;

import java.util.Date;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;
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
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class UserServiceImpl extends BaseService implements UserService{
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RegistMailProducer mailProducer;
	
	@Value("${email.queue}")
	private String EmailQueue;
	
	@Override
	@Transactional
	public BaseResponse regist(@RequestBody UserEntity user) {
		String password = user.getPassword();
		if(password==null) {
			return setResultMsgError("密碼不能為空");
		}
		String newPass = MD5Util.MD5(password);
		user.setPassword(newPass);
		user.setCreated(new Date());
		user.setUpdated(new Date());
		Integer insertUser = userDao.insertUser(user);
		if(insertUser < 0) {
			//手动事务回滚
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return setResultMsgError("注册失败!");
		}
		log.info("用户{}注册成功!",user.getUsername());
		String email = user.getEmail();
		String emailJson = setEmailJson(email);
		sendEamil(emailJson);
		return setResultMsgSuccess("注册用户成功!");
	}

	private void sendEamil(String emailJson) {
		ActiveMQQueue activeMQQueue = new ActiveMQQueue(EmailQueue);
		mailProducer.sendMailMessage(activeMQQueue, emailJson);
	}

	private String setEmailJson(String email) {
		JSONObject root = new JSONObject();
		JSONObject header = new JSONObject();
		header.put("interfaceType", "messEmail");
		JSONObject content = new JSONObject();
		content.put("email", email);
		root.put("header", header);
		root.put("content", content);
		return root.toJSONString();
	}

	@Override
	public BaseResponse getUserById(Integer id) {
		UserEntity user = userDao.findById(id);
		if(user == null) {
			return setResultMsgError("获取用户失败");
		}
		return setResultDataSuccess(user);
	}


	@Override
	public BaseResponse login(@RequestBody UserEntity user) {
		String password = user.getPassword();
		if(password ==null) {
			return setResultMsgError("密码不能为空!");
		}
		String newPassword = MD5Util.MD5(password);
		String username = user.getUsername();
		UserEntity userEntity = userDao.login(username, newPassword);
		if(userEntity==null) {
			return setResultMsgError("账号或者密码错误!");
		}
		Integer userId = userEntity.getId();
		String token = TokenUtils.getToken();
		baseRedisService.setString(token, String.valueOf(userId), Constants.REDIS_TOKEN_TIME);
		JSONObject Token = new JSONObject();
		Token.put(Constants.TOKEN, token);
		return setResultDataSuccess(Token);
	}

	@Override
	public BaseResponse getUserByToken(String token) {
		if(StringUtils.isEmpty(token)) {
			return setResultMsgError("token不能为空!");
		}
		String userId = (String) baseRedisService.getString(token);
		if(StringUtils.isEmpty(userId)) {
			return setResultMsgError("token失效或者过期!");
		}
		Integer userID = Integer.parseInt(userId);
		UserEntity user = userDao.findById(userID);
		if(user==null) {
			return setResultMsgError("查不到用户!");
		}
		user.setPassword(null);
		return setResultDataSuccess(user);
	}


}
