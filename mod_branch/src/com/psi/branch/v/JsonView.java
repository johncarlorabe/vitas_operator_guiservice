package com.psi.branch.v;

import org.json.simple.JSONObject;

import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.common.NormalView;

public class JsonView extends NormalView implements IView{

	public JsonView(Model m) {
		super(m);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String render() {
		
JSONObject obj = new JSONObject();
		
		JSONObject jsonGroup = new JSONObject();
		obj.put("Code", this.data.getState().getCode().toString());
		  obj.put("Message", this.data.getState().getMessage().toString());
//		  try {
//		   obj.put("Data", this.data.getString("Account").toString());
//		  } catch (NullPointerException e) {
//
//		  }
		  
		  for(String key: this.data.keys()){
			     jsonGroup.put(key,this.data.getObject(key).toString());
			}
			
			obj.put("Data",jsonGroup);
			
		  return obj.toString();
	}

}
