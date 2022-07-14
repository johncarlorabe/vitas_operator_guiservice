package com.tlc.common;

import java.util.Hashtable;

import org.json.*;

public class JSONParser {

	private Hashtable<String, String> _table = new Hashtable<String, String>();
	private JSONObject _myjson;
	
	
	public JSONParser(String json) throws Exception{
		try{
		_myjson = new JSONObject(json);
        JSONArray nameArray = _myjson.names();
        JSONArray valArray = _myjson.toJSONArray(nameArray);
        
        for(int i=0;i<valArray.length();i++)
        {
            _table.put(nameArray.getString(i),valArray.getString(i));
        }
        
		}catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}
	
	public String get(String key){
		try{
		return _table.get(key).toString();
		}catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	public String get(String root, String key, int index){
		try {
			JSONArray array = _myjson.getJSONArray(root);
			return array.getJSONObject(index).getString(key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
	
	public int getlength(){
		return _myjson.length();
	}
	
	public int getlength(String root) throws Exception{
		try {
			JSONArray array = _myjson.getJSONArray(root);
			return array.length();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		}
	}
	
	public int getlength(String root, int index) throws Exception{
		try {
			JSONArray array = _myjson.getJSONArray(root);
			return array.getJSONObject(index).length();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		}
	}
	
}
