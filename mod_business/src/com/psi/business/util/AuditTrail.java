package com.psi.business.util;

import com.tlc.common.DbWrapper;
import com.tlc.common.SystemInfo;

public class AuditTrail {
	
	private String username;
	private String ip;
	private String log;
	private String sessionid;
	private String status;
	private String entityid;
	private Integer userid;
	private String moduleid;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getLog() {
		return log;
	}
	public void setLog(String log) {
		this.log = log;
	}
	public String getSessionid() {
		return sessionid;
	}
	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEntityid() {
		return entityid;
	}
	public void setEntityid(String entityid) {
		this.entityid = entityid;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public String getModuleid() {
		return moduleid;
	}
	public void setModuleid(String moduleid) {
		this.moduleid = moduleid;
	}
	
	
	public boolean insert(){
		
		try{
		DbWrapper db = SystemInfo.getDb();
		
	int	res = db.QueryUpdate("INSERT INTO TBLAUDITTRAIL (USERNAME,USERID,LOG,IP,TIMESTAMP,MODULE,SESSIONID,STATUS,ENTITYID) VALUES(?,?,?,?,CURRENT_TIMESTAMP,?,?,?,?)",
				
										this.username,
										this.userid,
										this.log,
										this.ip,
										this.moduleid,
										this.sessionid,
										this.status,
										this.entityid);
	
		return res>0;
		}catch(NullPointerException e){
			
			
		}catch(Exception e){
			
		}
		return false;
		
		
	}

}
