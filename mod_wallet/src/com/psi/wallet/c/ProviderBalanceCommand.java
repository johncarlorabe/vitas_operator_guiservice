package com.psi.wallet.c;

import java.text.ParseException;

import com.psi.wallet.m.ProviderBalance;
import com.psi.wallet.util.AuditTrail;
import com.psi.wallet.v.JsonView;
import com.tlc.common.Logger;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class ProviderBalanceCommand extends UICommand{

	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;
		
		try {
			sess = ExistingSession.parse(this.reqHeaders);		
			if(sess.exists()) {
		
				String accountnumber = this.params.get("AccountNumber");

				ProviderBalance reg = new ProviderBalance();
				reg.setAuthorizedSession(sess);
				
				try {
						if(reg.balance(accountnumber)){
							AuditTrail audit  = new AuditTrail();
				    		audit.setIp(reg.getAuthorizedSession().getIpAddress());
				    		audit.setModuleid(String.valueOf(this.getId()));
				    		audit.setEntityid(accountnumber);
				    		audit.setLog(reg.getState().getMessage());
				    		audit.setStatus(reg.getState().getCode());
				    		audit.setUserid(reg.getAuthorizedSession().getAccount().getId());
				    		audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
				    		
				    		audit.insert();
							return new JsonView(reg); 
						}else{
							AuditTrail audit  = new AuditTrail();
				    		audit.setIp(reg.getAuthorizedSession().getIpAddress());
				    		audit.setModuleid(String.valueOf(this.getId()));
				    		audit.setEntityid(accountnumber);
				    		audit.setLog(reg.getState().getMessage());
				    		audit.setStatus(reg.getState().getCode());
				    		audit.setUserid(reg.getAuthorizedSession().getAccount().getId());
				    		audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
				    		
				    		audit.insert();
							return new JsonView(reg); 
						}
					} catch (ParseException e) {
						e.printStackTrace();
						reg.setState(new ObjectState("99", "System busy"));
						return new JsonView(reg); 
						
					} catch (Exception e) {
						reg.setState(new ObjectState("99", "System busy"));
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
		return "PROVIDERBALN";
	}

	@Override
	public int getId() {
		return 9502;
	}

}
