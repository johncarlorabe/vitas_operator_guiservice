package com.tlc.gui.absmobile.modules.session.m;


import java.util.HashMap;

import org.apache.http.entity.StringEntity;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.tlc.common.DataRow;
import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;
import com.tlc.gui.absmobile.modules.session.utils.HttpClientHelper;
import com.tlc.gui.absmobile.modules.session.utils.OtherProperties;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.session.UIPassword;

public class SessionPassword extends UIPassword {
	
	public SessionPassword(){}
	public SessionPassword(String value){this.value=value;}
	
	public boolean change(SessionPassword newPass){	
		 return SystemInfo.getDb().QueryUpdate("UPDATE TBLUSERS SET PASSWORD=ENCRYPT(?,?,USERNAME),ISFIRSTLOGON=0 WHERE USERID = ?",newPass.toString(),SystemInfo.getDb().getCrypt(), this.getAuthorizedSession().getToken().UserId)>0;
	}
	
	public boolean correct() {
		boolean ret =  db.QueryScalar("SELECT COUNT(1) FROM TBLUSERS WHERE PASSWORD = ENCRYPT(?,?,USERNAME) AND USERID=?",0,this.getValue(),db.getCrypt(),this.getAuthorizedSession().getToken().UserId)>0;
		this.setState(ret?new ObjectState("00"):new ObjectState("TLC-3904-02"));
		return ret;
	}
	
	public void send(){    
 	   try {
 		    OtherProperties prop = new OtherProperties();
 		      JSONObject request = new JSONObject();
 		      JSONObject request2 = new JSONObject();
 		     String day = SystemInfo.getDb().QueryScalar("SELECT TO_CHAR(SYSDATE,'Day') TODAY FROM DUAL", "");
 			String curdate = SystemInfo.getDb().QueryScalar("SELECT SYSDATE FROM DUAL", "");
 			DataRow user = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERID = ?", this.getAuthorizedSession().getToken().UserId);
 		      DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLSMTPCONFIG WHERE STATUS = 1", new Object[0]);
 		      DataRow message = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLMESSAGES WHERE MODULEID = '1025'");
 		      String email = user.getString("EMAIL").toString();

 		      String msg = message.get("MESSAGE").toString();
 		      msg = msg.replace("<firstname>", user.getString("FIRSTNAME"));
 		      msg = msg.replace("<day>", day);
 		     msg = msg.replace("<date>", curdate);

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
 		        }
 		        Logger.LogServer("Failed to Sent: " + object.get("code").toString() + " " + 
 		          object.get("message").toString() + " " + email);
 		      }

 		      Logger.LogServer("Failed to Sent: " + apiResponse.toString() + " " + email);
 		    }
 		    catch (Exception e) {
 		      Logger.LogServer("error: " + e);
 		    }
	}

}