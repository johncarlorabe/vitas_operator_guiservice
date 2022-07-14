 package com.psi.usermanagement.test;
 
 import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.psi.usermanagement.c.EditRegisteredCommand;
import com.psi.usermanagement.c.NewRegisterCommand;
import com.psi.usermanagement.c.RegisterCollectionCommand;
import com.tlc.common.EncryptedProperties;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.common.Util;
import com.tlc.gui.modules.common.IView;
 
 public class Tester
 {
   public static void main(String[] args)
   {
//	  //reg
//     String req = "{\"Email\":\"shermaine.pontillas@paytyche.com\",\"FirstName\":\"SHEPO\",\"LastName\":\"Pontillas\",\"MiddleName\":\"Lapore\",\"MSISDN\":\"639302603003\",\"Country\":\"Philippines\",\"Province\":\"Quezon\",\"City\":\"Lucena\"}";
//	  //validate
//	// String req = "{\"Email\":\"shermaine.pontillas25@gmail.com\",\"Code\":\"9880\"}";
//	 //edit
//	  //String req = "{\"Email\":\"shermaine.pontillas@paytyche.com\",\"FirstName\":\"shepo\",\"LastName\":\"gandang ganda\",\"MiddleName\":\"sa sarili\",\"MSISDN\":\"639302603003\",\"Country\":\"Philippines\",\"Province\":\"Quezon\",\"City\":\"Lucena\"}";
//	  //collection
//	// String req = "{\"Email\":\"shermaine.pontillas25@gmail.com\"}";
//		   
//     JsonRequest json = new JsonRequest(req);
//     view(json);
//	   EncryptedProperties prop = null;
//	   
//	   try {
//			prop =  new EncryptedProperties(Util.getWorkingDirectory() + "/bayadpoc.properties");
//			System.out.println("keyword="+prop.getProperty("keyword"));
//			System.out.println("operatorid="+prop.getProperty("operatorid"));
//			System.out.println("url="+prop.getProperty("url"));
////			System.out.println("balance_url="+prop.getProperty("balance_url"));
////			System.out.println("load_url="+prop.getProperty("load_url"));
////			System.out.println("remit_url="+prop.getProperty("remit_url"));
////			System.out.println("claim_url="+prop.getProperty("claim_url"));
////			System.out.println("bills_url="+prop.getProperty("bills_url"));
////			System.out.println("cashin_url="+prop.getProperty("cashin_url"));
////			System.out.println("cashinapproved_url="+prop.getProperty("cashinapproved_url"));
////			System.out.println("cashinreject_url="+prop.getProperty("cashinreject_url"));
////			System.out.println("branch_url="+prop.getProperty("branch_url"));
//		} catch (Exception e) {
//			
//		}
//	   String pattern = "^[0-9]+$";
//	   String num = "hhhh1111";
//	   Pattern r = Pattern.compile(pattern);
//	   Matcher m = r.matcher(num);
//		
//		if(!m.matches()){
//			System.out.print("hnd");
//		}else{
//			System.out.print("pasok");
//		}
	   
		try {
			Long amount = LongUtil.toLong("1");
			System.out.print(amount);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		 Long amount=null;
//			try {
//				amount = LongUtil.toLong("0.10");
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			System.out.println(amount);
//			   System.out.println(LongUtil.toString(amount));
		
   }
 
   public static void RegisteredProduct(JsonRequest json)
   {
     NewRegisterCommand rpndgac = new NewRegisterCommand();
     rpndgac.setRequest(json);
 
     IView view = rpndgac.execute();
     System.out.println(view.render());
   }
   
  
   
   public static void edit(JsonRequest json)
   {
     EditRegisteredCommand rpndgac = new EditRegisteredCommand();
     rpndgac.setRequest(json);
 
     IView view = rpndgac.execute();
     System.out.println(view.render());
   }
   
   public static void view(JsonRequest json)
   {
     RegisterCollectionCommand rpndgac = new RegisterCollectionCommand();
     rpndgac.setRequest(json);
 
     IView view = rpndgac.execute();
     System.out.println(view.render());
   }
 }
