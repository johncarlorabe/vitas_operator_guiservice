package com.psi.backoffice.v;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportView;

public class CollectionView  extends ReportView{

	public CollectionView(String code, ModelCollection data) {
		super(code, data);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String render() {
		
		JSONObject json = new JSONObject();
		JSONObject details = new JSONObject();
		
	
		json.put("Code", this.getCode());
		json.put("Message","Successfully Fetched");
		
		
		
		ArrayList<JSONObject> json2 =  new ArrayList<JSONObject>();
		for(int x=0;x<this.rows.size();x++){
		
		Model model=this.rows.get(x);
			
			JSONObject json3 = new JSONObject();
	
			
			  for(String key: model.keys()){
			        
		        json3.put(key,model.getObject(key));
		       }
			
			
			json2.add(json3);
			
		}
		
		
		
		details.put("details", json2);
		
		json.put("Data", details);
		
		return json.toString();
		
			
	
	}

}
