package com.psi.purchases.c;

import java.io.IOException;
import java.text.ParseException;

import com.psi.purchases.m.WalletTopUp;
import com.psi.purchases.util.AuditTrail;
import com.psi.purchases.v.JsonView;
import com.tlc.common.Logger;
import com.tlc.common.LongUtil;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class WalletTopupRequestCommand extends UICommand{

	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;
		
		try {
			sess = ExistingSession.parse(this.reqHeaders);		
			if(sess.exists()) {
		
				String id = this.params.get("LinkId");
				Long amount = null;
				try {
					amount = LongUtil.toLong(this.params.get("Amount"));
				} catch (ParseException e1) {
					e1.printStackTrace();
					Logger.LogServer(e1);
				}	
				String reference = this.params.get("ReferenceNumber"); //empty string
				String image = this.params.get("Image");
				String password = this.params.get("Password");
				String dateofdepo = this.params.get("DateOfDeposit");
				String timeofdepo = this.params.get("TimeOfDeposit");	
				String branchcode = this.params.get("BankBranchCode");
				String bankname = this.params.get("BankName");
				String depochannel = this.params.get("DepositChannel");
				String remarks = this.params.get("Remarks");
				
				WalletTopUp topup = new WalletTopUp();
				topup.setDepochannel(depochannel);
				topup.setRemarks(remarks);
				topup.setAmount(amount);
				topup.setId(id);
				topup.setReference(reference);
				topup.setImage(image);
				topup.setPassword(password);
				topup.setDateofdepo(dateofdepo);
				topup.setTimeofdepo(timeofdepo);
				topup.setBranchcode(branchcode);
			    topup.setAuthorizedSession(sess);
			    topup.setBankname(bankname);
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
//						if(topup.isUnique()){
//							topup.setState(new ObjectState("02", "Duplicate Reference Number"));
//							AuditTrail audit  = new AuditTrail();
//				    		audit.setIp(topup.getAuthorizedSession().getIpAddress());
//				    		audit.setModuleid(String.valueOf(this.getId()));
//				    		audit.setEntityid(id);
//				    		audit.setLog(topup.getState().getMessage());
//				    		audit.setStatus(topup.getState().getCode());
//				    		audit.setUserid(topup.getAuthorizedSession().getAccount().getId());
//				    		audit.setUsername(topup.getAuthorizedSession().getAccount().getUserName());
//				    		
//				    		audit.insert();
//							return new JsonView(topup); 
//						}
						try {
							if(topup.topup()){
								topup.setState(new ObjectState("00", "Successful"));
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
								topup.setState(new ObjectState("99", "System is busy"));
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
						} catch (org.json.simple.parser.ParseException e) {
							e.printStackTrace();
							topup.setState(new ObjectState("99", "System is busy"));
							return new JsonView(topup);
						} catch (IOException e) {
							e.printStackTrace();
							topup.setState(new ObjectState("99", "System is busy"));
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
		return "WALLETTOPUPREQUEST";
	}

	@Override
	public int getId() {
		return 2002;
	}

}
