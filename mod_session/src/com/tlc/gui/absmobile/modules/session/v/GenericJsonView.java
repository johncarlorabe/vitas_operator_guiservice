package com.tlc.gui.absmobile.modules.session.v;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.common.NormalView;

public class GenericJsonView extends NormalView {

	public GenericJsonView(Model m) {
		super(m);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String render() {
		JSONObject rows = parse(this.data);
		JSONObject ret = new JSONObject();
		ret.put("Code", this.data.getState().getCode());
		if(this.data.getState().getMessage()!=null)ret.put("Message", this.data.getState().getMessage());
		if(rows!=null && !rows.isEmpty())ret.put("Data", rows);
		return ret.toJSONString();
	}
	
	@SuppressWarnings("unchecked")
	public static JSONObject parse(Model data){
		JSONObject ret = new JSONObject();
		JSONObject rows = new JSONObject();
		for(Object o: data.keys()){
			String key = o.toString();
			Object value = data.getObject(key);
			if(value instanceof Model){
				JSONObject obj = parse((Model)value);
				rows.put(key, obj);
			}else if(value.getClass().isArray()){ //to be used for module ids
				JSONArray arr = new JSONArray();
				for(Object val :(Object[])value){
					arr.add(val);
				}
				rows.put(key, arr);
			}
			else{
				if(value!=null)rows.put(key, value.toString());
			}
			
		}
		
		return rows;
	}

}
