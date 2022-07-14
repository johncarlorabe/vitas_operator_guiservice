 package com.psi.backoffice.test;
 
 import com.psi.backoffice.c.EditRegisteredCommand;
import com.psi.backoffice.c.NewRegisterCommand;
import com.psi.backoffice.c.RegisterCollectionCommand;
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
