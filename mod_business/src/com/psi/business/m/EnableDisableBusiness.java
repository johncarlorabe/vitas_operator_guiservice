package com.psi.business.m;

import com.psi.business.util.Branch;
import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.SystemInfo;

public class EnableDisableBusiness extends Branch{
	
	public boolean update (){
		String rr = "";
		DataRowCollection row = SystemInfo.getDb().QueryDataRows("SELECT ACCOUNTNUMBER FROM TBLBRANCHES WHERE STATUS=1 AND KEYACCOUNT = ?", this.accountnumber);
		if(!row.isEmpty()){
			for(DataRow r:row){
				rr += "'"+r.getString("ACCOUNTNUMBER")+"',";
			}
		}else{
			rr = "'',";
		}
		
		String rrr = "";
		DataRowCollection roww = SystemInfo.getDb().QueryDataRows("SELECT ACCOUNTNUMBER FROM TBLBRANCHES WHERE STATUS=0 AND KEYACCOUNT = ?", this.accountnumber);
		if(!roww.isEmpty()){
			for(DataRow r:roww){
				rrr += "'"+r.getString("ACCOUNTNUMBER")+"',";
			}
		}else{
			rrr = "'',";
		}
		if(this.status.equals("DISABLE")){
		StringBuilder query = new StringBuilder("BEGIN\n");
		query.append("UPDATE TBLUSERS SET LOCKED = 'YES',STATUS='INACTIVE' WHERE ACCOUNTNUMBER IN ("+rr+"''"+"); \n");
		query.append("UPDATE TBLPOSUSERS SET PASSWORD='1166' WHERE ACCOUNTNUMBER IN ("+rr+"''"+"); \n");
		query.append("UPDATE TBLBUSINESS SET STATUS=0 WHERE ACCOUNTNUMBER=?; \n");
		query.append("UPDATE TBLBRANCHES SET STATUS=0 WHERE KEYACCOUNT=? AND STATUS = 1; \n");
		query.append("UPDATE TBLUSERS SET LOCKED = 'YES',STATUS='INACTIVE' WHERE ACCOUNTNUMBER = ?; \n");
		query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		 return SystemInfo.getDb().QueryUpdate(query.toString(),this.accountnumber,this.accountnumber, this.accountnumber)>0;	
		 
		}else{
			StringBuilder query = new StringBuilder("BEGIN\n");
			query.append("UPDATE TBLUSERS SET LOCKED = 'NO',STATUS='ACTIVE' WHERE ACCOUNTNUMBER IN ("+rrr+"''"+"); \n");
			query.append("UPDATE TBLPOSUSERS SET PASSWORD='123456789' WHERE ACCOUNTNUMBER IN ("+rrr+"''"+"); \n");
			query.append("UPDATE TBLBUSINESS SET STATUS=1 WHERE ACCOUNTNUMBER=?; \n");
			query.append("UPDATE TBLBRANCHES SET STATUS=1 WHERE KEYACCOUNT=? AND STATUS = 0; \n");
			query.append("UPDATE TBLUSERS SET LOCKED = 'NO',STATUS='ACTIVE' WHERE ACCOUNTNUMBER = ?; \n");
			query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
			 return SystemInfo.getDb().QueryUpdate(query.toString(),this.accountnumber,this.accountnumber, this.accountnumber)>0;	
		}
	}
	
	public boolean exist(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLBUSINESS WHERE ACCOUNTNUMBER=?", this.accountnumber).size()>0;
	}

}
