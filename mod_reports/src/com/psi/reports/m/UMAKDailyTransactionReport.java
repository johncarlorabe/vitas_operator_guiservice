package com.psi.reports.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class UMAKDailyTransactionReport extends ModelCollection{

	@Override
	public boolean hasRows() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT UT.TIMESTAMP, TBP.REFERENCEID,UT.TIMESTAMP AS POSTEDDATE, SOAREFERENCE,TOTALAMOUNT, TOKENFEE, MISCFEE, NSTPFEE, ASSESSMENTFEE, STUDENTNUMBER, FIRSTNAME, LASTNAME, BRAND, CASHIER, IDFEE, UT.EMAIL FROM TBLTRANSACTIONSBILLSPAYMENT TBP INNER JOIN UMAK.TBLTRANSACTIONS UT ON TBP.REFERENCEID=UT.REQUESTID WHERE BRAND='UMAK' AND TBP.TIMESTAMP > TRUNC(SYSDATE)");
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
		         ReportItem m = new ReportItem();	         
		         m.setProperty("TIMESTAMP", row.getString("TIMESTAMP").toString());
		         m.setProperty("POSTEDDATE", row.getString("POSTEDDATE").toString());
		         m.setProperty("SOAREFERENCE", row.getString("SOAREFERENCE").toString());
		         m.setProperty("REFERENCEID", row.getString("REFERENCEID").toString());
		         m.setProperty("TOTALAMOUNT", row.getString("TOTALAMOUNT") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("TOTALAMOUNT").toString())));
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
	    	 }
	     }
	     return r.size() > 0;
	}

}
