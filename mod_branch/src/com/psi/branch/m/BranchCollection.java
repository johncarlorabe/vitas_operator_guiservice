package com.psi.branch.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class BranchCollection extends ModelCollection{
protected String accountnumber;
	@Override
	public boolean hasRows() {
			DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT B.*,DECRYPT(CS.AMOUNT, ? , CS.ACCOUNTNUMBER) CURRENTBAL FROM TBLBRANCHES B INNER JOIN ADMDBMC.TBLCURRENTSTOCK CS ON B.ACCOUNTNUMBER=CS.ACCOUNTNUMBER WHERE CS.WALLETID=1 AND B.STATUS NOT IN(3,4) AND B.KEYACCOUNT=?",SystemInfo.getDb().getCrypt(),this.accountnumber);
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
		         ReportItem m = new ReportItem();
		         
		         DataRow manager = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLBRANCHES B INNER JOIN TBLUSERS U ON U.ACCOUNTNUMBER = B.ACCOUNTNUMBER WHERE B.ACCOUNTNUMBER=? AND U.USERSLEVEL IN ('MANAGER','VITASMANAGER')", row.getString("ACCOUNTNUMBER"));
		         if(!manager.isEmpty()){
		        	 m.setProperty("Id", row.getString("ID") == null ? "" : row.getString("ID").toString());
		        	 m.setProperty("AccountNumber", row.getString("ACCOUNTNUMBER") == null ? "" : row.getString("ACCOUNTNUMBER").toString());
		        	 m.setProperty("Branch", row.getString("BRANCH") == null ? "" : row.getString("BRANCH").toString());
			         m.setProperty("ContactNumber", row.getString("CONTACTNUMBER") == null ? "" : row.getString("CONTACTNUMBER").toString());
			         m.setProperty("Location", row.getString("ADDRESS") == null ? "" : row.getString("ADDRESS").toString());
			         m.setProperty("Status", row.getString("STATUS") == null ? "" : row.getString("STATUS").toString());
			        // m.setProperty("StoreHours", row.getString("STOREHOURS") == null ? "" : row.getString("STOREHOURS").toString());
			         m.setProperty("City", row.getString("CITY") == null ? "" : row.getString("CITY").toString());
			         m.setProperty("Province", row.getString("PROVINCE") == null ? "" : row.getString("PROVINCE").toString());
			         m.setProperty("Country", row.getString("COUNTRY") == null ? "" : row.getString("COUNTRY").toString());
			         m.setProperty("XOrdinate", row.getString("XORDINATES") == null ? "" : row.getString("XORDINATES").toString());
			         m.setProperty("YOrdinate", row.getString("YORDINATES") == null ? "" : row.getString("YORDINATES").toString());
			         m.setProperty("BranchCode", row.getString("BRANCHCODE") == null ? "" : row.getString("BRANCHCODE").toString());
			         m.setProperty("Monday", row.getString("MONDAY") == null ? "" : row.getString("MONDAY").toString());
			         m.setProperty("Tuesday", row.getString("TUESDAY") == null ? "" : row.getString("TUESDAY").toString());
			         m.setProperty("Wednesday", row.getString("WEDNESDAY") == null ? "" : row.getString("WEDNESDAY").toString());
			         m.setProperty("Thursday", row.getString("THURSDAY") == null ? "" : row.getString("THURSDAY").toString());
			         m.setProperty("Friday", row.getString("FRIDAY") == null ? "" : row.getString("FRIDAY").toString());
			         m.setProperty("Saturday", row.getString("SATURDAY") == null ? "" : row.getString("SATURDAY").toString());
			         m.setProperty("Sunday", row.getString("SUNDAY") == null ? "" : row.getString("SUNDAY").toString());
			         m.setProperty("ZipCode", row.getString("ZIPCODE") == null ? "" : row.getString("ZIPCODE").toString());
		        	 m.setProperty("FirstName", manager.getString("FIRSTNAME") == null ? "" : manager.getString("FIRSTNAME").toString());
			         m.setProperty("LastName", manager.getString("LASTNAME") == null ? "" : manager.getString("LASTNAME").toString());
			         m.setProperty("UserName", manager.getString("USERNAME") == null ? "" : manager.getString("USERNAME").toString());
		        	 m.setProperty("ManagerStatus", "YES");
		        	 m.setProperty("FileLocation", row.getString("RAFILELOCATION") == null ? "" : row.getString("RAFILELOCATION").toString());
		        	 m.setProperty("FileName", row.getString("RAFILENAME") == null ? "" : row.getString("RAFILENAME").toString());
		        	 m.setProperty("Tin", row.getString("TIN") == null ? "" : row.getString("TIN").toString());
		        	 m.setProperty("NatureOfBusiness", row.getString("NATUREOFBUSINESS") == null ? "" : row.getString("NATUREOFBUSINESS").toString());
		        	 m.setProperty("GrossSales", row.getString("GROSSSALES") == null ? "" : row.getString("GROSSSALES").toString());
		        	 m.setProperty("RegisteredBy", row.getString("REGISTEREDBY") == null ? "" : row.getString("REGISTEREDBY").toString());
		        	 m.setProperty("CurrentBalance", row.getString("CURRENTBAL") == null ? "" :LongUtil.toString(Long.parseLong(row.getString("CURRENTBAL").toString())));
						
		        	 
		         }else{
		        	 m.setProperty("Id", row.getString("ID") == null ? "" : row.getString("ID").toString());
		        	 m.setProperty("AccountNumber", row.getString("ACCOUNTNUMBER") == null ? "" : row.getString("ACCOUNTNUMBER").toString());
		        	 m.setProperty("Branch", row.getString("BRANCH") == null ? "" : row.getString("BRANCH").toString());
			         m.setProperty("ContactNumber", row.getString("CONTACTNUMBER") == null ? "" : row.getString("CONTACTNUMBER").toString());
			         m.setProperty("Location", row.getString("ADDRESS") == null ? "" : row.getString("ADDRESS").toString());
			         m.setProperty("Status", row.getString("STATUS") == null ? "" : row.getString("STATUS").toString());
			         m.setProperty("City", row.getString("CITY") == null ? "" : row.getString("CITY").toString());
			         m.setProperty("Province", row.getString("PROVINCE") == null ? "" : row.getString("PROVINCE").toString());
			         m.setProperty("Country", row.getString("COUNTRY") == null ? "" : row.getString("COUNTRY").toString());
			         m.setProperty("XOrdinate", row.getString("XORDINATES") == null ? "" : row.getString("XORDINATES").toString());
			         m.setProperty("YOrdinate", row.getString("YORDINATES") == null ? "" : row.getString("YORDINATES").toString());
			         m.setProperty("BranchCode", row.getString("BRANCHCODE") == null ? "" : row.getString("BRANCHCODE").toString());
			         m.setProperty("Monday", row.getString("MONDAY") == null ? "" : row.getString("MONDAY").toString());
			         m.setProperty("Tuesday", row.getString("TUESDAY") == null ? "" : row.getString("TUESDAY").toString());
			         m.setProperty("Wednesday", row.getString("WEDNESDAY") == null ? "" : row.getString("WEDNESDAY").toString());
			         m.setProperty("Thursday", row.getString("THURSDAY") == null ? "" : row.getString("THURSDAY").toString());
			         m.setProperty("Friday", row.getString("FRIDAY") == null ? "" : row.getString("FRIDAY").toString());
			         m.setProperty("Saturday", row.getString("SATURDAY") == null ? "" : row.getString("SATURDAY").toString());
			         m.setProperty("Sunday", row.getString("SUNDAY") == null ? "" : row.getString("SUNDAY").toString());
			         m.setProperty("ZipCode", row.getString("ZIPCODE") == null ? "" : row.getString("ZIPCODE").toString());
		        	 m.setProperty("FirstName", "N/A");
			         m.setProperty("LastName", "N/A");
			         m.setProperty("UserName", "N/A");
		        	 m.setProperty("ManagerStatus", "NO");
		        	 m.setProperty("FileLocation", row.getString("RAFILELOCATION") == null ? "" : row.getString("RAFILELOCATION").toString());
		        	 m.setProperty("FileName", row.getString("RAFILENAME") == null ? "" : row.getString("RAFILENAME").toString());
		        	 m.setProperty("Tin", row.getString("TIN") == null ? "" : row.getString("TIN").toString());
		        	 m.setProperty("NatureOfBusiness", row.getString("NATUREOFBUSINESS") == null ? "" : row.getString("NATUREOFBUSINESS").toString());
		        	 m.setProperty("GrossSales", row.getString("GROSSSALES") == null ? "" : row.getString("GROSSSALES").toString());
		        	 m.setProperty("RegisteredBy", row.getString("REGISTEREDBY") == null ? "" : row.getString("REGISTEREDBY").toString());
		        	 m.setProperty("CurrentBalance", row.getString("CURRENTBAL") == null ? "" :LongUtil.toString(Long.parseLong(row.getString("CURRENTBAL").toString())));
						
		        	 
		         }

		         add(m);
	    	 }
	     }
	     return r.size() > 0;
	}
	
	public boolean hasPendingRows() {
	if(!this.accountnumber.equals("834591471124")){
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT B.*,AI.ACCOUNTNUMBER ACCOUNTNUMBERONE FROM TBLBRANCHES B INNER JOIN ADMDBMC.TBLACCOUNTINFOPNDG AI ON B.CONTACTNUMBER = AI.MSISDN AND B.BRANCH = DECRYPT(FQN,?,AI.ACCOUNTNUMBER) WHERE B.STATUS = 3 AND KEYACCOUNT=?",SystemInfo.getDb().getCrypt(),this.accountnumber);
     
     if (!r.isEmpty())
     {
    	 for(DataRow row: r){
	         ReportItem m = new ReportItem();
	         
	        	 m.setProperty("Id", row.getString("ID") == null ? "" : row.getString("ID").toString());
	        	 m.setProperty("AccountNumber", row.getString("ACCOUNTNUMBERONE") == null ? "" : row.getString("ACCOUNTNUMBERONE").toString());
	        	 m.setProperty("Branch", row.getString("BRANCH") == null ? "" : row.getString("BRANCH").toString());
		         m.setProperty("ContactNumber", row.getString("CONTACTNUMBER") == null ? "" : row.getString("CONTACTNUMBER").toString());
		         m.setProperty("Location", row.getString("ADDRESS") == null ? "" : row.getString("ADDRESS").toString());
		         m.setProperty("Status", row.getString("STATUS") == null ? "" : row.getString("STATUS").toString());
		        // m.setProperty("StoreHours", row.getString("STOREHOURS") == null ? "" : row.getString("STOREHOURS").toString());
		         m.setProperty("City", row.getString("CITY") == null ? "" : row.getString("CITY").toString());
		         m.setProperty("Province", row.getString("PROVINCE") == null ? "" : row.getString("PROVINCE").toString());
		         m.setProperty("Country", row.getString("COUNTRY") == null ? "" : row.getString("COUNTRY").toString());
		         m.setProperty("XOrdinate", row.getString("XORDINATES") == null ? "" : row.getString("XORDINATES").toString());
		         m.setProperty("YOrdinate", row.getString("YORDINATES") == null ? "" : row.getString("YORDINATES").toString());
		         m.setProperty("BranchCode", row.getString("BRANCHCODE") == null ? "" : row.getString("BRANCHCODE").toString());
		         m.setProperty("Monday", row.getString("MONDAY") == null ? "" : row.getString("MONDAY").toString());
		         m.setProperty("Tuesday", row.getString("TUESDAY") == null ? "" : row.getString("TUESDAY").toString());
		         m.setProperty("Wednesday", row.getString("WEDNESDAY") == null ? "" : row.getString("WEDNESDAY").toString());
		         m.setProperty("Thursday", row.getString("THURSDAY") == null ? "" : row.getString("THURSDAY").toString());
		         m.setProperty("Friday", row.getString("FRIDAY") == null ? "" : row.getString("FRIDAY").toString());
		         m.setProperty("Saturday", row.getString("SATURDAY") == null ? "" : row.getString("SATURDAY").toString());
		         m.setProperty("Sunday", row.getString("SUNDAY") == null ? "" : row.getString("SUNDAY").toString());
		         m.setProperty("ZipCode", row.getString("ZIPCODE") == null ? "" : row.getString("ZIPCODE").toString());
	        	 m.setProperty("FileLocation", row.getString("RAFILELOCATION") == null ? "" : row.getString("RAFILELOCATION").toString());
	        	 m.setProperty("FileName", row.getString("RAFILENAME") == null ? "" : row.getString("RAFILENAME").toString());
	        	 m.setProperty("Tin", row.getString("TIN") == null ? "" : row.getString("TIN").toString());
	        	 m.setProperty("NatureOfBusiness", row.getString("NATUREOFBUSINESS") == null ? "" : row.getString("NATUREOFBUSINESS").toString());
	        	 m.setProperty("GrossSales", row.getString("GROSSSALES") == null ? "" : row.getString("GROSSSALES").toString());
	        	 m.setProperty("RegisteredBy", row.getString("REGISTEREDBY") == null ? "" : row.getString("REGISTEREDBY").toString());
	        	 add(m);
    	 }
     }
    	 return r.size() > 0;
     }else{
 		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT B.* FROM TBLBRANCHES B WHERE B.STATUS = 3 AND KEYACCOUNT=?",this.accountnumber);
 	     
 	     if (!r.isEmpty())
 	     {
 	    	 for(DataRow row: r){
 		         ReportItem m = new ReportItem();
 		         
 		        	 m.setProperty("Id", row.getString("ID") == null ? "" : row.getString("ID").toString());
 		        	 m.setProperty("AccountNumber", row.getString("ACCOUNTNUMBER") == null ? "" : row.getString("ACCOUNTNUMBER").toString());
 		        	 m.setProperty("Branch", row.getString("BRANCH") == null ? "" : row.getString("BRANCH").toString());
 			         m.setProperty("ContactNumber", row.getString("CONTACTNUMBER") == null ? "" : row.getString("CONTACTNUMBER").toString());
 			         m.setProperty("Location", row.getString("ADDRESS") == null ? "" : row.getString("ADDRESS").toString());
 			         m.setProperty("Status", row.getString("STATUS") == null ? "" : row.getString("STATUS").toString());
 			        // m.setProperty("StoreHours", row.getString("STOREHOURS") == null ? "" : row.getString("STOREHOURS").toString());
 			         m.setProperty("City", row.getString("CITY") == null ? "" : row.getString("CITY").toString());
 			         m.setProperty("Province", row.getString("PROVINCE") == null ? "" : row.getString("PROVINCE").toString());
 			         m.setProperty("Country", row.getString("COUNTRY") == null ? "" : row.getString("COUNTRY").toString());
 			         m.setProperty("XOrdinate", row.getString("XORDINATES") == null ? "" : row.getString("XORDINATES").toString());
 			         m.setProperty("YOrdinate", row.getString("YORDINATES") == null ? "" : row.getString("YORDINATES").toString());
 			         m.setProperty("BranchCode", row.getString("BRANCHCODE") == null ? "" : row.getString("BRANCHCODE").toString());
 			         m.setProperty("Monday", row.getString("MONDAY") == null ? "" : row.getString("MONDAY").toString());
 			         m.setProperty("Tuesday", row.getString("TUESDAY") == null ? "" : row.getString("TUESDAY").toString());
 			         m.setProperty("Wednesday", row.getString("WEDNESDAY") == null ? "" : row.getString("WEDNESDAY").toString());
 			         m.setProperty("Thursday", row.getString("THURSDAY") == null ? "" : row.getString("THURSDAY").toString());
 			         m.setProperty("Friday", row.getString("FRIDAY") == null ? "" : row.getString("FRIDAY").toString());
 			         m.setProperty("Saturday", row.getString("SATURDAY") == null ? "" : row.getString("SATURDAY").toString());
 			         m.setProperty("Sunday", row.getString("SUNDAY") == null ? "" : row.getString("SUNDAY").toString());
 			         m.setProperty("ZipCode", row.getString("ZIPCODE") == null ? "" : row.getString("ZIPCODE").toString());
 		        	 m.setProperty("FileLocation", row.getString("RAFILELOCATION") == null ? "" : row.getString("RAFILELOCATION").toString());
 		        	 m.setProperty("FileName", row.getString("RAFILENAME") == null ? "" : row.getString("RAFILENAME").toString());
 		        	 m.setProperty("Tin", row.getString("TIN") == null ? "" : row.getString("TIN").toString());
 		        	 m.setProperty("NatureOfBusiness", row.getString("NATUREOFBUSINESS") == null ? "" : row.getString("NATUREOFBUSINESS").toString());
 		        	m.setProperty("GrossSales", row.getString("GROSSSALES") == null ? "" : row.getString("GROSSSALES").toString());
 		        	 m.setProperty("RegisteredBy", row.getString("REGISTEREDBY") == null ? "" : row.getString("REGISTEREDBY").toString());
		        	 
 		        	 add(m);
 	    	 }
 	     }
 	    	 return r.size() > 0;
     	}
	} 
	public boolean hasPendingMercRows() {

			DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT B.*,AI.ACCOUNTNUMBER ACCOUNTNUMBERONE FROM TBLBRANCHES B INNER JOIN ADMDBMC.TBLACCOUNTINFOPNDG AI ON B.CONTACTNUMBER = AI.MSISDN AND B.BRANCH = DECRYPT(FQN,?,AI.ACCOUNTNUMBER) WHERE B.STATUS = 3 AND B.ACCOUNTNUMBER IS NULL "
																	+"UNION ALL "
																	+"SELECT B.*,B.ACCOUNTNUMBER ACCOUNTNUMBERONE FROM TBLBRANCHES B WHERE B.STATUS = 3 AND B.ACCOUNTNUMBER IS NOT NULL ",SystemInfo.getDb().getCrypt());
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
		         ReportItem m = new ReportItem();
		         
		        	 m.setProperty("Id", row.getString("ID") == null ? "" : row.getString("ID").toString());
		        	 m.setProperty("AccountNumber", row.getString("ACCOUNTNUMBERONE") == null ? "" : row.getString("ACCOUNTNUMBERONE").toString());
		        	 m.setProperty("Branch", row.getString("BRANCH") == null ? "" : row.getString("BRANCH").toString());
			         m.setProperty("ContactNumber", row.getString("CONTACTNUMBER") == null ? "" : row.getString("CONTACTNUMBER").toString());
			         m.setProperty("Location", row.getString("ADDRESS") == null ? "" : row.getString("ADDRESS").toString());
			         m.setProperty("Status", row.getString("STATUS") == null ? "" : row.getString("STATUS").toString());
			        // m.setProperty("StoreHours", row.getString("STOREHOURS") == null ? "" : row.getString("STOREHOURS").toString());
			         m.setProperty("City", row.getString("CITY") == null ? "" : row.getString("CITY").toString());
			         m.setProperty("Province", row.getString("PROVINCE") == null ? "" : row.getString("PROVINCE").toString());
			         m.setProperty("Country", row.getString("COUNTRY") == null ? "" : row.getString("COUNTRY").toString());
			         m.setProperty("XOrdinate", row.getString("XORDINATES") == null ? "" : row.getString("XORDINATES").toString());
			         m.setProperty("YOrdinate", row.getString("YORDINATES") == null ? "" : row.getString("YORDINATES").toString());
			         m.setProperty("BranchCode", row.getString("BRANCHCODE") == null ? "" : row.getString("BRANCHCODE").toString());
			         m.setProperty("Monday", row.getString("MONDAY") == null ? "" : row.getString("MONDAY").toString());
			         m.setProperty("Tuesday", row.getString("TUESDAY") == null ? "" : row.getString("TUESDAY").toString());
			         m.setProperty("Wednesday", row.getString("WEDNESDAY") == null ? "" : row.getString("WEDNESDAY").toString());
			         m.setProperty("Thursday", row.getString("THURSDAY") == null ? "" : row.getString("THURSDAY").toString());
			         m.setProperty("Friday", row.getString("FRIDAY") == null ? "" : row.getString("FRIDAY").toString());
			         m.setProperty("Saturday", row.getString("SATURDAY") == null ? "" : row.getString("SATURDAY").toString());
			         m.setProperty("Sunday", row.getString("SUNDAY") == null ? "" : row.getString("SUNDAY").toString());
			         m.setProperty("ZipCode", row.getString("ZIPCODE") == null ? "" : row.getString("ZIPCODE").toString());
		        	 m.setProperty("FileLocation", row.getString("RAFILELOCATION") == null ? "" : row.getString("RAFILELOCATION").toString());
		        	 m.setProperty("FileName", row.getString("RAFILENAME") == null ? "" : row.getString("RAFILENAME").toString());
		        	 m.setProperty("Tin", row.getString("TIN") == null ? "" : row.getString("TIN").toString());
		        	 m.setProperty("NatureOfBusiness", row.getString("NATUREOFBUSINESS") == null ? "" : row.getString("NATUREOFBUSINESS").toString());
		        	 m.setProperty("GrossSales", row.getString("GROSSSALES") == null ? "" : row.getString("GROSSSALES").toString());
		        	 m.setProperty("RegisteredBy", row.getString("REGISTEREDBY") == null ? "" : row.getString("REGISTEREDBY").toString());
		        	 
		        	 add(m);
	    	 }
	     }
	    	 return r.size() > 0;

}
	
	public String getAccountnumber() {
		return accountnumber;
	}
	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}
	

}
