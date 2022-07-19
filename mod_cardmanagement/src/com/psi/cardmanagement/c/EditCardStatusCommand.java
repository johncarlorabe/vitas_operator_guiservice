package com.psi.cardmanagement.c;

import com.psi.cardmanagement.m.ManageRegisteredCustomer;
import com.psi.cardmanagement.utils.AuditTrail;
import com.psi.cardmanagement.utils.CustomerHelper;
import com.psi.cardmanagement.v.JsonView;
import com.tlc.common.Logger;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class EditCardStatusCommand extends UICommand {

	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;

		try {
			sess = ExistingSession.parse(this.reqHeaders);
			ManageRegisteredCustomer regacct = new ManageRegisteredCustomer();
			String msisdn = this.params.get("Msisdn").toString();
			regacct.setMsisdn(msisdn);
			
			String password = this.params.get("Password").toString();
			String id = this.params.get("Id").toString();
			String reason = this.params.get("Reason").toString();
			String statuslog = this.params.get("Status").equals("ACTIVE")?"(ACTIVATE)":"(DEACTIVATE)";
			
			CustomerHelper passwordCheck = new CustomerHelper();
			passwordCheck.setPassword(password);
			passwordCheck.setId(id);
			
			if (sess.exists()) {
				regacct.setAuthorizedSession(sess);
				if (regacct.exists()) {
					if (passwordCheck.exists()) {	
						
						regacct.setReason(reason);
						regacct.setStatus(this.params.get("Status"));
						
							if (regacct.updateStatus(reason)) {
								regacct.setState(new ObjectState("00" ,"Successfully Updated Card Status"));
								
								AuditTrail audit = new AuditTrail();
					    		audit.setIp(regacct.getAuthorizedSession().getIpAddress());
					    		audit.setModuleid(String.valueOf(this.getId()));
					    		audit.setEntityid(msisdn);
					    		audit.setLog(regacct.getState().getMessage()  +"||"+statuslog+"||Remarks:"+reason);
					    		audit.setStatus(regacct.getState().getCode());
					    		audit.setUserid(regacct.getAuthorizedSession().getAccount().getId());
					    		audit.setUsername(regacct.getAuthorizedSession().getAccount().getUserName());
					    		audit.insert();
								return new JsonView(regacct); 
							} else {
								regacct.setState(new ObjectState("PSI-7300-01","Unsuccessful Updating Card Status"));
								AuditTrail audit = new AuditTrail();
					    		audit.setIp(regacct.getAuthorizedSession().getIpAddress());
					    		audit.setModuleid(String.valueOf(this.getId()));
					    		audit.setEntityid(msisdn);
					    		audit.setLog(regacct.getState().getMessage()  +"||"+statuslog+"||Remarks:"+reason);
					    		audit.setStatus(regacct.getState().getCode());
					    		audit.setUserid(regacct.getAuthorizedSession().getAccount().getId());
					    		audit.setUsername(regacct.getAuthorizedSession().getAccount().getUserName());
					    		return new JsonView(regacct); 
							}
					}else{
						regacct.setState(new ObjectState("PSI-7300-02", "Invalid Password"));
						
						AuditTrail audit = new AuditTrail();
			    		audit.setIp(regacct.getAuthorizedSession().getIpAddress());
			    		audit.setModuleid(String.valueOf(this.getId()));
			    		audit.setEntityid(msisdn);
			    		audit.setLog(regacct.getState().getMessage()  +"||"+statuslog+"||Remarks:"+reason);
			    		audit.setStatus(regacct.getState().getCode());
			    		audit.setUserid(regacct.getAuthorizedSession().getAccount().getId());
			    		audit.setUsername(regacct.getAuthorizedSession().getAccount().getUserName());
			    		
						return new JsonView(regacct);
					}
				}else {
					regacct.setState(new ObjectState("PSI-7300-03", "Account doesn't exist"));
					
					AuditTrail audit = new AuditTrail();
		    		audit.setIp(regacct.getAuthorizedSession().getIpAddress());
		    		audit.setModuleid(String.valueOf(this.getId()));
		    		audit.setEntityid(msisdn);
		    		audit.setLog(regacct.getState().getMessage()  +"||"+statuslog+"||Remarks:"+reason);
		    		audit.setStatus(regacct.getState().getCode());
		    		audit.setUserid(regacct.getAuthorizedSession().getAccount().getId());
		    		audit.setUsername(regacct.getAuthorizedSession().getAccount().getUserName());
		    		
					return new JsonView(regacct); 
				}
			}else {
				UISession u = new UISession(null);
				u.setState(new ObjectState("PSI-7300-04" , "Session Timeout"));
				v = new SessionView(u);
			}
			
		} catch (SessionNotFoundException e) {
			UISession u = new UISession(null);
			u.setState(new ObjectState("PSI-7300-04" , "Session Timeout"));
			v = new SessionView(u);
			Logger.LogServer(e);
		}
		return v;
		
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return "EDITCARDSTATUS";
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 7110;
	}
}
