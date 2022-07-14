package com.psi.reports.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class AllocationHistoryCollection extends ModelCollection{

	protected String id;
	protected String branch;
	protected String datefrom;
	protected String dateto;
	@Override
	public boolean hasRows() {
		DataRow acct = SystemInfo.getDb().QueryDataRow("SELECT ACCOUNTNUMBER FROM TBLUSERS WHERE USERID = ?", this.id);
		String acctno = acct.get("ACCOUNTNUMBER").toString();
		
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLALLOCTRANSACTIONS WHERE TOACCOUNT=? AND TO_CHAR(TIMESTAMP,'YYYY-MM-DD') BETWEEN ? AND ?",acctno,this.datefrom,this.dateto);
	     
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
		         
		         add(m);
	    	 }
	     }
	     return r.size() > 0;
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
