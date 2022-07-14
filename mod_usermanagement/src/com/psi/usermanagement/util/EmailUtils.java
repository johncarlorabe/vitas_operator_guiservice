package com.psi.usermanagement.util;


import java.util.HashMap;

import org.apache.http.entity.StringEntity;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.tlc.common.DataRow;
import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;
import com.tlc.smtp.EmailAddress;
import com.tlc.smtp.EmailMessage;

public class EmailUtils
{

/*  public static boolean send(String email,String fname, String lname, String password,String username,String manfname, String manlname) {
	  try{
		 
		       
		         EmailMessage emailMessage = new EmailMessage(new EmailAddress(email), 7000, "700000");
		 	    emailMessage.replace(new String []{"<firstname>",fname, 
		 	    		"<lastname>",lname,
		 	    		"<password>",password,
		 	    		"<username>",username,
		 	    		"<managerfirstname>",manfname,
		 	    		"<managerlastname>",manlname});
	   
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
/*  public static boolean sendEmail(String email,String fname, String lname, String password,String username) {
	  try{
		 
		       
		         EmailMessage emailMessage = new EmailMessage(new EmailAddress(email), 1026, "102600");
		 	    emailMessage.replace(new String []{"<firstname>",fname, 
		 	    		"<lastname>",lname,
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
	  }*/
	
	  public static boolean send(String email,String fname, String lname, String password,String username,String manfname, String manlname, String gui) {
		    try {
		    OtherProperties prop = new OtherProperties();
		      JSONObject request = new JSONObject();
		      JSONObject request2 = new JSONObject();

		      DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLSMTPCONFIG", new Object[0]);
		      String msg="";
		      String subject="";
		      if(gui.equals("ABSPAY")){
		      DataRow message = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMESSAGES WHERE MODULEID = '7020'");
		      	  msg = message.get("MESSAGE").toString();
			      msg = msg.replace("<firstname>", fname);
			      msg = msg.replace("<lastname>", lname);
			      msg = msg.replace("<password>", password);
			      msg = msg.replace("<username>", username);
			      msg = msg.replace("<url>", "https://makatizen.ibayad.com/absoperator/login");
			      msg = msg.replace("<gui>", "ABS PAY");
			      msg = msg.replace("<from>", "ABS PAY");
		      	  subject = "ABS PAY Registration Credentials";
		      }else{
		    	  DataRow message = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMESSAGES WHERE MODULEID = '7020'");
		    	  msg = message.get("MESSAGE").toString();
			      msg = msg.replace("<firstname>", fname);
			      msg = msg.replace("<lastname>", lname);
			      msg = msg.replace("<password>", password);
			      msg = msg.replace("<username>", username);
			      msg = msg.replace("<url>", "https://makatizen.ibayad.com/solbcompany/login");
			      msg = msg.replace("<gui>", "SOLB");
			      msg = msg.replace("<from>", "SOLB");
		    	  subject = "Registration Credentials";
		      }



		      Logger.LogServer("Message replace: " + msg);
		      request.put("auth", request2);
		      request2.put("host", row.get("HOST"));
		      request2.put("port", row.get("PORT").toString());
		      request2.put("username", row.get("USERNAME"));
		      request2.put("password", row.get("PASSWORD"));
		      request.put("to", email);
		      request.put("subject", subject);
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
  
  public static boolean sendEmail(String email,String fname, String lname, String password,String username, String gui) {
	    try {
	    OtherProperties prop = new OtherProperties();
	      JSONObject request = new JSONObject();
	      JSONObject request2 = new JSONObject();

	      DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLSMTPCONFIG", new Object[0]);
	      String msg="";
	      String subject="";
	      if(gui.equals("ABSPAY")){
	      DataRow message = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMESSAGES WHERE MODULEID = '1026'");
	      	  msg = message.get("MESSAGE").toString();
		      msg = msg.replace("<firstname>", fname);
		      msg = msg.replace("<lastname>", lname);
		      msg = msg.replace("<password>", password);
		      msg = msg.replace("<username>", username);
		      //msg = msg.replace("<url>", "https://makatizen.ibayad.com/absoutlet/login");
		      msg = msg.replace("<gui>", "ABS PAY");
		      msg = msg.replace("<from>", "ABS PAY");
	      	  subject = "ABS PAY Registration Credentials";
	      }
	      
	      else{
	    	  DataRow message = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMESSAGES WHERE MODULEID = '1026'");
	    	  msg = message.get("MESSAGE").toString();
		      msg = msg.replace("<firstname>", fname);
		      msg = msg.replace("<lastname>", lname);
		      msg = msg.replace("<password>", password);
		      msg = msg.replace("<username>", username);
		     // msg = msg.replace("<url>", "https://makatizen.ibayad.com/outlet/login");
		      msg = msg.replace("<gui>", "SOLB");
		      msg = msg.replace("<from>", "SOLB");
	    	  subject = "Registration Credentials";
	      }



	      Logger.LogServer("Message replace: " + msg);
	      request.put("auth", request2);
	      request2.put("host", row.get("HOST"));
	      request2.put("port", row.get("PORT").toString());
	      request2.put("username", row.get("USERNAME"));
	      request2.put("password", row.get("PASSWORD"));
	      request.put("to", email);
	      request.put("subject", subject);
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
  
  
  
  
}
