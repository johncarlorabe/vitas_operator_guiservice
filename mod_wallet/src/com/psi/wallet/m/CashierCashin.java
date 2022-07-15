package com.psi.wallet.m;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.tlc.common.DataRowCollection;
import com.tlc.common.Logger;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.common.ObjectState;

public class CashierCashin extends Model{
	
	protected String accountnumber;
	protected String referencenumber;
	protected String amount;
	protected String password;
	protected String id;
	protected String remarks;
	protected String status;
	protected String depositchannel;
	protected String timeofdeposit;
	protected String dateofdeposit;
	protected String bankcode;
	protected String bankname;
	protected String image;
	protected String description;
	protected String cardnumber;
	
	@SuppressWarnings("unchecked")
	public boolean topup() throws ParseException, IOException{
		OtherProperties prop = new OtherProperties();
		JSONObject request = new JSONObject();
		JSONObject request2 = new JSONObject();
		JSONObject request3 = new JSONObject();
		JSONArray array = new JSONArray();
		
		String reqid = SystemInfo.getDb().QueryScalar("SELECT ADMDBMC.GETREFERENCEID(7900) FROM DUAL", "0");
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
	    
		request.put("request-id", reqid);
		request.put("destination",this.accountnumber);
			request2.put("password", row.getString("PASSWORD"));
		request.put("auth", request2);
				request3.put("reference", this.referencenumber);
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
	    byte[] apiResponse = client.httpPost(prop.getUrl()+row.getString("ACCOUNTNUMBER")+"/transfers", null, headers, null, entity);
	    Logger.LogServer("Cashier Cashin url:"+prop.getUrl()+row.getString("ACCOUNTNUMBER")+"/transfers");
	   	Logger.LogServer("Cashier Cashin response:"+new String(apiResponse, "UTF-8"));
	    if(apiResponse.length>0){
	    	JSONObject object = (JSONObject)new JSONParser().parse(new String(apiResponse, "UTF-8"));	    
		    Logger.LogServer(" response : " + new String(apiResponse));
		   
		    if(object.get("code").toString().equals("0") || object.get("code").toString().equals(0)){
		    		SystemInfo.getDb().QueryUpdate("INSERT INTO TBLCASHINTRANSACTIONS (TYPE,LEVELOFAPPROVAL,REQUESTID,ACCOUNTNUMBER,CREATEDBY,AMOUNT,STATUS,REMARKS,DEPOSITCHANNEL,TIMEOFDEPOSIT, DATEOFDEPOSIT,BANKBRANCHCODE,BANKNAME,REFERENCEIMAGE,DESCRIPTION,REFERENCE) VALUES ('DIRECALLOC','0',?,?,?,?,?,?,?,?,TO_DATE(?,'YYYY/MM/DD'),?,?,?,?,?)",reqid,this.accountnumber,row.getString("USERNAME"),amounttwo,this.status,this.remarks,this.depositchannel, this.timeofdeposit, this.dateofdeposit, this.bankcode, this.bankname, this.image, this.description, this.referencenumber);
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
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERID=? AND PASSWORD=ENCRYPT(?,?,USERNAME)", this.id,this.password,SystemInfo.getDb().getCrypt()).size()>0;
	}
	public boolean isUnique(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLCASHINTRANSACTIONS WHERE REFERENCE =?", this.referencenumber).size()>0;
	}
	public String getAccountnumber() {
		return accountnumber;
	}

	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}

	public String getReferencenumber() {
		return referencenumber;
	}

	public void setReferencenumber(String referencenumber) {
		this.referencenumber = referencenumber;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDepositchannel() {
		return depositchannel;
	}
	public void setDepositchannel(String depositchannel) {
		this.depositchannel = depositchannel;
	}
	public String getTimeofdeposit() {
		return timeofdeposit;
	}
	public void setTimeofdeposit(String timeofdeposit) {
		this.timeofdeposit = timeofdeposit;
	}
	public String getDateofdeposit() {
		return dateofdeposit;
	}
	public void setDateofdeposit(String dateofdeposit) {
		this.dateofdeposit = dateofdeposit;
	}
	public String getBankcode() {
		return bankcode;
	}
	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
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
