package com.psi.reports.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class AdminBillsPaymentCollection extends ModelCollection {

	protected String branch;
	protected String datefrom;
	protected String dateto;
	
	@Override
	public boolean hasRows() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLTRANSACTIONSBILLSPAYMENT WHERE TO_CHAR(TIMESTAMP,'YYYY-MM-DD') BETWEEN ? AND ?",this.datefrom,this.dateto);
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
	    		 ReportItem m = new ReportItem();
		         m.setProperty("REFERENCEID", row.getString("REFERENCEID") == null ? "" : row.getString("REFERENCEID").toString());
		         m.setProperty("REQUESTID", row.getString("REQUESTID") == null ? "" : row.getString("REQUESTID").toString());
		         m.setProperty("TIMESTAMP", row.getString("TIMESTAMP") == null ? "" : row.getString("TIMESTAMP").toString());
		         m.setProperty("ORDERNUMBER", row.getString("ORDERNUMBER") == null ? "" : row.getString("ORDERNUMBER").toString());
		         m.setProperty("NAME", row.getString("NAME") == null ? "" : row.getString("NAME").toString());
		         m.setProperty("TYPE", row.getString("TYPE") == null ? "" : row.getString("TYPE").toString());
		         m.setProperty("ACCOUNTNUMBER", row.getString("ACCOUNTNUMBER") == null ? "" : row.getString("ACCOUNTNUMBER").toString());
			     m.setProperty("AMOUNT", row.getString("AMOUNT") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("AMOUNT").toString())));
		         m.setProperty("SOURCEBALANCEBEFORE", row.getString("SOURCEBALANCEBEFORE") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("SOURCEBALANCEBEFORE").toString())));
		         m.setProperty("SOURCEBALANCEAFTER", row.getString("SOURCEBALANCEAFTER") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("SOURCEBALANCEAFTER").toString())));
		         m.setProperty("SOURCEBRANCH", row.getString("SOURCEBRANCH") == null ? "" : row.getString("SOURCEBRANCH").toString());
		         m.setProperty("SOURCEBRANCHCODE", row.getString("SOURCEBRANCHCODE") == null ? "" : row.getString("SOURCEBRANCHCODE").toString());
			     m.setProperty("BRAND", row.getString("BRAND") == null ? "" : row.getString("BRAND").toString());
			     m.setProperty("CASHIER", row.getString("CASHIER") == null ? "" : row.getString("CASHIER").toString());
			     m.setProperty("MESSAGE", row.getString("MESSAGE") == null ? "" : row.getString("MESSAGE").toString());
			     m.setProperty("TPAID", row.getString("TPAID") == null ? "" : row.getString("TPAID").toString());
			     m.setProperty("DISCOUNT", row.getString("DISCOUNT") == null || row.getString("DISCOUNT") == "" ? "" : LongUtil.toString(Long.parseLong(row.getString("DISCOUNT").toString())));
			     m.setProperty("CHARGES", row.getString("CHARGES") == null || row.getString("CHARGES") == "" ? "" : LongUtil.toString(Long.parseLong(row.getString("CHARGES").toString())));
			     m.setProperty("BILLACCOUNTNUMBER", row.getString("BILLACCOUNTNUMBER") == null ? "" : row.getString("BILLACCOUNTNUMBER").toString());
			     add(m);
	    	 }
	     }
	     return r.size() > 0;
	}
	
	public boolean getBPColBranches() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLTRANSACTIONSBILLSPAYMENT WHERE ACCOUNTNUMBER=? AND TO_CHAR(TIMESTAMP,'YYYY-MM-DD') BETWEEN ? AND ?",this.branch,this.datefrom,this.dateto);
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
	    		 ReportItem m = new ReportItem();
		         m.setProperty("REFERENCEID", row.getString("REFERENCEID") == null ? "" : row.getString("REFERENCEID").toString());
		         m.setProperty("REQUESTID", row.getString("REQUESTID") == null ? "" : row.getString("REQUESTID").toString());
		         m.setProperty("TIMESTAMP", row.getString("TIMESTAMP") == null ? "" : row.getString("TIMESTAMP").toString());
		         m.setProperty("ORDERNUMBER", row.getString("ORDERNUMBER") == null ? "" : row.getString("ORDERNUMBER").toString());
		         m.setProperty("NAME", row.getString("NAME") == null ? "" : row.getString("NAME").toString());
		         m.setProperty("TYPE", row.getString("TYPE") == null ? "" : row.getString("TYPE").toString());
		         m.setProperty("ACCOUNTNUMBER", row.getString("ACCOUNTNUMBER") == null ? "" : row.getString("ACCOUNTNUMBER").toString());
			     m.setProperty("AMOUNT", row.getString("AMOUNT") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("AMOUNT").toString())));
		         m.setProperty("SOURCEBALANCEBEFORE", row.getString("SOURCEBALANCEBEFORE") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("SOURCEBALANCEBEFORE").toString())));
		         m.setProperty("SOURCEBALANCEAFTER", row.getString("SOURCEBALANCEAFTER") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("SOURCEBALANCEAFTER").toString())));
		         m.setProperty("SOURCEBRANCH", row.getString("SOURCEBRANCH") == null ? "" : row.getString("SOURCEBRANCH").toString());
		         m.setProperty("SOURCEBRANCHCODE", row.getString("SOURCEBRANCHCODE") == null ? "" : row.getString("SOURCEBRANCHCODE").toString());
			     m.setProperty("BRAND", row.getString("BRAND") == null ? "" : row.getString("BRAND").toString());
			     m.setProperty("CASHIER", row.getString("CASHIER") == null ? "" : row.getString("CASHIER").toString());
			     m.setProperty("MESSAGE", row.getString("MESSAGE") == null ? "" : row.getString("MESSAGE").toString());
			     m.setProperty("TPAID", row.getString("TPAID") == null ? "" : row.getString("TPAID").toString());
			     m.setProperty("DISCOUNT", row.getString("DISCOUNT") == null || row.getString("DISCOUNT") == "" ? "" : LongUtil.toString(Long.parseLong(row.getString("DISCOUNT").toString())));
			     m.setProperty("CHARGES", row.getString("CHARGES") == null || row.getString("CHARGES") == "" ? "" : LongUtil.toString(Long.parseLong(row.getString("CHARGES").toString())));
			     m.setProperty("BILLACCOUNTNUMBER", row.getString("BILLACCOUNTNUMBER") == null ? "" : row.getString("BILLACCOUNTNUMBER").toString());
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
