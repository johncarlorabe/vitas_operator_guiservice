package com.psi.accountmanagement.m;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

import org.apache.http.entity.StringEntity;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.psi.accountmanagement.utils.HttpClientHelper;
import com.psi.accountmanagement.utils.OtherProperties;
import com.psi.accountmanagement.utils.Users;
import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ObjectState;

public class NewRegister extends Users{
	
	
	public boolean registers(){
		
		StringBuilder query = new StringBuilder("BEGIN\n");
		//query.append("INSERT INTO TBLACCOUNTINFO(EMAIL,FIRSTNAME,LASTNAME,MIDDLENAME,MSISDN,COUNTRY,PROVINCE,CITY,STATUS,REGDATE,ACCOUNTNUMBER) VALUES(?,?,?,?,?,?,?,?,'APPROVED',SYSDATE,?); \n");
		query.append("INSERT INTO TBLUSERS(EMAIL,FIRSTNAME,LASTNAME,MSISDN,USERSLEVEL,STATUS,DATEREGISTERED,AUTHCODE,USERNAME,PASSWORD,CITY,PROVINCE,COUNTRY,MIDDLENAME) VALUES(?,?,?,?,'CUSTOMER','ACTIVE',SYSDATE,?,?,ENCRYPT(?,?,?),?,?,?,?); \n");
		query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		 return SystemInfo.getDb().QueryUpdate(query.toString(),this.email, this.firstname, this.lastname,this.msisdn,this.code,this.email,this.password,SystemInfo.getDb().getCrypt(),this.email,this.city,this.province,this.country,this.midname)>0;		
	}
	@SuppressWarnings({ "unchecked", "static-access" })
	public boolean register() throws IOException, ParseException, org.json.simple.parser.ParseException{
		OtherProperties prop = new OtherProperties();
		JSONObject request = new JSONObject();
		JSONObject request2 = new JSONObject();
		JSONObject request3 = new JSONObject();
		JSONObject request4 = new JSONObject();
		
		String reqid = SystemInfo.getDb().QueryScalar("SELECT ADMDBMC.GETREFERENCEID(1000) FROM DUAL", "0");
	    
		request.put("request-id", reqid);
		request3.put("valid-id", "NOTAPPLICABLE");
		request.put("valid-id-desc","NOTAPPLICABLE");
			request2.put("password", "1234");
		request.put("auth", request2);
				request3.put("account-name", this.firstname.toUpperCase()+"_"+this.lastname.toUpperCase());
				request3.put("first-name", this.firstname);
				request3.put("middle-name", this.midname);
				request3.put("last-name", this.lastname);
				request3.put("password", this.password);
					request4.put("region-code", this.province);
					request4.put("city-code", this.city);
					request4.put("postal-code", "");
					request4.put("coordinates", "");
					request4.put("specific-address", this.province+" "+this.city+" "+this.country);
				request3.put("resident-address", request4);
				request3.put("authorized-mobile",this.msisdn);
				request3.put("authorized-email", this.email);
		request.put("subscriber", request3);
		
		Logger.LogServer(request.toString());
		
		StringEntity entity = new StringEntity(request.toJSONString());
		
		HttpClientHelper client = new HttpClientHelper();
	    HashMap<String, String> headers = new HashMap<String, String>();
	    headers.put("Content-Type", prop.getType());
	    headers.put("token",prop.getToken());
	    headers.put("X-Forwarded-For","127.0.0.1");
	    byte[] apiResponse = client.httpPost(prop.getUrl()+"745897721237"+"/subscribers", null, headers, null, entity);

	    if(apiResponse.length>0){
	    	JSONObject object = (JSONObject)new JSONParser().parse(new String(apiResponse, "UTF-8"));	    
		    Logger.LogServer(" response : " + new String(apiResponse));
		   
		    if(object.get("code").toString().equals("0") || object.get("code").toString().equals(0)){
	
		    	StringBuilder query = new StringBuilder("BEGIN\n");
				//query.append("INSERT INTO TBLACCOUNTINFO(EMAIL,FIRSTNAME,LASTNAME,MIDDLENAME,MSISDN,COUNTRY,PROVINCE,CITY,STATUS,REGDATE,ACCOUNTNUMBER) VALUES(?,?,?,?,?,?,?,?,'APPROVED',SYSDATE,?); \n");
				query.append("INSERT INTO TBLUSERS(EMAIL,FIRSTNAME,LASTNAME,MSISDN,USERSLEVEL,STATUS,DATEREGISTERED,AUTHCODE,USERNAME,PASSWORD,CITY,PROVINCE,COUNTRY,MIDDLENAME,ACCOUNTNUMBER) VALUES(?,?,?,?,'CUSTOMER','ACTIVE',SYSDATE,?,?,ENCRYPT(?,?,?),?,?,?,?,?); \n");
				query.append("INSERT INTO TBLCUSTOMEREXTENDEDDETAILS(ACCOUNTNUMBER,DATEOFBIRTH,NATUREOFWORK,SOURCEOFFUND,NATIONALITY,IDTYPE,IDVALUE,PLACEOFBIRTH) VALUES(?,TO_DATE(?,'YYYY-MM-DD'),?,?,'','','',?);\n");
				query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
				 SystemInfo.getDb().QueryUpdate(query.toString(),this.email, this.firstname, this.lastname,this.msisdn,this.code,this.email,this.password,SystemInfo.getDb().getCrypt(),this.email,this.city,this.province,this.country,this.midname,object.get("account-number").toString(),
						 object.get("account-number").toString(),this.dateofbirth,this.natureofwork,this.sourceoffund,this.placeoofbirth);		
			
						this.setState(new ObjectState("0",object.get("message").toString()));
				    	return true;
		    		
		    	
			  }else{
			    this.setState(new ObjectState(object.get("code").toString(),object.get("message").toString()));
		    	return false;
			  }
	    }else{
	    	this.setState(new ObjectState("99","System is busy"));
	    	return false;
	    }
	}
	
	public boolean exist(){
		
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE EMAIL = ?", this.email).size()>0;
	}
}
