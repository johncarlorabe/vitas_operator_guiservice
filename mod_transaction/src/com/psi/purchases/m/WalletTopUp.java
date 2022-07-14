package com.psi.purchases.m;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.http.entity.StringEntity;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.psi.purchases.util.EmailUtils;
import com.psi.purchases.util.HttpClientHelper;
import com.psi.purchases.util.OtherProperties;
import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.Logger;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.common.ObjectState;

public class WalletTopUp extends Model{
	protected Long amount;
	protected String id;
	protected String reference;
	protected String image;
	protected String password;
	protected String dateofdepo;
	protected String timeofdepo;
	protected String branchcode;
	protected String bankname;
	protected String depochannel;
	protected String remarks;
	@SuppressWarnings("unchecked")
	public boolean topup() throws ParseException, IOException{
		OtherProperties prop = new OtherProperties();
		String store=null;
		String accountnumber =null;
		String username=null;
		JSONObject request = new JSONObject();
		JSONObject request2 = new JSONObject();
		String reqid = SystemInfo.getDb().QueryScalar("SELECT ADMDBMC.GETREFERENCEID(4000) FROM DUAL", "0");
	    String managerid=null;
	    String levelofapproval=null;
	    String email=null;
		DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERID=?", this.id);
		if(row.getString("USERSLEVEL").equals("CASHIER")){
			DataRow manager = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE ACCOUNTNUMBER = (SELECT ACCOUNTNUMBER FROM TBLUSERS WHERE USERID=?) AND USERSLEVEL = 'MANAGER'", this.id);
			store =manager.getString("STORE");
			username = row.getString("USERNAME");
			managerid = manager.getString("USERID");
			email = manager.getString("EMAIL");
			levelofapproval = "2";
			
			accountnumber = row.getString("ACCOUNTNUMBER");
			SystemInfo.getDb().QueryUpdate("INSERT INTO TBLCASHINTRANSACTIONS (TYPE,REQUESTID,ACCOUNTNUMBER,CREATEDBY,REFERENCE,AMOUNT,MANAGERID,STATUS,LEVELOFAPPROVAL,REFERENCEIMAGE,DATEOFDEPOSIT,TIMEOFDEPOSIT,BANKBRANCHCODE,BANKNAME) VALUES ('ALLOC',?,?,?,?,?,?,'PNDG',?,?,TO_DATE(?,'MM/DD/YYYY'),?,?,?)",reqid,accountnumber,username,this.reference,this.amount,managerid,levelofapproval,this.image,this.dateofdepo,this.timeofdepo,this.branchcode,this.bankname);
			EmailUtils.send(email, row.getString("LASTNAME"), row.getString("FIRSTNAME"), this.amount.toString());
			return true;
		}else{
			
			store = row.getString("STORE");
			username = row.getString("USERNAME");
			managerid = row.getString("USERID");
			levelofapproval = "1";
			request.put("destination", "478921234568");
			request.put("request-id", reqid);
			request.put("amount", LongUtil.toString(this.amount));
			request.put("reference", this.reference);
			request2.put("password", "1234");
			request.put("auth",request2);
			DataRow acct = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERID=?", this.id);
			accountnumber = acct.getString("ACCOUNTNUMBER");
			Logger.LogServer(request.toString());
		
				StringEntity entity = new StringEntity(request.toJSONString());
				
				HttpClientHelper client = new HttpClientHelper();
			    HashMap<String, String> headers = new HashMap<String, String>();
			    headers.put("Content-Type", prop.getType());
			    headers.put("token", prop.getToken());
			    headers.put("X-Forwarded-For","127.0.0.1");
			    byte[] apiResponse = client.httpPost(prop.getUrl()+accountnumber+prop.getCashin_url(), null, headers, null, entity);
	 
			    if(apiResponse.length>0){
			    	JSONObject object = (JSONObject)new JSONParser().parse(new String(apiResponse, "UTF-8"));	    
				    Logger.LogServer(" response : " + new String(apiResponse));
				   
				    if(object.get("code").toString().equals("0") || object.get("code").toString().equals(0)){
				    	
				    	SystemInfo.getDb().QueryUpdate("INSERT INTO TBLCASHINTRANSACTIONS (TYPE,REQUESTID,ACCOUNTNUMBER,CREATEDBY,REFERENCE,AMOUNT,MANAGERID,STATUS,LEVELOFAPPROVAL,REFERENCEIMAGE,EXTENDEDDATA,DATEOFDEPOSIT,TIMEOFDEPOSIT,BANKBRANCHCODE,BANKNAME,REMARKS,DEPOSITCHANNEL) VALUES ('ALLOC',?,?,?,?,?,?,'PNDG',?,?,?,TO_DATE(?,'MM/DD/YYYY'),?,?,?,?,?)",reqid,accountnumber,username,this.reference,this.amount,managerid,levelofapproval,this.image,object.get("response-id").toString(),this.dateofdepo,this.timeofdepo,this.branchcode,this.bankname,this.remarks,this.depochannel);
				    	DataRowCollection admins = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLUSERS WHERE USERSLEVEL='ADMIN OPERATOR'");
						DataRow branch = SystemInfo.getDb().QueryDataRow("SELECT U.FIRSTNAME,U.LASTNAME,CT.AMOUNT,B.BRANCH FROM TBLCASHINTRANSACTIONS CT INNER JOIN TBLUSERS U ON CT.MANAGERID = U.USERID INNER JOIN TBLACCOUNTINFO AI ON AI.ID=U.STORE INNER JOIN TBLBRANCHES B ON AI.ACCOUNTNUMBER = B.ACCOUNTNUMBER WHERE AI.ID =?", store);
						if(!admins.isEmpty()){
							for(DataRow admin:admins){
								EmailUtils.send(admin.getString("EMAIL"), row.getString("LASTNAME"), row.getString("FIRSTNAME"), this.amount.toString(),branch.getString("BRANCH"));	
							}
						}
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
		
	}
	
	public boolean validate(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERID =? AND PASSWORD=ENCRYPT(?,?,USERNAME)", this.id,this.password,SystemInfo.getDb().getCrypt()).size()>0;
	}
	
	public boolean isUnique(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLCASHINTRANSACTIONS WHERE REFERENCE =?", this.reference).size()>0;
	}
	



	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getDateofdepo() {
		return dateofdepo;
	}
	public void setDateofdepo(String dateofdepo) {
		this.dateofdepo = dateofdepo;
	}
	public String getTimeofdepo() {
		return timeofdepo;
	}
	public void setTimeofdepo(String timeofdepo) {
		this.timeofdepo = timeofdepo;
	}
	public String getBranchcode() {
		return branchcode;
	}
	public void setBranchcode(String branchcode) {
		this.branchcode = branchcode;
	}

	public String getBankname() {
		return bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	public String getDepochannel() {
		return depochannel;
	}

	public void setDepochannel(String depochannel) {
		this.depochannel = depochannel;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	

	
}
