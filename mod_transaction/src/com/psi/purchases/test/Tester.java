 package com.psi.purchases.test;
 

import java.text.ParseException;
import java.util.HashMap;

import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.psi.purchases.c.ApprovedRemittanceCommand;
import com.psi.purchases.c.ApprovedWalletTopUpCommand;
import com.psi.purchases.c.CancelTopupCommand;
import com.psi.purchases.c.PurchaseCommand;
import com.psi.purchases.c.RejectWalletTopupCommand;
import com.psi.purchases.c.WalletTopUpCollectionCommand;
import com.psi.purchases.c.WalletTopupRequestCommand;
import com.psi.purchases.util.HttpClientHelper;
import com.psi.purchases.util.OtherProperties;
import com.tlc.common.DataRow;
import com.tlc.common.Logger;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.IView;
 
 public class Tester
 {
   public static void main(String[] args)
   {
	 /*  OtherProperties prop = new OtherProperties();
		String password = "1234";

		
		 byte[] encodedToken = Base64.encodeBase64(password.toString().getBytes());
        String uPPasswordString = new String(encodedToken);
		HttpClientHelper client = new HttpClientHelper();
	    HashMap<String, String> headers = new HashMap<String, String>();
	    headers.put("Content-Type", prop.getType());
	    headers.put("token", prop.getToken());
	    headers.put("authorization", uPPasswordString);
	    headers.put("X-Forwarded-For","127.0.0.1");
	    
	    System.out.println(headers);
	    
	    System.out.println(" request : " + prop.getUrl()+"478921234568"+prop.getCashinreject_url());*/
	    
	    
				String req = "{\"Id\":\"666\",\"LinkId\":\"1011\",\"Password\":\"0987654321\",\"Remarks\":\"test\"}";
	  // String req = "{\"Amount\":\"15\",\"Brand\":\"SMART\",\"Reference\":\"SMARTLOAD\",\"MSISDN\":\"639302603003\",\"Id\":\"1\"}";
		   
     JsonRequest json = new JsonRequest(req);
				view(json);

     
   }
 
   public static void RegisteredProduct(JsonRequest json)
   {
	   ApprovedRemittanceCommand rpndgac = new ApprovedRemittanceCommand();
     rpndgac.setRequest(json);
 
     IView view = rpndgac.execute();
     System.out.println(view.render());
   }
   
   public static void cancel(JsonRequest json)
   {
	   CancelTopupCommand rpndgac = new CancelTopupCommand();
     rpndgac.setRequest(json);
 
     IView view = rpndgac.execute();
     System.out.println(view.render());
   }
   
   public static void load(JsonRequest json)
   {
	   PurchaseCommand rpndgac = new PurchaseCommand();
     rpndgac.setRequest(json);
 
     IView view = rpndgac.execute();
     System.out.println(view.render());
   }
   public static void view(JsonRequest json)
   {
	   RejectWalletTopupCommand rpndgac = new RejectWalletTopupCommand();
     rpndgac.setRequest(json);
 
     IView view = rpndgac.execute();
     System.out.println(view.render());
   }
   
   public static void approved(JsonRequest json)
   {
	   ApprovedWalletTopUpCommand rpndgac = new ApprovedWalletTopUpCommand();
     rpndgac.setRequest(json);
 
     IView view = rpndgac.execute();
     System.out.println(view.render());
   }
   
   public static void request(JsonRequest json)
   {
	   WalletTopupRequestCommand rpndgac = new WalletTopupRequestCommand();
     rpndgac.setRequest(json);
 
     IView view = rpndgac.execute();
     System.out.println(view.render());
   }

  
 }
