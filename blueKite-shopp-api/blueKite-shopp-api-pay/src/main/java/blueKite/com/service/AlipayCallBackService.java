package blueKite.com.service;

import java.util.Map;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import blueKite.com.base.baseEntity.BaseResponse;

@RequestMapping("/alipayCallBack")
public interface AlipayCallBackService {
	
	//支付宝同步回调地址
	@RequestMapping("/synCallBack")
	public BaseResponse synCallBack(@RequestParam Map<String,String> params);

	//支付宝异步回调地址
	@RequestMapping("/asyncCallBack")
	public String asyncCallBack(@RequestParam Map<String,String> params);

}
