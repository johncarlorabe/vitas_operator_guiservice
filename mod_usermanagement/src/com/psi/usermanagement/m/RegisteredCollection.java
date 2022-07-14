package com.psi.usermanagement.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

public class RegisteredCollection extends ModelCollection{
	
	protected String accoutnumber;
	
	@Override
	public boolean hasRows() {
		
	     DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT DECRYPT(PASSWORD,?,USERNAME) PASS,U.* FROM TBLUSERS U WHERE USERSLEVEL='CASHIER' AND ACCOUNTNUMBER=? ", SystemInfo.getDb().getCrypt(),this.accoutnumber);
	     
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
		         m.setProperty("Status", row.getString("STATUS")==null ? "" : row.getString("STATUS").toString());
		         m.setProperty("TerminalStatus", row.getString("PASS").equals(SystemInfo.getDb().QueryScalar("SELECT PASSWORD FROM TBLPOSUSERS WHERE USERID = ?", "", row.getString("USERNAME")))?"YES":"NO");
		         Logger.LogServer(SystemInfo.getDb().QueryScalar("SELECT PASSWORD FROM TBLPOSUSERS WHERE USERID = ?", "", row.getString("USERNAME")) +"|"+row.getString("PASS"));
		         add(m);
	    	 }
	     }
	     return r.size() > 0;
	}

	public String getAccoutnumber() {
		return accoutnumber;
	}

	public void setAccoutnumber(String accoutnumber) {
		this.accoutnumber = accoutnumber;
	}

	
	

	

}
