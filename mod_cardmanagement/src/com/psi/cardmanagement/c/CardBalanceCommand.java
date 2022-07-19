package com.psi.cardmanagement.c;

import com.psi.cardmanagement.m.CardDetailsCollection;
import com.psi.cardmanagement.utils.AuditTrail;
import com.psi.cardmanagement.v.CollectionView;
import com.psi.cardmanagement.v.NoDataFoundView;
import com.tlc.common.Logger;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class CardBalanceCommand extends UICommand {

	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;

		String msisdn = this.params.get("Msisdn").toString();
		
	    try
	    {
	      sess = ExistingSession.parse(this.reqHeaders);
	      if (!sess.exists()) {
	        throw new SessionNotFoundException(sess);
	      }
	      sess.getToken().extend(sess.getAccount().getGroup().getSessionTimeout());
	    
	      CardDetailsCollection model = new CardDetailsCollection();
	      model.setMsisdn(msisdn);
	      model.setAuthorizedSession(sess);
	      
	      if(model.validate()){
    		  	ObjectState state = new ObjectState("PSI-7100-02", "Please input a valid card number");
    		  
				AuditTrail audit  = new AuditTrail();
	    		audit.setIp(model.getAuthorizedSession().getIpAddress());
	    		audit.setModuleid(String.valueOf(this.getId()));
	    		audit.setEntityid(msisdn);
	    		audit.setLog("Please input a valid card number");
	    		audit.setStatus("PSI-7100-02");
	    		audit.setUserid(model.getAuthorizedSession().getAccount().getId());
	    		audit.setUsername(model.getAuthorizedSession().getAccount().getUserName());
	    		
	    		audit.insert();

				NoDataFoundView view = new NoDataFoundView(state);
	    		return view;
	      }
	      
		  if(model.getBalance()){

			  	AuditTrail audit  = new AuditTrail();
				audit.setIp(model.getAuthorizedSession().getIpAddress());
	    		audit.setModuleid(String.valueOf(this.getId()));
	    		audit.setEntityid(msisdn);
	    		audit.setLog("Successfully fetched data");
	    		audit.setStatus("00");
	    		audit.setUserid(model.getAuthorizedSession().getAccount().getId());
	    		audit.setUsername(model.getAuthorizedSession().getAccount().getUserName());
	    		
				audit.insert();
				return new CollectionView("00",model); 
    	  	}else{
	    		  ObjectState state = new ObjectState("99", "Failed");
	    		  AuditTrail audit  = new AuditTrail();
					audit.setIp(model.getAuthorizedSession().getIpAddress());
		    		audit.setModuleid(String.valueOf(this.getId()));
		    		audit.setEntityid(msisdn);
		    		audit.setLog("Failed");
		    		audit.setStatus("99");
		    		audit.setUserid(model.getAuthorizedSession().getAccount().getId());
		    		audit.setUsername(model.getAuthorizedSession().getAccount().getUserName());
		    		
					audit.insert();
	    		  NoDataFoundView view = new NoDataFoundView(state);
	    		  return view;
	    	  } 		
		}catch (SessionNotFoundException e) {
			UISession u = new UISession(null);
		    u.setState(new ObjectState("TLC-3902-01"));
		    v = new SessionView(u);
			Logger.LogServer(e);
	} catch (Exception e) {
		Logger.LogServer(e);
	}
		return v;
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return "CARDBALN";
	}
	
	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}

}
