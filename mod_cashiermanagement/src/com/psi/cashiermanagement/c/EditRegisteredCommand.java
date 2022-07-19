package com.psi.cashiermanagement.c;

import com.psi.cashiermanagement.m.ManageRegisteredUser;
import com.psi.cashiermanagement.util.AuditTrail;
import com.psi.cashiermanagement.v.JsonView;
import com.tlc.common.Logger;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class EditRegisteredCommand extends UICommand{

	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;
		
		try {
			sess = ExistingSession.parse(this.reqHeaders);		
			if(sess.exists()) {
				String firstname = this.params.get("FirstName").toString();
				String lastname = this.params.get("LastName").toString();
				String id = this.params.get("Id").toString();
					 	
				ManageRegisteredUser reg = new ManageRegisteredUser();
						
				reg.setFirstname(firstname);
				reg.setLastname(lastname);
				reg.setId(id);
			    reg.setAuthorizedSession(sess);
			
			    
				if(reg.existStatus()){
					if(reg.update()){
						reg.setState(new ObjectState("00", "Updated Succesfully"));
						AuditTrail audit  = new AuditTrail();
			    		audit.setIp(reg.getAuthorizedSession().getIpAddress());
			    		audit.setModuleid(String.valueOf(this.getId()));
			    		audit.setEntityid(id);
			    		audit.setLog("Edited successfully"+"|"+reg.getFirstname()+"|"+reg.getLastname());
			    		audit.setStatus("00");
			    		audit.setUserid(reg.getAuthorizedSession().getAccount().getId());
			    		audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
			    		
			    		audit.insert();
			    		return new JsonView(reg);
					}else{
						reg.setState(new ObjectState("99", "System busy"));
						AuditTrail audit  = new AuditTrail();
			    		audit.setIp(reg.getAuthorizedSession().getIpAddress());
			    		audit.setModuleid(String.valueOf(this.getId()));
			    		audit.setEntityid(id);
			    		audit.setLog("System is currently busy, please try again: "+"|"+reg.getFirstname()+"|"+reg.getLastname());
			    		audit.setStatus("99");
			    		audit.setUserid(reg.getAuthorizedSession().getAccount().getId());
			    		audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
			    		
			    		audit.insert();
						return new JsonView(reg);
					}
				}else{
					reg.setState(new ObjectState("PSI-01", "Account do not exist"));
					AuditTrail audit  = new AuditTrail();
		    		audit.setIp(reg.getAuthorizedSession().getIpAddress());
		    		audit.setModuleid(String.valueOf(this.getId()));
		    		audit.setEntityid(id);
		    		audit.setLog("Account do not exist: "+"|"+reg.getFirstname()+"|"+reg.getLastname());
		    		audit.setStatus("01");
		    		audit.setUserid(reg.getAuthorizedSession().getAccount().getId());
		    		audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
		    		
		    		audit.insert();
					return new JsonView(reg);
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
			Logger.LogServer(e);
	} catch (Exception e) {
		Logger.LogServer(e);
	}
		return v;
	}

	@Override
	public int getId() {
		return 7620;
	}

	@Override
	public String getKey() {
		return "EDITCASHIERDETAILS";
	}

}
