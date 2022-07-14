package com.psi.serviceconfig.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class BranchesCollection extends ModelCollection{
	protected String accountnumber;
	@Override
	public boolean hasRows() {
		if(!this.accountnumber.equals("834591471124")){
			DataRowCollection rows = SystemInfo.getDb().QueryDataRows("SELECT ID,BRANCH,ACCOUNTNUMBER,BRANCHCODE FROM TBLBRANCHES WHERE STATUS = 1 AND KEYACCOUNT = ? ",this.accountnumber);
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
		}else{
			DataRowCollection rows = SystemInfo.getDb().QueryDataRows("SELECT ID,BRANCH,ACCOUNTNUMBER,BRANCHCODE FROM TBLBRANCHES WHERE NATUREOFBUSINESS IS NOT NULL AND STATUS = 1 AND KEYACCOUNT = ? ",this.accountnumber);
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
	public String getAccountnumber() {
		return accountnumber;
	}
	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}

}
