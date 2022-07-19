package com.psi.cashiermanagement.m;

import com.psi.cashiermanagement.util.Cashier;
import com.tlc.common.DataRow;
import com.tlc.common.SystemInfo;

public class ManageRegisteredUser extends Cashier{

	
	public boolean update(){
		
		DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERID = ?", this.id);
		if(row.isEmpty()) {
			return false;
		}
		
		StringBuilder query = new StringBuilder("BEGIN\n");
		query.append("UPDATE TBLUSERS SET FIRSTNAME=?,LASTNAME=?,DATEMODIFIED=SYSDATE WHERE USERID = ?; \n");
		query.append("UPDATE ADMDBMC.TBLACCOUNTINFO SET FIRSTNAME=?,LASTNAME=?,DATEUPDATED=SYSDATE WHERE ACCOUNTNUMBER = ?; \n");
		query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		return SystemInfo.getDb().QueryUpdate(query.toString(),this.firstname,this.lastname,this.id,
				this.firstname,this.lastname,row.getString("ACCOUNTNUMBER"))>0;
	}
	
	
	public boolean exist(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERID = ?", this.id).size()>0;
	}
	public boolean existStatus(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE STATUS='ACTIVE' AND USERID = ?", this.id).size()>0;
	}
	 public boolean isEmailExist(){
			
			return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE STATUS='ACTIVE' AND USERID <> ? AND EMAIL = ?", this.id,this.email).size()>0;
		}
	 public boolean isMsisdnExist(){
			
			return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE STATUS='ACTIVE' AND USERID <> ? AND MSISDN = ?", this.id,this.msisdn).size()>0;
		}
	
	public boolean changestatus(){
		
		DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERID = ?", this.id);
		if(row.isEmpty()) {
			return false;
		}
		StringBuilder query = new StringBuilder("BEGIN\n");
		query.append("UPDATE TBLUSERS SET STATUS=? WHERE USERID = ?; \n");
		query.append("UPDATE ADMDBMC.TBLACCOUNTINFO SET STATUS=? WHERE ACCOUNTNUMBER = ?; \n");
		query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		return SystemInfo.getDb().QueryUpdate(query.toString(),this.status,this.id,this.status,row.getString("ACCOUNTNUMBER"))>0;
	}
	
		 
}
