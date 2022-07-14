package com.psi.serviceconfig.c;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import com.psi.serviceconfig.m.EnableDisableServices;
import com.psi.serviceconfig.util.AuditTrail;
import com.psi.serviceconfig.v.JsonView;
import com.tlc.common.Logger;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class EnableDisableCommand extends UICommand{

	@Override
	public IView execute() {
		  ExistingSession sess = null;
	    
	    SessionView v = null;
	    
	    try{
	     
	     
	    sess = ExistingSession.parse(this.reqHeaders);
	    
	    
	    
  if(sess.exists()) {
		String accountnumber = this.params.get("AccountNumber");
		String category = this.params.get("Category");
		
		EnableDisableServices changestat = new EnableDisableServices();
		changestat.setAuthorizedSession(sess);
		
		try {
			JSONParser addserviceParser = new JSONParser();
			JSONArray addservices;
			try{
				if(this.params.get("MODULE")!=null){
					addservices = (JSONArray) addserviceParser.parse(this.params.get("MODULE"));
					Integer[] arrServices = new Integer[addservices.size()];
					int x=0;
						for (Object m : addservices){
							Logger.LogServer(m.toString());
							Integer i = Integer.parseInt(m.toString());
							arrServices[x++]=i;
						}
						changestat.setAddservices(arrServices);
				}
			}
			catch(Exception e){
				Logger.LogServer("NO ADD SERVICES SELECTED\n"+e);
			}
			
			
			changestat.setAccountnumber(accountnumber);
			changestat.setCategory(category);	
				
				if(changestat.change()){
					ObjectState state = new ObjectState("00","Services Successfully Added");
					changestat.setState(state);
					AuditTrail audit  = new AuditTrail();
		    		audit.setIp(changestat.getAuthorizedSession().getIpAddress());
		    		audit.setModuleid(String.valueOf(this.getId()));
		    		audit.setEntityid(accountnumber);
		    		audit.setLog(changestat.getState().getMessage());
		    		audit.setStatus(changestat.getState().getCode());
		    		audit.setUserid(changestat.getAuthorizedSession().getAccount().getId());
		    		audit.setUsername(changestat.getAuthorizedSession().getAccount().getUserName());
		    		
		    		audit.insert();
					JsonView view = new JsonView(changestat);
					return view;
				}
				else{
					ObjectState state = new ObjectState("01","Add Unsuccessfull");
					changestat.setState(state);
					JsonView view = new JsonView(changestat);
					return view;
				}
		}catch(Exception e){
			Logger.LogServer(e);
			ObjectState state = new ObjectState("02","Add Unsuccessfull");
			changestat.setState(state);
			JsonView view = new JsonView(changestat);
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
		return "CHANGESTATUSSERVICES";
	}

	@Override
	public int getId() {
		return 6000;
	}

}
