 package com.psi.remittance.test;

import com.psi.reports.c.BillsPaymentPerBillerColCommand;
import com.psi.reports.c.DailyUMAKTransColCommand;
import com.psi.reports.c.EwalletPaymentsAdminColCommand;
import com.psi.reports.c.EwalletPaymentsColCommand;
import com.psi.reports.c.HomekingTransactionReportsCommand;
import com.psi.reports.c.OutRemitAdminCollectionCommand;
import com.psi.reports.c.ReportCommand;
import com.psi.reports.c.VoidedTransactionsAdminColCommand;
import com.psi.reports.c.WifiVoucherReportsCommand;
import com.tlc.gui.modules.common.IView;
 

 
 public class Tester
 {
   public static void main(String[] args)
   {
	  //re
    // String req = "{\"Beneficiary\":\"1\",\"FirstName\":\"SHEPO\",\"LastName\":\"Pontillas\",\"SecondName\":\"Lapore\",\"MSISDN\":\"639302603003\",\"Email\":\"shermaine.pontillas25@gmail.com\",\"AccountNumber\":\"1\",\"Amount\":\"100\"}";
     	 //edit
    // String req = "{\"OrderNumber\":\"15025458\",\"FirstName\":\"SHEPO\",\"LastName\":\"Ganda\",\"SecondName\":\"Lapore\",\"MSISDN\":\"639302603003\",\"Email\":\"shermaine.pontillas25@gmail.com\",\"TrackingNumber\":\"I50O9DPU\",\"Amount\":\"100\"}";
     //cancel
   //  String req = "{\"OrderNumber\":\"15025458\",\"TrackingNumber\":\"I50O9DPU\"}";
     
	     //collection
		//String req = "{\"LinkID\":\"1\"}";
		
		//view
				String req = "{\"Id\":\"123\",\"DateFrom\":\"2018-11-01\",\"DateTo\":\"2018-11-01\"}";
		   
     JsonRequest json = new JsonRequest(req);
     RegisteredProduct(json);
   }
 
   public static void RegisteredProduct(JsonRequest json)
   {
	   WifiVoucherReportsCommand rpndgac = new WifiVoucherReportsCommand();
     rpndgac.setRequest(json);
 
     IView view = rpndgac.execute();
     System.out.println(view.render());
   }

   
   
 }
