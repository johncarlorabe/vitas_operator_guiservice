package com.psi.serviceconfig.m;

import org.json.simple.JSONArray;

import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.session.UISession;

public class EnableDisableServices extends Model{
	protected Integer[] addservices;
	protected Integer[] deleteservices;
	protected String accountnumber;
	protected String category;
	
	@SuppressWarnings("unchecked")
	public boolean change(){
UISession sess = this.getAuthorizedSession();
		
		Integer[] serArr = this.getAddservices();
		JSONArray serJsonArr = new JSONArray();
		StringBuilder servicesBuilder = new StringBuilder();
		String services = "INSERT INTO TBLSERVICESCONFIG (SERVICES, ACCOUNTNUMBER, CATEGORY) VALUES";
		

		try{
			for(Integer s :serArr){
				serJsonArr.add(s);
			}
			
			 for(int i = 0; i < serJsonArr.size() ; i++){

				 servicesBuilder.append(services);
				 servicesBuilder.append("("+serArr[i]+","+this.accountnumber+",'"+this.category.toString()+"')");
				 servicesBuilder.append(";\n");
				 
				 Logger.LogServer("ID NILA"+serArr[i]);
				}
		}
		 catch(Exception e){
			 
		 }
		
		 String sql = "BEGIN\n"
				    + "DELETE FROM TBLSERVICESCONFIG WHERE ACCOUNTNUMBER = ? AND CATEGORY = ?;\n"
				    + servicesBuilder
				  //    + "INSERT INTO TBLAUDITTRAIL(USERNAME,USERID,LOG,IP,MODULE,SESSIONID,STATUS,ENTITYID) VALUES(?,?,?,?,?,?,?,?);\n"
				    + "END;\n";
		 
		int dr =  this.db.QueryUpdate(sql.toString(),
										this.accountnumber,this.category);
		//sess.getToken().Username,sess.getToken().UserId,this.name+" Updated Successfully",sess.getToken().IpAddress,"3040",sess.getId(),"TLC-3040-00",this.id);
		
		
		if (dr>0){
			return true;
		}
		else{
//			this.db.QueryUpdate("INSERT INTO TBLAUDITTRAIL(USERNAME,USERID,LOG,IP,MODULE,SESSIONID,STATUS,ENTITYID) VALUES(?,?,?,?,?,?,?,?)",
//					sess.getToken().Username,sess.getToken().UserId,"UserLevel Update Failed",sess.getToken().IpAddress,"3040",sess.getId(),"TLC-3040-01",this.id);
			return false;
		}

	}

	public String getAccountnumber() {
		return accountnumber;
	}
	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}
	public Integer[] getAddservices() {
		return addservices;
	}
	public void setAddservices(Integer[] addservices) {
		this.addservices = addservices;
	}
	public Integer[] getDeleteservices() {
		return deleteservices;
	}
	public void setDeleteservices(Integer[] deleteservices) {
		this.deleteservices = deleteservices;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	

}
