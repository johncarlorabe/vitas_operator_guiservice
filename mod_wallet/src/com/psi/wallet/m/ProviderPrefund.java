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
import com.tlc.common.DataRowCollection;
import com.tlc.common.Logger;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.session.UISession;

public class ProviderPrefund extends Model{
	protected Long amount;
	protected Long upfront;
	protected Long caskback;
	protected String pofilename;
	protected String pofilepath;
	protected String password;
	protected String dateofdepo;
	protected String timeofdepo;
	protected String bankrefno;
	protected String bankname;
	protected String accountnumber;
	protected String walletid;
	@SuppressWarnings("unchecked")
	public boolean topup() throws ParseException, IOException{
		UISession sess = this.getAuthorizedSession();
		OtherProperties prop = new OtherProperties();
		JSONObject request = new JSONObject();
		JSONObject request2 = new JSONObject();
		JSONObject request3 = new JSONObject();
		JSONArray array = new JSONArray();
		
		String reqid = SystemInfo.getDb().QueryScalar("SELECT ADMDBMC.GETREFERENCEID(9501) FROM DUAL", "0");
	    
	    DataRow row = SystemInfo.getDb().QueryDataRow("SELECT DECRYPT(AI.PASSWORD,?,AI.ACCOUNTNUMBER) PASSWORD,U.USERNAME,AI.ROOT,U.ACCOUNTNUMBER FROM ADMDBMC.TBLACCOUNTINFO AI INNER JOIN TBLUSERS U ON U.ACCOUNTNUMBER = AI.ACCOUNTNUMBER WHERE U.USERID = ?",SystemInfo.getDb().getCrypt(), sess.getAccount().getId());
    
		request.put("request-id", reqid);
		request.put("destination",this.accountnumber);
			request2.put("password", "1166");
		request.put("auth", request2);
				request3.put("reference", this.bankrefno);
				request3.put("pocket-id", this.walletid);
				request3.put("amount", LongUtil.toString(Long.parseLong(this.amount.toString())));
		request.put("payments", array);
		array.add(request3);
		
		Logger.LogServer(request.toString());
		Logger.LogServer("PREFUND URL:"+"http://localhost:6080/hermes/core/svc/"+"389112345679"+"/transfers");
		StringEntity entity = new StringEntity(request.toJSONString());
		
		HttpClientHelper client = new HttpClientHelper();
	    HashMap<String, String> headers = new HashMap<String, String>();
	    headers.put("Content-Type", prop.getType());
	    headers.put("token",prop.getToken());
	    headers.put("X-Forwarded-For","127.0.0.1");
	    byte[] apiResponse = client.httpPost("http://localhost:6080/hermes/core/svc/"+"389112345679"+"/transfers", null, headers, null, entity);
	   	Logger.LogServer("PREFUND RESPONSE:"+new String(apiResponse, "UTF-8"));
	    if(apiResponse.length>0){
	    	JSONObject object = (JSONObject)new JSONParser().parse(new String(apiResponse, "UTF-8"));	    
		    Logger.LogServer(" response : " + new String(apiResponse));
		   
		    if(object.get("code").toString().equals("0") || object.get("code").toString().equals(0)){
		    	StringBuilder q = new StringBuilder("BEGIN\n");
		    	q.append("INSERT INTO TBLCASHINTRANSACTIONS (TYPE,LEVELOFAPPROVAL,REQUESTID,ACCOUNTNUMBER,CREATEDBY,BANKBRANCHCODE,AMOUNT,STATUS,REMARKS,BANKNAME,DATEOFDEPOSIT,TIMEOFDEPOSIT,EXTENDEDDATA) VALUES ('PROVIDERTOPUP','0',?,?,?,?,?,'PAID','PROVIDERPREFUND',?);\n");
		    	q.append("INSERT INTO IBAYADPH.TBLPROVIDERCONFIG(ACCOUNTNUMBER,CASHBACK,UPFRONT,AMOUNT) VALUES(?,?,?,?);\n");
		    	q.append("END;");
		    		SystemInfo.getDb().QueryUpdate(q.toString(),reqid,this.accountnumber,sess.getAccount().getUserName(),this.bankrefno,this.amount,this.bankname,this.dateofdepo,this.timeofdepo,this.accountnumber,this.caskback,this.upfront,this.amount);
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
		UISession sess = this.getAuthorizedSession();
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERID =? AND PASSWORD=ENCRYPT(?,?,USERNAME)", sess.getAccount().getId(),this.password,SystemInfo.getDb().getCrypt()).size()>0;
	}
	
	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Long getUpfront() {
		return upfront;
	}

	public void setUpfront(Long upfront) {
		this.upfront = upfront;
	}

	public Long getCaskback() {
		return caskback;
	}

	public void setCaskback(Long caskback) {
		this.caskback = caskback;
	}

	public String getPofilename() {
		return pofilename;
	}

	public void setPofilename(String pofilename) {
		this.pofilename = pofilename;
	}

	public String getPofilepath() {
		return pofilepath;
	}

	public void setPofilepath(String pofilepath) {
		this.pofilepath = pofilepath;
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

	public String getBankrefno() {
		return bankrefno;
	}

	public void setBankrefno(String bankrefno) {
		this.bankrefno = bankrefno;
	}

	public String getBankname() {
		return bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	public String getAccountnumber() {
		return accountnumber;
	}

	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}

	public String getWalletid() {
		return walletid;
	}

	public void setWalletid(String walletid) {
		this.walletid = walletid;
	}
	
	
}
