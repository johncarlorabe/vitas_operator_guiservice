package com.psi.purchases.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.http.entity.StringEntity;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.psi.purchases.util.HttpClientHelper;
import com.psi.purchases.util.OtherProperties;
import com.tlc.common.DataRow;
import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.common.ObjectState;

public class Prepaid extends Model{

	@SuppressWarnings("unchecked")
	public boolean purchase(String id,String brand,String amount,String msisdn,String reference,String macaddress) throws ParseException, Exception {
		OtherProperties prop = new OtherProperties();
		String store=null;
		String cashier_id = null;
		String branchcode = null;
		String terminal = null;
		String accountnumber = null;
		String reqid = SystemInfo.getDb().QueryScalar("SELECT ADMDBMC.GETREFERENCEID(9000) FROM DUAL", "0");
		JSONObject request = new JSONObject();
		JSONObject request2 = new JSONObject();
		JSONObject request3 = new JSONObject();
		
		DataRow res = SystemInfo.getDb().QueryDataRow("SELECT U.USERNAME,U.ACCOUNTNUMBER,PO.BRANCHCODE,U.TERMINAL FROM TBLUSERS U INNER JOIN TBLBRANCHES B ON U.ACCOUNTNUMBER = B.ACCOUNTNUMBER INNER JOIN TBLPOSUSERS PO ON PO.USERID = U.USERNAME WHERE U.USERID=?", id);
		
			cashier_id = res.getString("USERNAME");
			branchcode = res.getString("BRANCHCODE");
			terminal = res.getString("TERMINAL");
			accountnumber = res.getString("ACCOUNTNUMBER");

				//request.put("destination", "PHILARP_PREPAID");
				request.put("request-id", reqid);
				request3.put("branch-id", branchcode);
				request3.put("terminal-id", macaddress);
				request3.put("cashier-id", cashier_id);
				request3.put("brand", brand);
				request3.put("amount", amount);
				request3.put("msisdn", msisdn);
				request3.put("account", msisdn);
				request3.put("sku", reference);
				request.put("payment-data", request3);
				request2.put("business", "1166");
				request.put("auth",request2);
		
		
				StringEntity entity = new StringEntity(request.toJSONString());
				Logger.LogServer(request.toJSONString());
				HttpClientHelper client = new HttpClientHelper();
			    HashMap<String, String> headers = new HashMap<String, String>();
			    headers.put("Content-Type", prop.getType());
			    headers.put("token", prop.getToken());
			    headers.put("X-Forwarded-For","127.0.0.1");
			    //"http://192.168.1.20:8080/hermes/core/svc/"
			    byte[] apiResponse = client.httpPost(prop.getUrl()+accountnumber+prop.getLoad_url(), null, headers, null, entity);
			   	Logger.LogServer("Prepaid url:"+prop.getUrl()+accountnumber+prop.getLoad_url());
			   	Logger.LogServer("Prepaid response:"+new String(apiResponse, "UTF-8"));
			    if(apiResponse.length>0){
			    		JSONObject object = (JSONObject)new JSONParser().parse(new String(apiResponse, "UTF-8"));
			    		 Logger.LogServer(object.toString());
					    if(object.get("code").toString().equals("0") || object.get("code").toString().equals(0)){
					    	Logger.LogServer("Dumaan");
					    	this.setState(new ObjectState("00",object.get("message").toString()));
					    	return true;
						}else if(object.get("code").toString().equals("99") || object.get("code").toString().equals(99)){
							  
								 this.setState(new ObjectState("05",object.get("message").toString()));
							    	return false;
								  
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
}
