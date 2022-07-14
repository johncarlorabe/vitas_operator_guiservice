package com.psi.wallet.c;

import java.text.NumberFormat;
import java.util.Locale;

import com.psi.wallet.m.ProviderPrefund;
import com.psi.wallet.util.AuditTrail;
import com.psi.wallet.v.JsonView;
import com.tlc.common.Logger;
import com.tlc.common.LongUtil;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class ProviderPrefundCommand extends UICommand{

	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;
		
		try {
			sess = ExistingSession.parse(this.reqHeaders);		
			if(sess.exists()) {
		
				String accountnumber = this.params.get("AccountNumber");
				String walletid = this.params.get("WalletId");
		        Long amount = null;
		        Long upfront = null;
		        Long cashback = null;
				try {
					amount = LongUtil.toLong(this.params.get("Amount"));
					upfront = LongUtil.toLong(this.params.get("UpFront"));
					cashback = LongUtil.toLong(this.params.get("CashBack"));
				} catch (java.text.ParseException e) {
					e.printStackTrace();
				}
				String password = this.params.get("Password");
				String bankname = this.params.get("BankName");
				String bankrefno = this.params.get("BankRefNo"); //to Bank branch code
				String dateofdepo = this.params.get("DateOfDepo");
				String timeofdepo = this.params.get("TimeOfDepo");
				String pofilename = this.params.get("POFileName");
				String pofilepath = this.params.get("POFilePath");
				
				ProviderPrefund topup = new ProviderPrefund();

				topup.setAccountnumber(accountnumber);
				topup.setWalletid(walletid);
				topup.setAmount(amount);
				topup.setUpfront(upfront);
				topup.setCaskback(cashback);
				topup.setDateofdepo(dateofdepo);
				topup.setTimeofdepo(timeofdepo);
				topup.setBankname(bankname);
				topup.setBankrefno(bankrefno);
				topup.setPofilename(pofilename);
				topup.setPofilepath(pofilepath);
				topup.setPassword(password);
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
					
						try {
							if(topup.topup()){
								topup.setState(new ObjectState("00", "Wallet successfully prefunded to provider"));
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
		return "PROVIDERPREFUND";
	}

	@Override
	public int getId() {
		return 9501;
	}

}
