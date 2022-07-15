package com.psi.purchases.c;

import java.text.NumberFormat;
import java.util.Locale;

import com.psi.purchases.m.VipWalletRequest;
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

public class VipWalletRequestCommand extends UICommand{

	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;
		
		try {
			sess = ExistingSession.parse(this.reqHeaders);		
			if(sess.exists()) {
		
				String id = this.params.get("LinkId");
				String remark = this.params.get("Remarks");
				String status = this.params.get("Status");
				NumberFormat format = NumberFormat.getInstance(Locale.US);

		        Number amount = null;
				try {
					amount = format.parse(this.params.get("Amount"));
				} catch (java.text.ParseException e) {
					e.printStackTrace();
				}
				String password = this.params.get("Password");
				String accountnumber = this.params.get("AccountNumber");
				String referencenumber = this.params.get("ReferenceNumber"); //to Bank branch code
				String depositchannel = this.params.get("DepositChannel");
				String timeofdeposit = this.params.get("TimeOfDeposit");
				String dateofdeposit = this.params.get("DateOfDeposit");
				String bankcode = this.params.get("BankCode");
				String bankname = this.params.get("BankName");
				String image = this.params.get("Image");
				String description = this.params.get("Description");
				
				
				VipWalletRequest topup = new VipWalletRequest();
				topup.setAmount(amount.toString());
				topup.setPassword(password);
				topup.setAccountnumber(accountnumber);
				topup.setId(id);
				topup.setReferencenumber(referencenumber);
				topup.setRemarks(remark);
				topup.setStatus(status);
				topup.setDepositchannel(depositchannel);
				topup.setTimeofdeposit(timeofdeposit);
				topup.setDateofdeposit(dateofdeposit);
				topup.setBankcode(bankcode);
				topup.setBankname(bankname);
				topup.setImage(image);
				topup.setDescription(description);
				topup.setAuthorizedSession(sess);
				
						if(!topup.validate()){
							topup.setState(new ObjectState("01", "Invalid Password"));
							AuditTrail audit  = new AuditTrail();
				    		audit.setIp(topup.getAuthorizedSession().getIpAddress());
				    		audit.setModuleid(String.valueOf(this.getId()));
				    		audit.setEntityid(accountnumber);
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
					    		audit.setEntityid(accountnumber);
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
					    		audit.setEntityid(accountnumber);
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
		return "VIPWALLETTOPUP";
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 9502;
	}

}
