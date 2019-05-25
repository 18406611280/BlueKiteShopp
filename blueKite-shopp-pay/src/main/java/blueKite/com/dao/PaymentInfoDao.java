package blueKite.com.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import blueKite.com.entity.PaymentInfo;

@Mapper
public interface PaymentInfoDao {
	
	@Select("select * from payment_info where id = #{id}")
	public PaymentInfo getPaymentById(@Param("id") Integer id);
	
	@Insert("insert into payment_info ( id,userid,typeid,orderid,platformorderid,price,source,state,created,updated) value(null,#{userId},#{typeId},#{orderId},#{platformorderId},#{price},#{source},#{state},#{created},#{updated})")
	@Options(useGeneratedKeys = true, keyProperty = "id") // 添加该行，product中的id将被自动添加
	public Integer insertPaymentInfo(PaymentInfo paymentInfo);
	
	@Select("select * from payment_info where  orderId=#{orderId}")
	public PaymentInfo getByOrderIdPayInfo(@Param("orderId") String orderId);

	@Update("update payment_info set state =#{state},payMessage=#{payMessage},platformorderId=#{platformorderId},updated=#{updated} where orderId=#{orderId} ")
	public void updatePayInfo(PaymentInfo paymentInfo);

}
