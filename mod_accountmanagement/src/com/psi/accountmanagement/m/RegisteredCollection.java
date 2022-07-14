package com.psi.accountmanagement.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.DbWrapper;
import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

public class RegisteredCollection extends ModelCollection{
	
	protected String id;
	protected String userslevel;
	

	@Override
	public boolean hasRows() {

		if(this.userslevel.equals("CUSTOMER")){
		     DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT U.*,C.NATUREOFWORK,C.SOURCEOFFUND,C.DATEOFBIRTH FROM TBLUSERS U LEFT JOIN TBLCUSTOMEREXTENDEDDETAILS C ON U.ACCOUNTNUMBER=C.ACCOUNTNUMBER WHERE USERID=? ", this.id);
		     
		     if (!r.isEmpty())
		     {
		    	 for(DataRow row: r){
			         ReportItem m = new ReportItem();
			         m.setProperty("UserId", row.getString("USERID") == null ? "" : row.getString("USERID").toString());
			         m.setProperty("Email", row.getString("EMAIL") == null ? "" : row.getString("EMAIL").toString());
			         m.setProperty("Msisdn", row.getString("MSISDN") == null ? "" : row.getString("MSISDN").toString());
			         m.setProperty("Firstname", row.getString("FIRSTNAME") == null ? "" : row.getString("FIRSTNAME").toString());
			         m.setProperty("Lastname", row.getString("LASTNAME") == null ? "" : row.getString("LASTNAME").toString());
			         m.setProperty("Middlename", row.getString("MIDDLENAME") == null ? "" : row.getString("MIDDLENAME").toString());
			         m.setProperty("Country", row.getString("COUNTRY") == null ? "" : row.getString("COUNTRY").toString());
			         m.setProperty("Province", row.getString("PROVINCE") == null ? "" : row.getString("PROVINCE").toString());
			         m.setProperty("City", row.getString("CITY") == null ? "" : row.getString("CITY").toString());
			         m.setProperty("UsersLevel", row.getString("USERSLEVEL") == null ? "" : row.getString("USERSLEVEL").toString());
			         m.setProperty("AccountNumber", row.getString("ACCOUNTNUMBER") == null ? "" : row.getString("ACCOUNTNUMBER").toString());
			         m.setProperty("NatureOfWork", row.getString("NATUREOFWORK") == null ? "" : row.getString("NATUREOFWORK").toString());
			         m.setProperty("SoureOfFund", row.getString("SOURCEOFFUND") == null ? "" : row.getString("SOURCEOFFUND").toString());
			         m.setProperty("DateOfBirth", row.getString("DATEOFBIRTH") == null ? "" : row.getString("DATEOFBIRTH").toString());
			         
			         add(m);
		    	 }
		     }
		     return r.size() > 0;
		}else{
			DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLUSERS WHERE USERID=? ", this.id);
		     
		     if (!r.isEmpty())
		     {
		    	 for(DataRow row: r){
			         ReportItem m = new ReportItem();
			         m.setProperty("UserId", row.getString("USERID") == null ? "" : row.getString("USERID").toString());
			         m.setProperty("Email", row.getString("EMAIL") == null ? "" : row.getString("EMAIL").toString());
			         m.setProperty("Msisdn", row.getString("MSISDN") == null ? "" : row.getString("MSISDN").toString());
			         m.setProperty("Firstname", row.getString("FIRSTNAME") == null ? "" : row.getString("FIRSTNAME").toString());
			         m.setProperty("Lastname", row.getString("LASTNAME") == null ? "" : row.getString("LASTNAME").toString());
			         m.setProperty("UsersLevel", row.getString("USERSLEVEL") == null ? "" : row.getString("USERSLEVEL").toString());
			         m.setProperty("Middlename", row.getString("MIDDLENAME") == null ? "" : row.getString("MIDDLENAME").toString());
			         m.setProperty("Country", row.getString("COUNTRY") == null ? "" : row.getString("COUNTRY").toString());
			         m.setProperty("Province", row.getString("PROVINCE") == null ? "" : row.getString("PROVINCE").toString());
			         m.setProperty("City", row.getString("CITY") == null ? "" : row.getString("CITY").toString());
			         
			         add(m);
		    	 }
		     }
		     return r.size() > 0;
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserslevel() {
		return userslevel;
	}

	public void setUserslevel(String userslevel) {
		this.userslevel = userslevel;
	}

	

}
