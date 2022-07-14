package com.psi.branch.m;

import com.psi.branch.utils.Manager;
import com.tlc.common.DataRow;
import com.tlc.common.SystemInfo;

public class NewManager extends Manager{
	
	public boolean register(){
		DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLBRANCHES WHERE ACCOUNTNUMBER = ?", this.accountnumber);
		StringBuilder query = new StringBuilder("BEGIN\n");
		query.append("INSERT INTO TBLUSERS(EMAIL,FIRSTNAME,LASTNAME,MSISDN,USERSLEVEL,STATUS,DATEREGISTERED,USERNAME,ACCOUNTNUMBER,PASSWORD,AUTHCODE,ISFIRSTLOGON,TERMINAL) VALUES(?,?,?,?,'VITASMANAGER','ACTIVE',SYSDATE,?,?,ADMDBMC.ENCRYPT(?,?,?),'PASS',1,'4339D22FA2180E39'); \n");
		query.append("Insert into TBLPOSUSERS(ACCOUNTNUMBER, TERMINALID, USERID, PASSWORD, BRANCHCODE, TYPE, MSISDN, DEFAULTPWD, FIRSTNAME, LASTNAME)Values(?, '4339D22FA2180E39', ?, '1166', ?, 'manager', ?, 0, ?,?); \n");
		query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		 return SystemInfo.getDb().QueryUpdate(query.toString(), 
				 									  this.email, this.firstname, this.lastname,this.msisdn,this.username,this.accountnumber,this.password,SystemInfo.getDb().getCrypt(),this.username
				 									  ,this.accountnumber,this.username,row.getString("BRANCHCODE"),this.msisdn,this.firstname,this.lastname)>0;		

	}
	public boolean exist(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERNAME=?", this.username).size()>0;
	}
	public boolean isEmailExist(){		
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE EMAIL = ?", this.email).size()>0;
	}
	
	public boolean isACM(){		
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM VWCREATEMGREMAIL WHERE ACCOUNTNUMBER = ? ", this.accountnumber).size()>0;
	}
}
