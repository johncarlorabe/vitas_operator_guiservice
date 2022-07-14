package com.psi.purchases.m;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.tlc.common.DataRowCollection;
import com.tlc.common.Logger;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.common.ObjectState;

public class ApprovedWalletTopup extends Model{
	protected String id;
	protected String linkid;
	protected String password;
	protected String depochannel;
	protected String remarks;
	protected String image;
	@SuppressWarnings("unchecked")
	public boolean approved() throws ParseException, IOException{
		OtherProperties prop = new OtherProperties();		
		JSONObject request = new JSONObject();
		JSONObject request2 = new JSONObject();
		String reqid = SystemInfo.getDb().QueryScalar("SELECT ADMDBMC.GETREFERENCEID(2003) FROM DUAL", "0");
		DataRow r = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERID = ?", this.linkid);
		if(r.getString("USERSLEVEL").equals("MANAGER")){
			DataRow row = SystemInfo.getDb().QueryDataRow("SELECT TBLCASHINTRANSACTIONS.*,AMOUNT/100 AMOUNT1 FROM TBLCASHINTRANSACTIONS WHERE ID=?", this.id);

			request.put("destination", "478921234568");
			request.put("request-id", reqid);
			request.put("amount",row.getString("AMOUNT1"));
			request.put("reference", row.getString("REFERENCE"));
			request2.put("password", "1234");
			request.put("auth",request2);
			
			Logger.LogServer(request.toString());
		
				StringEntity entity = new StringEntity(request.toJSONString());
				
				HttpClientHelper client = new HttpClientHelper();
			    HashMap<String, String> headers = new HashMap<String, String>();
			    headers.put("Content-Type", prop.getType());
			    headers.put("token", prop.getToken());
			    headers.put("X-Forwarded-For","127.0.0.1");
			    byte[] apiResponse = client.httpPost(prop.getUrl()+row.getString("ACCOUNTNUMBER")+prop.getCashin_url(), null, headers, null, entity);
			    Logger.LogServer(" before response : " + new String(apiResponse));
			    Logger.LogServer(" url : " + prop.getUrl()+row.getString("ACCOUNTNUMBER")+prop.getCashin_url());
			    if(apiResponse.length>0){
			    	JSONObject object = (JSONObject)new JSONParser().parse(new String(apiResponse, "UTF-8"));	    
				    Logger.LogServer(" response : " + new String(apiResponse));
				   
				    if(object.get("code").toString().equals("0") || object.get("code").toString().equals(0)){
				    	DataRowCollection admins = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLUSERS WHERE USERSLEVEL='ADMIN OPERATOR'");
						DataRow branch = SystemInfo.getDb().QueryDataRow("SELECT U.FIRSTNAME,U.LASTNAME,CT.AMOUNT,B.BRANCH FROM TBLCASHINTRANSACTIONS CT INNER JOIN TBLUSERS U ON CT.MANAGERID = U.USERID INNER JOIN TBLBRANCHES B ON U.ACCOUNTNUMBER = B.ACCOUNTNUMBER WHERE CT.ID =?", this.id);
						if(!admins.isEmpty()){
							for(DataRow admin:admins){
								EmailUtils.send(admin.getString("EMAIL"), branch.getString("LASTNAME"), branch.getString("FIRSTNAME"), LongUtil.toString(Long.parseLong(branch.getString("AMOUNT"))),branch.getString("BRANCH"));	
							}
						}
				    	SystemInfo.getDb().QueryUpdate("UPDATE TBLCASHINTRANSACTIONS SET LEVELOFAPPROVAL=1,STATUS='APPROVED',EXTENDEDDATA=?,REMARKS=?,DEPOSITCHANNEL=?,REFERENCEIMAGE=? WHERE ID =?", object.get("response-id").toString(),this.remarks,this.depochannel,this.image,this.id);
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
			DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLCASHINTRANSACTIONS WHERE ID=?", this.id);

			String password = "1234";
			 byte[] encodedToken = Base64.encodeBase64(password.toString().getBytes());
	         String uPPasswordString = new String(encodedToken);
			HttpClientHelper client = new HttpClientHelper();
		    HashMap<String, String> headers = new HashMap<String, String>();
		    headers.put("Content-Type", prop.getType());
		    headers.put("token", prop.getToken());
		    headers.put("authorization", uPPasswordString);
		    byte[] apiResponse = client.httpPut(prop.getUrl()+SystemInfo.getDb().QueryScalar("SELECT ACCOUNTNUMBER FROM ADMDBMC.TBLACCOUNTINFO WHERE ID = ?", "", new Object[] { SystemInfo.getDb().QueryScalar("SELECT ROOT FROM ADMDBMC.TBLACCOUNTINFO WHERE ACCOUNTNUMBER = ?", "", new Object[] { row.getString("ACCOUNTNUMBER") }) })+prop.getCashinapproved_url()+row.getString("EXTENDEDDATA"), null, headers, null, null);
		    Logger.LogServer("Approve Wallet Topup - operator [PUT]:"+prop.getUrl()+SystemInfo.getDb().QueryScalar("SELECT ACCOUNTNUMBER FROM ADMDBMC.TBLACCOUNTINFO WHERE ID = ?", "", new Object[] { SystemInfo.getDb().QueryScalar("SELECT ROOT FROM ADMDBMC.TBLACCOUNTINFO WHERE ACCOUNTNUMBER = ?", "", new Object[] { row.getString("ACCOUNTNUMBER") }) })+prop.getCashinapproved_url()+row.getString("EXTENDEDDATA"));
		    if(apiResponse.length>0){
		    	JSONObject object = (JSONObject)new JSONParser().parse(new String(apiResponse, "UTF-8"));	    
			    Logger.LogServer(" response : " + new String(apiResponse));
			   
			    if(object.get("code").toString().equals("0") || object.get("code").toString().equals(0)){
			    	SystemInfo.getDb().QueryUpdate("UPDATE TBLCASHINTRANSACTIONS SET LEVELOFAPPROVAL=0, STATUS='APPROVED',OPERATORID=?,EXTENDEDDATA2=? WHERE ID=?",this.linkid,object.get("response-id").toString(), this.id);
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
	
	public boolean isapproved(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLCASHINTRANSACTIONS WHERE ID=? AND STATUS='APPROVED' AND LEVELOFAPPROVAL=0", this.id).size()>0;
	}
	public boolean sendMail(){
		DataRow user =  SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLCASHINTRANSACTIONS WHERE ID=?", this.id);
		DataRow email = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERNAME = ?", user.getString("CREATEDBY"));
		DataRow manager = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERID = ?", user.getString("MANAGERID"));
		if(email.getString("USERSLEVEL").equals("MANAGER") || email.getString("USERSLEVEL").equals("COMPANY")){
			EmailUtils.sendApprovedCashier(email.getString("EMAIL"));
			return true;
		}{
			EmailUtils.sendApproved(manager.getString("EMAIL"),user.getString("REFERENCE"));
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
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}

}
