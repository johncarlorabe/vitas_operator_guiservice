package com.psi.purchases.c;

import java.text.ParseException;

import com.psi.purchases.m.Purchases;
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

public class PurchaseCommand extends UICommand{

	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;
		
		try {
			sess = ExistingSession.parse(this.reqHeaders);		
			if(sess.exists()) {
				String msisdn = this.params.get("MSISDN").toString();
				String amount = this.params.get("Amount").toString();
				String brand = this.params.get("Brand").toString();
				String reference = this.params.get("Reference").toString();
				String id = this.params.get("Id").toString();
				String isoverride = this.params.get("IsOverride").toString();
				String macaddress = this.params.get("MacAddress").toString();
				
				Purchases reg = new Purchases();
				
				reg.setMacaddress(macaddress);
				reg.setMsisdn(msisdn);
				reg.setAmount(amount);
				reg.setBrand(brand);
				reg.setReference(reference);
				reg.setId(id);
				reg.setIsoverride(isoverride);
				reg.setAuthorizedSession(sess);
				if(isoverride.equals("1")){
					String overrideusername = this.params.get("OverrideUserName").toString();
					String overridepassword = this.params.get("OverridePassword").toString();
					
					reg.setOverrideusername(overrideusername);
					reg.setOverridepassword(overridepassword);
					
					if(!reg.validateoverride()){
						reg.setState(new ObjectState("20","Invalid password/username"));
						AuditTrail audit  = new AuditTrail();
			    		audit.setIp(reg.getAuthorizedSession().getIpAddress());
			    		audit.setModuleid(String.valueOf(this.getId()));
			    		audit.setEntityid(id);
			    		audit.setLog(reg.getState().getMessage());
			    		audit.setStatus(reg.getState().getCode());
			    		audit.setUserid(reg.getAuthorizedSession().getAccount().getId());
			    		audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
			    		
			    		audit.insert();
						return new JsonView(reg);
					}
					
				}

					try {
						if(reg.purchase()){
							reg.setState(new ObjectState(reg.getState().getCode(),reg.getState().getMessage()));
							AuditTrail audit  = new AuditTrail();
				    		audit.setIp(reg.getAuthorizedSession().getIpAddress());
				    		audit.setModuleid(String.valueOf(this.getId()));
				    		audit.setEntityid(id);
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
				    		audit.setEntityid(id);
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
		UISession u = new UISession(null);
	    u.setState(new ObjectState("TLC-3902-01"));
	    v = new SessionView(u);
		Logger.LogServer(e);
	}return v;
	}

	@Override
	public String getKey() {
		return "PURCHASE";
	}

	@Override
	public int getId() {
		return 9000;
	}

}
