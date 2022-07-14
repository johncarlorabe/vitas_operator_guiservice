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

public class ActivateAccountCommand extends UICommand {

	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;
		
		try {
			sess = ExistingSession.parse(this.reqHeaders);		
			if(sess.exists()) {
				String userid = this.params.get("UserId").toString();
				String status = this.params.get("Status").toString();
				String stat = "";
				if(status.equalsIgnoreCase("ACTIVE")){
					stat = "Activated";
				} else {
					stat = "Deactivated";
				}
				ManageAccount mgtacct = new ManageAccount();
				mgtacct.setUserid(userid);
				mgtacct.setStatus(status);
				mgtacct.setAuthorizedSession(sess);
				if(mgtacct.activate()){
					mgtacct.setState(new ObjectState("00", "Account Successfully "+stat));
					AuditTrail audit  = new AuditTrail();
		    		audit.setIp(mgtacct.getAuthorizedSession().getIpAddress());
		    		audit.setModuleid(String.valueOf(this.getId()));
		    		audit.setEntityid(userid+"|"+status);
		    		audit.setLog(mgtacct.getState().getMessage());
		    		audit.setStatus(mgtacct.getState().getCode());
		    		audit.setUserid(mgtacct.getAuthorizedSession().getAccount().getId());
		    		audit.setUsername(mgtacct.getAuthorizedSession().getAccount().getUserName());
		    		
		    		audit.insert();
					return new JsonView(mgtacct);  
				} else {
					mgtacct.setState(new ObjectState("99", "System busy"));
					AuditTrail audit  = new AuditTrail();
		    		audit.setIp(mgtacct.getAuthorizedSession().getIpAddress());
		    		audit.setModuleid(String.valueOf(this.getId()));
		    		audit.setEntityid(userid+"|"+status);
		    		audit.setLog(mgtacct.getState().getMessage());
		    		audit.setStatus(mgtacct.getState().getCode());
		    		audit.setUserid(mgtacct.getAuthorizedSession().getAccount().getId());
		    		audit.setUsername(mgtacct.getAuthorizedSession().getAccount().getUserName());
		    		
		    		audit.insert();
					return new JsonView(mgtacct);  
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
		return "ACTIVATEUSERACCOUNT";
	}

	@Override
	public int getId() {
		return 9609;
	}

}
