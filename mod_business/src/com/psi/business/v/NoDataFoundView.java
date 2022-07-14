package com.psi.business.v;

import org.json.simple.JSONObject;

import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;

public class NoDataFoundView implements IView{
	
	
	private ObjectState state;
	
	public NoDataFoundView(ObjectState state){
		this.state = state;
	}

	@Override
	public String render() {
		

		JSONObject json = new JSONObject();
		
		json.put("Code", this.state.getCode());
		json.put("Message",this.state.getMessage());
		
		return json.toString();
		
	}

}
