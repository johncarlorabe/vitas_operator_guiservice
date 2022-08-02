package com.psi.branch.m;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.http.entity.StringEntity;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.psi.branch.utils.Branch;
import com.psi.branch.utils.HttpClientHelper;
import com.psi.branch.utils.OtherProperties;
import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ObjectState;

public class NewBranch extends Branch{
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
		
		String reqid = SystemInfo.getDb().QueryScalar("SELECT ADMDBMC.GETREFERENCEID(4001) FROM DUAL", "0");	    
	    String code = SystemInfo.getDb().QueryScalar("SELECT TBLBRANCHESCODE_SEQ.NEXTVAL FROM DUAL", "0");
	    String date = SystemInfo.getDb().QueryScalar("SELECT SYSDATE FROM DUAL", "0");
	   		
		request.put("type", "merchant");
		request.put("request-id", reqid);
		request.put("user-id", "POLEN");
		request.put("valid-id-desc","COMPANY");
		request.put("for-kyc", "true");
		request.put("kyc", "true");
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
					request4.put("tin", this.tin);
						request5.put("region-code", this.province);
						request5.put("city-code", this.city);
						request5.put("postal-code", this.zipcode);
						request5.put("coordinates", this.xordinate +","+this.yordinate);
						request5.put("specific-address", this.address);
					request4.put("business-address", request5);
				request3.put("corporate-info", request4);
		request.put("subscriber", request3);
		
		Logger.LogServer(request.toString());
		Logger.LogServer("Merch Reg URL:"+prop.getUrl()+acct+prop.getBranch_url());
		
		StringEntity entity = new StringEntity(request.toJSONString());
		
		HttpClientHelper client = new HttpClientHelper();
	    HashMap<String, String> headers = new HashMap<String, String>();
	    headers.put("Content-Type", prop.getType());
	    headers.put("token",prop.getToken());
	    byte[] apiResponse = client.httpPost(prop.getUrl()+acct+prop.getBranch_url(), null, headers, null, entity);
	    
	    if(apiResponse.length>0){
	    	JSONObject object = (JSONObject)new JSONParser().parse(new String(apiResponse, "UTF-8"));	    
		    Logger.LogServer(" response : " + new String(apiResponse));
		   
		    if(object.get("code").toString().equals("100") || object.get("code").toString().equals(100)){
		    	StringBuilder query = new StringBuilder("BEGIN\n");
				query.append("INSERT INTO TBLBRANCHES(BRANCH,ACCOUNTNUMBER,ADDRESS,BRANCHCODE,CITY,CONTACTNUMBER,PROOFADDRESS,PROVINCE,XORDINATES,YORDINATES,ZIPCODE,LOCATION,MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY,SUNDAY,COUNTRY,KEYACCOUNT,RAFILELOCATION,RAFILENAME,STATUS,TIN,NATUREOFBUSINESS,GROSSSALES,REGISTEREDBY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,3,?,?,?,?); \n");
				query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
				SystemInfo.getDb().QueryUpdate(query.toString(),this.branchname, "", this.address,"BAYAD"+code,this.city,this.contactnumber,this.image,this.province,this.xordinate,this.yordinate,this.zipcode,this.address,this.monday,this.tuesday,this.wednesday,this.thursday,this.friday,this.saturday,this.sunday,this.country,acct,this.rafilelocation,this.rafilename,this.tin,this.natureofbusiness,this.grosssales,this.registeredby);		
				this.setAccountnumber("");
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
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLBRANCHES WHERE BRANCH=? AND STATUS = 1", this.branchname).size()>0;
	}
	
	public boolean existcontactnumber(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLBRANCHES WHERE CONTACTNUMBER=?", this.contactnumber).size()>0;
	}

	public String getAccountnumber() {
		return accountnumber;
	}
	public void setAccountnumber(String accountnumber) {
		this.props.put(PROP_ACCOUNTNUMBER,accountnumber);
		this.accountnumber = accountnumber;
	}


	
}
