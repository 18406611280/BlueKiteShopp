package blueKite.com.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import blueKite.com.base.baseEntity.BaseResponse;
import blueKite.com.base.baseEntity.Constants;
import blueKite.com.base.config.AlipayConfig;
import blueKite.com.feign.FeignAlipayCallBackService;

@Controller
public class AlipayCallBackController {
	
	private static String PAYSUCCESS = "paySuccess";
	
	@Autowired
	private FeignAlipayCallBackService alipayCallBackService;
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/alipaySynCallBack")
	public void synCallBack(HttpServletRequest request, HttpServletResponse response) {
		try {
			PrintWriter writer = response.getWriter();
			response.setContentType("text/html;charset=utf-8");
			//获取支付宝GET过来反馈信息
			Map<String,String> params = new HashMap<String,String>();
			Map<String,String[]> requestParams = request.getParameterMap();
			for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				//乱码解决，这段代码在出现乱码时使用
				valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
				params.put(name, valueStr);
			}
			BaseResponse synCallBack = alipayCallBackService.synCallBack(params);
			Integer code = synCallBack.getCode();
			if(!code.equals(Constants.HTTP_RES_CODE_200)) {
				return;
			}
			LinkedHashMap map = (LinkedHashMap) synCallBack.getData();
			LinkedHashMap data = (LinkedHashMap) map.get("callBack");
			String outTradeNo = (String) data.get("out_trade_no");
			String tradeNo = (String) data.get("trade_no");
			String totalAmount = (String) data.get("total_amount");
			String returnBackUrl = AlipayConfig.returnBackUrl;
			//封装回调参数POST请求发送
			String htmlFrom="<form name='returnForm'"
					+ "method='post' "
					+ "action='"+returnBackUrl+"'>"
					+ "<input type='hidden' name='outTradeNo' value='"+outTradeNo+"'>"
					+ "<input type='hidden' name='tradeNo' value='"+tradeNo+"'>"
					+ "<input type='hidden' name='totalAmount' value='"+totalAmount+"'>"
					+ "<input type='submit' value='立即支付' style='display:none'>"
					+ "</form>"
					+ "<script>document.forms[0].submit();</script>";
			writer.write(htmlFrom);
			writer.close();
		} catch (Exception e) {
			return;
		}
	}
	
	@RequestMapping(value = "/alipayReturnBack", method = RequestMethod.POST)
	public String returnBack(HttpServletRequest request, String outTradeNo, String tradeNo, String totalAmount) {
		request.setAttribute("outTradeNo", outTradeNo);
		request.setAttribute("tradeNo", tradeNo);
		request.setAttribute("totalAmount", totalAmount);
		return PAYSUCCESS;
	}
	
	@RequestMapping("/alipayAsyncCallBack")
	@ResponseBody
	public String AsyncCallBack(HttpServletRequest request) {
		try {
			//获取支付宝GET过来反馈信息
			Map<String,String> params = new HashMap<String,String>();
			Map<String,String[]> requestParams = request.getParameterMap();
			for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				params.put(name, valueStr);
			}
			String result = alipayCallBackService.asyncCallBack(params);
			return result;
		}catch (Exception e) {
			return "fail";
		}
	}

}
