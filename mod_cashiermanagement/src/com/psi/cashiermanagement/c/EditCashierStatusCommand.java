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

public class EditCashierStatusCommand extends UICommand{

	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;
		
		try {
			sess = ExistingSession.parse(this.reqHeaders);		
			if(sess.exists()) {
				String id = this.params.get("Id").toString();
				String status = this.params.get("Status").toString();
					 	
				ManageRegisteredUser reg = new ManageRegisteredUser();
				reg.setStatus(status);
				reg.setId(id);
			    reg.setAuthorizedSession(sess);
				
				if(!reg.exist()){
					reg.setState(new ObjectState("01", "Account do not exists"));
					AuditTrail audit  = new AuditTrail();
		    		audit.setIp(reg.getAuthorizedSession().getIpAddress());
		    		audit.setModuleid(String.valueOf(this.getId()));
		    		audit.setEntityid(id);
		    		audit.setLog("Account do not exist: "+status);
		    		audit.setStatus(reg.getState().getCode());
		    		audit.setUserid(reg.getAuthorizedSession().getAccount().getId());
		    		audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
		    		
		    		audit.insert();
					return new JsonView(reg);  
				}
					if(reg.changestatus()){
						reg.setState(new ObjectState("00", "Successfully change account status"));
						AuditTrail audit  = new AuditTrail();
			    		audit.setIp(reg.getAuthorizedSession().getIpAddress());
			    		audit.setModuleid(String.valueOf(this.getId()));
			    		audit.setEntityid(id);
			    		audit.setLog("Successfully change account status: "+status);
			    		audit.setStatus(reg.getState().getCode());
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
			    		audit.setLog("System is currently busy : "+status);
			    		audit.setStatus(reg.getState().getCode());
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
	public String getKey() {
		// TODO Auto-generated method stub
		return "EDITCASHIERSTATUS";
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 7610;
	}

}
