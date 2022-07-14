 package com.psi.branch.test;
 

import com.psi.branch.c.NewBranchCommand;
import com.psi.branch.m.NewBranch;
import com.tlc.gui.modules.common.IView;
 
 public class Tester
 {
   public static void main(String[] args)
   {
	 //reg
     String req = "{\"BranchName\":\"BranchOne\",\"Address\":\"Lucena City, Quezon\",\"City\":\"Lucena\",\"Province\":\"Quezon\",\"Country\":\"Philippines\",\"ZipCode\":\"5522\",\"ContactNumber\":\"639302603008\",\"OperatingHours\":\"10AM-12AM\",\"ImgProof\":\"dfghjk\",\"XCoordinate\":\"10000000\",\"YCoordinate\":\"1111111\"}";
     	 //edit
   
     JsonRequest json = new JsonRequest(req);
     RegisteredProduct(json);
   }
 
   public static void RegisteredProduct(JsonRequest json)
   {
	  NewBranchCommand rpndgac = new NewBranchCommand();
     rpndgac.setRequest(json);
 
     IView view = rpndgac.execute();
     System.out.println(view.render());
   }

  
 }
