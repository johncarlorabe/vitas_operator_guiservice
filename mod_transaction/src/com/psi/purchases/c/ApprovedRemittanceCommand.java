package com.psi.purchases.c;

import com.psi.purchases.m.Remittance;
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

public class ApprovedRemittanceCommand extends UICommand{

	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;
		
		try {
			sess = ExistingSession.parse(this.reqHeaders);		
			if(sess.exists()) {
				String ordernumber = this.params.get("OrderNumber").toString();
				String trackingnumber = this.params.get("TrackingNumber").toString();
				String id = this.params.get("Id");
				String accountnumber = this.params.get("AccountNumber");
				String isoverride = this.params.get("IsOverride").toString();
				String macaddress = this.params.get("MacAddress").toString();
				
				Remittance reg = new Remittance();
				
				reg.setMacaddress(macaddress);
				reg.setOrdernumber(ordernumber);
				reg.setTrackingnumber(trackingnumber);
				reg.setLinkid(id);
				reg.setAccountnumber(accountnumber);
				reg.setIsoverride(isoverride);
				reg.setAuthorizedSession(sess);
				if(isoverride.equals("1")){
					String overrideusername = this.params.get("OverrideUserName").toString();
					String overridepassword = this.params.get("OverridePassword").toString();
					reg.setOverrideusername(overrideusername);
					reg.setOverridepassword(overridepassword);
					
					if(!reg.validateverride()){
						reg.setState(new ObjectState("20","Invalid password"));
						AuditTrail audit  = new AuditTrail();
			    		audit.setIp(reg.getAuthorizedSession().getIpAddress());
			    		audit.setModuleid(String.valueOf(this.getId()));
			    		audit.setEntityid(ordernumber);
			    		audit.setLog(reg.getState().getMessage());
			    		audit.setStatus(reg.getState().getCode());
			    		audit.setUserid(reg.getAuthorizedSession().getAccount().getId());
			    		audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
			    		
			    		audit.insert();
						return new JsonView(reg);
					}
				}

					try {
						if(reg.createremit()){
							reg.setState(new ObjectState("00",reg.getState().getMessage()));
							AuditTrail audit  = new AuditTrail();
				    		audit.setIp(reg.getAuthorizedSession().getIpAddress());
				    		audit.setModuleid(String.valueOf(this.getId()));
				    		audit.setEntityid(ordernumber);
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
				    		audit.setEntityid(ordernumber);
				    		audit.setLog(reg.getState().getMessage());
				    		audit.setStatus(reg.getState().getCode());
				    		audit.setUserid(reg.getAuthorizedSession().getAccount().getId());
				    		audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
				    		
				    		audit.insert();
							return new JsonView(reg); 
						}
					} catch (Exception e) {
						Logger.LogServer(e);
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
		// TODO Auto-generated method stub
		return "APPROVEDREMITTANCE";
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 4100;
	}

}
