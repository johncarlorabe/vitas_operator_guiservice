package com.psi.purchases.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.Logger;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

public class WalletTopupStatusCollection extends ModelCollection{
	protected String id;
	@Override
	public boolean hasRows() {

		DataRow user = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERID=?", this.id);
		if(user.getString("USERSLEVEL").equals("CASHIER")){
			DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLCASHINTRANSACTIONS WHERE CREATEDBY = ? ORDER BY TIMESTAMP DESC",user.getString("USERNAME"));	     
		    
			 if (!r.isEmpty())
		     {
				  
		    	 for(DataRow row: r){
			         ReportItem m = new ReportItem();
			         m.setProperty("CreatedBy", row.getString("CREATEDBY") == null ? "" : row.getString("CREATEDBY"));
			         m.setProperty("Email", user.getString("EMAIL") == null ? "" : user.getString("EMAIL").toString());
			         m.setProperty("LastName", user.getString("LASTNAME") == null ? "" : user.getString("LASTNAME").toString());
			         m.setProperty("FirstName", user.getString("FIRSTNAME") == null ? "" : user.getString("FIRSTNAME").toString());
			         m.setProperty("Id", row.getString("ID") == null ? "" : row.getString("ID").toString());
			         m.setProperty("AccountNumber", row.getString("ACCOUNTNUMBER") == null ? "" : row.getString("ACCOUNTNUMBER").toString());
			         m.setProperty("Reference", row.getString("REFERENCE") == null ? "" : row.getString("REFERENCE").toString());
			         m.setProperty("Amount", LongUtil.toString(Long.parseLong(row.getString("AMOUNT"))) == null ? "" : LongUtil.toString(Long.parseLong(row.getString("AMOUNT").toString())));
			         m.setProperty("Timestamp", row.getString("TIMESTAMP") == null ? "" : row.getString("TIMESTAMP").toString());
			         if(row.getString("LEVELOFAPPROVAL").equals(1) || row.getString("LEVELOFAPPROVAL").equals("1") && row.getString("STATUS").equals("APPROVED")){
			        	 m.setProperty("Status", "APPROVED");
			         }else if(row.getString("LEVELOFAPPROVAL").equals(0) || row.getString("LEVELOFAPPROVAL").equals("0") && row.getString("STATUS").equals("APPROVED")){
			        	 m.setProperty("Status", "COMPLETED");
			         }else if(row.getString("LEVELOFAPPROVAL").equals(2) || row.getString("LEVELOFAPPROVAL").equals("2") && row.getString("STATUS").equals("PNDG")){
			        	 m.setProperty("Status", "PENDING");
			         }else if(row.getString("LEVELOFAPPROVAL").equals(2) || row.getString("LEVELOFAPPROVAL").equals("2") && row.getString("STATUS").equals("CANCEL")){
			        	 m.setProperty("Status", "CANCELLED");
			         }else if(row.getString("LEVELOFAPPROVAL").equals(2) || row.getString("LEVELOFAPPROVAL").equals("2") || row.getString("LEVELOFAPPROVAL").equals(1) || row.getString("LEVELOFAPPROVAL").equals("1") && row.getString("STATUS").equals("REJECT")){
			        	 m.setProperty("Status", "REJECTED");
			         }else if(row.getString("LEVELOFAPPROVAL").equals(1) || row.getString("LEVELOFAPPROVAL").equals("1") && row.getString("STATUS").equals("PNDG")){
			        	 m.setProperty("Status", "PENDING");
			         }else if(row.getString("LEVELOFAPPROVAL").equals(1) || row.getString("LEVELOFAPPROVAL").equals("1") && row.getString("STATUS").equals("CANCEL")){
			        	 m.setProperty("Status", "CANCELLED");
			         }
			         m.setProperty("Remarks", row.getString("REMARKS") == null ? "" : row.getString("REMARKS").toString());
			         m.setProperty("DepositChannel", row.getString("DEPOSITCHANNEL") == null ? "" : row.getString("DEPOSITCHANNEL").toString());
			         
			         add(m);
		    	 }
		     }
			 return r.size() > 0;
		}
		else if(user.getString("USERSLEVEL").equals("MANAGER")){
			DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLCASHINTRANSACTIONS  WHERE CREATEDBY = ? OR ACCOUNTNUMBER = (SELECT DISTINCT ACCOUNTNUMBER FROM TBLCASHINTRANSACTIONS  WHERE CREATEDBY = ?) AND CREATEDBY NOT IN (SELECT USERNAME FROM TBLUSERS WHERE USERSLEVEL NOT IN ('CASHIER','MANAGER','CUSTOMER')) AND STATUS <> 'PNDG' ORDER BY TIMESTAMP DESC ",user.getString("USERNAME"),user.getString("USERNAME"));	     
		    
			 if (!r.isEmpty())
		     {
				  
		    	 for(DataRow row: r){
			         ReportItem m = new ReportItem();
			         m.setProperty("CreatedBy", row.getString("CREATEDBY") == null ? "" : row.getString("CREATEDBY"));
			         m.setProperty("Email", user.getString("EMAIL") == null ? "" : user.getString("EMAIL").toString());
			         m.setProperty("LastName", user.getString("LASTNAME") == null ? "" : user.getString("LASTNAME").toString());
			         m.setProperty("FirstName", user.getString("FIRSTNAME") == null ? "" : user.getString("FIRSTNAME").toString());
			         m.setProperty("Id", row.getString("ID") == null ? "" : row.getString("ID").toString());
			         m.setProperty("AccountNumber", row.getString("ACCOUNTNUMBER") == null ? "" : row.getString("ACCOUNTNUMBER").toString());
			         m.setProperty("Reference", row.getString("REFERENCE") == null ? "" : row.getString("REFERENCE").toString());
			         m.setProperty("Amount", LongUtil.toString(Long.parseLong(row.getString("AMOUNT"))) == null ? "" : LongUtil.toString(Long.parseLong(row.getString("AMOUNT").toString())));
			         m.setProperty("Timestamp", row.getString("TIMESTAMP") == null ? "" : row.getString("TIMESTAMP").toString());
			         if(row.getString("LEVELOFAPPROVAL").equals(1) || row.getString("LEVELOFAPPROVAL").equals("1") && row.getString("STATUS").equals("APPROVED")){
			        	 m.setProperty("Status", "APPROVED");
			         }else if(row.getString("LEVELOFAPPROVAL").equals(0) || row.getString("LEVELOFAPPROVAL").equals("0") && row.getString("STATUS").equals("APPROVED")){
			        	 m.setProperty("Status", "COMPLETED");
			         }else if(row.getString("LEVELOFAPPROVAL").equals(2) || row.getString("LEVELOFAPPROVAL").equals("2") && row.getString("STATUS").equals("PNDG")){
			        	 m.setProperty("Status", "PENDING");
			         }else if(row.getString("LEVELOFAPPROVAL").equals(2) || row.getString("LEVELOFAPPROVAL").equals("2") && row.getString("STATUS").equals("CANCEL")){
			        	 m.setProperty("Status", "CANCELLED");
			         }else if(row.getString("LEVELOFAPPROVAL").equals(2) || row.getString("LEVELOFAPPROVAL").equals("2") || row.getString("LEVELOFAPPROVAL").equals(1) || row.getString("LEVELOFAPPROVAL").equals("1") && row.getString("STATUS").equals("REJECT")){
			        	 m.setProperty("Status", "REJECTED");
			         }else if(row.getString("LEVELOFAPPROVAL").equals(1) || row.getString("LEVELOFAPPROVAL").equals("1") && row.getString("STATUS").equals("PNDG")){
			        	 m.setProperty("Status", "PENDING");
			         }else if(row.getString("LEVELOFAPPROVAL").equals(1) || row.getString("LEVELOFAPPROVAL").equals("1") && row.getString("STATUS").equals("CANCEL")){
			        	 m.setProperty("Status", "CANCELLED");
			         }
			         m.setProperty("Remarks", row.getString("REMARKS") == null ? "" : row.getString("REMARKS").toString());
			         m.setProperty("DepositChannel", row.getString("DEPOSITCHANNEL") == null ? "" : row.getString("DEPOSITCHANNEL").toString());
			         
			         add(m);
		    	 }
		     }
			 return r.size() > 0;
		}else{
			DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT CT.ID,CT.ACCOUNTNUMBER,CT.REFERENCE,CT.AMOUNT,CT.TIMESTAMP,CT.STATUS,CT.LEVELOFAPPROVAL,B.BRANCH,CT.CREATEDBY,CT.REMARKS,CT.DEPOSITCHANNEL FROM TBLCASHINTRANSACTIONS CT INNER JOIN TBLBRANCHES B ON CT.ACCOUNTNUMBER = B.ACCOUNTNUMBER WHERE CT.STATUS IN ('APPROVED','PAID','NOTPAID') AND CT.LEVELOFAPPROVAL = 0 AND CT.ACCOUNTNUMBER IN (SELECT ACCOUNTNUMBER FROM ADMDBMC.TBLACCOUNTINFO WHERE ROOT = ?) ORDER BY CT.TIMESTAMP DESC",SystemInfo.getDb().QueryScalar("SELECT ID FROM ADMDBMC.TBLACCOUNTINFO WHERE ACCOUNTNUMBER = ?", "", user.getString("ACCOUNTNUMBER")));	     
		     
			if (!r.isEmpty())
		     {
		    	 for(DataRow row: r){
			         ReportItem m = new ReportItem();
			         m.setProperty("CreatedBy", row.getString("CREATEDBY") == null ? "" : row.getString("CREATEDBY"));
			         m.setProperty("Email", user.getString("EMAIL") == null ? "" : user.getString("EMAIL").toString());
			         m.setProperty("LastName", user.getString("LASTNAME") == null ? "" : user.getString("LASTNAME").toString());
			         m.setProperty("FirstName", user.getString("FIRSTNAME") == null ? "" : user.getString("FIRSTNAME").toString());
			         m.setProperty("Id", row.getString("ID") == null ? "" : row.getString("ID").toString());
			         m.setProperty("AccountNumber", row.getString("ACCOUNTNUMBER") == null ? "" : row.getString("ACCOUNTNUMBER").toString());
			         m.setProperty("Reference", row.getString("REFERENCE") == null ? "" : row.getString("REFERENCE").toString());
			         m.setProperty("Amount", LongUtil.toString(Long.parseLong(row.getString("AMOUNT"))) == null ? "" : LongUtil.toString(Long.parseLong(row.getString("AMOUNT").toString())));
			         m.setProperty("Timestamp", row.getString("TIMESTAMP") == null ? "" : row.getString("TIMESTAMP").toString());
			         if(row.getString("LEVELOFAPPROVAL").equals(1) || row.getString("LEVELOFAPPROVAL").equals("1") && row.getString("STATUS").equals("APPROVED")){
			        	 m.setProperty("Status", "APPROVED");
			         }else if(row.getString("LEVELOFAPPROVAL").equals(0) || row.getString("LEVELOFAPPROVAL").equals("0") && row.getString("STATUS").equals("APPROVED")){
			        	 m.setProperty("Status", "COMPLETED");
			         }else if(row.getString("LEVELOFAPPROVAL").equals(2) || row.getString("LEVELOFAPPROVAL").equals("2") && row.getString("STATUS").equals("PNDG")){
			        	 m.setProperty("Status", "PENDING");
			         }else if(row.getString("LEVELOFAPPROVAL").equals(2) || row.getString("LEVELOFAPPROVAL").equals("2") && row.getString("STATUS").equals("CANCEL")){
			        	 m.setProperty("Status", "CANCELLED");
			         }else if(row.getString("LEVELOFAPPROVAL").equals(2) || row.getString("LEVELOFAPPROVAL").equals("2") || row.getString("LEVELOFAPPROVAL").equals(1) || row.getString("LEVELOFAPPROVAL").equals("1") && row.getString("STATUS").equals("REJECT")){
			        	 m.setProperty("Status", "REJECTED");
			         }else if(row.getString("LEVELOFAPPROVAL").equals(1) || row.getString("LEVELOFAPPROVAL").equals("1") && row.getString("STATUS").equals("PNDG")){
			        	 m.setProperty("Status", "PENDING");
			         }else if(row.getString("LEVELOFAPPROVAL").equals(1) || row.getString("LEVELOFAPPROVAL").equals("1") && row.getString("STATUS").equals("CANCEL")){
			        	 m.setProperty("Status", "CANCELLED");
			         }
			         else if( row.getString("STATUS").equals("PAID")){
			        	 m.setProperty("Status", "COMPLETED-PAID");
			         }
			         else if( row.getString("STATUS").equals("NOTPAID")){
			        	 m.setProperty("Status", "COMPLETED-NOTPAID");
			         }
			         m.setProperty("Branch", row.getString("BRANCH") == null ? "" : row.getString("BRANCH").toString());
			         m.setProperty("Remarks", row.getString("REMARKS") == null ? "" : row.getString("REMARKS").toString());
			         m.setProperty("DepositChannel", row.getString("DEPOSITCHANNEL") == null ? "" : row.getString("DEPOSITCHANNEL").toString());
			         
			         add(m);
		    	 }
		     }
			 return r.size() > 0;
		}
		 
		   
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

}
