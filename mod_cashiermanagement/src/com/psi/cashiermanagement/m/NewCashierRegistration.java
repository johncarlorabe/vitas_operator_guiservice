package com.psi.cashiermanagement.m;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.entity.StringEntity;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.psi.cashiermanagement.util.Cashier;
import com.psi.cashiermanagement.util.HttpClientHelper;
import com.psi.cashiermanagement.util.OtherProperties;
import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.modules.common.ObjectState;

public class NewCashierRegistration extends Cashier{
	

	protected String otp;
	DataRow userslevel;
	
	public boolean register() throws IOException, ParseException{
		String apiurl="";
		this.setState(new ObjectState("99","System Busy"));
		OtherProperties prop = new OtherProperties();
		JSONObject request = new JSONObject();
		JSONObject request2 = new JSONObject();
		JSONObject request3 = new JSONObject();
		JSONObject request4 = new JSONObject();
		JSONObject request5 = new JSONObject();
		
		String reqid = "PORTAL"+SystemInfo.getDb().QueryScalar("SELECT ADMDBMC.GETREFERENCEID(7500) FROM DUAL", "0");	    
	    String date = SystemInfo.getDb().QueryScalar("SELECT SYSDATE FROM DUAL", "0");
	    
	    DataRow branch = SystemInfo.getDb().QueryDataRow("SELECT ID,BRANCH,BRANCHCODE,PROVINCE,CITY,ZIPCODE,XORDINATES,YORDINATES,ADDRESS,KEYACCOUNT FROM TBLBRANCHES WHERE ACCOUNTNUMBER=?", this.accountnumber);
	    
	    if (branch.isEmpty()){	
	    	this.setState(new ObjectState("PSI-05","Branch not found"));
	    	return false;
	    }
	    
		request.put("type", "cashier");
		request.put("request-id", reqid);
		request.put("user-id", branch.getString("BRANCH"));
		request.put("valid-id-desc","COMPANY");
			request2.put("password", "1234");
		request.put("auth", request2);
				//Static 1234 password for mobile application 07252022
				request3.put("password", 1234);
				request3.put("account-name", this.firstname +" "+ this.lastname);
				request3.put("business-name", branch.getString("BRANCH"));
				request3.put("first-name", this.firstname);
				request3.put("middle-name", "");
				request3.put("last-name", this.lastname);
				request3.put("authorized-mobile", "0000"+this.msisdn);
				request3.put("email-address", this.email);
				request3.put("valid-id",  branch.getString("BRANCHCODE"));
					request4.put("registration-date", date);
					request4.put("tin", "");
						request5.put("region-code", branch.getString("PROVINCE"));
						request5.put("city-code", branch.getString("CITY"));
						request5.put("postal-code", branch.getString("ZIPCODE"));
						request5.put("coordinates", branch.getString("XORDINATES") +","+branch.getString("YORDINATES"));
						request5.put("specific-address", branch.getString("ADDRESS"));
					request4.put("business-address", request5);
				request3.put("corporate-info", request4);
		request.put("subscriber", request3);
		Logger.LogServer(request.toString());
		
		StringEntity entity = new StringEntity(request.toJSONString());
		
		HttpClientHelper client = new HttpClientHelper();
	    HashMap<String, String> headers = new HashMap<String, String>();
	    headers.put("Content-Type", prop.getType());
	    headers.put("token",prop.getToken());
	    //*************************	    

	    
		//*************************	   
	    apiurl =prop.getUrl()+this.accountnumber+prop.getCashier_url();
	    byte[] apiResponse = client.httpPost(apiurl, null, headers, null, entity);
	    Logger.LogServer("Create Cashier Url :" + apiurl);
	    Logger.LogServer("Create Cashier Response : " + new String(apiResponse));
	    if(apiResponse.length>0){
	    	JSONObject object = (JSONObject)new JSONParser().parse(new String(apiResponse, "UTF-8"));	    
		    
		    if(object.get("code").toString().equals("0") || object.get("code").toString().equals(0)){
		    	
		    	this.setState(new ObjectState(object.get("code").toString(),object.get("message").toString()));
		    	
				String cashierAccountNumber = object.get("account-number").toString();
				
				StringBuilder query = new StringBuilder("BEGIN\n");
				query.append("INSERT INTO TBLUSERS(EMAIL,FIRSTNAME,LASTNAME,MSISDN,USERSLEVEL,STATUS,DATEREGISTERED,USERNAME,STORE,PASSWORD,AUTHCODE,ISFIRSTLOGON,TERMINAL,ACCOUNTNUMBER,GUIINTERFACE,STOREACCOUNTNUMBER,CREATEDBY) VALUES(?,?,?,?,?,'ACTIVE',SYSDATE,?,?,ADMDBMC.ENCRYPT(?,?,?),'PASS',1,'4339D22FA2180E39',?,'vitascashier',?,?); \n");
				query.append("INSERT INTO TBLPOSUSERS(ACCOUNTNUMBER, TERMINALID, USERID, PASSWORD, BRANCHCODE, TYPE, MSISDN, DEFAULTPWD, FIRSTNAME, LASTNAME)Values(?, '4339D22FA2180E39', ?, '1166', ?, 'cashier', ?, 0, ?,?); \n");
				query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
				return SystemInfo.getDb().QueryUpdate(query.toString(), 
						 									  this.email, this.firstname, this.lastname,this.msisdn,this.getUserslevel(),this.username,this.manager,this.password,SystemInfo.getDb().getCrypt(),this.username,cashierAccountNumber,this.accountnumber,this.getAuthorizedSession().getAccount().getUserName(),
						 									 cashierAccountNumber,this.username,branch.getString("BRANCHCODE"),this.msisdn,this.firstname,this.lastname)>0;		
						 									 		    	
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
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERNAME=?", this.username).size()>0;
	}
	
	
	public boolean isEmailExist(){		
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE UPPER(EMAIL) = UPPER(?)", this.email).size()>0;
	}
	
	public boolean isMsisdnExist(){		
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE MSISDN = ?", this.msisdn).size()>0;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}
	
	
	
}
