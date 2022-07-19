package com.psi.cashiermanagement.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

public class RegisteredCollection extends ModelCollection{
	
	protected String id;
	
	@Override
	public boolean hasRows() {
		
	     DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLUSERS WHERE GUIINTERFACE='vitascashier'");
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){	
		         ReportItem m = new ReportItem();
		         m.setProperty("Id", row.getString("USERID") == null ? "" : row.getString("USERID").toString());
		         m.setProperty("Email", row.getString("EMAIL") == null ? "" : row.getString("EMAIL").toString());
		         m.setProperty("Msisdn", row.getString("MSISDN") == null ? "" : row.getString("MSISDN").toString());
		         m.setProperty("Firstname", row.getString("FIRSTNAME") == null ? "" : row.getString("FIRSTNAME").toString());
		         m.setProperty("Lastname", row.getString("LASTNAME") == null ? "" : row.getString("LASTNAME").toString());
		         m.setProperty("Username", row.getString("USERNAME") == null ? "" : row.getString("USERNAME").toString());
		         m.setProperty("UsersLevel", row.getString("USERSLEVEL") == null ? "" : row.getString("USERSLEVEL").toString());
		         m.setProperty("Status", row.getString("STATUS") == null ? "" : row.getString("STATUS").toString());
		         m.setProperty("AccountNumber", row.getString("ACCOUNTNUMBER") == null ? "" : row.getString("ACCOUNTNUMBER").toString());
		         add(m);
	    	 }
	     }
	     return r.size() > 0;
	}
	
	public boolean getActiveCashiers() {
		
	     DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLUSERS WHERE STATUS='ACTIVE' AND LOCKED='NO' AND GUIINTERFACE='vitascashier'");
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){	
		         ReportItem m = new ReportItem();
		         m.setProperty("Id", row.getString("USERID") == null ? "" : row.getString("USERID").toString());
		         m.setProperty("Email", row.getString("EMAIL") == null ? "" : row.getString("EMAIL").toString());
		         m.setProperty("Msisdn", row.getString("MSISDN") == null ? "" : row.getString("MSISDN").toString());
		         m.setProperty("Firstname", row.getString("FIRSTNAME") == null ? "" : row.getString("FIRSTNAME").toString());
		         m.setProperty("Lastname", row.getString("LASTNAME") == null ? "" : row.getString("LASTNAME").toString());
		         m.setProperty("Username", row.getString("USERNAME") == null ? "" : row.getString("USERNAME").toString());
		         m.setProperty("UsersLevel", row.getString("USERSLEVEL") == null ? "" : row.getString("USERSLEVEL").toString());
		         m.setProperty("Status", row.getString("STATUS") == null ? "" : row.getString("STATUS").toString());
		         m.setProperty("AccountNumber", row.getString("ACCOUNTNUMBER") == null ? "" : row.getString("ACCOUNTNUMBER").toString());
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
