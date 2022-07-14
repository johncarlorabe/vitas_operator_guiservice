package com.tlc.common;
import org.json.*;

public class JSONConstructor {
	private JSONObject _js;
	
	public JSONConstructor() {
		_js = new JSONObject();
	}
	
	public void set(String key, String value) throws JSONException{
		_js.put(key, value);
	}
	
	public String toString(){		
		return _js.toString();		
	}
}