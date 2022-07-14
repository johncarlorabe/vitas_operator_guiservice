package com.psi.branch.m;

import com.psi.branch.utils.Branch;
import com.tlc.common.DataRow;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.session.UISession;

public class ManageBranch extends Branch{

	public boolean approve(){
		DataRow rr = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLBRANCHES WHERE ACCOUNTNUMBER=?", this.accountnumber);
		if(!rr.getString("KEYACCOUNT").equals("834591471124")){
		DataRow row = SystemInfo.getDb().QueryDataRow("SELECT AI.*,DECRYPT(FQN,?,ACCOUNTNUMBER) ALIAS FROM ADMDBMC.TBLACCOUNTINFOPNDG AI WHERE ACCOUNTNUMBER = ?", SystemInfo.getDb().getCrypt(),this.accountnumber);
		
		if(!row.isEmpty()){
			StringBuilder query = new StringBuilder("BEGIN\n");
			query.append("INSERT INTO ADMDBMC.TBLACCOUNTINFO (ID, ACCOUNTNUMBER, STATUS, LOCKED, EMAIL, FQN, NETWORK, MSISDN, TYPE, "
					+ "	REGDATE, LANGUAGE, PASSWORD, OTP, LASTNAME, FIRSTNAME, MIDDLENAME, TIN, "
					+ "	SSS, ROOT, PARENT, CITY, SPECIFICADDRESS, POSTALCODE, COORDINATES) "
					+" VALUES "
					+" ( ?, ?, 'ACTIVE', 'NO', ?, ?, ?, ?, ?, SYSDATE, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); \n");
			query.append("UPDATE TBLBRANCHES SET ACCOUNTNUMBER=?,STATUS = 1 WHERE CONTACTNUMBER = ? AND BRANCH=?;\n");
			query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
			 
			int res = SystemInfo.getDb().QueryUpdate(query.toString(),row.getString("ID"),row.getString("ACCOUNTNUMBER"),row.getString("EMAIL"),row.getString("FQN"),row.getString("NETWORK"),row.getString("MSISDN"),row.getString("TYPE"),
					row.getString("LANGUAGE"),row.getString("PASSWORD"),row.getString("OTP"),row.getString("LASTNAME"),row.getString("FIRSTNAME"),row.getString("MIDDLENAME"),row.getString("TIN"),
					row.getString("SSS"),row.getString("ROOT"),row.getString("PARENT"),row.getString("CITY"),row.getString("SPECIFICADDRESS"),row.getString("POSTALCODE"),row.getString("COORDINATES"),
					row.getString("ACCOUNTNUMBER"),row.getString("MSISDN"),row.getString("ALIAS"));
			
			if(res>0){
				return SystemInfo.getDb().QueryUpdate("DELETE FROM ADMDBMC.TBLACCOUNTINFOPNDG WHERE ACCOUNTNUMBER =?", this.accountnumber)>0;
			}else{
				return false;
			}
		}else{
			return false;
		}
		}else{
				return SystemInfo.getDb().QueryUpdate("UPDATE TBLBRANCHES SET STATUS = 1 WHERE ACCOUNTNUMBER=?",this.accountnumber)>0;
		}
	}
	
	public boolean reject(){
		DataRow rr = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLBRANCHES WHERE ACCOUNTNUMBER=?", this.accountnumber);
		if(!rr.getString("KEYACCOUNT").equals("834591471124")){
		
		DataRow row = SystemInfo.getDb().QueryDataRow("SELECT AI.*,DECRYPT(FQN,?,ACCOUNTNUMBER) ALIAS FROM ADMDBMC.TBLACCOUNTINFOPNDG AI WHERE ACCOUNTNUMBER = ?", SystemInfo.getDb().getCrypt(),this.accountnumber);
		
		StringBuilder query = new StringBuilder("BEGIN\n");
		query.append("UPDATE ADMDBMC.TBLACCOUNTINFOPNDG SET KYCSTATUS='REJECTED' WHERE ACCOUNTNUMBER = ?; \n");
		query.append("UPDATE TBLBRANCHES SET ACCOUNTNUMBER=?,REMARKS=?,STATUS='4' WHERE CONTACTNUMBER = ? AND BRANCH = ?; \n");
		query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		return SystemInfo.getDb().QueryUpdate(query.toString(), this.accountnumber,row.getString("ACCOUNTNUMBER"),this.remarks,row.getString("MSISDN"),row.getString("ALIAS"))>0;
		}else{
			StringBuilder query = new StringBuilder("BEGIN\n");
			query.append("UPDATE TBLBRANCHES SET REMARKS=?,STATUS='4' WHERE ACCOUNTNUMBER=?; \n");
			query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
			return SystemInfo.getDb().QueryUpdate(query.toString(), this.remarks,this.accountnumber)>0;
		}
	}
	
	public boolean validate(){
		UISession sess = this.getAuthorizedSession();
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE PASSWORD=ENCRYPT(?,?,USERNAME) AND USERNAME =?",this.password,SystemInfo.getDb().getCrypt(),sess.getAccount().getUserName() ).size()>0;
	}
	
	public boolean exist(){
		DataRow rr = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLBRANCHES WHERE ACCOUNTNUMBER=?", this.accountnumber);
		if(!rr.getString("KEYACCOUNT").equals("834591471124")){
			return SystemInfo.getDb().QueryDataRow("SELECT * FROM ADMDBMC.TBLACCOUNTINFOPNDG WHERE ACCOUNTNUMBER = ?", this.accountnumber).size()>0;	
		}else{
			return SystemInfo.getDb().QueryDataRow("SELECT * FROM ADMDBMC.TBLACCOUNTINFO WHERE ACCOUNTNUMBER = ?", this.accountnumber).size()>0;
		}
	}
}
