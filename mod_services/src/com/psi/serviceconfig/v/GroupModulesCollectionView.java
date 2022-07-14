package com.psi.serviceconfig.v;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.psi.serviceconfig.m.Services;
import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportView;

public class GroupModulesCollectionView extends ReportView{
		public GroupModulesCollectionView(String code, ModelCollection data) {
		super(code, data);
		// TODO Auto-generated constructor stub
	}

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		
		@Override
		public String render() {
			JSONArray arr = new JSONArray();
			JSONObject obj = new JSONObject();
			if(this.rows!=null)
			for(Model m: this.rows){
				Services i = (Services) m;
				JSONObject item = new JSONObject();
					JSONArray modules = new JSONArray();
					JSONArray notifications = new JSONArray();
					for(Integer intModule : i.getServices()){
						modules.add(intModule);
					}
					item.put("SERVICES",modules);
					
					
				arr.add(item);
			}
			
			obj.put("Code", 00);
			obj.put("Message", "Success");
			obj.put("Data",arr.toString());
			return obj.toString();
		}

	}
