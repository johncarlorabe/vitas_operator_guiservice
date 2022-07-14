package com.psi.accountmanagement.c;

import com.psi.accountmanagement.m.ManageRegisteredUser;
import com.psi.accountmanagement.m.NewRegister;
import com.psi.accountmanagement.utils.EmailUtils;
import com.psi.accountmanagement.v.JsonView;
import com.tlc.encryption.PasswordGenerator;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;

public class EditRegisteredCommand extends UICommand{

	@Override
	public IView execute() {
//		ExistingSession sess = null;
//		SessionView v = null;
//		
//		try {
//			sess = ExistingSession.parse(this.reqHeaders);		
//			if(sess.exists()) {
				String userslevel = this.params.get("UsersLevel").toString();
				ManageRegisteredUser reg = new ManageRegisteredUser();
				String email = this.params.get("Email").toString();
				String firstname = this.params.get("FirstName").toString();
				String lastname = this.params.get("LastName").toString();
				String msisdn = this.params.get("MSISDN").toString();
				String userid = this.params.get("UserId").toString();
				String midname = this.params.get("MiddleName").toString();
				String country = this.params.get("Country").toString();
				String province = this.params.get("Province").toString();
				String city = this.params.get("City").toString();
				if(userslevel.equals("CUSTOMER")){
					
					String id = this.params.get("Id").toString();
					String natureofwork = this.params.get("NatureOfWork").toString();
					String sourceoffund = this.params.get("SourceOfFund").toString();
						 						
					reg.setId(id);
					reg.setNatureofwork(natureofwork);
					reg.setSourceoffund(sourceoffund);
				}
					reg.setEmail(email);
					reg.setFirstname(firstname);
					reg.setLastname(lastname);
					reg.setMsisdn(msisdn);
					reg.setUserid(userid);
					reg.setUserslevel(userslevel);
					reg.setMidname(midname);
					reg.setCountry(country);
					reg.setProvince(province);
					reg.setCity(city);
					//reg.setAuthorizedSession(sess);
				
//				if(!reg.exist()){
//					reg.setState(new ObjectState("01", "Account do not exist"));
//					return new JsonView(reg);  
//				}
					if(reg.update()){
						reg.setState(new ObjectState("00", "Updated Succesfully"));
						return new JsonView(reg);  
					}else{
						reg.setState(new ObjectState("99", "System busy"));
						return new JsonView(reg);  
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
		return 1010;
	}

	@Override
	public String getKey() {
		return "EDITREGISTEREDUSER";
	}

}
