package blueKite.com.adapter;

import com.alibaba.fastjson.JSONObject;

//统一推送适配器
public interface MessageAdapter {
	
	public void sendMsg(JSONObject body);

}
