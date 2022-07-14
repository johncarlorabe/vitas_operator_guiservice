package com.tlc.sky.pingen.c;


import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import com.tlc.common.Logger;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;
import com.tlc.sky.pingen.m.AuditTrail;
import com.tlc.sky.pingen.m.RegisteredPingenGroup;
import com.tlc.sky.pingen.v.JsonView;

public class UpdateGroupCommand extends UICommand{

	@Override
	public IView execute() {
	  ExistingSession sess = null;
	    
	    SessionView v = null;
	    
	    try{
	     
	     
	    sess = ExistingSession.parse(this.reqHeaders);
	    
	    
	    
  if(sess.exists()) {
		String name = this.params.get("GROUPNAME");
		
		RegisteredPingenGroup pingenGroup = new RegisteredPingenGroup(name);
		pingenGroup.setAuthorizedSession(sess);
		
		String status = this.params.get("ACCOUNTSTATUS");
		int groupId = (Integer.parseInt(this.params.get("GROUPID")));
		int sessionTimeout = (Integer.parseInt(this.params.get("SESSIONTIMEOUT")));
		int passwordExpiry = (Integer.parseInt(this.params.get("PASSWORDEXPIRY")));
		int minPassword = (Integer.parseInt(this.params.get("MINPASSWORD")));
		int passwordHistory = (Integer.parseInt(this.params.get("PASSWORDHISTORY")));
		int searchRange = (Integer.parseInt(this.params.get("SEARCHRANGE")));
		String homepage = this.params.get("HOMEPAGE");
		
		try {
			JSONParser modParser = new JSONParser();
			JSONParser notifParser = new JSONParser();
			JSONArray modules,notifications;
			
			try{
				if(this.params.get("MODULE")!=null){
					modules = (JSONArray) modParser.parse(this.params.get("MODULE"));
					Integer[] arrModules = new Integer[modules.size()];
					int x=0;
						for (Object m : modules){
							Logger.LogServer(m.toString());
							Integer i = Integer.parseInt(m.toString());
							arrModules[x++]=i;
						}
					pingenGroup.setModules(arrModules);
				}
			}
			catch(Exception e){
				Logger.LogServer("NO MODULE SELECTED\n"+e);
			}
			
			try{
				if(this.params.get("NOTIFICATIONS")!=null){
					notifications = (JSONArray) notifParser.parse(this.params.get("NOTIFICATIONS"));
					Integer[] arrNotifications = new Integer[notifications.size()];
					int n=0;
						for (Object m : notifications){
							Logger.LogServer(m.toString());
							Integer i = Integer.parseInt(m.toString());
							arrNotifications[n++]=i;
						}
					pingenGroup.setNotifications(arrNotifications);
				}
			}
			catch(Exception e){
				Logger.LogServer("NO NOTIFICATION SELECTED\n"+e);
			}
				
				pingenGroup.setAccountStatus(status);
				pingenGroup.setSessionTimeout(sessionTimeout);
				pingenGroup.setPasswordExpiry(passwordExpiry);
				pingenGroup.setMinPassword(minPassword);
				pingenGroup.setPasswordHistory(passwordHistory);
				pingenGroup.setSearchRange(searchRange);
				pingenGroup.setId(groupId);
				pingenGroup.setHomePage(homepage);
				
				
				if(pingenGroup.update()){
					ObjectState state = new ObjectState("00","Role Successfully Updated");
					pingenGroup.setState(state);
					AuditTrail audit  = new AuditTrail();
		    		audit.setIp(pingenGroup.getAuthorizedSession().getIpAddress());
		    		audit.setModuleid(String.valueOf(this.getId()));
		    		audit.setEntityid(name);
		    		audit.setLog(pingenGroup.getState().getMessage());
		    		audit.setStatus(pingenGroup.getState().getCode());
		    		audit.setUserid(pingenGroup.getAuthorizedSession().getAccount().getId());
		    		audit.setUsername(pingenGroup.getAuthorizedSession().getAccount().getUserName());
		    		
		    		audit.insert();
					JsonView view = new JsonView(pingenGroup);
					return view;
				}
				else{
					ObjectState state = new ObjectState("01","Add Unsuccessfull");
					pingenGroup.setState(state);
					AuditTrail audit  = new AuditTrail();
		    		audit.setIp(pingenGroup.getAuthorizedSession().getIpAddress());
		    		audit.setModuleid(String.valueOf(this.getId()));
		    		audit.setEntityid(name);
		    		audit.setLog(pingenGroup.getState().getMessage());
		    		audit.setStatus(pingenGroup.getState().getCode());
		    		audit.setUserid(pingenGroup.getAuthorizedSession().getAccount().getId());
		    		audit.setUsername(pingenGroup.getAuthorizedSession().getAccount().getUserName());
		    		
		    		audit.insert();
					JsonView view = new JsonView(pingenGroup);
					return view;
				}
		}catch(Exception e){
			Logger.LogServer(e);
			ObjectState state = new ObjectState("02","Add Unsuccessfull");
			pingenGroup.setState(state);
			AuditTrail audit  = new AuditTrail();
    		audit.setIp(pingenGroup.getAuthorizedSession().getIpAddress());
    		audit.setModuleid(String.valueOf(this.getId()));
    		audit.setEntityid(name);
    		audit.setLog(pingenGroup.getState().getMessage());
    		audit.setStatus(pingenGroup.getState().getCode());
    		audit.setUserid(pingenGroup.getAuthorizedSession().getAccount().getId());
    		audit.setUsername(pingenGroup.getAuthorizedSession().getAccount().getUserName());
    		
    		audit.insert();
			JsonView view = new JsonView(pingenGroup);
			return view;
			
		}
  }else{
      UISession u = new UISession(null);
   u.setState(new ObjectState("TLC-3902-01"));
   v = new SessionView(u);
    
 }
 
}catch (SessionNotFoundException e) {
  UISession u = new UISession(null);
  u.setState(new ObjectState("TLC-3902-01"));
  v = new SessionView(u);
 
}
return v;
			
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return "UPDATEROLE";
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 3040;
	}




}
