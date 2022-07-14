package com.psi.purchases.m;

import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.ClientProtocolException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.psi.purchases.util.EmailUtils;
import com.psi.purchases.util.HttpClientHelper;
import com.psi.purchases.util.OtherProperties;
import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.common.ObjectState;

public class CancelWalletTopUp extends Model{
	protected String id;
	protected String linkid;
	protected String password;
	
	public boolean cancel() throws ClientProtocolException, IOException, ParseException{
		OtherProperties prop = new OtherProperties();
		DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERID=?", this.linkid);
		if(row.getString("USERSLEVEL").equals("CASHIER")){
			DataRow user = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLCASHINTRANSACTIONS CT INNER JOIN TBLUSERS U ON CT.MANAGERID=U.USERID WHERE CT.ID=?", this.id);
			EmailUtils.sendCancel(user.getString("EMAIL"), user.getString("REFERENCE"));
		return SystemInfo.getDb().QueryUpdate("UPDATE TBLCASHINTRANSACTIONS SET STATUS='CANCEL' WHERE ID=?", this.id)>0;
		}else{
			DataRow acct = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLCASHINTRANSACTIONS CT INNER JOIN  TBLUSERS U ON U.USERID = CT.MANAGERID WHERE ID = ?", this.id);
			String password = "1234";
			 byte[] encodedToken = Base64.encodeBase64(password.toString().getBytes());
	         String uPPasswordString = new String(encodedToken);
			HttpClientHelper client = new HttpClientHelper();
		    HashMap<String, String> headers = new HashMap<String, String>();
		    headers.put("Content-Type", prop.getType());
		    headers.put("token", prop.getToken());
		    headers.put("authorization", uPPasswordString);
		    headers.put("X-Forwarded-For","127.0.0.1");
		    byte[] apiResponse = client.httpDelete(prop.getUrl()+acct.getString("ACCOUNTNUMBER")+prop.getCashinreject_url()+acct.getString("EXTENDEDDATA"), null, headers, null);
		    JSONObject object = (JSONObject)new JSONParser().parse(new String(apiResponse, "UTF-8"));	 
	    	
		    Logger.LogServer(" response : " + new String(apiResponse));
		   Logger.LogServer("response:"+object.get("response-id").toString());
		    if(apiResponse.length>0){
		    	
			    if(object.get("code").toString().equals("0") || object.get("code").toString().equals(0)){
			    	DataRow branch = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLCASHINTRANSACTIONS WHERE ID=?", this.id);
			    	DataRowCollection admins = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLUSERS WHERE USERSLEVEL='OPERATOR'");
					
			    	if(!admins.isEmpty()){
						for(DataRow admin:admins){
							EmailUtils.sendCancel(admin.getString("EMAIL"),branch.getString("REFERENCE"));	
						}
					}
			    	SystemInfo.getDb().QueryUpdate("UPDATE TBLCASHINTRANSACTIONS SET STATUS='CANCEL',MANAGERID=?,EXTENDEDDATA2=? WHERE ID=?",this.linkid,object.get("response-id").toString(), this.id);
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
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERID =? AND PASSWORD=ENCRYPT(?,?,USERNAME)", this.linkid,this.password,SystemInfo.getDb().getCrypt()).size()>0;
	}
	public boolean isExist(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLCASHINTRANSACTIONS WHERE ID = ?", this.id).size()>0;
	}
	
	public boolean iscancelled(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLCASHINTRANSACTIONS WHERE ID=? AND STATUS='CANCEL'", this.id).size()>0;
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
	
}
