package com.tlc.sky.pingen.v;

import org.json.simple.JSONObject;

import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.common.NormalView;
import com.tlc.gui.modules.session.UIGroup;

public class SearchView extends NormalView{
		
		public SearchView(Model m) {
		super(m);
		// TODO Auto-generated constructor stub
	}

		@Override
		public String render() {
			
			JSONObject obj = new JSONObject();
			JSONObject jsonGroup = new JSONObject();
			UIGroup group = (UIGroup) this.data;
			obj.put("Code",this.data.getState().getCode().toString());
			obj.put("Message",this.data.getState().getMessage().toString());
			
			//jsonGroup.put(group.PROP_SESSIONTIMEOUT,group.getSessionTimeout());
			
			for(String key: this.data.keys()){
			     jsonGroup.put(key,this.data.getObject(key).toString());
			}
			
			obj.put("Data",jsonGroup);
			
			
			
			return obj.toJSONString();
		}

	}

