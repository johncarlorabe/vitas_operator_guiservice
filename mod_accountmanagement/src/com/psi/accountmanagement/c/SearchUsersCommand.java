package com.psi.accountmanagement.c;

import com.psi.accountmanagement.m.UserAccountCollection;
import com.psi.accountmanagement.utils.AuditTrail;
import com.psi.accountmanagement.v.CollectionView;
import com.psi.accountmanagement.v.NoDataFoundView;
import com.tlc.common.Logger;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class SearchUsersCommand extends UICommand{

	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;
		
		try {
			sess = ExistingSession.parse(this.reqHeaders);		
			if(sess.exists()) {
				String username = this.params.get("USERNAME").toString();
				String userslevel = this.params.get("USERSLEVEL").toString();
				
				UserAccountCollection useraccounts = new UserAccountCollection();
				useraccounts.setUsername(username);
				useraccounts.setUserslevel(userslevel);
				useraccounts.setAuthorizedSession(sess);
				if(useraccounts.hasRows()){
					AuditTrail audit  = new AuditTrail();
		    		audit.setIp(useraccounts.getAuthorizedSession().getIpAddress());
		    		audit.setModuleid(String.valueOf(this.getId()));
		    		audit.setEntityid(userslevel+"|"+username);
		    		audit.setLog("Successfully fetched user data");
		    		audit.setStatus("00");
		    		audit.setUserid(useraccounts.getAuthorizedSession().getAccount().getId());
		    		audit.setUsername(useraccounts.getAuthorizedSession().getAccount().getUserName());
		    		
		    		audit.insert();
					return new CollectionView("00",useraccounts);
				} else {
					ObjectState state = new ObjectState("01","No user Data Found");
					AuditTrail audit  = new AuditTrail();
		    		audit.setIp(useraccounts.getAuthorizedSession().getIpAddress());
		    		audit.setModuleid(String.valueOf(this.getId()));
		    		audit.setEntityid(userslevel+"|"+username);
		    		audit.setLog("No user data found");
		    		audit.setStatus("01");
		    		audit.setUserid(useraccounts.getAuthorizedSession().getAccount().getId());
		    		audit.setUsername(useraccounts.getAuthorizedSession().getAccount().getUserName());
		    		
		    		audit.insert();
					return new NoDataFoundView(state);
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
		return "USERACCOUNTCOLLECTION";
	}

	@Override
	public int getId() {
		return 9606;
	}
}
