package com.psi.business.c;

import com.psi.business.m.NewBusinessUser;
import com.psi.business.util.EmailUtils;
import com.psi.business.v.JsonView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;

public class NewBusinessUserCommand extends UICommand{

	@Override
	public IView execute() {	
//		ExistingSession sess = null;
//		SessionView v = null;
//		
//		try {
//			sess = ExistingSession.parse(this.reqHeaders);		
//			if(sess.exists()) {
				String email = this.params.get("Email").toString();
				String firstname = this.params.get("FirstName").toString();
				String lastname = this.params.get("LastName").toString();
				String msisdn = this.params.get("MSISDN").toString();
				String username = this.params.get("UserName").toString();
				String accountnumber = this.params.get("AccountNumber");
				//String password = PasswordGenerator.generatePassword(5, PasswordGenerator.NUMERIC_CHAR);
				String password = "123456789";
					 	
				NewBusinessUser reg = new NewBusinessUser();
						
				reg.setEmail(email);
				reg.setFirstname(firstname);
				reg.setLastname(lastname);
				reg.setMsisdn(msisdn);
				reg.setUsername(username);
				reg.setPassword(password);
				reg.setAccountnumber(accountnumber);
			  //reg.setAuthorizedSession(sess);
				
				if(!reg.exist()){
					if(reg.register()){
						reg.setState(new ObjectState("00", "Register Succesfully"));
						EmailUtils.send(email, firstname, lastname, password,username);	
					}else{
						reg.setState(new ObjectState("99", "System busy"));
					}
				}else{
					reg.setState(new ObjectState("01", "Account already registered"));
				}
				return new JsonView(reg);  
//		}catch (SessionNotFoundException e) {
//			UISession u = new UISession(null);
//		    u.setState(new ObjectState("TLC-3902-01"));
//		    v = new SessionView(u);
//			Logger.LogServer(e);
//	} catch (Exception e) {
//		Logger.LogServer(e);
//	}
				  
	}

	@Override
	public int getId() {	
		return 7000;
	}

	@Override
	public String getKey() {
		return "NEWBUSINESSUSER";
	}

}
