package com.psi.accountmanagement.c;

import com.psi.accountmanagement.m.ManageRegisteredUser;
import com.psi.accountmanagement.m.NewRegister;
import com.psi.accountmanagement.utils.EmailUtils;
import com.psi.accountmanagement.v.JsonView;
import com.tlc.common.SystemInfo;
import com.tlc.encryption.PasswordGenerator;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;

public class ValidateCodeCommand extends UICommand{

	@Override
	public IView execute() {
//		ExistingSession sess = null;
//		SessionView v = null;
//		
//		try {
//			sess = ExistingSession.parse(this.reqHeaders);		
//			if(sess.exists()) {
				String id = this.params.get("Id").toString();
				String code = this.params.get("Code").toString();
				 	
				ManageRegisteredUser model = new ManageRegisteredUser();
				
				model.setId(id);
				model.setCode(code);
			
			  //model.setAuthorizedSession(sess);
				if(model.isValidated()){
					model.setState(new ObjectState("02", "Validated already"));
					return new JsonView(model);  
				}
				if(model.isValid()){
					if(model.validate()){
						model.setState(new ObjectState("00", "Validated Succesfully"));	
						return new JsonView(model); 
					}else{
						model.setState(new ObjectState("99", "System busy"));
						return new JsonView(model); 
					}
				}else{
					model.setState(new ObjectState("01", "Authentication is invalid"));
					return new JsonView(model); 
				}
				 
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
		return 1020;
	}

	@Override
	public String getKey() {
		return "VALIDATECODE";
	}

}
