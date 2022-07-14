package com.tlc.sky.pingen.v;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;
import com.tlc.gui.modules.common.ReportView;
import com.tlc.gui.modules.session.UIGroup;

public class ModuleCollectionView extends ReportView{
		public ModuleCollectionView(String code, ModelCollection data) {
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
				ReportItem i = (ReportItem) m;
				JSONObject item = new JSONObject();
				for(String key: i.keys()){				
					item.put(key, i.getString(key));
				}
				arr.add(item);
			}
			
			obj.put("Code", 00);
			obj.put("Message", "Success");
			obj.put("Data",arr.toString());
			return obj.toString();
		}

	}

