package com.psi.reports.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class BillsPaymentPerBillerCollection extends ModelCollection {

	protected String billercode;
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
			     add(m);
	    	 }
	     }
	     return r.size() > 0;
	}
	
	public boolean getBillsPayCol() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLTRANSACTIONSBILLSPAYMENT WHERE BRAND=? AND TO_CHAR(TIMESTAMP,'YYYY-MM-DD') BETWEEN ? AND ?",this.billercode,this.datefrom,this.dateto);
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
	    		 ReportItem m = new ReportItem();
		         m.setProperty("REFERENCEID", row.getString("REFERENCEID") == null ? "" : row.getString("REFERENCEID").toString());
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
			     add(m);
	    	 }
	     }
	     return r.size() > 0;
	}
	
	
	public boolean getBillsPayUMAK() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM VWUMAKTRANSACTIONS WHERE BRAND=? AND TO_CHAR(TIMESTAMP,'YYYY-MM-DD') BETWEEN ? AND ?",this.billercode,this.datefrom,this.dateto);
		
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
		         ReportItem m = new ReportItem();
		         
		         for (String key : row.keySet()) {
	    	          m.setProperty(key, row.getString(key).toString());
	    	     }
		         m.setProperty("AMOUNT", row.getString("AMOUNT") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("AMOUNT").toString())));
		         m.setProperty("SOURCEBALANCEBEFORE", row.getString("SOURCEBALANCEBEFORE") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("SOURCEBALANCEBEFORE").toString())));
		         m.setProperty("SOURCEBALANCEAFTER", row.getString("SOURCEBALANCEAFTER") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("SOURCEBALANCEAFTER").toString())));
		         m.setProperty("DISCOUNT", row.getString("DISCOUNT") == null || row.getString("DISCOUNT") == "" ? "" : LongUtil.toString(Long.parseLong(row.getString("DISCOUNT").toString())));
			     m.setProperty("CHARGES", row.getString("CHARGES") == null || row.getString("CHARGES") == "" ? "" : LongUtil.toString(Long.parseLong(row.getString("CHARGES").toString())));
			     m.setProperty("PAIDAMOUNT", row.getString("PAIDAMOUNT") == null || row.getString("PAIDAMOUNT") == "" ? "" : LongUtil.toString(Long.parseLong(row.getString("PAIDAMOUNT").toString())));
		         add(m);
	    	 }
	    	/* for(DataRow row: r){
	    		 ReportItem m = new ReportItem();
	    		 m.setProperty("TIMESTAMP", row.getString("TIMESTAMP").toString());
		         m.setProperty("POSTEDDATE", row.getString("POSTEDDATE").toString());
		         m.setProperty("SOAREFERENCE", row.getString("SOAREFERENCE").toString());
		         m.setProperty("REFERENCEID", row.getString("REFERENCEID").toString());
		         m.setProperty("TOTALAMOUNT", row.getString("TOTALAMOUNT") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("TOTALAMOUNT").toString())));
		         m.setProperty("CHARGES", row.getString("CHARGES") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("CHARGES").toString())));
		         m.setProperty("TOKENFEE", row.getString("TOKENFEE") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("TOKENFEE").toString())));
		         m.setProperty("MISCFEE", row.getString("MISCFEE") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("MISCFEE").toString())));
		         m.setProperty("NSTPFEE", row.getString("NSTPFEE") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("NSTPFEE").toString())));
		         m.setProperty("IDFEE", row.getString("IDFEE") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("IDFEE").toString())));
		         m.setProperty("ASSESSMENTFEE", row.getString("ASSESSMENTFEE") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("ASSESSMENTFEE").toString())));
		         m.setProperty("STUDENTNUMBER", row.getString("STUDENTNUMBER").toString());
		         m.setProperty("FIRSTNAME", row.getString("FIRSTNAME").toString());
		         m.setProperty("LASTNAME", row.getString("LASTNAME").toString());
		         m.setProperty("BRAND", row.getString("BRAND").toString());
		         m.setProperty("CASHIER", row.getString("CASHIER").toString());
		         m.setProperty("EMAIL", row.getString("EMAIL").toString());
			     add(m);
	    	 }*/
	     }
	     return r.size() > 0;
	}

	

	public String getBillercode() {
		return billercode;
	}

	public void setBillercode(String billercode) {
		this.billercode = billercode;
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
