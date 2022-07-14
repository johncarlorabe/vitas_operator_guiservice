package com.psi.business.m;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.http.entity.StringEntity;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.psi.business.util.Branch;
import com.psi.business.util.HttpClientHelper;
import com.psi.business.util.OtherProperties;
import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ObjectState;

public class NewBusiness extends Branch{
	public static final String PROP_ACCOUNTNUMBER="ACCOUNTNUMBER";
	protected String accountnumber;
	
	@SuppressWarnings("unchecked")
	public boolean register(String acct) throws IOException, ParseException{
		OtherProperties prop = new OtherProperties();
		JSONObject request = new JSONObject();
		JSONObject request2 = new JSONObject();
		JSONObject request3 = new JSONObject();
		JSONObject request4 = new JSONObject();
		JSONObject request5 = new JSONObject();
		
		String reqid = SystemInfo.getDb().QueryScalar("SELECT ADMDBMC.GETREFERENCEID(3010) FROM DUAL", "0");
	    String code = SystemInfo.getDb().QueryScalar("SELECT TBLBRANCHESCODE_SEQ.NEXTVAL FROM DUAL", "0");
	    String date = SystemInfo.getDb().QueryScalar("SELECT SYSDATE FROM DUAL", "0");
	    		
		request.put("type", "merchant");
		request.put("request-id", reqid);
		request.put("user-id", "POLEN");
		request.put("valid-id-desc","COMPANY");
			request2.put("password", "1234");
		request.put("auth", request2);
				request3.put("password", "1234");
				request3.put("account-name", this.branchname);
				request3.put("business-name", this.branchname);
				request3.put("first-name", this.branchname);
				request3.put("middle-name", this.branchname);
				request3.put("last-name", this.branchname);
				request3.put("authorized-mobile", this.contactnumber);
				request3.put("valid-id", "BAYAD"+code);
					request4.put("registration-date", date);
					request4.put("tin", "");
						request5.put("region-code", this.province);
						request5.put("city-code", this.city);
						request5.put("postal-code", this.zipcode);
						request5.put("coordinates", this.xordinate +","+this.yordinate);
						request5.put("specific-address", this.address);
					request4.put("business-address", request5);
				request3.put("corporate-info", request4);
		request.put("subscriber", request3);
		
		Logger.LogServer("business request:"+request.toString());
		Logger.LogServer("url:"+prop.getUrl()+acct+"/corporates");
		StringEntity entity = new StringEntity(request.toJSONString());
		
		HttpClientHelper client = new HttpClientHelper();
	    HashMap<String, String> headers = new HashMap<String, String>();
	    headers.put("Content-Type", prop.getType());
	    headers.put("token",prop.getToken());
	    headers.put("X-Forwarded-For","127.0.0.1");
	    byte[] apiResponse = client.httpPost(prop.getUrl()+acct+"/corporates", null, headers, null, entity);

	    if(apiResponse.length>0){
	    	JSONObject object = (JSONObject)new JSONParser().parse(new String(apiResponse, "UTF-8"));	    
		    Logger.LogServer(" response : " + new String(apiResponse));
		   
		    if(object.get("code").toString().equals("0") || object.get("code").toString().equals(0)){
		    	StringBuilder query = new StringBuilder("BEGIN\n");
				query.append("INSERT INTO TBLBUSINESS(BUSINESS,ACCOUNTNUMBER,ADDRESS,BUSINESSCODE,CITY,CONTACTNUMBER,PROOFADDRESS,PROVINCE,XORDINATES,YORDINATES,ZIPCODE,LOCATION,MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY,SUNDAY,COUNTRY,ISWITHHOLDINGTAX) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); \n");
				query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
				SystemInfo.getDb().QueryUpdate(query.toString(),this.branchname, object.get("account-number").toString(), this.address,"KA"+code,this.city,this.contactnumber,this.image,this.province,this.xordinate,this.yordinate,this.zipcode,this.address,this.monday,this.tuesday,this.wednesday,this.thursday,this.friday,this.saturday,this.sunday,this.country,this.iswithholdingtax);		
				this.setAccountnumber(object.get("account-number").toString());
		    	this.setState(new ObjectState(object.get("code").toString(),object.get("message").toString()));
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
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLBUSINESS WHERE BUSINESS=? ", this.branchname).size()>0;
	}
	
	public boolean contactexist(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM ADMDBMC.TBLACCOUNTINFO WHERE MSISDN=?", this.contactnumber).size()>0;
	}


	public String getAccountnumber() {
		return accountnumber;
	}
	public void setAccountnumber(String accountnumber) {
		this.props.put(PROP_ACCOUNTNUMBER,accountnumber);
		this.accountnumber = accountnumber;
	}


	
}
