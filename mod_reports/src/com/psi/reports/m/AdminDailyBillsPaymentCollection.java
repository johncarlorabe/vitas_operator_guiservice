package com.psi.reports.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class AdminDailyBillsPaymentCollection extends ModelCollection{

	protected String branch;
	protected String datefrom;
	protected String dateto;
	
	@Override
	public boolean hasRows() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLDAILYBILLSPAYMENTHISTORY WHERE TIMESTAMP BETWEEN ? AND ?",this.datefrom,this.dateto);
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
	    		 ReportItem m = new ReportItem();
	    		 for (String key : row.keySet()) {
			 	        m.setProperty(key, row.getString(key).toString());
			 	       m.setProperty("COLAMOUNT", row.getString("COLAMOUNT") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("COLAMOUNT").toString())));
				        
			 	      }
			     add(m);
	    	 }
	     }
	     return r.size() > 0;
	}
	
	public boolean getBPColBranches() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLDAILYBILLSPAYMENTHISTORY WHERE ACCOUNTNUMBER=? AND TIMESTAMP BETWEEN ? AND ?",this.branch,this.datefrom,this.dateto);
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
	    		 ReportItem m = new ReportItem();
	    		 for (String key : row.keySet()) {
			 	        m.setProperty(key, row.getString(key).toString());
			 	       m.setProperty("COLAMOUNT", row.getString("COLAMOUNT") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("COLAMOUNT").toString())));
				        
			 	      }
			     add(m);
	    	 }
	     }
	     return r.size() > 0;
	}

	
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getDatefrom() {
		return datefrom;
	}
	public void setDatefrom(String datefrom) {
		this.datefrom = datefrom;
	}
	public String getDateto() {
		return dateto;
	}
	public void setDateto(String dateto) {
		this.dateto = dateto;
	}

}
