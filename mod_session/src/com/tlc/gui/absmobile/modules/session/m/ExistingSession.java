package com.tlc.gui.absmobile.modules.session.m;

import java.util.Date;

import com.tlc.absmobile.bp.session.AbstractExistingSession;
import com.tlc.common.DataRow;
import com.tlc.common.Logger;
import com.tlc.common.StringUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.PluginHeaders;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.Token;
import com.tlc.gui.modules.session.UIAccount;
import com.tlc.gui.modules.session.UIGroup;

public class ExistingSession extends AbstractExistingSession {

	public static ExistingSession parse(PluginHeaders h) throws SessionNotFoundException{
		String auth = h.get("authorization");
		String decoded = new String(StringUtil.base64Decode(auth));
		String contextInfo[] = decoded.split(":");
		String[] authInfo = contextInfo[2].split("@");
		ExistingSession ret = new ExistingSession(contextInfo[1],authInfo[1],Token.parse(authInfo[0]));
		return ret;
	}
	public ExistingSession(String id,String ip,Token t) throws SessionNotFoundException {
		super(id,ip,t);
	}

	@Override
	public boolean exists() {
		boolean ret = false;
		StringBuilder query = new StringBuilder();
		query.append("SELECT WU.USERID,WU.USERNAME,LASTNAME,FIRSTNAME,LASTLOGIN,U.ID AS GROUPID,U.USERSLEVEL AS GROUPNAME,ALLOWEDIP\n" );
		query.append("FROM TBLUSERS WU \n");
		query.append("    INNER JOIN TBLUSERSLEVEL U ON U.USERSLEVEL=WU.USERSLEVEL\n");
		query.append("WHERE USERID=? AND SESSIONID=? AND U.ACCOUNTSTATUS='ACTIVE'\n");
		DataRow r = db.QueryDataRow(query.toString(),this.token.UserId,this.id);
		if(r!=null && !r.isEmpty()){
			this.setAccount(new UIAccount());
			this.getAccount().setId((Integer.parseInt(r.get("USERID").toString())));
			this.getAccount().setUsername(r.get("USERNAME").toString());
			this.getAccount().setLastName(r.get("LASTNAME").toString());
			this.getAccount().setFirstName(r.get("FIRSTNAME").toString());
			this.getAccount().setLastLogin((Date)r.get("LASTLOGIN"));
			this.getAccount().setGroup(new UIGroup(r.get("GROUPNAME").toString()));
			this.getAccount().getGroup().setId(Integer.parseInt(r.get("GROUPID").toString()));
			this.getAccount().setAllowedIp(r.get("ALLOWEDIP").toString());
			this.getAccount().getGroup().setModules(db.QueryArray("SELECT MODULEID FROM TBLGROUPMODULESVITAS WHERE GROUPID=?",new Integer(0),this.getAccount().getGroup().getId()));
			this.getAccount().getGroup().setSessionTimeout(db.QueryScalar("SELECT SESSIONTIMEOUT FROM TBLUSERSLEVEL WHERE USERSLEVEL=?",0,r.getString("GROUPNAME")));
			
			ret = true;
		}
		return ret;
	}
	

	@Override
	public boolean monitor() {
		this.setState(new ObjectState("TLC-3902-01"));
		if(!this.valid()){
			Logger.LogServer("Invalid");
			this.setState(new ObjectState("Invalid"));
			return false;
		}
		if(this.expired()){
			Logger.LogServer("Expired");
			this.setState(new ObjectState("Expired"));
			return false;
		} 
		if(!this.exists()){
			Logger.LogServer("not exist");
			this.setState(new ObjectState("not exists"));
			return false;
		}
	
		Logger.LogServer("session extend:"+this.getAccount().getGroup().getSessionTimeout());
		this.token.extend(this.getAccount().getGroup().getSessionTimeout());
		this.removeProperty(PROP_ACCOUNT);
		this.setState(new ObjectState("00"));
		return true;
	}
	
	

	@Override
	public boolean terminate() {
		this.setState(new ObjectState("TLC-3903-01"));
		if(this.valid() && this.exists()){
			StringBuilder query = new StringBuilder();
			
			query.append("DECLARE\n");
			query.append("	vMODULEID 				NUMBER;\n");
			query.append("	vUSERNAME				VARCHAR(50);\n");
			query.append("	vUSERID 				NUMBER;\n");
			query.append("	vSESSIONID				VARCHAR(50);\n");
			query.append("	vIPADDRESS				VARCHAR(50);\n");
			query.append("BEGIN\n");
			query.append("	vMODULEID	:=3903;\n");
			query.append("	vUSERID	:=?;\n");
			query.append("	vUSERNAME	:=?;\n");
			query.append("	vSESSIONID	:=?;\n");
			query.append("	vIPADDRESS	:=?;\n");
			query.append("	UPDATE TBLUSERS SET SESSIONID=NULL,LASTLOGIN=SYSDATE WHERE USERID=vUSERID;\n");
			query.append("	INSERT INTO TBLAUDITTRAIL(USERNAME,USERID,SESSIONID,MODULE,IP,STATUS) VALUES(vUSERNAME,vUSERID,vSESSIONID,vMODULEID,vIPADDRESS,'00');\n");
			query.append(SQL_FOOTER);
			if(this.db.QueryUpdate(query.toString(), this.getAccount().getId(),this.getAccount().getUserName(),this.id,this.token.IpAddress)>0){
				this.setState(new ObjectState("00"));
				return true;	
			}
		}
		return false;
	}

}
