package com.psi.accountmanagement.c;

import java.util.Random;

import com.psi.accountmanagement.m.ManageAccount;
import com.psi.accountmanagement.utils.AuditTrail;
import com.psi.accountmanagement.v.JsonView;
import com.tlc.common.Logger;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class ResetPasswordCommand extends UICommand {

	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;
		
		try {
			sess = ExistingSession.parse(this.reqHeaders);		
			if(sess.exists()) {
				String userid = this.params.get("UserId").toString();
				
				ManageAccount mgtacct = new ManageAccount();
				mgtacct.setUserid(userid);
				mgtacct.setAuthorizedSession(sess);
				
				 String aToZ="123456789QWERTYUIOPASDFGHJKLZXCVBNM"; 
				 String randomStr = generateRandom(aToZ);
				 
				 mgtacct.setPassword(randomStr);
				 
				if(mgtacct.resetpassword(userid)){
					mgtacct.setState(new ObjectState("00", "Password Reset Success"));
					AuditTrail audit  = new AuditTrail();
		    		audit.setIp(mgtacct.getAuthorizedSession().getIpAddress());
		    		audit.setModuleid(String.valueOf(this.getId()));
		    		audit.setEntityid(userid);
		    		audit.setLog("Password Reset Success");
		    		audit.setStatus("00");
		    		audit.setUserid(mgtacct.getAuthorizedSession().getAccount().getId());
		    		audit.setUsername(mgtacct.getAuthorizedSession().getAccount().getUserName());
		    		
		    		audit.insert();
					return new JsonView(mgtacct);  
				} else {
					mgtacct.setState(new ObjectState("01", "Failed! Unable to reset user password"));
					AuditTrail audit  = new AuditTrail();
		    		audit.setIp(mgtacct.getAuthorizedSession().getIpAddress());
		    		audit.setModuleid(String.valueOf(this.getId()));
		    		audit.setEntityid(userid);
		    		audit.setLog("Failed! Unable to reset user password");
		    		audit.setStatus("01");
		    		audit.setUserid(mgtacct.getAuthorizedSession().getAccount().getId());
		    		audit.setUsername(mgtacct.getAuthorizedSession().getAccount().getUserName());
		    		
		    		audit.insert();
					return new JsonView(mgtacct);  
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
	
	private static String generateRandom(String aToZ) {
	    Random rand=new Random();
	    StringBuilder res=new StringBuilder();
	    for (int i = 0; i < 9; i++) {
	       int randIndex=rand.nextInt(aToZ.length()); 
	       res.append(aToZ.charAt(randIndex));            
	    }
	    return res.toString();
	}

	@Override
	public String getKey() {
		return "RESETUSERPASSWORD";
	}

	@Override
	public int getId() {
		return 9605;
	}

}
