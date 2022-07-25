package com.psi.cardmanagement.m;
import com.psi.cardmanagement.utils.Customers;
import com.tlc.common.SystemInfo;

public class ManageRegisteredCustomer extends Customers{
	
	private String msisdn;
	private String message;

	//FOR UPDATE STATUS
	public boolean updateStatus(String arg0){
		StringBuilder query = new StringBuilder("BEGIN\n");
		query.append("UPDATE ADMDBMC.TBLACCOUNTINFO SET STATUS=? WHERE MSISDN = ?; \n");
		query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		return SystemInfo.getDb().QueryUpdate(query.toString(), this.status, this.msisdn)>0;
	}
	
	public boolean updateLockStatus(String arg0){
		StringBuilder query = new StringBuilder("BEGIN\n");
		query.append("UPDATE ADMDBMC.TBLACCOUNTINFO SET LOCKED=? WHERE MSISDN = ?; \n");
		query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		return SystemInfo.getDb().QueryUpdate(query.toString(), this.status, this.msisdn)>0;
	}
	
	public boolean validate(){
		return SystemInfo.getDb().QueryDataRow("SELECT DECRYPT(PASSWORD, ? , USERNAME) AS PASSWORD FROM TBLUSERS WHERE PASSWORD = ENCRYPT( ? , ? , USERNAME)  AND USERNAME = ? " , SystemInfo.getDb().getCrypt(), this.password, SystemInfo.getDb().getCrypt(), this.username).size()>0;
	}
	
	public boolean exists(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM ADMDBMC.TBLACCOUNTINFO WHERE MSISDN = ?", this.msisdn).size()>0;
	}
	
    
    
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
		 
}
