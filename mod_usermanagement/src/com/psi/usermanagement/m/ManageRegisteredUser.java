package com.psi.usermanagement.m;

import com.psi.usermanagement.util.Users;
import com.tlc.common.DataRow;
import com.tlc.common.SystemInfo;

public class ManageRegisteredUser extends Users{

	
	public boolean update(){
		
		StringBuilder query = new StringBuilder("BEGIN\n");
		query.append("UPDATE TBLUSERS SET FIRSTNAME=?,LASTNAME=?,MSISDN=?,DATEMODIFIED=SYSDATE, EMAIL = ? WHERE USERID = ?; \n");
		query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		 
		return SystemInfo.getDb().QueryUpdate(query.toString(), 
											this.firstname,this.lastname,this.msisdn,this.email,this.id)>0;
	}
	
	
	public boolean exist(){
		
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERID = ?", this.id).size()>0;
	}
    public boolean isEmailExist(){
		
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE STATUS='ACTIVE' AND USERID <> ? AND EMAIL = ?", this.id,this.email).size()>0;
	}
	
	public boolean delete(){
		if(this.status.equals("DISABLE")){
			StringBuilder query = new StringBuilder("BEGIN\n");
			query.append("UPDATE TBLUSERS SET STATUS='INACTIVE', LOCKED='YES' WHERE USERID = ?; \n");
			query.append("UPDATE TBLPOSUSERS SET PASSWORD='1166' WHERE USERID = ?; \n");
			query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
			 
			return SystemInfo.getDb().QueryUpdate(query.toString(), this.id,SystemInfo.getDb().QueryScalar("SELECT USERNAME FROM TBLUSERS WHERE USERID = ?", "", this.id))>0;
		}else{
			StringBuilder query = new StringBuilder("BEGIN\n");
			query.append("UPDATE TBLUSERS SET STATUS='ACTIVE', LOCKED='NO' WHERE USERID = ?; \n");
			query.append("UPDATE TBLPOSUSERS SET PASSWORD=? WHERE USERID = ?; \n");
			query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
			 
			return SystemInfo.getDb().QueryUpdate(query.toString(), this.id,SystemInfo.getDb().QueryScalar("SELECT DECRYPT(PASSWORD,?,USERNAME) PASSWORD FROM TBLUSERS WHERE USERID = ?", "", SystemInfo.getDb().getCrypt(),this.id),SystemInfo.getDb().QueryScalar("SELECT USERNAME FROM TBLUSERS WHERE USERID = ?", "", this.id))>0;
		}
		
	}
	
	public boolean allow(){
		StringBuilder query = new StringBuilder("BEGIN\n");
		query.append("UPDATE TBLPOSUSERS SET PASSWORD = ? WHERE USERID = ?;\n");
		query.append("UPDATE TBLUSERS SET INVALIDPASSWORDCOUNT=4, LOCKED='NO' WHERE USERNAME=?;\n");
		query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		return SystemInfo.getDb().QueryUpdate(query.toString(), SystemInfo.getDb().QueryScalar("SELECT DECRYPT(PASSWORD,?,USERNAME) PASSWORD FROM TBLUSERS WHERE USERNAME = ? ","",SystemInfo.getDb().getCrypt(),this.username),this.username,this.username)>0;
	}
	
	public boolean disable(){
		return SystemInfo.getDb().QueryUpdate("UPDATE TBLPOSUSERS SET PASSWORD = '1166' WHERE USERID = ?", this.username)>0;
	}
		 
}
