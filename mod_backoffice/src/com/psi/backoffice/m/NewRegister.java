package com.psi.backoffice.m;

import com.psi.backoffice.util.Users;
import com.tlc.common.DataRow;
import com.tlc.common.SystemInfo;

public class NewRegister
  extends Users {
  public boolean register() {
    //DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLBRANCHES WHERE ACCOUNTNUMBER = ?", new Object[] { this.accountnumber });
    StringBuilder query = new StringBuilder("BEGIN\n");
    query.append("INSERT INTO TBLUSERS(EMAIL,FIRSTNAME,LASTNAME,MSISDN,USERSLEVEL,STATUS,DATEREGISTERED,USERNAME,STORE,PASSWORD,AUTHCODE,ISFIRSTLOGON,TERMINAL,ACCOUNTNUMBER,GUIINTERFACE,CREATEDBY) VALUES(?,?,?,?,?,'ACTIVE',SYSDATE,?,?,ADMDBMC.ENCRYPT(?,?,?),'PASS',1,'4339D22FA2180E39',?,?,?); \n");
    query.append("Insert into TBLPOSUSERS(ACCOUNTNUMBER, TERMINALID, USERID, PASSWORD, BRANCHCODE, TYPE, MSISDN, DEFAULTPWD, FIRSTNAME, LASTNAME)Values(?, '4339D22FA2180E39', ?, '1166', ?, 'cashier', ?, 0, ?,?); \n");
    query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n\tROLLBACK;\n RAISE;\nEND;");
    return (SystemInfo.getDb().QueryUpdate(query.toString(), new Object[] { 
          this.email, this.firstname, this.lastname, this.msisdn, this.userslevel, this.username, this.manager, this.password, SystemInfo.getDb().getCrypt(), this.username, this.accountnumber, this.guiinterface, this.createdby, 
          this.accountnumber, this.username, "", this.msisdn, this.firstname, this.lastname }) > 0);
  }

  
  public boolean exist() { return (SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERNAME = ?", new Object[] { this.username }).size() > 0); }

  
  public boolean isEmailExist() { return (SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE EMAIL = ?", new Object[] { this.email }).size() > 0); }

  
  public boolean isMsisdnExist() { return (SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE MSISDN = ?", new Object[] { this.msisdn }).size() > 0); }
}
