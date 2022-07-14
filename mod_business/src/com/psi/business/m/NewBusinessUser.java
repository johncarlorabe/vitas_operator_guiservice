package com.psi.business.m;

import com.psi.business.util.Manager;
import com.tlc.common.DataRow;
import com.tlc.common.SystemInfo;

public class NewBusinessUser extends Manager{
	
	public boolean register(){		
		StringBuilder query = new StringBuilder("BEGIN\n");
		query.append("INSERT INTO TBLUSERS(EMAIL,FIRSTNAME,LASTNAME,MSISDN,USERSLEVEL,STATUS,DATEREGISTERED,USERNAME,ACCOUNTNUMBER,PASSWORD,AUTHCODE,ISFIRSTLOGON,TERMINAL) VALUES(?,?,?,?,'COMPANY','ACTIVE',SYSDATE,?,?,ADMDBMC.ENCRYPT(?,?,?),'PASS',1,'4339D22FA2180E39'); \n");
		query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		 return SystemInfo.getDb().QueryUpdate(query.toString(), 
				 									  this.email, this.firstname, this.lastname,this.msisdn,this.username,this.accountnumber,this.password,SystemInfo.getDb().getCrypt(),this.username
				 									  )>0;		

		
	}
	public boolean exist(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERNAME=?", this.username).size()>0;
	}
}
