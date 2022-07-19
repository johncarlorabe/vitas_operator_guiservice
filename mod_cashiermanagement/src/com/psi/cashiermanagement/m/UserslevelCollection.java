package com.psi.cashiermanagement.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class UserslevelCollection extends ModelCollection{

	@Override
	public boolean hasRows() {
		DataRowCollection rows = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLUSERSLEVEL WHERE ACCOUNTSTATUS = 'ACTIVE' AND GUIINTERFACE='vitascashier' ORDER BY USERSLEVEL ASC");
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
