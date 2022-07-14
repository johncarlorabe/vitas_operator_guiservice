package com.psi.reports.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class AdminEwalletPaymentsCollection extends ModelCollection{
	protected String branch;
	protected String datefrom;
	protected String dateto;

	@Override
	public boolean hasRows() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT TP.* FROM TBLTRANSACTIONPAYMENT TP INNER JOIN SOLBPH.TBLBRANCHES B1 ON B1.ACCOUNTNUMBER=TP.FRACCOUNT WHERE TO_CHAR(TIMESTAMP,'YYYY-MM-DD') BETWEEN ? AND ?",this.datefrom,this.dateto);
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
	    		 ReportItem m = new ReportItem();
		         m.setProperty("REFERENCEID", row.getString("REFERENCEID") == null ? "" : row.getString("REFERENCEID").toString());
		         m.setProperty("AMOUNT", row.getString("AMOUNT") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("AMOUNT").toString())));
		         m.setProperty("TIMESTAMP", row.getString("TIMESTAMP") == null ? "" : row.getString("TIMESTAMP").toString());
		         m.setProperty("FRACCOUNT", row.getString("FRACCOUNT") == null ? "" : row.getString("FRACCOUNT").toString());
			     m.setProperty("CASHIER", row.getString("CASHIER") == null ? "" : row.getString("CASHIER").toString());
			     m.setProperty("MESSAGE", row.getString("MESSAGE") == null ? "" : row.getString("MESSAGE").toString());
			     m.setProperty("PRODUCT", row.getString("PRODUCT") == null ? "" : row.getString("PRODUCT").toString());
			     m.setProperty("DESTACCOUNT", row.getString("DESTACCOUNT") == null ? "" : row.getString("DESTACCOUNT").toString());
			      add(m);
	    	 }
	     }
	     return r.size() > 0;
	}
	public boolean getEPColBranches() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLTRANSACTIONPAYMENT WHERE FRACCOUNT=? AND  TO_CHAR(TIMESTAMP,'YYYY-MM-DD') BETWEEN ? AND ?",this.branch,this.datefrom,this.dateto);
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
	    		 ReportItem m = new ReportItem();
	    		 m.setProperty("REFERENCEID", row.getString("REFERENCEID") == null ? "" : row.getString("REFERENCEID").toString());
		         m.setProperty("AMOUNT", row.getString("AMOUNT") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("AMOUNT").toString())));
		         m.setProperty("TIMESTAMP", row.getString("TIMESTAMP") == null ? "" : row.getString("TIMESTAMP").toString());
		         m.setProperty("FRACCOUNT", row.getString("FRACCOUNT") == null ? "" : row.getString("FRACCOUNT").toString());
			     m.setProperty("CASHIER", row.getString("CASHIER") == null ? "" : row.getString("CASHIER").toString());
			     m.setProperty("MESSAGE", row.getString("MESSAGE") == null ? "" : row.getString("MESSAGE").toString());
			     m.setProperty("PRODUCT", row.getString("PRODUCT") == null ? "" : row.getString("PRODUCT").toString());
			     m.setProperty("DESTACCOUNT", row.getString("DESTACCOUNT") == null ? "" : row.getString("DESTACCOUNT").toString());
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
