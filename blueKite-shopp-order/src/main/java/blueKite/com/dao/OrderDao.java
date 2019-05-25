package blueKite.com.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface OrderDao {
	
	@Update("update order_info set isPay = #{isPay}, payId = #{payId} where orderId = #{orderId}")
	public Integer updateOrderState(@Param("orderId") String orderId, @Param("payId") String payId, @Param("isPay") Integer isPay);

}
