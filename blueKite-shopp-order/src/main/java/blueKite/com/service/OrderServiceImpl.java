package blueKite.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.codingapi.tx.annotation.ITxTransaction;
import blueKite.com.base.baseEntity.BaseResponse;
import blueKite.com.base.baseService.BaseService;
import blueKite.com.dao.OrderDao;

@RestController
public class OrderServiceImpl extends BaseService implements OrderService ,ITxTransaction{
	
	@Autowired
	private OrderDao orderDao;

	@Override
	@Transactional
	public BaseResponse updateOrderState(@RequestParam("orderId") String orderId, @RequestParam("payId") String payId, @RequestParam("isPay") Integer isPay) {
		Integer updateOrderState = orderDao.updateOrderState(orderId, payId, isPay);
		if(updateOrderState<0) {
			return setResultMsgError("更新失败");
		}
		return setResultSuccess();
	}

}
