package com.psi.accountmanagement.c;

import com.psi.accountmanagement.m.ManageAccount;
import com.psi.accountmanagement.utils.AuditTrail;
import com.psi.accountmanagement.v.JsonView;
import com.tlc.common.Logger;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class LockAccountCommand extends UICommand {

	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;
		
		try {
			sess = ExistingSession.parse(this.reqHeaders);		
			if(sess.exists()) {
				String userid = this.params.get("UserId").toString();
				String locked = this.params.get("Locked").toString();
				String stat = "";
				if(locked.equalsIgnoreCase("YES")){
					stat = "Locked";
				} else {
					stat = "Unlocked";
				}
				ManageAccount manageacct = new ManageAccount();
				manageacct.setUserid(userid);
				manageacct.setIsLocked(locked);
				manageacct.setAuthorizedSession(sess);
				if(manageacct.update()){
					manageacct.setState(new ObjectState("00", "Account Successfully "+stat));
					AuditTrail audit  = new AuditTrail();
		    		audit.setIp(manageacct.getAuthorizedSession().getIpAddress());
		    		audit.setModuleid(String.valueOf(this.getId()));
		    		audit.setEntityid(userid+"|"+locked);
		    		audit.setLog(manageacct.getState().getMessage());
		    		audit.setStatus(manageacct.getState().getCode());
		    		audit.setUserid(manageacct.getAuthorizedSession().getAccount().getId());
		    		audit.setUsername(manageacct.getAuthorizedSession().getAccount().getUserName());
		    		
		    		audit.insert();
					return new JsonView(manageacct);  
				}else{
					manageacct.setState(new ObjectState("99", "System busy"));
					AuditTrail audit  = new AuditTrail();
		    		audit.setIp(manageacct.getAuthorizedSession().getIpAddress());
		    		audit.setModuleid(String.valueOf(this.getId()));
		    		audit.setEntityid(userid+"|"+locked);
		    		audit.setLog(manageacct.getState().getMessage());
		    		audit.setStatus(manageacct.getState().getCode());
		    		audit.setUserid(manageacct.getAuthorizedSession().getAccount().getId());
		    		audit.setUsername(manageacct.getAuthorizedSession().getAccount().getUserName());
		    		
		    		audit.insert();
					return new JsonView(manageacct);  
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
UISession u = new UISession(null);
u.setState(new ObjectState("TLC-3902-01"));
v = new SessionView(u);
Logger.LogServer(e);
}return v;
}

	@Override
	public String getKey() {
		return "MANAGEUSERACCOUNT";
	}

	@Override
	public int getId() {
		
		return 9610;
	}
		
}
