 package com.psi.business.test;

import com.psi.business.c.BusinessCollectionCommand;
import com.psi.business.c.NewBusinessCommand;
import com.psi.business.c.NewBusinessUserCommand;
import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.IView;
 

 
 public class Tester
 {
   public static void main(String[] args)
   {
	   //newdebt 
	   String req = "{\"BranchName\":\"HOMEKING\",\"Address\":\"1st, Mezzanine & 2nd Floors, Filhigh Trading Bldg, 1923-25 Angel Linao St.\",\"City\":\"ERMITA\",\"Province\":\"NCR CITY OF MANILA FIRST DISTRICT\",\"Country\":\"PHILIPPINES\","
	   		+ "\"ZipCode\":\"\",\"ContactNumber\":\"(02) 242 7801\",\"ImgProof\":\"\",\"XCoordinate\":\"\",\"YCoordinate\":\"\","
	   		+ "\"Monday\":\"\",\"Tuesday\":\"\",\"Wednesday\":\"\",\"Thursday\":\"\",\"Friday\":\"\",\"Saturday\":\"\",\"Sunday\":\"\",\"AccountNumber\":\"478921234568\",\"IsWithHoldingTax\":\"\"}";
		   	   
	   
	   //fulfilldebt
	  // String req = "{\"REFERENCEID\":\"1234567890\"}";
	/*   System.out.println(SystemInfo.getDb().QueryScalar("SELECT SYSDATE FROM DUAL", ""));
		
	   DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLBUSINESS WHERE ACCOUNTNUMBER='349312345918'");
	   //  System.out.println(""+r.size());
	     if(r.isEmpty()){
	    	 System.out.println("empty");
	     }else{
	    	 for(DataRow rr : r){
	    		 System.out.println(rr.getString("PROOFADDRESS"));
	    	 }
	     }*/
	   JsonRequest json = new JsonRequest(req);
     RegisteredProduct(json);
   }
 
   public static void RegisteredProduct(JsonRequest json)
   {
	   NewBusinessCommand rpndgac = new NewBusinessCommand();

     rpndgac.setRequest(json); 
 
     IView view = rpndgac.execute();
     System.out.println(view.render());
   }

   
   
 }
