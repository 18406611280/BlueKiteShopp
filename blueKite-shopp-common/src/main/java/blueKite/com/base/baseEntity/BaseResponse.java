package blueKite.com.base.baseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseResponse {
	
	private Integer code;
	private String msg;
	private Object data;
	
	public BaseResponse() {}

	public BaseResponse(Integer code, String msg, Object data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

}
