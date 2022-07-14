package com.psi.branch.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

public class CustomerBranchCollection extends ModelCollection{

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
			         m.setProperty("Location", row.getString("ADDRESS") == null ? "" : row.getString("ADDRESS").toString());
			         m.setProperty("Status", row.getString("STATUS") == null ? "" : row.getString("STATUS").toString());
			        // m.setProperty("StoreHours", row.getString("STOREHOURS") == null ? "" : row.getString("STOREHOURS").toString());
			         m.setProperty("City", row.getString("CITY") == null ? "" : row.getString("CITY").toString());
			         m.setProperty("Province", row.getString("PROVINCE") == null ? "" : row.getString("PROVINCE").toString());
			         m.setProperty("Country", row.getString("COUNTRY") == null ? "" : row.getString("COUNTRY").toString());
			         m.setProperty("XOrdinate", row.getString("XORDINATES") == null ? "" : row.getString("XORDINATES").toString());
			         m.setProperty("YOrdinate", row.getString("YORDINATES") == null ? "" : row.getString("YORDINATES").toString());
			         m.setProperty("BranchCode", row.getString("BRANCHCODE") == null ? "" : row.getString("BRANCHCODE").toString());
			         m.setProperty("Monday", row.getString("MONDAY") == null ? "" : row.getString("MONDAY").toString());
			         m.setProperty("Tuesday", row.getString("TUESDAY") == null ? "" : row.getString("TUESDAY").toString());
			         m.setProperty("Wednesday", row.getString("WEDNESDAY") == null ? "" : row.getString("WEDNESDAY").toString());
			         m.setProperty("Thursday", row.getString("THURSDAY") == null ? "" : row.getString("THURSDAY").toString());
			         m.setProperty("Friday", row.getString("FRIDAY") == null ? "" : row.getString("FRIDAY").toString());
			         m.setProperty("Saturday", row.getString("SATURDAY") == null ? "" : row.getString("SATURDAY").toString());
			         m.setProperty("Sunday", row.getString("SUNDAY") == null ? "" : row.getString("SUNDAY").toString());
			         m.setProperty("ZipCode", row.getString("ZIPCODE") == null ? "" : row.getString("ZIPCODE").toString());

		         add(m);
	    	 }
	     }
	     return r.size() > 0;
	}

}
