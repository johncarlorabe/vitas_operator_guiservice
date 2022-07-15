package com.psi.wallet.c;

import java.text.NumberFormat;
import java.util.Locale;

import com.psi.wallet.m.CardCashin;
import com.psi.wallet.m.CashierCashin;
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

public class CashierCashinCommand extends UICommand{

	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;
		
		try {
			sess = ExistingSession.parse(this.reqHeaders);		
			if(sess.exists()) {
		
				String id = this.params.get("LinkId");
				NumberFormat format = NumberFormat.getInstance(Locale.US);

		        Number amount = null;
				try {
					amount = format.parse(this.params.get("Amount"));
				} catch (java.text.ParseException e) {
					e.printStackTrace();
				}
				String password = this.params.get("Password");
				String accountnumber = this.params.get("AccountNumber");
				
				
				CashierCashin topup = new CashierCashin();
				topup.setAmount(amount.toString());
				topup.setPassword(password);
				topup.setAccountnumber(accountnumber);
				topup.setId(id);
				topup.setReferencenumber("");
				topup.setDescription("CASHIER CASH IN");
				topup.setAuthorizedSession(sess);
				
						if(!topup.validate()){
							topup.setState(new ObjectState("01", "Invalid Password"));
							AuditTrail audit  = new AuditTrail();
				    		audit.setIp(topup.getAuthorizedSession().getIpAddress());
				    		audit.setModuleid(String.valueOf(this.getId()));
				    		audit.setEntityid(id);
				    		audit.setLog(topup.getState().getMessage());
				    		audit.setStatus(topup.getState().getCode());
				    		audit.setUserid(topup.getAuthorizedSession().getAccount().getId());
				    		audit.setUsername(topup.getAuthorizedSession().getAccount().getUserName());
				    		
				    		audit.insert();
							return new JsonView(topup); 
						}

					
						try {
							if(topup.topup()){
								topup.setState(new ObjectState("00", "Success"));
								AuditTrail audit  = new AuditTrail();
					    		audit.setIp(topup.getAuthorizedSession().getIpAddress());
					    		audit.setModuleid(String.valueOf(this.getId()));
					    		audit.setEntityid(id);
					    		audit.setLog(topup.getState().getMessage());
					    		audit.setStatus(topup.getState().getCode());
					    		audit.setUserid(topup.getAuthorizedSession().getAccount().getId());
					    		audit.setUsername(topup.getAuthorizedSession().getAccount().getUserName());
					    		
					    		audit.insert();
								return new JsonView(topup); 

							}else{
								AuditTrail audit  = new AuditTrail();
					    		audit.setIp(topup.getAuthorizedSession().getIpAddress());
					    		audit.setModuleid(String.valueOf(this.getId()));
					    		audit.setEntityid(id);
					    		audit.setLog(topup.getState().getMessage());
					    		audit.setStatus(topup.getState().getCode());
					    		audit.setUserid(topup.getAuthorizedSession().getAccount().getId());
					    		audit.setUsername(topup.getAuthorizedSession().getAccount().getUserName());
					    		
					    		audit.insert();
								return new JsonView(topup); 
							}
						} catch (Exception e) {
							topup.setState(new ObjectState("99", "System busy"));
							return new JsonView(topup); 
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
		// TODO Auto-generated method stub
		return "CASHIERCASHIN";
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 7900;
	}

}
