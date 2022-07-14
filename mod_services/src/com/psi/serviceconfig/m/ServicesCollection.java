package com.psi.serviceconfig.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class ServicesCollection extends ModelCollection{
	protected String category;
	@Override
	public boolean hasRows() {
		if(this.category.equals("ALL")){
			DataRowCollection rows = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLSERVICES WHERE STATUS = 0");
			if (!rows.isEmpty())
		     {
		    	 for(DataRow row: rows){	
			         ReportItem m = new ReportItem();
			         for (String key : row.keySet()) {
			 	        m.setProperty(key, row.getString(key).toString());
			 	      }
			 	      add(m);
		    	 }
		     }
		     return rows.size() > 0;
		}else{
			DataRowCollection rows = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLSERVICES WHERE CATEGORY = ? AND STATUS =0", this.category);
			if (!rows.isEmpty())
		     {
		    	 for(DataRow row: rows){	
			         ReportItem m = new ReportItem();
			         for (String key : row.keySet()) {
			 	        m.setProperty(key, row.getString(key).toString());
			 	      }
			 	      add(m);
		    	 }
		     }
		     return rows.size() > 0;
		}
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}

}
