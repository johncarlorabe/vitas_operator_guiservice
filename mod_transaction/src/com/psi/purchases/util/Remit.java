package com.psi.purchases.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.http.entity.StringEntity;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.tlc.common.DataRow;
import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.common.ObjectState;

public class Remit extends Model{
	
	protected Long amount;
	protected String receivermsisdn;
	protected String orderid;
	protected String trackingid;
	protected String senderaccountnumber;
	protected String linkid;
	protected String type;
	protected String referencenumber;
	protected String macaddress;
	protected String senderfirstname;
	protected String senderlastname;
	protected String sendermsisdn;
	protected String senderemail;
	protected String senderspecificaddress;
	protected String sendercountry;
	protected String sendercity;
	protected String recfirstname;
	protected String reclastname;
	protected String recmsisdn;
	protected String recemail;
	protected String recspecificaddress;
	protected String reccountry;
	protected String reccity;
	
	@SuppressWarnings("unchecked")
	public boolean create() throws ParseException, IOException{
		
		OtherProperties prop = new OtherProperties();
		String reqid = SystemInfo.getDb().QueryScalar("SELECT ADMDBMC.GETREFERENCEID(4100) FROM DUAL", "0");

		JSONObject request = new JSONObject();
		JSONObject request2 = new JSONObject();
		JSONObject request3 = new JSONObject();
		JSONObject request4 = new JSONObject();
		JSONObject reqsenderdetails = new JSONObject();
		JSONObject reqsenderadddetails = new JSONObject();
		JSONObject reqrecdetails = new JSONObject();
		JSONObject reqrecadddetails = new JSONObject();
		
		DataRow cashier = SystemInfo.getDb().QueryDataRow("SELECT PO.BRANCHCODE,U.USERNAME,U.TERMINAL,U.ACCOUNTNUMBER FROM TBLUSERS U INNER JOIN TBLBRANCHES B ON U.ACCOUNTNUMBER=B.ACCOUNTNUMBER INNER JOIN TBLPOSUSERS PO ON PO.USERID=U.USERNAME WHERE U.USERID=?", this.linkid);
		DataRow rec = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE MSISDN = ?", this.receivermsisdn);	
					request2.put("amount", this.amount/100);
					request2.put("msisdn", this.receivermsisdn);
					request2.put("reference",this.orderid);
					request2.put("branch-id", cashier.getString("BRANCHCODE"));
					request2.put("brand", this.type);
					request2.put("cashier-id", cashier.getString("USERNAME"));
					request2.put("terminal-id", this.macaddress);
					request2.put("pin", this.trackingid);
					request.put("payment-data", request2);
					reqsenderadddetails.put("city", this.sendercity);
					reqsenderadddetails.put("country", this.sendercountry);
					reqsenderadddetails.put("postalcode", "");
					reqsenderadddetails.put("specificaddress", this.senderspecificaddress);
					reqsenderdetails.put("address", reqsenderadddetails);
					reqsenderdetails.put("full-name", this.senderlastname+", "+this.senderfirstname);
					reqsenderdetails.put("contact-number", this.sendermsisdn);
					reqsenderdetails.put("email-address", this.senderemail);
					request4.put("sender", reqsenderdetails);
					reqrecadddetails.put("city", this.reccity);
					reqrecadddetails.put("country", this.reccountry);
					reqrecadddetails.put("postalcode", "");
					reqrecadddetails.put("specificaddress", this.recspecificaddress);
					reqrecdetails.put("address", reqrecadddetails);
					reqrecdetails.put("full-name", this.reclastname+", "+this.recfirstname);
					reqrecdetails.put("contact-number", this.recmsisdn);
					reqrecdetails.put("email-address", this.recemail);
					request4.put("beneficiary", reqrecdetails);
					request.put("remittance-details", request4);
					request.put("request-id", reqid);
					request3.put("business", "1166");
					request.put("auth", request3);
					request.put("destination", rec.getString("ACCOUNTNUMBER"));
			
					Logger.LogServer(request.toString());
					Logger.LogServer("url:"+prop.getUrl()+this.senderaccountnumber+"/"+this.type+prop.getRemit_url());
					StringEntity entity = new StringEntity(request.toJSONString());
			
					HttpClientHelper client = new HttpClientHelper();
				    HashMap<String, String> headers = new HashMap<String, String>();
				    headers.put("Content-Type", prop.getType());
				    headers.put("token", prop.getToken());
				    headers.put("X-Forwarded-For","127.0.0.1");
				    byte[] apiResponse = client.httpPost(prop.getUrl()+this.senderaccountnumber+"/"+this.type+prop.getRemit_url(), null, headers, null, entity);
		 
				    if(apiResponse.length>0){
				    	JSONObject object = (JSONObject)new JSONParser().parse(new String(apiResponse, "UTF-8"));
				    	Logger.LogServer(" response : " + new String(apiResponse));
				    	if(object.get("code").toString().equals("0") || object.get("code").toString().equals(0)){
				    		Logger.LogServer(" response : " + new String(apiResponse));
					    	this.setState(new ObjectState("00",object.get("message").toString()));
					    	return true;
				    	}
				    	else if(object.get("code").toString().equals("99") || object.get("code").toString().equals(99)){
					    	this.setState(new ObjectState("01",object.get("message").toString()));
					    	return false;
					    	}
				    	else{
				    	this.setState(new ObjectState(object.get("code").toString(),object.get("message").toString()));
				    	return false;
				    	}
				    }else{
				    	this.setState(new ObjectState("99","System is busymnnhmgmj"));
				    	return false;
				    }
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getReceivermsisdn() {
		return receivermsisdn;
	}

	public void setReceivermsisdn(String receivermsisdn) {
		this.receivermsisdn = receivermsisdn;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getTrackingid() {
		return trackingid;
	}

	public void setTrackingid(String trackingid) {
		this.trackingid = trackingid;
	}

	public String getSenderaccountnumber() {
		return senderaccountnumber;
	}

	public void setSenderaccountnumber(String senderaccountnumber) {
		this.senderaccountnumber = senderaccountnumber;
	}

	public String getLinkid() {
		return linkid;
	}

	public void setLinkid(String linkid) {
		this.linkid = linkid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getReferencenumber() {
		return referencenumber;
	}

	public void setReferencenumber(String referencenumber) {
		this.referencenumber = referencenumber;
	}

	public String getMacaddress() {
		return macaddress;
	}
	public void setMacaddress(String macaddress) {
		this.macaddress = macaddress;
	}

	public String getSenderfirstname() {
		return senderfirstname;
	}

	public void setSenderfirstname(String senderfirstname) {
		this.senderfirstname = senderfirstname;
	}

	public String getSenderlastname() {
		return senderlastname;
	}

	public void setSenderlastname(String senderlastname) {
		this.senderlastname = senderlastname;
	}

	public String getSendermsisdn() {
		return sendermsisdn;
	}

	public void setSendermsisdn(String sendermsisdn) {
		this.sendermsisdn = sendermsisdn;
	}

	public String getSenderemail() {
		return senderemail;
	}

	public void setSenderemail(String senderemail) {
		this.senderemail = senderemail;
	}

	public String getSenderspecificaddress() {
		return senderspecificaddress;
	}

	public void setSenderspecificaddress(String senderspecificaddress) {
		this.senderspecificaddress = senderspecificaddress;
	}

	public String getSendercountry() {
		return sendercountry;
	}

	public void setSendercountry(String sendercountry) {
		this.sendercountry = sendercountry;
	}

	public String getSendercity() {
		return sendercity;
	}

	public void setSendercity(String sendercity) {
		this.sendercity = sendercity;
	}

	public String getRecfirstname() {
		return recfirstname;
	}

	public void setRecfirstname(String recfirstname) {
		this.recfirstname = recfirstname;
	}

	public String getReclastname() {
		return reclastname;
	}

	public void setReclastname(String reclastname) {
		this.reclastname = reclastname;
	}

	public String getRecmsisdn() {
		return recmsisdn;
	}

	public void setRecmsisdn(String recmsisdn) {
		this.recmsisdn = recmsisdn;
	}

	public String getRecemail() {
		return recemail;
	}

	public void setRecemail(String recemail) {
		this.recemail = recemail;
	}

	public String getRecspecificaddress() {
		return recspecificaddress;
	}

	public void setRecspecificaddress(String recspecificaddress) {
		this.recspecificaddress = recspecificaddress;
	}

	public String getReccountry() {
		return reccountry;
	}

	public void setReccountry(String reccountry) {
		this.reccountry = reccountry;
	}

	public String getReccity() {
		return reccity;
	}

	public void setReccity(String reccity) {
		this.reccity = reccity;
	}

	
}
