package com.psi.reports.v2.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class MerchantWalletCollection extends ModelCollection{

	protected String accountnumber;
	
	@Override
	public boolean hasRows() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLMERCHANTWALLET ORDER BY REGDATE ASC");
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
	    		 ReportItem m = new ReportItem();
	    		 for (String key : row.keySet()) {
			 	        m.setProperty(key, row.getString(key).toString());
			 	 }
	    		 m.setProperty("BALANCE", row.getString("BALANCE") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("BALANCE").toString())));
		         
		         add(m);
	    	 }
	     }
	     return r.size() > 0;
	}
	public boolean hasRowsnotall() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLMERCHANTWALLET WHERE ROOT = (SELECT ID FROM ADMDBMC.TBLACCOUNTINFO WHERE ACCOUNTNUMBER =?) ORDER BY REGDATE ASC",this.accountnumber);
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
	    		 ReportItem m = new ReportItem();
	    		 for (String key : row.keySet()) {
			 	        m.setProperty(key, row.getString(key).toString());
			 	 }
	    		 m.setProperty("BALANCE", row.getString("BALANCE") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("BALANCE").toString())));
		         
		         add(m);
	    	 }
	     }
	     return r.size() > 0;
	}
	public String getAccountnumber() {
		return accountnumber;
	}
	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}

	
}
