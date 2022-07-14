package com.psi.accountmanagement.m;

import com.psi.accountmanagement.utils.EmailUtils;
import com.psi.accountmanagement.utils.Users;
import com.tlc.common.SystemInfo;

public class ManageAccount extends Users{
	
	public boolean update(){
		String userslevel = SystemInfo.getDb().QueryScalar("SELECT USERSLEVEL FROM TBLUSERS WHERE USERID=?", "", this.userid);
		if(userslevel.equals("CASHIER")){
			StringBuilder query = new StringBuilder("BEGIN\n");
			query.append("UPDATE TBLUSERS SET LOCKED=?,INVALIDPASSWORDCOUNT=5 WHERE USERID = ?; \n");
			query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
			 
			return SystemInfo.getDb().QueryUpdate(query.toString(), this.locked, this.userid)>0;
		}else{
			StringBuilder query = new StringBuilder("BEGIN\n");
			query.append("UPDATE TBLUSERS SET LOCKED=?,INVALIDPASSWORDCOUNT=0 WHERE USERID = ?; \n");
			query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
			 
			return SystemInfo.getDb().QueryUpdate(query.toString(), this.locked, this.userid)>0;
		}

	}
	
	public boolean activate(){
		StringBuilder query2 = new StringBuilder("BEGIN\n");
		query2.append("UPDATE TBLUSERS SET STATUS=? WHERE USERID = ?; \n");
		query2.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		 
		return SystemInfo.getDb().QueryUpdate(query2.toString(), this.status, this.userid)>0;
	}
	
	public boolean resetpassword(String userid){

		if(EmailUtils.sendEmailResetPassword(this.userid)){
			return true;
		}else{
			return false;
		}
	}
	
}
