package com.psi.purchases.c;

import java.text.ParseException;

import com.psi.purchases.m.Balance;
import com.psi.purchases.util.AuditTrail;
import com.psi.purchases.v.JsonView;
import com.tlc.common.Logger;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class BalanceInqCardNumberCommand extends UICommand{

	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;
		
		try {
			sess = ExistingSession.parse(this.reqHeaders);		
			if(sess.exists()) {
		
				String cardnumber = this.params.get("CARDNUMBER").toString();

				Balance reg = new Balance();
				reg.setAuthorizedSession(sess);
				
				try {
					if(reg.balanceCardNumber(cardnumber)){
						AuditTrail audit  = new AuditTrail();
			    		audit.setIp(reg.getAuthorizedSession().getIpAddress());
			    		audit.setModuleid(String.valueOf(this.getId()));
			    		audit.setEntityid("");
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
			    		audit.setEntityid("");
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
		return "BALNCARDNUMBER";
	}

	@Override
	public int getId() {
		return 1000;
	}

}
