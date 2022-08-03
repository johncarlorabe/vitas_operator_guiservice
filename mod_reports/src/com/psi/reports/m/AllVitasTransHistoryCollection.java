package com.psi.reports.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.Logger;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class AllVitasTransHistoryCollection extends ModelCollection{

	protected String branch;
	protected String datefrom;
	protected String dateto;
	protected String accounttype;
	protected String cashier;
	protected String keyaccount;
	
	@Override
	public boolean hasRows() {
		String query = "SELECT * FROM VWALLVITASTRANSACTIONS T "
				+ "WHERE (TO_CHAR(TIMESTAMP,'YYYY-MM-DD') BETWEEN ? AND ? )"
				+ "AND (T.FRACCOUNT IN (SELECT ACCOUNTNUMBER FROM TBLBRANCHES WHERE KEYACCOUNT=?) "
				+ "OR T.TOACCOUNT IN (SELECT ACCOUNTNUMBER FROM TBLBRANCHES WHERE KEYACCOUNT=?) "
				+ "OR T.ACCOUNTNUMBER IN (SELECT ACCOUNTNUMBER FROM TBLUSERS WHERE STOREACCOUNTNUMBER IN (SELECT ACCOUNTNUMBER FROM TBLBRANCHES WHERE KEYACCOUNT=?)))";
		DataRowCollection r = SystemInfo.getDb().QueryDataRows(query,
				this.datefrom,this.dateto,
				this.keyaccount,
				this.keyaccount,
				this.keyaccount);
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
	    		 ReportItem m = new ReportItem();
	    		 for (String key : row.keySet()) {
			 	        m.setProperty(key, row.getString(key).toString());
			 	 }
		         m.setProperty("BALANCEBEFORE", row.getString("BALANCEBEFORE") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("BALANCEBEFORE").toString())));
		         m.setProperty("BALANCEAFTER", row.getString("BALANCEAFTER") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("BALANCEAFTER").toString())));
		         m.setProperty("AMOUNT", row.getString("AMOUNT") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("AMOUNT").toString())));
		         
		         add(m);
	    	 }
	     }
	     return r.size() > 0;
	}
	
	public boolean hasRowsSpecific() {
		Logger.LogServer("ALL VITAS REPORT FOR ~" +this.branch);
		String query = "SELECT * FROM VWALLVITASTRANSACTIONS T "
				+ "WHERE (TO_CHAR(TIMESTAMP,'YYYY-MM-DD') BETWEEN ? AND ?)"
				+ "AND (T.FRACCOUNT = ? "
				+ "OR T.TOACCOUNT = ? "
				+ "OR T.ACCOUNTNUMBER IN (SELECT ACCOUNTNUMBER FROM TBLUSERS WHERE STOREACCOUNTNUMBER = ?))";
		DataRowCollection r = SystemInfo.getDb().QueryDataRows(query,
				this.datefrom,this.dateto,
				this.branch,
				this.branch,
				this.branch);
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
	    		 ReportItem m = new ReportItem();
	    		 for (String key : row.keySet()) {
			 	        m.setProperty(key, row.getString(key).toString());
			 	 }
	    		 m.setProperty("BALANCEBEFORE", row.getString("BALANCEBEFORE") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("BALANCEBEFORE").toString())));
		         m.setProperty("BALANCEAFTER", row.getString("BALANCEAFTER") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("BALANCEAFTER").toString())));
		         m.setProperty("AMOUNT", row.getString("AMOUNT") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("AMOUNT").toString())));
		         
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

	public String getAccounttype() {
		return accounttype;
	}

	public void setAccounttype(String accounttype) {
		this.accounttype = accounttype;
	}

	public String getCashier() {
		return cashier;
	}

	public void setCashier(String cashier) {
		this.cashier = cashier;
	}

	public String getKeyaccount() {
		return keyaccount;
	}

	public void setKeyaccount(String keyaccount) {
		this.keyaccount = keyaccount;
	}
	
	
}
