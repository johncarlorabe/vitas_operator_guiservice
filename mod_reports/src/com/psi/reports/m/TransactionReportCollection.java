package com.psi.reports.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

public class TransactionReportCollection extends ModelCollection{

	protected String id;
	protected String branch;
	protected String datefrom;
	protected String dateto;
	@Override
	public boolean hasRows() {
		DataRow acct = SystemInfo.getDb().QueryDataRow("SELECT ACCOUNTNUMBER,USERSLEVEL,USERNAME FROM TBLUSERS WHERE USERID = ?", this.id);
		String acctno = acct.get("ACCOUNTNUMBER").toString();
		if(acct.getString("USERSLEVEL").equals("MANAGER")){
			DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLALLTRANSACTIONMANAGER WHERE (FRACCOUNT=? OR TOACCOUNT = ? OR FROUTLET =? OR TOOUTLET = ?) AND TO_CHAR(TIMESTAMP,'YYYY-MM-DD') BETWEEN ? AND ?",acctno,acctno,acctno,acctno,this.datefrom,this.dateto);
		     
		     if (!r.isEmpty())
		     {
		    	 for(DataRow row: r){
		    		 ReportItem m = new ReportItem();
		    		 for (String key : row.keySet()) {
				 	        m.setProperty(key, row.getString(key).toString());
				 	 }
			         m.setProperty("AMOUNT", row.getString("AMOUNT") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("AMOUNT").toString())));
			         m.setProperty("DESTBALANCEBEFORE", row.getString("DESTBALANCEBEFORE") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("DESTBALANCEBEFORE").toString())));
			         m.setProperty("DESTBALANCEAFTER", row.getString("DESTBALANCEAFTER") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("DESTBALANCEAFTER").toString())));
			         m.setProperty("SOURCEBALANCEBEFORE", row.getString("SOURCEBALANCEBEFORE") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("SOURCEBALANCEBEFORE").toString())));
			         m.setProperty("SOURCEBALANCEAFTER", row.getString("SOURCEBALANCEAFTER") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("SOURCEBALANCEAFTER").toString())));
			         m.setProperty("DISCOUNT", row.getString("DISCOUNT") == null || row.getString("DISCOUNT") == "" ? "" : LongUtil.toString(Long.parseLong(row.getString("DISCOUNT").toString())));
				     m.setProperty("CHARGES", row.getString("CHARGES") == null || row.getString("CHARGES") == "" ? "" : LongUtil.toString(Long.parseLong(row.getString("CHARGES").toString())));
			         add(m);
		    	 }
		     }
		     return r.size() > 0;
		}else{
			DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLTRANSACTIONSOUTLET WHERE (FRACCOUNT=? OR TOACCOUNT = ?) AND TO_CHAR(TIMESTAMP,'YYYY-MM-DD') BETWEEN ? AND ? AND UPPER(CREATEDBY) = ?",acctno,acctno,this.datefrom,this.dateto,acct.getString("USERNAME").toUpperCase());
		     
		     if (!r.isEmpty())
		     {
		    	 for(DataRow row: r){
		    		 ReportItem m = new ReportItem();
		    		 for (String key : row.keySet()) {
				 	        m.setProperty(key, row.getString(key).toString());
				 	 }
			         m.setProperty("AMOUNT", row.getString("AMOUNT") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("AMOUNT").toString())));
			         m.setProperty("DESTBALANCEBEFORE", row.getString("DESTBALANCEBEFORE") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("DESTBALANCEBEFORE").toString())));
			         m.setProperty("DESTBALANCEAFTER", row.getString("DESTBALANCEAFTER") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("DESTBALANCEAFTER").toString())));
			         m.setProperty("SOURCEBALANCEBEFORE", row.getString("SOURCEBALANCEBEFORE") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("SOURCEBALANCEBEFORE").toString())));
			         m.setProperty("SOURCEBALANCEAFTER", row.getString("SOURCEBALANCEAFTER") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("SOURCEBALANCEAFTER").toString())));
			         m.setProperty("DISCOUNT", row.getString("DISCOUNT") == null || row.getString("DISCOUNT") == "" ? "" : LongUtil.toString(Long.parseLong(row.getString("DISCOUNT").toString())));
				     m.setProperty("CHARGES", row.getString("CHARGES") == null || row.getString("CHARGES") == "" ? "" : LongUtil.toString(Long.parseLong(row.getString("CHARGES").toString())));
			         add(m);
		    	 }
		     }
		     return r.size() > 0;
		}
	}
	
	public boolean exists() {
		boolean ret = false;
		DataRow r = SystemInfo.getDb().QueryDataRow("SELECT STORE FROM TBLUSERS WHERE USERID = ?", this.id);
		if(r!=null && !r.isEmpty()){
			ret = true;
		}
		return ret;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
