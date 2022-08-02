package com.psi.branch.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

public class BranchPersonelCollection extends ModelCollection{
	protected String id;
	@Override
	public boolean hasRows() {
		DataRow rr = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLBRANCHES WHERE ID=?",this.id);
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLUSERS WHERE ACCOUNTNUMBER=? AND USERSLEVEL IN ('MANAGER','VITASMANAGER')",rr.getString("ACCOUNTNUMBER"));
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
		         ReportItem m = new ReportItem();
//		         for (String key : row.keySet()) {
//			 	        m.setProperty(key, row.getString(key).toString());
//			 	      }	
		         m.setProperty("EMAIL", row.getString("EMAIL").toString());
		         m.setProperty("USERNAME", row.getString("USERNAME").toString());
		         m.setProperty("MSISDN", row.getString("MSISDN").toString());
		         m.setProperty("FIRSTNAME", row.getString("FIRSTNAME").toString());
		         m.setProperty("LASTNAME", row.getString("LASTNAME").toString());
   	             add(m);
	    	 }
	     }
	     return r.size() > 0;
	}
	public boolean hasRowsCashier() {
		DataRow rr = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLBRANCHES WHERE ID=?",this.id);
//		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT U.*,M.TPAID, M.TERMINALID FROM TBLUSERS U LEFT JOIN TBLMACBINDING M ON M.USERNAME = U.USERNAME WHERE U.ACCOUNTNUMBER=? AND U.USERSLEVEL='CASHIER'",rr.getString("ACCOUNTNUMBER"));
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT U.*,M.TPAID, M.TERMINALID FROM TBLUSERS U LEFT JOIN TBLMACBINDING M ON M.USERNAME = U.USERNAME WHERE U.STOREACCOUNTNUMBER=?",rr.getString("ACCOUNTNUMBER"));
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
		         ReportItem m = new ReportItem();
//		         for (String key : row.keySet()) {
//			 	        m.setProperty(key, row.getString(key).toString());
//			 	      }	
		         m.setProperty("EMAIL", row.getString("EMAIL").toString());
		         m.setProperty("USERNAME", row.getString("USERNAME").toString());
		         m.setProperty("TPAID", row.getString("TPAID").toString());
		         m.setProperty("MSISDN", row.getString("MSISDN").toString());
		         m.setProperty("FIRSTNAME", row.getString("FIRSTNAME").toString());
		         m.setProperty("LASTNAME", row.getString("LASTNAME").toString());
   	             add(m);
	    	 }
	     }
	     return r.size() > 0;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
