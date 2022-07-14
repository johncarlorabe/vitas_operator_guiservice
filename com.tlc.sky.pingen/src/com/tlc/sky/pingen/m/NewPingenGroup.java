package com.tlc.sky.pingen.m;


import com.tlc.gui.modules.session.UISession;
import com.tlc.sky.pingen.arch.uc.acct.AbstractNewPingenGroup;


public class NewPingenGroup extends AbstractNewPingenGroup{

	public NewPingenGroup(String name) {
		super(name);
	}

	@Override
	public boolean register() {
		UISession sess = this.getAuthorizedSession();
		String nextID = null;
		
		nextID = this.db.QueryScalar("select TBLUSERSLEVEL_SEQ.nextval from dual","0");
		
		String sql = "BEGIN\n"
			    + "INSERT INTO TBLUSERSLEVEL (ID,USERSLEVEL,SESSIONTIMEOUT,PASSWORDEXPIRY,MINPASSWORD,PASSWORDHISTORY,MAXALLOCUSER,SEARCHRANGE,ACCOUNTSTATUS,HOMEPAGE,ISOPERATOR,GUIINTERFACE) VALUES (?,?,?,?,?,?,?,?,?,?,0,?);\n"
			   // + "INSERT INTO TBLAUDITTRAIL(USERNAME,USERID,LOG,IP,MODULE,SESSIONID,STATUS,ENTITYID) VALUES(?,?,?,?,?,?,?,?);\n"
			    + "END;\n";
		int dr =  this.db.QueryUpdate(sql.toString(),
												nextID,this.name,this.sessionTimeout,this.passwordExpiry,this.minPassword,this.passwordHistory,this.maxAllocactionPerDay,this.searchRange,"ACTIVE",this.homepage,this.guiinterface);
												//sess.getToken().Username,sess.getToken().UserId,"New UserLevel Added",sess.getToken().IpAddress,"3050",sess.getId(),"TLC-3050-00",nextID
		if (dr>0){
			return true;
		}
		
		else{
//			this.db.QueryUpdate("INSERT INTO TBLAUDITTRAIL(USERNAME,USERID,LOG,IP,MODULE,SESSIONID,STATUS,ENTITYID) VALUES(?,?,?,?,?,?,?,?)",
//					sess.getToken().Username,sess.getToken().UserId,"UserLevel Registration Failed",sess.getToken().IpAddress,"3050",sess.getId(),"TLC-3050-01",nextID);
			return false;
		}
	}



}
