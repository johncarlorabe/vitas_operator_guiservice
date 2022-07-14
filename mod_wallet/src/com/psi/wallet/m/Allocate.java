package com.psi.wallet.m;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.entity.StringEntity;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.psi.wallet.util.HttpClientHelper;
import com.psi.wallet.util.OtherProperties;
import com.tlc.common.DataRow;
import com.tlc.common.Logger;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.common.ObjectState;

public class Allocate extends Model{
	
	protected String msisdn;
	protected long amount;
	protected String password;
	protected String accountnumber;
	protected String linkid;
	
	@SuppressWarnings("unchecked")
	public boolean topup() throws ParseException, IOException{
		OtherProperties prop = new OtherProperties();
		JSONObject request = new JSONObject();
		JSONObject request2 = new JSONObject();
		JSONObject request3 = new JSONObject();
		JSONArray array = new JSONArray();
		
		String reqid = SystemInfo.getDb().QueryScalar("SELECT ADMDBMC.GETREFERENCEID(2005) FROM DUAL", "0");
	    
	    DataRow row = SystemInfo.getDb().QueryDataRow("SELECT DECRYPT(AI.PASSWORD,?,AI.ACCOUNTNUMBER) PASSWORD,U.USERNAME,AI.ROOT,U.ACCOUNTNUMBER FROM ADMDBMC.TBLACCOUNTINFO AI INNER JOIN TBLUSERS U ON U.ACCOUNTNUMBER = AI.ACCOUNTNUMBER WHERE U.USERID = ?",SystemInfo.getDb().getCrypt(), this.linkid);
	    DataRow desrow = SystemInfo.getDb().QueryDataRow("SELECT * FROM ADMDBMC.TBLACCOUNTINFO WHERE MSISDN =?", this.msisdn);
    	if(desrow.isEmpty()){
    		this.setState(new ObjectState("77","Mobile number doest not exist"));
			return false;
    	}
		request.put("request-id", reqid);
		request.put("destination",desrow.getString("ACCOUNTNUMBER"));
			request2.put("password", row.getString("PASSWORD"));
		request.put("auth", request2);
				request3.put("reference", "A"+reqid);
				request3.put("pocket-id", row.getString("ROOT"));
				request3.put("amount", LongUtil.toString(this.amount));
		request.put("payments", array);
		array.add(request3);
		
		Logger.LogServer(request.toString());
		
		StringEntity entity = new StringEntity(request.toJSONString());
		
		HttpClientHelper client = new HttpClientHelper();
	    HashMap<String, String> headers = new HashMap<String, String>();
	    headers.put("Content-Type", prop.getType());
	    headers.put("token",prop.getToken());
	    headers.put("X-Forwarded-For","127.0.0.1");
	    byte[] apiResponse = client.httpPost(prop.getUrl()+row.getString("ACCOUNTNUMBER")+"/transfers", null, headers, null, entity);
	    Logger.LogServer("VIP Wallet url:"+prop.getUrl()+row.getString("ACCOUNTNUMBER")+"/transfers");
	   	Logger.LogServer("VIP Wallet response:"+new String(apiResponse, "UTF-8"));
	    if(apiResponse.length>0){
	    	JSONObject object = (JSONObject)new JSONParser().parse(new String(apiResponse, "UTF-8"));	    
		    Logger.LogServer(" response : " + new String(apiResponse));
		   
		    if(object.get("code").toString().equals("0") || object.get("code").toString().equals(0)){
		    		SystemInfo.getDb().QueryUpdate("INSERT INTO TBLCASHINTRANSACTIONS (TYPE,LEVELOFAPPROVAL,REQUESTID,ACCOUNTNUMBER,CREATEDBY,BANKBRANCHCODE,AMOUNT,STATUS,REMARKS,MANAGERID) VALUES ('P2P','0',?,?,?,?,?,?,?,?)",reqid,desrow.getString("ACCOUNTNUMBER"),row.getString("USERNAME"),"A"+reqid,this.amount,"APPROVED","",SystemInfo.getDb().QueryScalar("SELECT USERID FROM TBLUSERS WHERE ACCOUNTNUMBER =? AND USERSLEVEL = 'MANAGER'", "", desrow.getString("ACCOUNTNUMBER")));
		    		this.setState(new ObjectState(object.get("code").toString(),object.get("message").toString()));
					return true;
		    
			  } else if(object.get("code").toString().equals("99") || object.get("code").toString().equals(99)){
				  {
					  this.setState(new ObjectState("05","Sorry, we are unable to process your request. Please check if you have sufficient balance"));
				    	return false;
					  }
				  }
		    else{
				   this.setState(new ObjectState(object.get("code").toString(),object.get("message").toString()));
			    	return false;
			  }
	    }else{
	    	this.setState(new ObjectState("99","System is busy"));
	    	return false;
	    }
	}
	public boolean validate(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERID=? AND PASSWORD=ENCRYPT(?,?,USERNAME)", this.linkid,this.password,SystemInfo.getDb().getCrypt()).size()>0;
	}
	
	public boolean issubscriber(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM ADMDBMC.TBLACCOUNTINFO WHERE MSISDN =? AND TYPE=ENCRYPT('subscriber',?,ACCOUNTNUMBER)", this.msisdn,SystemInfo.getDb().getCrypt()).size()>0;
	}
	
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAccountnumber() {
		return accountnumber;
	}
	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}
	public String getLinkid() {
		return linkid;
	}
	public void setLinkid(String linkid) {
		this.linkid = linkid;
	}
	

}
