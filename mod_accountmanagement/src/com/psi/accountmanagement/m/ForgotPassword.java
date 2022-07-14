package com.psi.accountmanagement.m;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.psi.accountmanagement.utils.Users;
import com.tlc.common.DataRow;
import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;

public class ForgotPassword extends Users{
	private static final String REGEX_OUTLET = "outlet";
	private static final String REGEX_OPERATOR = "operator";
	private static final String REGEX_CUSTOMER = "customer";
	
	public boolean reset(){	
		return SystemInfo.getDb().QueryUpdate("UPDATE TBLUSERS SET PASSWORD=ENCRYPT(?,?,USERNAME) WHERE EMAIL = ?",this.password,SystemInfo.getDb().getCrypt(), this.email)>0;
		
	}
	
	public boolean exist(){
		
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE EMAIL = ?", this.email).size()>0;
	}
	public boolean isValid(){
		
		  Pattern p = Pattern.compile(REGEX_OUTLET);Pattern p1 = Pattern.compile(REGEX_OPERATOR);Pattern p2 = Pattern.compile(REGEX_CUSTOMER);
	      Matcher m = p.matcher(this.url);Matcher m1 = p1.matcher(this.url);Matcher m2 = p2.matcher(this.url);   
	      int count = 0;int count1 = 0;int count2 = 0;
	      while(m.find()) {
	         count++;
	      }
	      while(m1.find()) {
		         count1++;
		  }
	      while(m2.find()) {
		         count2++;
		  }
			if(count==1){
				Logger.LogServer("pasok outlet");
				return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE EMAIL = ? AND USERSLEVEL IN ('CASHIER','MANAGER')", this.email).size()>0;
			}else if(count2==1){
				Logger.LogServer("pasok customer");
			    return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE EMAIL = ? AND USERSLEVEL = 'CUSTOMER'", this.email).size()>0;
			}else if(count1==1){
				Logger.LogServer("pasok operator1");
				return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE EMAIL = ? AND USERSLEVEL NOT IN ('CASHIER','MANAGER','CUSTOMER')", this.email).size()>0;
			}
			else {
				return false;
			}
	}
	public String userslevel(){
		
		return SystemInfo.getDb().QueryScalar("SELECT USERSLEVEL FROM TBLUSERS WHERE EMAIL = ?", "",this.email);
	}
}
