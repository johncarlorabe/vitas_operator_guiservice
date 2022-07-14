package com.psi.accountmanagement.utils;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.http.entity.StringEntity;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.DbWrapper;
import com.tlc.common.Logger;
import com.tlc.common.StringUtil;
import com.tlc.common.SystemInfo;
import com.tlc.smtp.EmailAddress;
import com.tlc.smtp.EmailMessage;

public class EmailUtils
{

  public static boolean send(String email,String fname, String lname, String authcode,String password,String username) {
	  try{
		 
		       
		         EmailMessage emailMessage = new EmailMessage(new EmailAddress(email), 1000, "100000");
		 	    emailMessage.replace(new String []{"<firstname>",fname, 
		 	    		"<email>",lname,
		 	    		"<authcode>",authcode,
		 	    		"<password>",password,
		 	    		"<username>",username});
	   
	    emailMessage.send();  
	   
	    Logger.LogServer("Email Sent to: " + email.toString());
	    
	    return true;
	  }
	  catch(Exception e){
		  Logger.LogServer("error: " + e);
		  return false;
	  }
	  }
  public static boolean sendPassword(String email, String password,String url) {
	  try{
	
		         EmailMessage emailMessage = new EmailMessage(new EmailAddress(email), 1020, "102000");
		 	    emailMessage.replace(new String []{"<password>",password,"<url>",url});
	   
	    emailMessage.send();  
	   
	    Logger.LogServer("Email Sent to: " + email.toString());
	    
	    return true;
	  }
	  catch(Exception e){
		  Logger.LogServer("error: " + e);
		  return false;
	  }
	  }
  
  public static boolean sendEmailResetPassword(String userid){
	    try {

	    	OtherProperties prop = new OtherProperties();

	        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMddHHmmss");
	        Date now = new Date();
	        String strDate = sdfDate.format(now);
	        String requestid = strDate + "01";
	        DataRow username = SystemInfo.getDb().QueryDataRow("SELECT USERNAME FROM TBLUSERS WHERE USERID=?", new Object[]  {userid });
	        String uname = username.get("USERNAME").toString();
	        
	        Logger.LogServer(prop.getUrl()+"ibayad/pos/password?cashier-id="+uname+"&"+"request-id="+requestid);

		    
			HttpClientHelper client = new HttpClientHelper();
		    HashMap<String, String> headers = new HashMap<String, String>();
		    headers.put("Content-Type", prop.getType());
		    headers.put("token", prop.getToken());
		    headers.put("X-Forwarded-For","127.0.0.1");
		    
		    byte[] apiResponse = client.httpDelete(prop.getUrl()+"ibayad/pos/password?cashier-id="+uname+"&"+"request-id="+requestid, null, headers, null);
		    JSONObject object = (JSONObject)new JSONParser().parse(new String(apiResponse, "UTF-8"));	 
	    	
		    Logger.LogServer(" response : " + new String(apiResponse));

	        if (apiResponse.length > 0) {
	          Logger.LogServer(" response : " + new String(apiResponse));

	          if ((object.get("code").toString().equals("1")) || (object.get("code").toString().equals(Integer.valueOf(0))) || (object.get("code").toString().equals("0"))) {
	            Logger.LogServer("Email Sent to: " + userid.toString());
	            return true;
	          }else{
		          Logger.LogServer("Failed to Sent1: " + object.get("code").toString() + " " + 
		            object.get("message").toString() + " " + userid.toString());
		          return false;
	          }
	        }else{

	        Logger.LogServer("Failed to Sent2: " + apiResponse.toString() + " " + userid.toString());
	        return false;
	        }
	      }
	      catch (Exception e) {
	        Logger.LogServer("error: " + e);
	        
	      }return true;
	  
  }
  public static boolean sendEmailForgotPassword(String email, String password,String url) {
	    try {
	    OtherProperties prop = new OtherProperties();
	      JSONObject request = new JSONObject();
	      JSONObject request2 = new JSONObject();

	      DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLSMTPCONFIG WHERE STATUS = 1", new Object[0]);
	      DataRow message = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMESSAGES WHERE MODULEID = '1020'");


	      String msg = message.get("MESSAGE").toString();
	      msg = msg.replace("<password>", password);
	      msg = msg.replace("<url>", url);

	      Logger.LogServer("Message replace: " + msg);
	      request.put("auth", request2);
	      request2.put("host", row.get("HOST"));
	      request2.put("port", row.get("PORT").toString());
	      request2.put("username", row.get("USERNAME"));
	      request2.put("password", row.get("PASSWORD"));
	      request.put("to", email);
	      request.put("subject", message.getString("DESCRIPTION"));
	      request.put("body", msg);

	      Logger.LogServer("Request: " + request.toString());
	      StringEntity entity = new StringEntity(request.toJSONString());

	      HttpClientHelper client = new HttpClientHelper();
	      HashMap headers = new HashMap();
	      headers.put("Content-Type", prop.getType());
	      headers.put("token", prop.getToken());

	      byte[] apiResponse = client.httpPost(row.getString("URL").toString(), null, headers, null, 
	        entity);
	      Logger.LogServer(row.getString("URL").toString());
	      if (apiResponse.length > 0) {
	        JSONObject object = (JSONObject)new JSONParser().parse(new String(apiResponse, "UTF-8"));
	        Logger.LogServer(" response : " + new String(apiResponse));

	        if ((object.get("code").toString().equals("1")) || 
	          (object.get("code").toString().equals(Integer.valueOf(0)))) {
	          Logger.LogServer("Email Sent to: " + email);
	          return true;
	        }
	        Logger.LogServer("Failed to Sent: " + object.get("code").toString() + " " + 
	          object.get("message").toString() + " " + email);
	        return false;
	      }

	      Logger.LogServer("Failed to Sent: " + apiResponse.toString() + " " + email);
	      return false;
	    }
	    catch (Exception e) {
	      Logger.LogServer("error: " + e);
	    }return false;
	  }
 

}
