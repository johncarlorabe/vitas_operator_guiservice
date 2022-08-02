package com.psi.serviceconfig.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class BusinessCollection extends ModelCollection{
protected String accountnumber;
	@Override
	public boolean hasRows() {
		//DataRowCollection rows = SystemInfo.getDb().QueryDataRows("SELECT ID,BUSINESS,ACCOUNTNUMBER,BUSINESSCODE FROM TBLBUSINESS WHERE ACCOUNTNUMBER IN (SELECT ACCOUNTNUMBER FROM ADMDBMC.TBLACCOUNTINFO WHERE ROOT = ?)",SystemInfo.getDb().QueryScalar("SELECT ID FROM ADMDBMC.TBLACCOUNTINFO WHERE ACCOUNTNUMBER = ?", "", this.accountnumber));
		
		DataRowCollection rows = SystemInfo.getDb().QueryDataRows("SELECT ID,BUSINESS,B.ACCOUNTNUMBER,BUSINESSCODE, DECRYPT(AMOUNT,'sunev8clt1234567890', CS.ACCOUNTNUMBER) AS BALANCE FROM TBLBUSINESS B INNER JOIN ADMDBMC.TBLCURRENTSTOCK CS ON B.ACCOUNTNUMBER=CS.ACCOUNTNUMBER WHERE B.ACCOUNTNUMBER IN (SELECT B.ACCOUNTNUMBER FROM ADMDBMC.TBLACCOUNTINFO WHERE ROOT = ?) AND WALLETID=? ORDER BY B.BUSINESS ASC",SystemInfo.getDb().QueryScalar("SELECT ID FROM ADMDBMC.TBLACCOUNTINFO WHERE ACCOUNTNUMBER = ?", "", this.accountnumber), SystemInfo.getDb().QueryScalar("SELECT ID FROM ADMDBMC.TBLACCOUNTINFO WHERE ACCOUNTNUMBER = ?", "", this.accountnumber));
		
		if (!rows.isEmpty())
	     {
	    	 for(DataRow row: rows){	
		         ReportItem m = new ReportItem();
		         for (String key : row.keySet()) {
		 	        m.setProperty(key, row.getString(key).toString());
		 	       m.setProperty("BALANCE", LongUtil.toString(Long.parseLong(row.getString("BALANCE"))) == null ? "" : LongUtil.toString(Long.parseLong(row.getString("BALANCE").toString())));
		 	      }
		 	      add(m);
	    	 }
	     }
	     return rows.size() > 0;
	}
	public String getAccountnumber() {
		return accountnumber;
	}
	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}

}
