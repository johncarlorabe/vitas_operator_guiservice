package com.psi.purchases.m;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.http.entity.StringEntity;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.psi.purchases.util.EmailUtils;
import com.psi.purchases.util.HttpClientHelper;
import com.psi.purchases.util.OtherProperties;
import com.psi.purchases.util.Remit;
import com.tlc.common.DataRow;
import com.tlc.common.Logger;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.common.ObjectState;

public class Remittance extends Model{
	protected String ordernumber;
	protected String trackingnumber;
	protected String accountnumber;
	protected String linkid;
	protected String overrideusername;
	protected String overridepassword;
	protected String isoverride;
	protected String macaddress;
	
	public boolean createremit()  {
		try{
			String reqid = SystemInfo.getDb().QueryScalar("SELECT ADMDBMC.GETREFERENCEID(4100) FROM DUAL", "0");
		    
		    DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLORDERREQUEST WHERE ORDERNUMBER = ? AND TRACKINGNUMBER = ?", this.ordernumber,this.trackingnumber);
		    if(row.size()==0){
		    	Logger.LogServer("No data found");
		    	return false;
		    }
			Remit remit = new Remit();
			remit.setAmount(Long.parseLong(row.getString("AMOUNT")));
			remit.setOrderid(row.getString("ORDERNUMBER"));
			remit.setTrackingid(row.getString("TRACKINGNUMBER"));
			remit.setType(row.getString("TRANSACTIONTYPE"));
			remit.setReceivermsisdn(row.getString("MSISDN"));
			remit.setReferencenumber(row.getString("REMARKS"));
			remit.setMacaddress(this.macaddress);
			remit.setSenderaccountnumber(SystemInfo.getDb().QueryScalar("SELECT ACCOUNTNUMBER FROM TBLUSERS WHERE EMAIL = ?", "", row.getString("SENDEREMAIL")));
			remit.setSenderfirstname(row.getString("SENDERFIRSTNAME"));
			remit.setSenderlastname(row.getString("SENDERLASTNAME"));
			remit.setSendermsisdn(row.getString("SENDERMSISDN"));
			remit.setSenderemail(row.getString("SENDEREMAIL"));
			remit.setSenderspecificaddress(row.getString("SENDERCITY")+" "+row.getString("SENDERSTATE")+", "+row.getString("SENDERCOUNTRY"));
			remit.setSendercity(row.getString("SENDERCITY"));
			remit.setSendercountry(row.getString("SENDERCOUNTRY"));
			remit.setRecfirstname(row.getString("FIRSTNAME"));
			remit.setReclastname(row.getString("LASTNAME"));
			remit.setRecmsisdn(row.getString("MSISDN"));
			remit.setRecemail(row.getString("EMAIL"));
			remit.setRecspecificaddress(row.getString("CITY")+" "+row.getString("STATE")+", "+row.getString("COUNTRY"));
			remit.setReccity(row.getString("CITY"));
			remit.setReccountry(row.getString("COUNTRY"));
			
			String user = "";
			if(this.isoverride.equals("1")){
				user = SystemInfo.getDb().QueryScalar("SELECT USERNAME FROM TBLUSERS WHERE USERNAME = ?","",this.overrideusername);
				remit.setLinkid(SystemInfo.getDb().QueryScalar("SELECT USERID FROM TBLUSERS WHERE USERNAME = ?","",this.overrideusername));
			}else{
				user = SystemInfo.getDb().QueryScalar("SELECT USERNAME FROM TBLUSERS WHERE USERID = ?","",this.linkid);
				remit.setLinkid(this.linkid);
			}
			
			if(row.getString("TRANSACTIONTYPE").equals("local")){
				if(remit.create()){
					StringBuilder query = new StringBuilder("BEGIN\n");
						query.append("UPDATE TBLORDERREQUEST SET STATUS = 'PAID', REQUESTBYCASHIER=? WHERE ORDERNUMBER = ? AND TRACKINGNUMBER = ?; \n");	
						query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");		 
					SystemInfo.getDb().QueryUpdate(query.toString(),user,this.ordernumber,this.trackingnumber);
					
					EmailUtils.sendClaim(row.getString("EMAIL"),trackingnumber,ordernumber,row.getString("FIRSTNAME"));
			    	SystemInfo.getDb().QueryUpdate("INSERT INTO IBAYADPH.TBLSMPPPNDG(REFERENCEID, MSISDN, MESSAGE, TYPE, OPCODE) VALUES(?,?,?,0,51503)", reqid,row.getString("MSISDN"),"Thank you for using Bayad Center Remittance Service. Your tracking number is "+trackingnumber+" and order number "+ordernumber+".Present this to any bayad center branches nationwide.");
			    	Logger.LogServer("message:"+remit.getState());
			    	this.setState(remit.getState());
					return true;
				}else{
//					StringBuilder query = new StringBuilder("BEGIN\n");
//					query.append("UPDATE TBLORDERREQUEST SET STATUS = 'FAILED',RESPONSEMESSAGE=?,REQUESTBYCASHIER=? WHERE ORDERNUMBER = ? AND TRACKINGNUMBER = ?; \n");	
//					query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");		 
//					SystemInfo.getDb().QueryUpdate(query.toString(),remit.getState().getMessage(),user,this.ordernumber,this.trackingnumber);
					this.setState(remit.getState());
					return false;
				}
			}else{
				Logger.LogServer("Transaction is MoneyGram");
				StringBuilder query = new StringBuilder("BEGIN\n");
				query.append("UPDATE TBLORDERREQUEST SET STATUS = 'COMPLETED',REQUESTBYCASHIER=? WHERE ORDERNUMBER = ? AND TRACKINGNUMBER = ?; \n");	
				query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");		 
				SystemInfo.getDb().QueryUpdate(query.toString(),user,this.ordernumber,this.trackingnumber);
			
				EmailUtils.sendClaim(row.getString("EMAIL"),trackingnumber,ordernumber,row.getString("FIRSTNAME"));
		    	SystemInfo.getDb().QueryUpdate("INSERT INTO IBAYADPH.TBLSMPPPNDG(REFERENCEID, MSISDN, MESSAGE, TYPE, OPCODE) VALUES(?,?,?,0,51503)", reqid,row.getString("MSISDN"),"Thank you for using Bayad Center Remittance Service. Your tracking number is "+trackingnumber+" and order number "+ordernumber+".Present this to any bayad center branches nationwide.");
		    	this.setState(new ObjectState("00","Successful"));
		    	return true;
			}
		}catch(Exception  e){
			Logger.LogServer("Create Remit" + e);
		}
		return false;
	}
	
	public boolean validateverride(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERNAME=? AND PASSWORD=ENCRYPT(?,?,USERNAME)", this.overrideusername,this.overridepassword,SystemInfo.getDb().getCrypt()).size()>0;
	}
	

	public String getOrdernumber() {
		return ordernumber;
	}
	public void setOrdernumber(String ordernumber) {
		this.ordernumber = ordernumber;
	}
	public String getTrackingnumber() {
		return trackingnumber;
	}
	public void setTrackingnumber(String trackingnumber) {
		this.trackingnumber = trackingnumber;
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
	public String getOverrideusername() {
		return overrideusername;
	}
	public void setOverrideusername(String overrideusername) {
		this.overrideusername = overrideusername;
	}
	public String getOverridepassword() {
		return overridepassword;
	}
	public void setOverridepassword(String overridepassword) {
		this.overridepassword = overridepassword;
	}
	public String getIsoverride() {
		return isoverride;
	}
	public void setIsoverride(String isoverride) {
		this.isoverride = isoverride;
	}

	public String getMacaddress() {
		return macaddress;
	}

	public void setMacaddress(String macaddress) {
		this.macaddress = macaddress;
	}

}
