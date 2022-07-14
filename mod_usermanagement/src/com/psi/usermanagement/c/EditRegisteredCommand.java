package com.psi.usermanagement.c;

import com.psi.usermanagement.m.ManageRegisteredUser;
import com.psi.usermanagement.util.AuditTrail;
import com.psi.usermanagement.v.JsonView;
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
				String email = this.params.get("Email").toString();
				String firstname = this.params.get("FirstName").toString();
				String lastname = this.params.get("LastName").toString();
				String msisdn = this.params.get("MSISDN").toString();
				String id = this.params.get("Id").toString();
					 	
				ManageRegisteredUser reg = new ManageRegisteredUser();
						
				reg.setEmail(email);
				reg.setFirstname(firstname);
				reg.setLastname(lastname);
				reg.setMsisdn(msisdn);
				reg.setId(id);
			    reg.setAuthorizedSession(sess);
				
				if(reg.exist()){
					if(!reg.isEmailExist()){
						if(reg.update()){
							reg.setState(new ObjectState("00", "Updated Succesfully"));
						}else{
							reg.setState(new ObjectState("99", "System busy"));
						}
					}else{
						reg.setState(new ObjectState("PSI-02", "Email address already exist"));
					}
				}else{
					reg.setState(new ObjectState("PSI-01", "Account do not exist"));
				}
				AuditTrail audit  = new AuditTrail();
	    		audit.setIp(reg.getAuthorizedSession().getIpAddress());
	    		audit.setModuleid(String.valueOf(this.getId()));
	    		audit.setEntityid(id);
	    		audit.setLog(reg.getState().getMessage()+"|"+reg.getUsername()+"|"+reg.getFirstname()+"|"+reg.getLastname()+"|"+reg.getEmail()+"|"+reg.getMsisdn());
	    		audit.setStatus(reg.getState().getCode());
	    		audit.setUserid(reg.getAuthorizedSession().getAccount().getId());
	    		audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
	    		
	    		audit.insert();
				return new JsonView(reg); 
				
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
		return 7010;
	}

	@Override
	public String getKey() {
		return "EDITUSER";
	}

}
