package com.psi.reports.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class FailedTransAdminCollection extends ModelCollection{
	protected String branch;
	protected String datefrom;
	protected String dateto;
	protected String accountnumber;
	@Override
	public boolean hasRows() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLFAILEDTRANSACTIONS WHERE TO_CHAR(TIMESTAMP,'YYYY-MM-DD') BETWEEN ? AND ? AND MAINWALLET = ?",this.datefrom,this.dateto,SystemInfo.getDb().QueryScalar("SELECT ID FROM ADMDBMC.TBLACCOUNTINFO WHERE ACCOUNTNUMBER = ?", "", this.accountnumber));
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
	    		 ReportItem m = new ReportItem();
	    		 for (String key : row.keySet()) {
			 	        m.setProperty(key, row.getString(key).toString());
			 	 }
		         m.setProperty("AMOUNT", row.getString("AMOUNT") == null ? "0.00" : LongUtil.toString(Long.parseLong(row.getString("AMOUNT").toString())));
		         m.setProperty("DESTBALANCEBEFORE", row.getString("DESTBALANCEBEFORE") == null ? "0.00" : LongUtil.toString(Long.parseLong(row.getString("DESTBALANCEBEFORE").toString())));
		         m.setProperty("DESTBALANCEAFTER", row.getString("DESTBALANCEAFTER") == null ? "0.00" : LongUtil.toString(Long.parseLong(row.getString("DESTBALANCEAFTER").toString())));
		         m.setProperty("SOURCEBALANCEBEFORE", row.getString("SRCBALANCEBEFORE") == null ? "0.00" : LongUtil.toString(Long.parseLong(row.getString("SRCBALANCEBEFORE").toString())));
		         m.setProperty("SOURCEBALANCEAFTER", row.getString("SRCBALANCEAFTER") == null ? "0.00" : LongUtil.toString(Long.parseLong(row.getString("SRCBALANCEAFTER").toString())));
		         
		         add(m);
	    	 }
	     }
	     return r.size() > 0;
	}
	
	public boolean getTransCol() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLFAILEDTRANSACTIONS WHERE (FRACCOUNT=? OR TOACCOUNT=?) AND TO_CHAR(TIMESTAMP,'YYYY-MM-DD') BETWEEN ? AND ? AND MAINWALLET = ?",this.branch,this.datefrom,this.dateto,SystemInfo.getDb().QueryScalar("SELECT ID FROM ADMDBMC.TBLACCOUNTINFO WHERE ACCOUNTNUMBER=?", "", this.accountnumber));
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
	    		 ReportItem m = new ReportItem();
	    		 for (String key : row.keySet()) {
			 	        m.setProperty(key, row.getString(key).toString());
			 	 }
		         m.setProperty("AMOUNT", row.getString("AMOUNT") == null ? "0.00" : LongUtil.toString(Long.parseLong(row.getString("AMOUNT").toString())));
		         m.setProperty("DESTBALANCEBEFORE", row.getString("DESTBALANCEBEFORE") == null ? "0.00" : LongUtil.toString(Long.parseLong(row.getString("DESTBALANCEBEFORE").toString())));
		         m.setProperty("DESTBALANCEAFTER", row.getString("DESTBALANCEAFTER") == null ? "0.00" : LongUtil.toString(Long.parseLong(row.getString("DESTBALANCEAFTER").toString())));
		         m.setProperty("SOURCEBALANCEBEFORE", row.getString("SRCBALANCEBEFORE") == null ? "0.00" : LongUtil.toString(Long.parseLong(row.getString("SRCBALANCEBEFORE").toString())));
		         m.setProperty("SOURCEBALANCEAFTER", row.getString("SRCBALANCEAFTER") == null ? "0.00" : LongUtil.toString(Long.parseLong(row.getString("SRCBALANCEAFTER").toString())));
		         
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

	public String getAccountnumber() {
		return accountnumber;
	}

	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}
	
}
