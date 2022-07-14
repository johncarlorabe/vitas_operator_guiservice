package com.psi.reports.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class TerritoryCollection extends ModelCollection{

	@Override
	public boolean hasRows() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLTERRITORIES ORDER BY REGION ASC");
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
		         ReportItem m = new ReportItem();
		         for (String key : row.keySet()) {
			 	        m.setProperty(key, row.getString(key).toString());
			 	 }
		         add(m);
	    	 }
	     }
	     return r.size() > 0;
	}

}
