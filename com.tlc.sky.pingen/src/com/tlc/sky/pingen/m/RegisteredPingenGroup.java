package com.tlc.sky.pingen.m;


import org.json.simple.JSONArray;

import com.tlc.common.DataRow;
import com.tlc.common.Logger;
import com.tlc.gui.modules.session.UISession;
import com.tlc.sky.pingen.arch.uc.acct.AbstractRegisteredPingenGroup;

public class RegisteredPingenGroup extends AbstractRegisteredPingenGroup {
	

	public RegisteredPingenGroup(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean exists() {
		UISession sess = this.getAuthorizedSession();
		
		DataRow dr =  this.db.QueryDataRow("SELECT * FROM TBLUSERSLEVEL WHERE UPPER(USERSLEVEL) = UPPER(?)", name);
			if(dr!=null){
				this.setSessionTimeout(dr.getInteger("SESSIONTIMEOUT"));
				this.setSearchRange(dr.getInteger("SEARCHRANGE"));
				this.setPasswordExpiry(dr.getInteger("PASSWORDEXPIRY"));
				this.setPasswordHistory(dr.getInteger("PASSWORDHISTORY"));
				this.setMinPassword(dr.getInteger("MINPASSWORD"));
				this.setAccountStatus(dr.getString("ACCOUNTSTATUS"));
				this.setHomePage(dr.getString("HOMEPAGE"));
				
//				this.db.QueryUpdate("INSERT INTO TBLAUDITTRAIL(USERNAME,USERID,LOG,IP,MODULE,SESSIONID,STATUS,ENTITYID) VALUES(?,?,?,?,?,?,?,?)",
//						sess.getToken().Username,sess.getToken().UserId,this.name+" Selected",sess.getToken().IpAddress,"3020",sess.getId(),"TLC-3020-00",this.id);
				
				return true;
				
			}
			else{
				
//				this.db.QueryUpdate("INSERT INTO TBLAUDITTRAIL(USERNAME,USERID,LOG,IP,MODULE,SESSIONID,STATUS,ENTITYID) VALUES(?,?,?,?,?,?,?,?)",
//						sess.getToken().Username,sess.getToken().UserId,"Failed to SELECT "+this.name,sess.getToken().IpAddress,"3020",sess.getId(),"TLC-3020-01",this.id);
				return false;
			}
		}
	
	public boolean duplicateEntry(String accountnumber)
	  {
	    DataRow dr = this.db.QueryDataRow("SELECT * FROM TBLUSERSLEVEL WHERE UPPER(USERSLEVEL) = UPPER(?) AND ACCOUNTNUMBER = ?",this.name,accountnumber);
	   return dr.size()>0;  
	  }
	
	public boolean duplicateEntry()
	  {
	    DataRow dr = this.db.QueryDataRow("SELECT * FROM TBLUSERSLEVEL WHERE UPPER(USERSLEVEL) = UPPER(?)",this.name);
	   return dr.size()>0;  
	  }
	
	public boolean duplicateWithOtherInterface(String guiinterface)
	  {
	    DataRow dr = this.db.QueryDataRow("SELECT * FROM TBLUSERSLEVEL WHERE UPPER(USERSLEVEL) = UPPER(?) AND UPPER(GUIINTERFACE) = UPPER(?)",this.name,guiinterface);
	   return dr.size()>0;  
	  }
	
	public boolean duplicateWithOtherDealer(String accountnumber)
	  {
	    DataRow dr = this.db.QueryDataRow("SELECT * FROM TBLUSERSLEVEL WHERE UPPER(USERSLEVEL) = UPPER(?) AND ACCOUNTNUMBER <> ?",this.name,accountnumber);
	   return dr.size()>0;  
	  }
	

	@Override
	public boolean deactivate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update() {
		
		UISession sess = this.getAuthorizedSession();
		
		Integer[] modArr = this.getModules();
		Integer[] notifArr = this.getNotifications();
		JSONArray modJsonArr = new JSONArray();
		JSONArray notifJsonArr = new JSONArray();
		StringBuilder modulesBuilder = new StringBuilder();
		StringBuilder notifBuilder = new StringBuilder();
		String modules = "INSERT INTO TBLGROUPMODULESVITAS (MODULEID, GROUPID) VALUES";
		String notifications = "INSERT INTO TBLNOTIFICATIONMGR (MODULEID, GROUPID) VALUES";
		

		try{
			for(Integer m : modArr){
				modJsonArr.add(m);
			}
			
			 for(int i = 0; i < modJsonArr.size() ; i++){
				 modulesBuilder.append(modules);
				 modulesBuilder.append("("+modArr[i]+","+this.id+")");
				 modulesBuilder.append(";\n");
				}
		}
		 catch(Exception e){
			 
		 }

		 try{
			 for(Integer m : notifArr){
				 notifJsonArr.add(m);
				}
				
				 for(int i = 0; i < notifJsonArr.size() ; i++){
					 notifBuilder.append(notifications);
					 notifBuilder.append("("+notifArr[i]+","+this.id+")");
					 notifBuilder.append(";\n");
					}
		 }
		 catch(Exception e){
			 
		 }
			 
		 
		
		 String sql = "BEGIN\n"
				    + "UPDATE TBLUSERSLEVEL SET SESSIONTIMEOUT = ?, PASSWORDEXPIRY = ?, MINPASSWORD = ?, PASSWORDHISTORY = ?,  SEARCHRANGE = ?, ACCOUNTSTATUS = ?, HOMEPAGE = ? WHERE USERSLEVEL = ?;\n"
				    + "DELETE FROM TBLGROUPMODULESVITAS WHERE GROUPID = ?;\n"
				    + "DELETE FROM TBLNOTIFICATIONMGR WHERE GROUPID = ?;\n"
				    + modulesBuilder
				    + notifBuilder
				//    + "INSERT INTO TBLAUDITTRAIL(USERNAME,USERID,LOG,IP,MODULE,SESSIONID,STATUS,ENTITYID) VALUES(?,?,?,?,?,?,?,?);\n"
				    + "END;\n";
		 
		int dr =  this.db.QueryUpdate(sql.toString(),
		this.sessionTimeout,this.passwordExpiry,this.minPassword,this.passwordHistory,this.searchRange,this.accountStatus,this.homepage,this.name,this.id,this.id);
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

	
}
