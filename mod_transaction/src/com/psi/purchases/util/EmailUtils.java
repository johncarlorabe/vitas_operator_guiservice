package com.psi.purchases.util;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.entity.StringEntity;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.tlc.common.DataRow;
import com.tlc.common.DbWrapper;
import com.tlc.common.Logger;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.smtp.EmailAddress;
import com.tlc.smtp.EmailMessage;

public class EmailUtils
{

  public static boolean sendClaim(String email,String track,String ordernumber,String fname) {
	  try{       
		       EmailMessage emailMessage = new EmailMessage(new EmailAddress(email), 1013, "101300");
		 	    emailMessage.replace(new String []{"<trackingnumber>",track,"<ordernumber>",ordernumber,"<firstname>",fname});
	   
	    emailMessage.send();  
	   
	    Logger.LogServer("Email Sent to: " + email.toString());
	    
	    return true;
	  }
	  catch(Exception e){
		  Logger.LogServer("error: " + e);
		  return false;
	  }
}
  public static boolean sendEmail(String email,String track,String ordernumber) {
	  try{       
		       EmailMessage emailMessage = new EmailMessage(new EmailAddress(email), 1021, "102100");
		 	    emailMessage.replace(new String []{"<trackingnumber>",track,"<ordernumber>",ordernumber});
	   
	    emailMessage.send();  
	   
	    Logger.LogServer("Email Sent to: " + email.toString());
	    
	    return true;
	  }
	  catch(Exception e){
		  Logger.LogServer("error: " + e);
		  return false;
	  }
}
  public static boolean send(String email, String lastname, String firstname,String amount) {
	  try{
		  		EmailMessage emailMessage = new EmailMessage(new EmailAddress(email), 1014, "101400");
		 	    emailMessage.replace(new String []{"<amount>",LongUtil.toString(Long.parseLong(amount)),"<firstname>",firstname
		 	    		,"<lastname>",lastname});
	   
	    emailMessage.send();  
	   
	    Logger.LogServer("Email Sent to: " + email.toString());
	    
	    return true;
	  }
	  catch(Exception e){
		  Logger.LogServer("error: " + e);
		  return false;
	  }
	  }
  public static boolean send(String email, String lastname, String firstname,String amount,String operator) {
	  try{
		  		EmailMessage emailMessage = new EmailMessage(new EmailAddress(email), 1015, "101500");
		 	    emailMessage.replace(new String []{"<amount>",LongUtil.toString(Long.parseLong(amount)),"<firstname>",firstname
		 	    		,"<lastname>",lastname});
	   
	    emailMessage.send();  
	   
	    Logger.LogServer("Email Sent to: " + email.toString());
	    
	    return true;
	  }
	  catch(Exception e){
		  Logger.LogServer("error: " + e);
		  return false;
	  }
}
  public static boolean send(String email) {
	  try{       
		       EmailMessage emailMessage = new EmailMessage(new EmailAddress(email), 1016, "101600");
		 	    emailMessage.replace(new String []{});
	   
	    emailMessage.send();  
	   
	    Logger.LogServer("Email Sent to: " + email.toString());
	    
	    return true;
	  }
	  catch(Exception e){
		  Logger.LogServer("error: " + e);
		  return false;
	  }
}
  public static boolean sendCashier(String email) {
	  try{       
		       EmailMessage emailMessage = new EmailMessage(new EmailAddress(email), 1017, "101700");
		 	    emailMessage.replace(new String []{});
	   
	    emailMessage.send();  
	   
	    Logger.LogServer("Email Sent to: " + email.toString());
	    
	    return true;
	  }
	  catch(Exception e){
		  Logger.LogServer("error: " + e);
		  return false;
	  }
}
/*  public static boolean sendApprovedCashier(String email) {
	  try{       
		       EmailMessage emailMessage = new EmailMessage(new EmailAddress(email), 1018, "101800");
		 	    emailMessage.replace(new String []{});
	   
	    emailMessage.send();  
	   
	    Logger.LogServer("Email Sent to: " + email.toString());
	    
	    return true;
	  }
	  catch(Exception e){
		  Logger.LogServer("error: " + e);
		  return false;
	  }
}
  */
    public static boolean sendApprovedCashier(String email) {
  try {
  OtherProperties prop = new OtherProperties();
    JSONObject request = new JSONObject();
    JSONObject request2 = new JSONObject();

    DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLSMTPCONFIG", new Object[0]);
    DataRow message = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMESSAGES WHERE MODULEID = '1018'");


    String msg = message.get("MESSAGE").toString();

    Logger.LogServer("Message replace: " + msg);
    request.put("auth", request2);
    request2.put("host", row.get("HOST"));
    request2.put("port", row.get("PORT").toString());
    request2.put("username", row.get("USERNAME"));
    request2.put("password", row.get("PASSWORD"));
    request.put("to", email);
    request.put("subject", message.get("DESCRIPTION"));
    request.put("body", msg);

    Logger.LogServer("Request: " + request.toString());
    StringEntity entity = new StringEntity(request.toJSONString());

    HttpClientHelper client = new HttpClientHelper();
    HashMap headers = new HashMap();
    headers.put("Content-Type", prop.getType());
    headers.put("token", prop.getToken());

    byte[] apiResponse = client.httpPost(row.get("URL").toString(), null, headers, null, 
      entity);
    Logger.LogServer(row.get("URL").toString());
    

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
  
/*  public static boolean sendApproved(String email,String reference) {
	  try{       
		       EmailMessage emailMessage = new EmailMessage(new EmailAddress(email), 1019, "101900");
		 	    emailMessage.replace(new String []{"<referencenumber>",reference});
	   
	    emailMessage.send();  
	   
	    Logger.LogServer("Email Sent to: " + email.toString());
	    
	    return true;
	  }
	  catch(Exception e){
		  Logger.LogServer("error: " + e);
		  return false;
	  }
}*/
  
  public static boolean sendApproved(String email,String reference) {
	    try {
	    OtherProperties prop = new OtherProperties();
	      JSONObject request = new JSONObject();
	      JSONObject request2 = new JSONObject();

	      DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLSMTPCONFIG", new Object[0]);
	      DataRow message = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMESSAGES WHERE MODULEID = '1019'");


	      String msg = message.get("MESSAGE").toString();
	      msg = msg.replace("<referencenumber>", reference);

	      Logger.LogServer("Message replace: " + msg);
	      request.put("auth", request2);
	      request2.put("host", row.get("HOST"));
	      request2.put("port", row.get("PORT").toString());
	      request2.put("username", row.get("USERNAME"));
	      request2.put("password", row.get("PASSWORD"));
	      request.put("to", email);
	      request.put("subject", message.get("DESCRIPTION"));
	      request.put("body", msg);

	      Logger.LogServer("Request: " + request.toString());
	      StringEntity entity = new StringEntity(request.toJSONString());

	      HttpClientHelper client = new HttpClientHelper();
	      HashMap headers = new HashMap();
	      headers.put("Content-Type", prop.getType());
	      headers.put("token", prop.getToken());

	      byte[] apiResponse = client.httpPost(row.get("URL").toString(), null, headers, null, 
	        entity);
	      Logger.LogServer(row.get("URL").toString());
	      

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
  
  
/*  public static boolean sendReject(String email) {
	  try{       
		       EmailMessage emailMessage = new EmailMessage(new EmailAddress(email), 1022, "102200");
		 	    emailMessage.replace(new String []{});
	   
	    emailMessage.send();  
	   
	    Logger.LogServer("Email Sent to: " + email.toString());
	    
	    return true;
	  }
	  catch(Exception e){
		  Logger.LogServer("error: " + e);
		  return false;
	  }
}*/
  
  public static boolean sendReject(String email) {
	    try {
	    OtherProperties prop = new OtherProperties();
	      JSONObject request = new JSONObject();
	      JSONObject request2 = new JSONObject();

	      DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLSMTPCONFIG", new Object[0]);
	      DataRow message = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMESSAGES WHERE MODULEID = '1022'");


	      String msg = message.get("MESSAGE").toString();

	      Logger.LogServer("Message replace: " + msg);
	      request.put("auth", request2);
	      request2.put("host", row.get("HOST"));
	      request2.put("port", row.get("PORT").toString());
	      request2.put("username", row.get("USERNAME"));
	      request2.put("password", row.get("PASSWORD"));
	      request.put("to", email);
	      request.put("subject", message.get("DESCRIPTION"));
	      request.put("body", msg);

	      Logger.LogServer("Request: " + request.toString());
	      StringEntity entity = new StringEntity(request.toJSONString());

	      HttpClientHelper client = new HttpClientHelper();
	      HashMap headers = new HashMap();
	      headers.put("Content-Type", prop.getType());
	      headers.put("token", prop.getToken());

	      byte[] apiResponse = client.httpPost(row.get("URL").toString(), null, headers, null, 
	        entity);
	      Logger.LogServer(row.get("URL").toString());
	      

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
  
  public static boolean sendRejectManager(String email) {
	  try{       
		       EmailMessage emailMessage = new EmailMessage(new EmailAddress(email), 1023, "102300");
		 	    emailMessage.replace(new String []{});
	   
	    emailMessage.send();  
	   
	    Logger.LogServer("Email Sent to: " + email.toString());
	    
	    return true;
	  }
	  catch(Exception e){
		  Logger.LogServer("error: " + e);
		  return false;
	  }
}
  public static boolean sendCancel(String email,String refnum) {
	  try{       
		       EmailMessage emailMessage = new EmailMessage(new EmailAddress(email), 1024, "102400");
		 	    emailMessage.replace(new String []{"<referencenumber>",refnum});
	   
	    emailMessage.send();  
	   
	    Logger.LogServer("Email Sent to: " + email.toString());
	    
	    return true;
	  }
	  catch(Exception e){
		  Logger.LogServer("error: " + e);
		  return false;
	  }
}
}
