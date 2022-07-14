package com.psi.purchases.m;

import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.entity.StringEntity;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.psi.purchases.util.EmailUtils;
import com.psi.purchases.util.HttpClientHelper;
import com.psi.purchases.util.OtherProperties;
import com.tlc.common.DataRow;
import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.common.ObjectState;

public class RejectWalletTopUp extends Model{
	protected String id;
	protected String linkid;
	protected String password;
	protected String remarks;
	public boolean reject() throws ParseException, IOException{
		
		OtherProperties prop = new OtherProperties();
		DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERID=?", this.linkid);
		
		
		if(!row.getString("USERSLEVEL").equals("MANAGER")){	
			Logger.LogServer("reject wallet allocation");
			DataRow acct = SystemInfo.getDb().QueryDataRow("SELECT U.ACCOUNTNUMBER,U.EMAIL,CT.EXTENDEDDATA FROM TBLCASHINTRANSACTIONS CT INNER JOIN  TBLUSERS U ON U.USERID = CT.MANAGERID WHERE ID = ?", this.id);
			String password = "1234";
			
			
			
		/*	DataRow pass = SystemInfo.getDb().QueryDataRow("SELECT DECRYPT (PASSWORD,?, USERNAME) as PASSWORD FROM TBLUSERS WHERE ACCOUNTNUMBER=?", SystemInfo.getDb().getCrypt(), this.linkid);
			String password = pass.getString("PASSWORD");*/
			
			Logger.LogServer(password.toString());
			
			 byte[] encodedToken = Base64.encodeBase64(password.toString().getBytes());
	         String uPPasswordString = new String(encodedToken);
			HttpClientHelper client = new HttpClientHelper();
		    HashMap<String, String> headers = new HashMap<String, String>();
		    headers.put("Content-Type", prop.getType());
		    headers.put("token", prop.getToken());
		    headers.put("authorization", uPPasswordString);
		    headers.put("X-Forwarded-For","127.0.0.1");
		    
		    
		    Logger.LogServer(headers.toString());
		    
		    byte[] apiResponse = client.httpDelete(prop.getUrl()+"478921234568"+prop.getCashinreject_url()+acct.getString("EXTENDEDDATA"), null, headers, null);
		    JSONObject object = (JSONObject)new JSONParser().parse(new String(apiResponse, "UTF-8"));	 
		    
		    Logger.LogServer(" request : " + prop.getUrl()+"478921234568"+prop.getCashinreject_url()+acct.getString("EXTENDEDDATA"));
		    Logger.LogServer(" response : " + new String(apiResponse));
		   Logger.LogServer("response:"+object.get("response-id").toString());
		    if(apiResponse.length>0){
		    	
			    if(object.get("code").toString().equals("0") || object.get("code").toString().equals(0)){
			    	EmailUtils.sendReject(acct.getString("EMAIL"));
			    	SystemInfo.getDb().QueryUpdate("UPDATE TBLCASHINTRANSACTIONS SET STATUS='REJECT',OPERATORID=?,EXTENDEDDATA2=?,REMARKS=? WHERE ID=?",this.linkid,object.get("response-id").toString(),this.remarks, this.id);
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
			 
		}else{
			DataRow acct = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLCASHINTRANSACTIONS CT INNER JOIN  TBLUSERS U ON U.USERNAME = CT.CREATEDBY WHERE ID = ?", this.id);
			
			if( SystemInfo.getDb().QueryUpdate("UPDATE TBLCASHINTRANSACTIONS SET STATUS='REJECT',MANAGERID=?,REMARKS=? WHERE ID=?",this.linkid, this.remarks,this.id)>0){
				EmailUtils.sendReject(acct.getString("EMAIL"));
				this.setState(new ObjectState("00","Successfull"));
		    	return true;
			}	else{
				this.setState(new ObjectState("01","Unable to reject request"));
		    	return true;
			}
		}	
		
	}
	public boolean validate(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERID =? AND PASSWORD=ENCRYPT(?,?,USERNAME)", this.linkid,this.password,SystemInfo.getDb().getCrypt()).size()>0;
	}
	public boolean isExist(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLCASHINTRANSACTIONS WHERE ID=? ", this.id).size()>0;
	}
	public boolean isrejected(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLCASHINTRANSACTIONS WHERE ID=? AND STATUS='REJECT'", this.id).size()>0;
	}
	public boolean sendMail(){
		DataRow rr = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLCASHINTRANSACTIONS WHERE ID=?", this.id);
		DataRow user = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERNAME = ?", rr.getString("CREATEDBY"));	
		if(user.getString("USERSLEVEL").equals("MANAGER")){
			EmailUtils.send(user.getString("EMAIL"));
			return true;
		}else{
			EmailUtils.sendCashier(user.getString("EMAIL"));
			return true;
		}
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLinkid() {
		return linkid;
	}
	public void setLinkid(String linkid) {
		this.linkid = linkid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
