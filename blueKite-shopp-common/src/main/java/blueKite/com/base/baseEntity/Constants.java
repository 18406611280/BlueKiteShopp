package blueKite.com.base.baseEntity;

public interface Constants {
	// 响应code
	String HTTP_RES_CODE_NAME = "code";
	// 响应msg
	String HTTP_RES_CODE_MSG = "msg";
	// 响应data
	String HTTP_RES_CODE_DATA = "data";
	// 响应请求成功
	String HTTP_SUCCESS = "success";
	// 系统错误
	String HTTP_FAIL = "fail";
	// 响应请求成功code
	Integer HTTP_RES_CODE_200 = 200;
	// 响应未关联
	Integer HTTP_RES_CODE_201 = 201;
	// 系统错误
	Integer HTTP_RES_CODE_500 = 500;
	// 会员token
	String TOKEN = "token";
	// 支付token
	String PAY_TOKEN = "payToken";
	//保存在cookie中token的有效时间
	Integer COOKIE_TOKEN_TIME = 60*60*24*60;
	//保存在redis中token的有效时间
	Long REDIS_TOKEN_TIME = 60*60*24*60L;
	//保存在redis中支付token的有效时间
	Long REDIS_PAY_TOKEN_TIME = 60*15L;
}
