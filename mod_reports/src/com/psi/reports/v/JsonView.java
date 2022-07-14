package com.psi.reports.v;

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
		obj.put("Code",this.data.getState().getCode().toString());
		obj.put("Message",this.data.getState().getMessage().toString());		
		return obj.toJSONString();
	}

}
