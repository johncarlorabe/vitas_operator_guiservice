package com.psi.wallet.m;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.psi.wallet.util.HttpClientHelper;
import com.psi.wallet.util.OtherProperties;
import com.tlc.common.DataRow;
import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.common.ObjectState;

public class ProviderBalance extends Model {
	public static final String PROP_CURRENT="CURRENT";
	public static final String PROP_AVAILABLE="AVAILABLE";
	String currentbal;
	String availablebal;
	
	@SuppressWarnings("unchecked")
	public boolean balance(String accountnumber) throws ParseException, Exception {
		OtherProperties prop = new OtherProperties();
		
	
				HttpClientHelper client = new HttpClientHelper();
			    HashMap<String, String> headers = new HashMap<String, String>();
			    headers.put("Content-Type", prop.getType());
			    headers.put("token",prop.getToken());
			    headers.put("X-Forwarded-For","127.0.0.1");
			    byte[] apiResponse = client.httpGet(prop.getUrl()+accountnumber+"/wallet", null, headers, null);
			    Logger.LogServer(" response : " + new String(apiResponse));
				    if(apiResponse.length>0){
				    JSONObject object = (JSONObject)new JSONParser().parse(new String(apiResponse, "UTF-8"));
				
					    JSONParser p = new JSONParser();
					    JSONObject bal = (JSONObject) p.parse(object.toJSONString());
					    
					    if(bal.get("code").toString().equals("0") || bal.get("code").toString().equals(0)){
					    ArrayList<Object> pockets = (ArrayList)bal.get("pockets");
					    HashMap<String,Object> pocket = (HashMap<String, Object>) pockets.get(0);
					   
					    
					   // if(pocket.get("pocket-id").toString().equals("0") || pocket.get("pocket-id").toString().equals(0)  ){
					    	HashMap<String,Object> pocket1 = (HashMap<String, Object>) pockets.get(0);
					    	this.setCurrentbal(pocket1.get("current-balance").toString());
					    	this.setAvailablebal( pocket1.get("available-balance").toString());
					    	this.setState(new ObjectState("00",bal.get("message").toString()));  
					    	 return true;

//					    }else{
//					    	this.setCurrentbal(pocket.get("current-balance").toString());
//					    	this.setAvailablebal( pocket.get("available-balance").toString());
//					    	this.setState(new ObjectState("00",bal.get("message").toString()));  
//					    	 return true;
//
//					    }	    
					    						    
					   
					    }else{
					    	this.setState(new ObjectState(bal.get("code").toString(),bal.get("message").toString()));
					    	return false;
					    }

				    }else{
				    this.setState(new ObjectState("99","System is busy"));
				    return false;
				    }
		}
	
	public String getCurrentbal() {
		return currentbal;
	}
	public void setCurrentbal(String currentbal) {
		this.props.put(PROP_CURRENT,currentbal);
		this.currentbal = currentbal;
	}
	public String getAvailablebal() {
		return availablebal;
	}
	public void setAvailablebal(String availablebal) {
		this.props.put(PROP_AVAILABLE,availablebal);
		this.availablebal = availablebal;
	}

}
