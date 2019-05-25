package blueKite.com.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderEntity {
	
	private Integer id; 
	private Integer isPay; 
	private String orderId; 
	private String payId; 
	private Integer userId; 
	private Date created;
	private Date updated;

}
