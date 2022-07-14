package com.psi.purchases.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.http.entity.StringEntity;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.psi.purchases.util.HttpClientHelper;
import com.psi.purchases.util.OtherProperties;
import com.tlc.common.DataRow;
import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.common.ObjectState;

public class Bill extends Model{
	
	@SuppressWarnings("unchecked")
	public boolean billspayment(String ordernumber, String trackingnumber, String id,String billertag,String macaddress) throws ParseException, Exception {
		OtherProperties prop = new OtherProperties();		
		JSONObject request = new JSONObject();
		JSONObject request2 = new JSONObject();
		JSONObject request3 = new JSONObject();
		JSONObject request4 = new JSONObject();
		String cashier_id = null;
		String branchcode = null;
		String terminal = null;
		String accountnumber = null;
		String amount = null;
		String billamount =null;
		String account = null;
		String ref = null;
		String loantype=null;
		String reqid = SystemInfo.getDb().QueryScalar("SELECT ADMDBMC.GETREFERENCEID(5100) FROM DUAL", "0");
				
		DataRow acct = SystemInfo.getDb().QueryDataRow("SELECT U.USERNAME,PO.BRANCHCODE,U.TERMINAL,U.ACCOUNTNUMBER FROM TBLUSERS U INNER JOIN TBLBRANCHES B ON U.ACCOUNTNUMBER=B.ACCOUNTNUMBER INNER JOIN TBLPOSUSERS PO ON PO.USERID = U.USERNAME WHERE U.USERID=?", id);
		DataRow row = SystemInfo.getDb().QueryDataRow("SELECT REFERENCECODE1,REFERENCECODE2,AMOUNT/100 AMOUNT,FIRSTNAME,LASTNAME,SECONDNAME,TO_CHAR(BILLDATE,'YYYY-MM-DD') BILLDATE, TO_CHAR(DUEDATE,'YYYY-MM-DD') DUEDATE,MEMBERTYPE,PAYMENTTYPE,COUNTRY,COMPANY,BILLAMOUNT/100 BILLAMOUNT,LOANTYPE,SERVICETYPE,PAYMENTENTRY,MSISDN FROM TBLORDERREQUEST WHERE TYPE='BILLS' AND ORDERNUMBER=? AND TRACKINGNUMBER=?",ordernumber,trackingnumber);
			
		cashier_id = acct.getString("USERNAME");
		branchcode = acct.getString("BRANCHCODE");
		terminal = acct.getString("TERMINAL");
		accountnumber = acct.getString("ACCOUNTNUMBER");
		account = row.getString("REFERENCECODE1");
		amount = row.getString("AMOUNT");
		billamount = row.getString("BILLAMOUNT");
		ref = row.getString("REFERENCECODE2");
		loantype = row.getString("LOANTYPE");
	

			//request.put("destination", "PHILARP_POSTPAID");
			request.put("request-id", reqid);
			request3.put("branch-id", branchcode);
			request3.put("terminal-id", macaddress);
			request3.put("cashier-id", cashier_id);
			request3.put("paid-amount", amount);
			request3.put("bill-amount", billamount);
			request3.put("service-reference", account);
			request3.put("billing-account", ref);
			request3.put("biller", billertag);
			request3.put("last-name", row.getString("LASTNAME"));
			request3.put("first-name", row.getString("FIRSTNAME"));
			if(billertag.equals("SSS-EMPLOYER") || billertag.equals("PHILHEALTH") || billertag.equals("PAGIBIG-CONTRIB") || billertag.equals("PAGIBIG-LOAN") || billertag.equals("PHILHEALTH-OFW") || billertag.equals("SSS-REGULAR") || billertag.equals("SSS-OFW") || billertag.equals("SSS-FLEXIFUND")||billertag.equals("EQUICOM-CREDITCARD") ){
				String bdate,ddate = "";
				if(row.getString("BILLDATE").equals("")){
					bdate = "2017-01-01";
				}else{
					bdate = row.getString("BILLDATE");
				}
				if(row.getString("DUEDATE").equals("")){
					ddate = "2017-01-01";
				}else{
					ddate = row.getString("DUEDATE");
				}
				request3.put("period-from", bdate);
				request3.put("period-to", ddate);
			}else{
				String bdate,ddate = "";
				if(row.getString("BILLDATE").equals("")){
					bdate = "2017-01-01";
				}else{
					bdate = row.getString("BILLDATE");
				}
				if(row.getString("DUEDATE").equals("")){
					ddate = "2017-01-01";
				}else{
					ddate = row.getString("DUEDATE");
				}
				request3.put("bill-date", bdate);
				request3.put("due-date", ddate);
			}
			request3.put("middle-name", row.getString("SECONDNAME"));
			request3.put("membership-type", row.getString("MEMBERTYPE"));
			request3.put("country",row.getString("COUNTRY"));
			request3.put("company-name", row.getString("COMPANY"));
			if(billertag.equals("PHILHEALTH") || billertag.equals("PHILHEALTH-OFW") || billertag.equals("EQUICOM-CREDITCARD")){
				request3.put("payment-interval", row.getString("PAYMENTTYPE"));
			}else{
				request3.put("payment-type", row.getString("PAYMENTTYPE"));
			}
			request4.put("loan-type", loantype);
			request4.put("service-type", row.get("SERVICETYPE"));
			request4.put("country",row.getString("COUNTRY") );
			request4.put("payment-entry", row.getString("PAYMENTENTRY"));
			request4.put("contact-number", row.getString("MSISDN"));
			request3.put("bill-specific", request4);
			request.put("payment-data", request3);
			request2.put("business", "1166");
			request.put("auth",request2);
			
			Logger.LogServer(request.toString());
		
				StringEntity entity = new StringEntity(request.toJSONString());
				
				HttpClientHelper client = new HttpClientHelper();
			    HashMap<String, String> headers = new HashMap<String, String>();
			    headers.put("Content-Type", prop.getType());
			    headers.put("token", prop.getToken());
			    headers.put("X-Forwarded-For","127.0.0.1");
			    byte[] apiResponse = client.httpPost(prop.getUrl()+accountnumber+prop.getBills_url(), null, headers, null, entity);
			    Logger.LogServer("Billspayment url:"+prop.getUrl()+accountnumber+prop.getBills_url());
			   	Logger.LogServer("Billspayment response:"+new String(apiResponse, "UTF-8"));
			    if(apiResponse.length>0){
			    	JSONObject object = (JSONObject)new JSONParser().parse(new String(apiResponse, "UTF-8"));	    
				    Logger.LogServer(" response : " + new String(apiResponse));
				   
				    if(object.get("code").toString().equals("0") || object.get("code").toString().equals(0)){
				    	SystemInfo.getDb().QueryUpdate("UPDATE TBLORDERREQUEST SET STATUS='COMPLETE',EXTENDEDDATA=?,RESPONSEMESSAGE=? WHERE ORDERNUMBER = ? AND TRACKINGNUMBER = ?",object.get("response-id").toString(), ordernumber,trackingnumber);
				    	this.setState(new ObjectState(object.get("code").toString(),object.get("message").toString()));
				    	return true;
					  }else if(object.get("code").toString().equals("99") || object.get("code").toString().equals(99)){
						  {
							 // SystemInfo.getDb().QueryUpdate("UPDATE TBLORDERREQUEST SET STATUS='FAILED',EXTENDEDDATA=?,RESPONSEMESSAGE=? WHERE ORDERNUMBER = ? AND TRACKINGNUMBER = ?",object.get("response-id").toString(),object.get("message").toString(), ordernumber,trackingnumber);
							  this.setState(new ObjectState("05",object.get("message").toString()));
						    	return false;
							  }
						  }
				    else{
				    	//SystemInfo.getDb().QueryUpdate("UPDATE TBLORDERREQUEST SET STATUS='FAILED',EXTENDEDDATA=?,RESPONSEMESSAGE=? WHERE ORDERNUMBER = ? AND TRACKINGNUMBER = ?",object.get("response-id").toString(),object.get("message").toString(), ordernumber,trackingnumber);
						  
					    this.setState(new ObjectState(object.get("code").toString(),object.get("message").toString()));
				    	return false;
					  }
			    }else{
			    	this.setState(new ObjectState("01","Unable to connect, Please try again later"));
			    	return false;
			    }

	}

}
