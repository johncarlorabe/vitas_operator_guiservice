 package com.psi.accountmanagement.test;
 
 import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import com.psi.accountmanagement.c.ActivateAccountCommand;
import com.psi.accountmanagement.c.BranchesCommand;
import com.psi.accountmanagement.c.EditRegisteredCommand;
import com.psi.accountmanagement.c.ForgotPasswordCommand;
import com.psi.accountmanagement.c.LockAccountCommand;
import com.psi.accountmanagement.c.NewRegisterCommand;
import com.psi.accountmanagement.c.ResetPasswordCommand;
import com.psi.accountmanagement.c.SearchUsersCommand;
import com.psi.accountmanagement.c.ValidateCodeCommand;
import com.psi.accountmanagement.c.ViewRegisteredCommand;
import com.tlc.common.LongUtil;
import com.tlc.gui.modules.common.IView;
 
 public class Tester
 {
   public static void main(String[] args)
   {
	  //reg
     //String req = "{\"Email\":\"shermaine.pontillas@paytyche.com\",\"FirstName\":\"SHEPO\",\"LastName\":\"Pontillas\",\"MiddleName\":\"Lapore\",\"MSISDN\":\"639302603003\",\"Country\":\"Philippines\",\"Province\":\"Quezon\",\"City\":\"Lucena\"}";
	  //validate
	// String req = "{\"Email\":\"shermaine.pontillas25@gmail.com\",\"Code\":\"9880\"}";
	 //edit
	  //String req = "{\"Email\":\"shermaine.pontillas@paytyche.com\",\"FirstName\":\"shepo\",\"LastName\":\"gandang ganda\",\"MiddleName\":\"sa sarili\",\"MSISDN\":\"639302603003\",\"Country\":\"Philippines\",\"Province\":\"Quezon\",\"City\":\"Lucena\"}";
	  //collection
//		 String req = "{\"Id\":\"1\",\"UsersLevel\":\"CUSTOMER\"}";
//		   
//     JsonRequest json = new JsonRequest(req);
//     branch(json);
/*	   NumberFormat format = NumberFormat.getInstance(Locale.US);
	   Number amountone = null;
		try {
			amountone = format.parse("10.03");
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		Long amount  = null;
	   try {
		amount = LongUtil.toLong("10.03");
		System.out.println(amount);
		
		System.out.print(LongUtil.toString(amount));
	} catch (ParseException e) {
		e.printStackTrace();
	}*/
	   
	   String json = "{\"Email\":\"gelline.francia@paytyche.com\",\"Url\":\"https://www.ibayadcenter.com/operator/login\"}";
	 //  String json2 = "{\"USERNAME\":\"CARLOPASIGCSH\",\"USERSLEVEL\":\"CASHIER\"}";
	//   JsonRequest jsonReq2 = new JsonRequest(json);
	   JsonRequest jsonReq = new JsonRequest(json);
	   
	   ForgotPasswordCommand resetpass = new ForgotPasswordCommand();
	   resetpass.setRequest(jsonReq);
	   IView view = resetpass.execute();
	   System.out.println(view.render());
	   
//	   	ActivateAccountCommand activeacct = new ActivateAccountCommand();
//	   	activeacct.setRequest(jsonReq);
//	   	IView view = activeacct.execute();
//	   	System.out.println(view.render());
	
		
/*		LockAccountCommand lockaccount = new LockAccountCommand();
		lockaccount.setRequest(jsonReq2);
		IView view = lockaccount.execute();
		System.out.println(view.render());*/
		
		
//		SearchUsersCommand searchuser = new SearchUsersCommand();
//		   searchuser.setRequest(jsonReq);
//			IView view = searchuser.execute();
//			System.out.println(view.render());
	   
   }
 
   public static void RegisteredProduct(JsonRequest json)
   {
     NewRegisterCommand rpndgac = new NewRegisterCommand();
     rpndgac.setRequest(json);
 
     IView view = rpndgac.execute();
     System.out.println(view.render());
   }
   
   public static void AuthCode(JsonRequest json)
   {
     ValidateCodeCommand rpndgac = new ValidateCodeCommand();
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
     ViewRegisteredCommand rpndgac = new ViewRegisteredCommand();
     rpndgac.setRequest(json);
 
     IView view = rpndgac.execute();
     System.out.println(view.render());
   }
   public static void branch(JsonRequest json)
   {
     BranchesCommand rpndgac = new BranchesCommand();
     rpndgac.setRequest(json);
 
     IView view = rpndgac.execute();
     System.out.println(view.render());
   }
   
   public static void search(JsonRequest json)
   {
	   SearchUsersCommand searchuser = new SearchUsersCommand();
	   searchuser.setRequest(json);
		IView view = searchuser.execute();
		System.out.println(view.render());
   }
 }
