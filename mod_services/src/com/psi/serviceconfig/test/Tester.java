 package com.psi.serviceconfig.test;


import com.psi.serviceconfig.c.BusinessCollectionCommand;
import com.tlc.gui.modules.common.IView;
 

 
 public class Tester
 {
   public static void main(String[] args)
   {
	   String req = "{\"AccountNumber\":\"478921234568\"}";

	   
	   JsonRequest json = new JsonRequest(req);
     RegisteredProduct(json);
   }
 
   public static void RegisteredProduct(JsonRequest json)
   {
	   BusinessCollectionCommand rpndgac = new BusinessCollectionCommand();

     rpndgac.setRequest(json); 
 
     IView view = rpndgac.execute();
     System.out.println(view.render());
   }

   
   
 }
