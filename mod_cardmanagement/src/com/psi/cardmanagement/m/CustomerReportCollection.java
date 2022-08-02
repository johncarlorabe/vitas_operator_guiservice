package com.psi.cardmanagement.m;

import java.text.NumberFormat;
import java.util.Locale;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class CustomerReportCollection extends ModelCollection{

	protected String type;
	protected String datefrom;
	protected String dateto;
	protected String msisdn;
	protected String accountnumber;
	protected String module;
	

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public boolean hasRows() {
		
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM MJCSXP.VWALLVITASCARDTRANSACTIONS WHERE TIMESTAMP BETWEEN TO_DATE(?,'YYYY-MM-DD') AND TO_DATE(?,'YYYY-MM-DD') + 1 AND ACCOUNTNUMBER=? ORDER BY TIMESTAMP DESC",this.datefrom,this.dateto,this.accountnumber);
		 if (!r.isEmpty())
		 {
			 for(DataRow row: r){
				 ReportItem m = new ReportItem();
				 for (String key : row.keySet()) {
			 	        m.setProperty(key, row.getString(key).toString());
			 	 }
				 m.setProperty("AMOUNT", row.getString("AMOUNT") == null ? "" :LongUtil.toString(Long.parseLong(row.getString("AMOUNT").toString())));
				 m.setProperty("BALANCEBEFORE", row.getString("BALANCEBEFORE") == null ? "" :LongUtil.toString(Long.parseLong(row.getString("BALANCEBEFORE").toString())));
				 m.setProperty("BALANCEAFTER", row.getString("BALANCEAFTER") == null ? "" :LongUtil.toString(Long.parseLong(row.getString("BALANCEAFTER").toString())));
				  add(m);
			 }
		 }
		 return r.size() > 0;
	}
	
	public boolean filterByType() {
		
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM MJCSXP.VWALLVITASCARDTRANSACTIONS WHERE TIMESTAMP BETWEEN TO_DATE(?,'YYYY-MM-DD') AND TO_DATE(?,'YYYY-MM-DD') + 1 AND ACCOUNTNUMBER=? AND MODULE=? ORDER BY TIMESTAMP DESC",this.datefrom,this.dateto,this.accountnumber,this.type);
		 if (!r.isEmpty())
		 {
			 for(DataRow row: r){
				 ReportItem m = new ReportItem();
				 for (String key : row.keySet()) {
			 	        m.setProperty(key, row.getString(key).toString());
			 	 }
				 m.setProperty("AMOUNT", row.getString("AMOUNT") == null ? "" :LongUtil.toString(Long.parseLong(row.getString("AMOUNT").toString())));
				 m.setProperty("BALANCEBEFORE", row.getString("BALANCEBEFORE") == null ? "" :LongUtil.toString(Long.parseLong(row.getString("BALANCEBEFORE").toString())));
				 m.setProperty("BALANCEAFTER", row.getString("BALANCEAFTER") == null ? "" :LongUtil.toString(Long.parseLong(row.getString("BALANCEAFTER").toString())));
				 add(m);
			 }
		 }
		 return r.size() > 0;
	}

	
	public boolean hasRowsCashin() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLALLVITASCARDTRANSACTIONS WHERE TIMESTAMP BETWEEN TO_DATE(?,'YYYY-MM-DD') AND TO_DATE(?,'YYYY-MM-DD') + 1 AND ? IN (TOACCOUNT,FRACCOUNT) AND MODULENAME IN ('CARD CASH IN') ORDER BY TIMESTAMP DESC",this.datefrom,this.dateto,this.accountnumber);
		 if (!r.isEmpty())
		 {
			 for(DataRow row: r){
				 ReportItem m = new ReportItem();
				 for (String key : row.keySet()) {
			 	        m.setProperty(key, row.getString(key).toString());
			 	 }
				 m.setProperty("AMOUNT", row.getString("AMOUNT") == null ? "" :LongUtil.toString(Long.parseLong(row.getString("AMOUNT").toString())));
				 m.setProperty("DESTBALANCEBEFORE", row.getString("DESTBALANCEBEFORE") == null ? "" :LongUtil.toString(Long.parseLong(row.getString("DESTBALANCEBEFORE").toString())));
				 m.setProperty("DESTBALANCEAFTER", row.getString("DESTBALANCEAFTER") == null ? "" :LongUtil.toString(Long.parseLong(row.getString("DESTBALANCEAFTER").toString())));
				 m.setProperty("SOURCEBALANCEBEFORE", row.getString("SRCBALANCEBEFORE") == null ? "" :LongUtil.toString(Long.parseLong(row.getString("SRCBALANCEBEFORE").toString())));
				 m.setProperty("SOURCEBALANCEAFTER", row.getString("SRCBALANCEAFTER") == null ? "" :LongUtil.toString(Long.parseLong(row.getString("SRCBALANCEAFTER").toString())));
				  add(m);
			 }
		 }
		 return r.size() > 0;
	}
	
	
	public boolean hasRowsCashout() {

		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLALLVITASCARDTRANSACTIONS WHERE TIMESTAMP BETWEEN TO_DATE(?,'YYYY-MM-DD') AND TO_DATE(?,'YYYY-MM-DD') + 1 AND ? IN (TOACCOUNT,FRACCOUNT) AND MODULENAME IN ('CARD CASH OUT') ORDER BY TIMESTAMP DESC",this.datefrom,this.dateto,this.accountnumber);
		 if (!r.isEmpty())
		 {
			 for(DataRow row: r){
				 ReportItem m = new ReportItem();
				 for (String key : row.keySet()) {
			 	        m.setProperty(key, row.getString(key).toString());
			 	 }
				 m.setProperty("AMOUNT", row.getString("AMOUNT") == null ? "" :LongUtil.toString(Long.parseLong(row.getString("AMOUNT").toString())));
				 m.setProperty("DESTBALANCEBEFORE", row.getString("DESTBALANCEBEFORE") == null ? "" :LongUtil.toString(Long.parseLong(row.getString("DESTBALANCEBEFORE").toString())));
				 m.setProperty("DESTBALANCEAFTER", row.getString("DESTBALANCEAFTER") == null ? "" :LongUtil.toString(Long.parseLong(row.getString("DESTBALANCEAFTER").toString())));
				 m.setProperty("SOURCEBALANCEBEFORE", row.getString("SRCBALANCEBEFORE") == null ? "" :LongUtil.toString(Long.parseLong(row.getString("SRCBALANCEBEFORE").toString())));
				 m.setProperty("SOURCEBALANCEAFTER", row.getString("SRCBALANCEAFTER") == null ? "" :LongUtil.toString(Long.parseLong(row.getString("SRCBALANCEAFTER").toString())));
				 add(m);
			 }
		 }
		 return r.size() > 0;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getAccountnumber() {
		return accountnumber;
	}

	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}

	

	
	
}
