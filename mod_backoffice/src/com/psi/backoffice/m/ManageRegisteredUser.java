package com.psi.backoffice.m;

import com.psi.backoffice.util.Users;
import com.tlc.common.DataRow;
import com.tlc.common.SystemInfo;

public class ManageRegisteredUser extends Users{

	
	public boolean update(){
		
		StringBuilder query = new StringBuilder("BEGIN\n");
		query.append("UPDATE TBLUSERS SET FIRSTNAME=?,LASTNAME=?,MSISDN=?,DATEMODIFIED=SYSDATE, EMAIL = ?,USERSLEVEL=? WHERE USERID = ?; \n");
		query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		 
		return SystemInfo.getDb().QueryUpdate(query.toString(), 
											this.firstname,this.lastname,this.msisdn,this.email,this.userslevel,this.id)>0;
	}
	
	
	public boolean exist(){
		
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE STATUS='ACTIVE' AND USERID = ?", this.id).size()>0;
	}
	 public boolean isEmailExist(){
			
			return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE STATUS='ACTIVE' AND USERID <> ? AND EMAIL = ?", this.id,this.email).size()>0;
		}
	 public boolean isMsisdnExist(){
			
			return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE STATUS='ACTIVE' AND USERID <> ? AND MSISDN = ?", this.id,this.msisdn).size()>0;
		}
	
	public boolean delete(){
		DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERID = ?", this.id);
		StringBuilder query = new StringBuilder("BEGIN\n");
		query.append("DELETE FROM TBLUSERS WHERE USERID = ?; \n");
		query.append("DELETE FROM TBLPOSUSERS WHERE USERID = ?; \n");
		query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		 
		return SystemInfo.getDb().QueryUpdate(query.toString(), this.id,row.getString("USERNAME"))>0;
	}
	
		 
}
