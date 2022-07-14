package com.psi.accountmanagement.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

public class BranchesCollection extends ModelCollection{

	@Override
	public boolean hasRows() {
		 DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLBRANCHES ");
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
		         ReportItem m = new ReportItem();
		         m.setProperty("Id", row.getString("ID") == null ? "" : row.getString("ID").toString());
		         m.setProperty("AccountNumber", row.getString("ACCOUNTNUMBER") == null ? "" : row.getString("ACCOUNTNUMBER").toString());
		         m.setProperty("Branch", row.getString("BRANCH") == null ? "" : row.getString("BRANCH").toString());
		         m.setProperty("ContactNumber", row.getString("CONTACTNUMBER") == null ? "" : row.getString("CONTACTNUMBER").toString());
		         m.setProperty("Location", row.getString("LOCATION") == null ? "" : row.getString("LOCATION").toString());
		         m.setProperty("Status", row.getString("STATUS") == null ? "" : row.getString("STATUS").toString());
		         m.setProperty("StoreHour", row.getString("STOREHOURS") == null ? "" : row.getString("STOREHOURS").toString());
		         
		         add(m);
	    	 }
	     }
	     return r.size() > 0;
	}

}
