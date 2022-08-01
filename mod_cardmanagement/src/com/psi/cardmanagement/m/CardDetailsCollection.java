package com.psi.cardmanagement.m;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Properties;

import com.psi.cardmanagement.utils.OtherProperties;
import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.common.Util;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;
import com.tlc.gui.modules.session.UISession;

public class CardDetailsCollection extends ModelCollection{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String msisdn;
	
	@Override
	public boolean hasRows() {
		OtherProperties prop = new OtherProperties();
		DataRow row = SystemInfo.getDb().QueryDataRow("SELECT FIRSTNAME,LASTNAME,MIDDLENAME,TO_CHAR(REGDATE, 'DD/MM/YYYY HH12:MI AM') REGDATE,DECRYPT (TYPE, ? , ACCOUNTNUMBER) TYPE,ACCOUNTNUMBER,STATUS,ID,EMAIL,MSISDN,LOCKED FROM ADMDBMC.TBLACCOUNTINFO WHERE MSISDN=? AND DECRYPT (TYPE, ? , ACCOUNTNUMBER) IN  ('subscriber')", SystemInfo.getDb().getCrypt(), this.msisdn, SystemInfo.getDb().getCrypt());
		DataRow currentbal = SystemInfo.getDb().QueryDataRow("SELECT DECRYPT(AMOUNT, ? , ACCOUNTNUMBER) CURRENTBAL  FROM ADMDBMC.TBLCURRENTSTOCK A WHERE ACCOUNTNUMBER = ? AND WALLETID = 1", SystemInfo.getDb().getCrypt(), row.getString("ACCOUNTNUMBER"));
		
		 	if (!row.isEmpty()) {
		 			ReportItem m = new ReportItem();
					for (String key : row.keySet()) {
						m.setProperty(key, row.getString(key));
					}
					m.setProperty("CURRENTBAL", currentbal.getString("CURRENTBAL") == null ? "" :LongUtil.toString(Long.parseLong(currentbal.getString("CURRENTBAL").toString())));
					this.add(m);
			}
			
			return this.size() > 0;
	}
	
	
	
	
	public boolean getBalance() {
		OtherProperties prop = new OtherProperties();
		DataRow currentbal= currentbal = SystemInfo.getDb().QueryDataRow("SELECT DECRYPT(AMOUNT, ? , A.ACCOUNTNUMBER) CURRENTBAL  FROM ADMDBMC.TBLCURRENTSTOCK A INNER JOIN ADMDBMC.TBLACCOUNTINFO AI ON A.ACCOUNTNUMBER=AI.ACCOUNTNUMBER WHERE MSISDN = ? AND WALLETID = 1", SystemInfo.getDb().getCrypt(), this.msisdn);
		
	 	if (!currentbal.isEmpty()) {
				ReportItem m = new ReportItem();					
				m.setProperty("CURRENTBAL", currentbal.getString("CURRENTBAL") == null ? "" :LongUtil.toString(Long.parseLong(currentbal.getString("CURRENTBAL").toString())));
				this.add(m);
		}
		return this.size() > 0;
	}
	
	public boolean validate(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM ADMDBMC.TBLACCOUNTINFO  WHERE MSISDN = ? AND DECRYPT (TYPE, ? , ACCOUNTNUMBER) NOT IN ('subscriber', 'premium')", SystemInfo.getDb().getCrypt(),this.msisdn).size()>0;				
	}
	



	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	
	

	

}
