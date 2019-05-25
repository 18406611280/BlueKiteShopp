package blueKite.com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import blueKite.com.base.baseEntity.BaseResponse;
import blueKite.com.base.baseEntity.Constants;
import blueKite.com.feign.FeignPayService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class PayController {
	
	@Autowired
	private FeignPayService payService;
	
	//支付宝支付
	@SuppressWarnings("rawtypes")
	@RequestMapping("/alipay")
	public void alipay(String token, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter writer = response.getWriter();
		if(StringUtils.isEmpty(token)) {
			writer.write("没有token!");
			return;
		}
		BaseResponse responseData = payService.getPayHtmlByToken(token);
		Integer code = responseData.getCode();
		if(!code.equals(Constants.HTTP_RES_CODE_200)) {
			String msg = responseData.getMsg();
			writer.write(msg);
			return;
		}
		LinkedHashMap map = (LinkedHashMap) responseData.getData();
		String payHtml = (String) map.get("payHtml");
		log.info("支付宝post表单:{}",payHtml);
		writer.write(payHtml);
		writer.close();
	}

}
