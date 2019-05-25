package blueKite.com.base.baseUtils;

import java.util.UUID;

import blueKite.com.base.baseEntity.Constants;

public class TokenUtils {
	
	public static String getToken() {
		return Constants.TOKEN+"-"+UUID.randomUUID();
	}
	
	public static String getPayToken() {
		return Constants.PAY_TOKEN+"-"+UUID.randomUUID();
	}

}
