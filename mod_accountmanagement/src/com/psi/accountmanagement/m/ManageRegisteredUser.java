package com.psi.accountmanagement.m;

import com.psi.accountmanagement.utils.Users;
import com.tlc.common.DataRow;
import com.tlc.common.SystemInfo;

public class ManageRegisteredUser extends Users{

	
	public boolean update(){
		if(this.userslevel.equals("CUSTOMER")){
			DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERID = ?", this.userid);
			if(row.isEmpty()){
				return false;
			}
		StringBuilder query = new StringBuilder("BEGIN\n");
		//query.append("UPDATE TBLACCOUNTINFO SET FIRSTNAME=?,LASTNAME=?,MIDDLENAME=?,MSISDN=?,COUNTRY=?,PROVINCE=?,CITY=? WHERE ID=?; \n");
		query.append("UPDATE TBLUSERS SET FIRSTNAME=?,LASTNAME=?,MSISDN=?,DATEMODIFIED=SYSDATE,MIDDLENAME=? WHERE USERID = ?; \n");
		query.append("UPDATE TBLCUSTOMEREXTENDEDDETAILS SET NATUREOFWORK = ?, SOURCEOFFUND = ? WHERE ACCOUNTNUMBER = ?;\n");
		query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		 
		return SystemInfo.getDb().QueryUpdate(query.toString(), 
											this.firstname,this.lastname,this.msisdn,this.midname,this.userid,
											this.natureofwork,this.sourceoffund,row.getString("ACCOUNTNUMBER"))>0;
	
		}else{
			StringBuilder query = new StringBuilder("BEGIN\n");
			query.append("UPDATE TBLUSERS SET FIRSTNAME=?,LASTNAME=?,MIDDLENAME=?,MSISDN=?,DATEMODIFIED=SYSDATE,EMAIL=?,CITY=?,PROVINCE=? WHERE USERID = ?; \n");
			query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
			 
			return SystemInfo.getDb().QueryUpdate(query.toString(), 
												this.firstname,this.lastname,this.midname,this.msisdn,this.email,this.city,this.province,this.userid)>0;
		}
	}
	
	
	public boolean exist(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE STATUS='ACTIVE' AND USERID = ?", this.userid).size()>0;
	}
	
	
	public boolean validate(){
		StringBuilder query = new StringBuilder("BEGIN\n");
		query.append("UPDATE TBLUSERS SET AUTHCODE = 'PASS' , ISFIRSTLOGON = 0 WHERE USERID = ?; \n");
		query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		 
		return SystemInfo.getDb().QueryUpdate(query.toString(),this.id )>0;
	}
	
	public boolean isValid(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERID = ? AND AUTHCODE = ?", this.id,this.code).size()>0;	
	}
	
	public boolean isValidated(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERID = ? AND AUTHCODE = 'PASS'", this.id).size()>0;	
	}
	
	public String getEmail(){
		 DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERID=?", this.id);	
		 if(!row.isEmpty()){
			return row.getString("EMAIL");
		 }else{
			 return "";
		 }
	}

		 
}
