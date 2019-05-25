package blueKite.com.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import blueKite.com.base.baseEntity.BaseResponse;
import blueKite.com.base.baseEntity.Constants;
import blueKite.com.base.baseService.BaseService;
import blueKite.com.base.baseUtils.TokenUtils;
import blueKite.com.config.AlipayConfig;
import blueKite.com.dao.PaymentInfoDao;
import blueKite.com.entity.PaymentInfo;

@RestController
public class PaymentServiceImpl extends BaseService implements PaymentService{
	
	@Autowired
	private PaymentInfoDao paymentDao;

	@Override
	public BaseResponse createPayToken(@RequestBody PaymentInfo paymentInfo) {
		Integer insert = paymentDao.insertPaymentInfo(paymentInfo);
		if(insert < 0) {
			setResultMsgError("保存支付信息失败!");
		}
		Integer payId = paymentInfo.getId();
		String payToken = TokenUtils.getPayToken();
		baseRedisService.setString(payToken, payId+"", Constants.REDIS_PAY_TOKEN_TIME);
		JSONObject data = new JSONObject();
		data.put("payToken", payToken);
		return setResultDataSuccess(data);
	}

	@Override
	public BaseResponse getPayHtmlByToken(@RequestParam("token") String token) {
		String payId = (String) baseRedisService.getString(token);
		if(StringUtils.isEmpty(payId)) {
			return setResultMsgError("payToken已失效!");
		}
		Integer payID = Integer.valueOf(payId);
		PaymentInfo paymentInfo = paymentDao.getPaymentById(payID);
		if(paymentInfo ==null) {
			return setResultMsgError("找不到payment信息!");
		}
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
		
		//设置请求参数
		AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
		alipayRequest.setReturnUrl(AlipayConfig.return_url);
		alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
		
		//商户订单号，商户网站订单系统中唯一订单号，必填
		String out_trade_no = paymentInfo.getOrderId();
		//付款金额，必填
		String total_amount = paymentInfo.getPrice().toString();
		//订单名称，必填
		String subject = "性感内衣";
		//商品描述，可空
		String body = "";
		
		alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\"," 
				+ "\"total_amount\":\""+ total_amount +"\"," 
				+ "\"subject\":\""+ subject +"\"," 
				+ "\"body\":\""+ body +"\"," 
				+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
		
		//若想给BizContent增加其他可选请求参数，以增加自定义超时时间参数timeout_express来举例说明
		//alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\"," 
		//		+ "\"total_amount\":\""+ total_amount +"\"," 
		//		+ "\"subject\":\""+ subject +"\"," 
		//		+ "\"body\":\""+ body +"\"," 
		//		+ "\"timeout_express\":\"10m\"," 
		//		+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
		//请求参数可查阅【电脑网站支付的API文档-alipay.trade.page.pay-请求参数】章节
		
		String result = "";
		try {
			result = alipayClient.pageExecute(alipayRequest).getBody();
		} catch (AlipayApiException e) {
			return setResultMsgError("支付失败!");
		}
		JSONObject data = new JSONObject();
		data.put("payHtml", result);
		return setResultDataSuccess(data);
	}

}
