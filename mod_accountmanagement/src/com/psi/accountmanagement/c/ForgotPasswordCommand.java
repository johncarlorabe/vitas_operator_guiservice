package com.psi.accountmanagement.c;

import com.psi.accountmanagement.m.ForgotPassword;
import com.psi.accountmanagement.m.NewRegister;
import com.psi.accountmanagement.utils.EmailUtils;
import com.psi.accountmanagement.v.JsonView;
import com.tlc.common.Logger;
import com.tlc.encryption.PasswordGenerator;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class ForgotPasswordCommand extends UICommand{

	@Override
	public IView execute() {
		/*ExistingSession sess = null;
		SessionView v = null;
		
		try {
			sess = ExistingSession.parse(this.reqHeaders);		
			if(sess.exists()) {*/
				String email = this.params.get("Email").toString();
				String url = this.params.get("Url").toString();
				String password = PasswordGenerator.generatePassword(4, PasswordGenerator.UPPER_ALPHA);
	 	
				ForgotPassword reg = new ForgotPassword();
						
				reg.setEmail(email);
				reg.setPassword(password);
				reg.setUrl(url);
			 // reg.setAuthorizedSession(sess);
				
					if(!reg.exist()){
						reg.setState(new ObjectState("01", "Account do not exist"));
						return new JsonView(reg);  
					}
					if(!reg.isValid()){
						reg.setState(new ObjectState("02", "Unauthorized Account"));
						return new JsonView(reg);  
					}
					if(reg.reset()){
						reg.setState(new ObjectState("00", "Send Succesfully"));
						if(reg.userslevel().equals("MANAGER") || reg.userslevel().equals("CASHIER")){
							
							EmailUtils.sendEmailForgotPassword(email,password,"https://uat-vitas.ibayad.com/operator/login");
						}else if(reg.userslevel().equals("CUSTOMER"))
						{
							
							EmailUtils.sendEmailForgotPassword(email,password,"");
						}else{
							
							EmailUtils.sendEmailForgotPassword(email,password,"https://uat-vitas.ibayad.com/operator/login");
						}
						return new JsonView(reg);  
					}else{
						reg.setState(new ObjectState("99", "System busy"));
						return new JsonView(reg);  
					}
				
	/*		}else{	
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
	}return v;*/
	}
		
		

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return "FORGOTPASSWORD";
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}

}
