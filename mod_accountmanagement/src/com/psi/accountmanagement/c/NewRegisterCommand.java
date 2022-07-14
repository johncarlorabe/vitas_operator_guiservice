package com.psi.accountmanagement.c;

import java.io.IOException;
import java.text.ParseException;

import com.psi.accountmanagement.m.NewRegister;
import com.psi.accountmanagement.utils.EmailUtils;
import com.psi.accountmanagement.v.JsonView;
import com.tlc.encryption.PasswordGenerator;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;

public class NewRegisterCommand extends UICommand{

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
				String midname = this.params.get("MiddleName").toString();
				String msisdn = this.params.get("MSISDN").toString();
				String country = this.params.get("Country").toString();
				String province = this.params.get("Province").toString();
				String city = this.params.get("City").toString();
				String code = PasswordGenerator.generatePassword(4, PasswordGenerator.NUMERIC_CHAR);
				//String password = PasswordGenerator.generatePassword(4, PasswordGenerator.UPPER_ALPHA);
				String password = "123456789";
				String dateofbirth = this.params.get("DateOfBirth").toString();
				String natureofwork = this.params.get("NatureOfWork").toString();
				String sourceoffund = this.params.get("SourceOfFund").toString();
				String placeofbirth = this.params.get("PlaceOfBirth").toString();
				
					 	
				NewRegister reg = new NewRegister();
						
				reg.setEmail(email);
				reg.setFirstname(firstname);
				reg.setLastname(lastname);
				reg.setMidname(midname);
				reg.setMsisdn(msisdn);
				reg.setCountry(country);
				reg.setProvince(province);
				reg.setCity(city);
				reg.setCode(code);
				reg.setPassword(password);
				reg.setDateofbirth(dateofbirth);
				reg.setNatureofwork(natureofwork);
				reg.setSourceoffund(sourceoffund);
				reg.setPlaceoofbirth(placeofbirth);
			  //reg.setAuthorizedSession(sess);
				
				if(!reg.exist()){
					try {
						if(reg.register()){
							reg.getState();
							EmailUtils.send(email, firstname, lastname, code,password,email);	
						}else{
							reg.getState();
						}
					} catch (IOException e) {
						reg.setState(new ObjectState("99", e.getMessage()));
						e.printStackTrace();
					} catch (ParseException e) {
						reg.setState(new ObjectState("99", e.getMessage()));
						e.printStackTrace();
					} catch (org.json.simple.parser.ParseException e) {
						reg.setState(new ObjectState("99", e.getMessage()));
						e.printStackTrace();
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
		return 1000;
	}

	@Override
	public String getKey() {
		return "NEWREGISTERUSER";
	}

}
