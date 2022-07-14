package com.psi.purchases.c;

import com.psi.purchases.m.WalletTopUpCollection;
import com.psi.purchases.util.AuditTrail;
import com.psi.purchases.v.CollectionView;
import com.psi.purchases.v.NoDataFoundView;
import com.tlc.common.Logger;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class WalletTopUpCollectionCommand extends UICommand{

	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;
		
		try {
			sess = ExistingSession.parse(this.reqHeaders);		
			if(sess.exists()) {
		
				String id = this.params.get("LinkId");
				
				WalletTopUpCollection topup = new WalletTopUpCollection();
				topup.setId(id);
				topup.setAuthorizedSession(sess);
				if(topup.hasRows()){
					AuditTrail audit  = new AuditTrail();
		    		audit.setIp(topup.getAuthorizedSession().getIpAddress());
		    		audit.setModuleid(String.valueOf(this.getId()));
		    		audit.setEntityid(id);
		    		audit.setLog("Successfully fetched data");
		    		audit.setStatus("00");
		    		audit.setUserid(topup.getAuthorizedSession().getAccount().getId());
		    		audit.setUsername(topup.getAuthorizedSession().getAccount().getUserName());
		    		
		    		audit.insert();
					return new CollectionView("00",topup);  
				}else{
						ObjectState state = new ObjectState("01", "NO data found");
						AuditTrail audit  = new AuditTrail();
			    		audit.setIp(topup.getAuthorizedSession().getIpAddress());
			    		audit.setModuleid(String.valueOf(this.getId()));
			    		audit.setEntityid(id);
			    		audit.setLog("No data found");
			    		audit.setStatus("01");
			    		audit.setUserid(topup.getAuthorizedSession().getAccount().getId());
			    		audit.setUsername(topup.getAuthorizedSession().getAccount().getUserName());
			    		
			    		audit.insert();
						return new NoDataFoundView(state); 
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
		return "WALLETTOPUPCOLLECTIONREQUEST";
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 2002;
	}

}
