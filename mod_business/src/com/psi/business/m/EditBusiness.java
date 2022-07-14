package com.psi.business.m;

import com.psi.business.util.Branch;
import com.tlc.common.SystemInfo;

public class EditBusiness extends Branch{

	public boolean update(){
		StringBuilder query = new StringBuilder("BEGIN\n");
		query.append("UPDATE TBLBUSINESS SET BUSINESS=?,ADDRESS=?,CITY=?,CONTACTNUMBER=?,PROOFADDRESS=?,PROVINCE=?,XORDINATES=?,YORDINATES=?,ZIPCODE=?,MONDAY=?,TUESDAY=?,WEDNESDAY=?,THURSDAY=?,FRIDAY=?,SATURDAY=?,SUNDAY=?,COUNTRY=?,ISWITHHOLDINGTAX=? WHERE ACCOUNTNUMBER=?; \n");
		query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		return SystemInfo.getDb().QueryUpdate(query.toString(), 
				 									  this.branchname, this.address,this.city,this.contactnumber,this.image,this.province,this.xordinate,this.yordinate,this.zipcode,this.monday,this.tuesday,this.wednesday,this.thursday,this.friday,this.saturday,this.sunday,this.country,this.iswithholdingtax,this.accountnumber)>0;		
		
	}
	public boolean exist(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLBUSINESS WHERE ACCOUNTNUMBER=?", this.accountnumber).size()>0;
	}
	
}
