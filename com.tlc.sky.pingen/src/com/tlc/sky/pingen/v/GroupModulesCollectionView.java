package com.tlc.sky.pingen.v;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportView;
import com.tlc.gui.modules.session.UIGroup;
import com.tlc.sky.pingen.arch.beans.PingenGroup;

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
				PingenGroup i = (PingenGroup) m;
				JSONObject item = new JSONObject();
					JSONArray modules = new JSONArray();
					JSONArray notifications = new JSONArray();
					for(Integer intModule : i.getModules()){
						modules.add(intModule);
					}
					item.put("MODULES",modules);
					
					for(Integer intNotifications : i.getNotifications()){
						notifications.add(intNotifications);
					}
					item.put("NOTIFICATIONS",notifications);
					
				
				arr.add(item);
			}
			
			obj.put("Code", 00);
			obj.put("Message", "Success");
			obj.put("Data",arr.toString());
			return obj.toString();
		}

	}
