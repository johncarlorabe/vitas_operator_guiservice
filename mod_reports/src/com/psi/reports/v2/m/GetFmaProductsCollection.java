package com.psi.reports.v2.m;

import org.json.simple.JSONArray;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

public class GetFmaProductsCollection extends ModelCollection {
	protected String type;
	protected String accountnumber;
	@Override
	public boolean hasRows() {
		
		
	     DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLPRODUCTS WHERE TYPE =? ORDER BY PRODUCTNAME ASC",this.type);
	     
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
	public boolean hasRowsperaccount() {
		
		
	     DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT P.* FROM TBLPRODUCTS P INNER JOIN TBLSERVICES S ON S.NAME = P.PRODUCTNAME INNER JOIN TBLSERVICESCONFIG SC ON SC.SERVICES = S.ID  WHERE P.TYPE =? AND SC.ACCOUNTNUMBER=? ORDER BY PRODUCTNAME ASC",this.type,this.accountnumber);
	     
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAccountnumber() {
		return accountnumber;
	}
	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}
	
}
