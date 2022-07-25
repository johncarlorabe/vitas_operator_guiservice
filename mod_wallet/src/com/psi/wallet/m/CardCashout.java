package com.psi.wallet.m;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;

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

public class CardCashout extends Model{
	
	protected String accountnumber;
	protected String amount;
	protected String password;
	protected String id;
	protected String remarks;
	protected String description;
	protected String cardnumber;
	
	@SuppressWarnings("unchecked")
	public boolean topup() throws ParseException, IOException{
		OtherProperties prop = new OtherProperties();
		JSONObject request = new JSONObject();
		JSONObject request2 = new JSONObject();
		JSONObject request3 = new JSONObject();
		JSONArray array = new JSONArray();
		
		String reqid = SystemInfo.getDb().QueryScalar("SELECT ADMDBMC.GETREFERENCEID(7800) FROM DUAL", "0");
	    NumberFormat format = NumberFormat.getInstance(Locale.US);

        Number amountone = null;
		try {
			amountone = format.parse(this.amount);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		Long amounttwo = null;
			try {
				amounttwo = LongUtil.toLong(this.amount);
			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}
		
	    
	    DataRow row = SystemInfo.getDb().QueryDataRow("SELECT DECRYPT(AI.PASSWORD,?,AI.ACCOUNTNUMBER) PASSWORD,U.USERNAME,AI.ROOT,U.ACCOUNTNUMBER FROM ADMDBMC.TBLACCOUNTINFO AI INNER JOIN TBLUSERS U ON U.ACCOUNTNUMBER = AI.ACCOUNTNUMBER WHERE U.USERID = ?",SystemInfo.getDb().getCrypt(), this.id);
	    DataRow cashierdetails = SystemInfo.getDb().QueryDataRow("SELECT DECRYPT(PASSWORD,?,ACCOUNTNUMBER) PASSWORD,ROOT,ACCOUNTNUMBER FROM ADMDBMC.TBLACCOUNTINFO WHERE MSISDN= ?",SystemInfo.getDb().getCrypt(),this.cardnumber);
   	    
	    if(cashierdetails.isEmpty()) {
	    	this.setState(new ObjectState("99","Account does not exist"));
	    	return false;
	    }
		request.put("request-id", reqid);
		//Destination Cashier account, mababawasan as per sir sam 
		request.put("destination",cashierdetails.getString("ACCOUNTNUMBER"));
			request2.put("password", row.getString("PASSWORD"));
		request.put("auth", request2);
				request3.put("reference", this.remarks);
				request3.put("pocket-id", row.getString("ROOT"));
				request3.put("amount", amountone);
		request.put("payments", array);
		array.add(request3);
		
		Logger.LogServer(request.toString());
		
		StringEntity entity = new StringEntity(request.toJSONString());
		
		HttpClientHelper client = new HttpClientHelper();
	    HashMap<String, String> headers = new HashMap<String, String>();
	    headers.put("Content-Type", prop.getType());
	    headers.put("token",prop.getToken());
	    headers.put("X-Forwarded-For","127.0.0.1");
	    byte[] apiResponse = client.httpPost(prop.getUrl()+row.getString("ACCOUNTNUMBER")+"/cashout", null, headers, null, entity);
	    Logger.LogServer("Card Cashout url:"+prop.getUrl()+row.getString("ACCOUNTNUMBER")+"/cashout");
	   	Logger.LogServer("Card Cashout response:"+new String(apiResponse, "UTF-8"));
	    if(apiResponse.length>0){
	    	JSONObject object = (JSONObject)new JSONParser().parse(new String(apiResponse, "UTF-8"));	    
		    Logger.LogServer(" response : " + new String(apiResponse));
		   
		    if(object.get("code").toString().equals("0") || object.get("code").toString().equals(0)){
		    		SystemInfo.getDb().QueryUpdate("INSERT INTO TBLCASHINTRANSACTIONS (TYPE,LEVELOFAPPROVAL,REQUESTID,ACCOUNTNUMBER,CREATEDBY,AMOUNT,STATUS,REMARKS,DESCRIPTION,EXTENDEDDATA) VALUES ('DIRECDEALLOC','0',?,?,?,?,'APPROVED',?,?,?)",reqid,this.accountnumber,row.getString("USERNAME"),amounttwo,this.remarks, this.description,object.get("response-id").toString());
		    		this.setState(new ObjectState(object.get("code").toString(),object.get("message").toString()));
		    		DataRow manager = SystemInfo.getDb().QueryDataRow("SELECT BRANCH,U.EMAIL FROM TBLBRANCHES B INNER JOIN TBLUSERS U ON B.ACCOUNTNUMBER = U.ACCOUNTNUMBER WHERE USERSLEVEL = 'MANAGER' AND U.ACCOUNTNUMBER = ?", this.accountnumber);
//		    		EmailUtils.sendDealloc(manager.getString("EMAIL"), object.get("response-id").toString(), manager.getString("BRANCH"), "", this.amount);
//		    		EmailUtils.sendDeallocOperator(object.get("response-id").toString(), manager.getString("BRANCH"), "", this.amount);
		    		
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
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERID=? AND PASSWORD=ENCRYPT(?,?,USERNAME)", this.id,this.password,SystemInfo.getDb().getCrypt()).size()>0;
	}
	
	
	public boolean isZeroBalance(){
		this.setState(new ObjectState("99","Failed to check card balance"));
		DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM ADMDBMC.TBLACCOUNTINFO WHERE MSISDN = ?", this.cardnumber);
		
		if(row.isEmpty()) {
			this.setState(new ObjectState("05","Card does not exist"));
			return false;
		}
		
		 DataRow card = SystemInfo.getDb().QueryDataRow("SELECT NVL(DECRYPT(AMOUNT,?,ACCOUNTNUMBER),0) BALANCE FROM ADMDBMC.TBLCURRENTSTOCK WHERE ACCOUNTNUMBER=? AND WALLETID=1",SystemInfo.getDb().getCrypt(),row.getString("ACCOUNTNUMBER"));
	   	    Logger.LogServer(card.getString("BALANCE"));  Logger.LogServer(card.getString("BALANCE").equals("0")?"TRUE":"FALSE");
		    if(card.isEmpty()) {
		    	this.setState(new ObjectState("99","Card account does not exist"));
		    	return false;
		    }
		    return card.getString("BALANCE").equals("0");
	}
	
	public String getAccountnumber() {
		return accountnumber;
	}

	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCardnumber() {
		return cardnumber;
	}
	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}
	
}
