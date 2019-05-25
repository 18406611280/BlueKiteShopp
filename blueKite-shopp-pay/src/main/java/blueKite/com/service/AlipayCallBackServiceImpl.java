package blueKite.com.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.codingapi.tx.annotation.TxTransaction;
import blueKite.com.base.baseEntity.BaseResponse;
import blueKite.com.base.baseEntity.Constants;
import blueKite.com.base.baseService.BaseService;
import blueKite.com.base.config.AlipayConfig;
import blueKite.com.dao.PaymentInfoDao;
import blueKite.com.entity.PaymentInfo;
import blueKite.com.feign.FeignOrderService;

@RestController
public class AlipayCallBackServiceImpl extends BaseService implements AlipayCallBackService{
	
	@Autowired
	private PaymentInfoDao paymentInfoDao;
	
	@Autowired
	private FeignOrderService orderService;

	@Override
	public BaseResponse synCallBack(@RequestParam Map<String, String> params) {
		try {
			boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名
			if(!signVerified) {
				return setResultMsgError("支付宝回调验签失败!");
			}else {
				//商户订单号
				String out_trade_no = params.get("out_trade_no");
				//支付宝交易号
				String trade_no = params.get("trade_no");
				//付款金额
				String total_amount = params.get("total_amount");
				JSONObject callBack = new JSONObject();
				JSONObject data = new JSONObject();
				data.put("out_trade_no", out_trade_no);
				data.put("trade_no", trade_no);
				data.put("total_amount", total_amount);
				callBack.put("callBack", data);
				return setResultDataSuccess(callBack);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return setResultMsgError("支付宝回调失败!");
		}
	}

	@Override
	@Transactional
	@TxTransaction(isStart = true)
	public String asyncCallBack(@RequestParam Map<String, String> params) {
		boolean signVerified = false;
		try {
			//调用SDK验证签名
			signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type);
		} catch (AlipayApiException e) {
			System.out.println("验签失败!");
			return "fail";
		}
		if(!signVerified) {
			return "fail";
		}else {
			//商户订单号
			String orderId = params.get("out_trade_no");
			PaymentInfo paymentInfo = paymentInfoDao.getByOrderIdPayInfo(orderId);
			if(paymentInfo ==null) {
				return "fail";
			}
			Integer state = paymentInfo.getState();
			//支付宝重试机制,处理幂等性问题
			if(state.equals(1)) {
				return "success";
			}
			//支付宝交易号
			String tradeNo = params.get("trade_no");
			paymentInfo.setPayMessage(params.toString());
			paymentInfo.setState(1);
			paymentInfo.setPlatformorderId(tradeNo);
			paymentInfoDao.updatePayInfo(paymentInfo);
			BaseResponse updateOrderState = orderService.updateOrderState(orderId, tradeNo, 1);
			if(!updateOrderState.getCode().equals(Constants.HTTP_RES_CODE_200)) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return "fail";
			}
			return "success";
		}
	}

}
