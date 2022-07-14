package com.psi.usermanagement.m;

import com.psi.usermanagement.util.Users;
import com.tlc.common.DataRow;
import com.tlc.common.SystemInfo;

public class NewRegister extends Users{
	
	protected String manfirstname;
	protected String manlastname;
	protected String userslevel;
	
	public boolean register(){
		DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLBRANCHES WHERE ACCOUNTNUMBER = ?", this.accountnumber);
		StringBuilder query = new StringBuilder("BEGIN\n");
		query.append("INSERT INTO TBLUSERS(EMAIL,FIRSTNAME,LASTNAME,MSISDN,USERSLEVEL,STATUS,DATEREGISTERED,USERNAME,STORE,PASSWORD,AUTHCODE,ISFIRSTLOGON,TERMINAL,ACCOUNTNUMBER) VALUES(?,?,?,?,'CASHIER','ACTIVE',SYSDATE,?,?,ADMDBMC.ENCRYPT(?,?,?),'PASS',1,'4339D22FA2180E39',?); \n");
		query.append("Insert into TBLPOSUSERS(ACCOUNTNUMBER, TERMINALID, USERID, PASSWORD, BRANCHCODE, TYPE, MSISDN, DEFAULTPWD, FIRSTNAME, LASTNAME)Values(?, '4339D22FA2180E39', ?, '1166', ?, 'cashier', ?, 0, ?,?); \n");
		query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		 return SystemInfo.getDb().QueryUpdate(query.toString(), 
				 									  this.email, this.firstname, this.lastname,this.msisdn,this.username,this.manager,this.password,SystemInfo.getDb().getCrypt(),this.username,this.accountnumber,
				 									 this.accountnumber,this.username,row.getString("BRANCHCODE"),this.msisdn,this.firstname,this.lastname)>0;		

	}
	public boolean exist(){		
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERNAME = ?", this.username).size()>0;
	}
	public boolean isEmailExist(){		
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE EMAIL = ?", this.email).size()>0;
	}
	public boolean isManager(){
		DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERID = ?", this.manager);
		
		if(row.size() > 0){
			this.setManfirstname(row.getString("FIRSTNAME"));
			this.setManlastname(row.getString("LASTNAME"));
			this.setUserslevel(row.getString("USERSLEVEL"));
			return true;
		}
		else{
			return false;
		}
	}
	
	public String getManfirstname() {
		return manfirstname;
	}
	public void setManfirstname(String manfirstname) {
		this.manfirstname = manfirstname;
	}
	public String getManlastname() {
		return manlastname;
	}
	public void setManlastname(String manlastname) {
		this.manlastname = manlastname;
	}
	public String getUserslevel() {
		return userslevel;
	}
	public void setUserslevel(String userslevel) {
		this.userslevel = userslevel;
	}
	
	
}
