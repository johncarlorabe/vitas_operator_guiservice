package com.psi.purchases.m;

import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.Model;

public class ClaimRemittance extends Model{
	protected String ordernumber;
	protected String username;
	
	public boolean update(){
		return SystemInfo.getDb().QueryUpdate("UPDATE TBLORDERREQUEST SET STATUS='COMPLETED',CLAIMBRANCH=?,STATUSTIMESTAMP=SYSDATE WHERE TRANSACTIONTYPE='local' AND ORDERNUMBER=?", this.username,this.ordernumber)>0;
	}
	public boolean exist(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLORDERREQUEST WHERE ORDERNUMBER=?", this.ordernumber).size()>0;
	}
	public String getOrdernumber() {
		return ordernumber;
	}
	public void setOrdernumber(String ordernumber) {
		this.ordernumber = ordernumber;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	

}
