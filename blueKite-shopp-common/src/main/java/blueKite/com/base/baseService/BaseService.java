package blueKite.com.base.baseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import blueKite.com.base.baseEntity.BaseResponse;
import blueKite.com.base.baseEntity.Constants;

public class BaseService {
	
	@Autowired
	protected BaseRedisService baseRedisService;
	
	//返回成功,没有data值
	public BaseResponse setResultSuccess() {
		return new BaseResponse(Constants.HTTP_RES_CODE_200, Constants.HTTP_SUCCESS, null);
	}
	
	// 返回错误，可以传响应和msg
	public BaseResponse setResultResMsgError(Integer res, String msg) {
		return setResult(res, msg, null);
	}
	
	// 返回错误，可以传msg
	public BaseResponse setResultMsgError(String msg) {
		return setResult(Constants.HTTP_RES_CODE_500, msg, null);
	}

	// 返回成功，可以传data值
	public BaseResponse setResultDataSuccess(Object data) {
		return setResult(Constants.HTTP_RES_CODE_200, Constants.HTTP_SUCCESS, data);
	}

	// 返回成功，沒有data值
	public BaseResponse setResultMsgSuccess(String msg) {
		return setResult(Constants.HTTP_RES_CODE_200, msg, null);
	}

	// 通用封装
	public BaseResponse setResult(Integer code, String msg, Object data) {
		return new BaseResponse(code, msg, data);
	}

}
