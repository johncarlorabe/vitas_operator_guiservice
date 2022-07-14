package com.psi.reports.c;

import com.psi.reports.m.AuditTrail;
import com.psi.reports.m.UMAKDailyTransactionReport;
import com.psi.reports.v.CollectionView;
import com.psi.reports.v.NoDataFoundView;
import com.tlc.common.Logger;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class DailyUMAKTransColCommand extends UICommand{

	@Override
	public IView execute() {
		ExistingSession sess = null;
	    SessionView v = null;
	    try
	    {
	      sess = ExistingSession.parse(this.reqHeaders);
	      if (sess.exists())
	      {
	    	  UMAKDailyTransactionReport model = new UMAKDailyTransactionReport();
				model.setAuthorizedSession(sess);

		
				if(model.hasRows()){
					 AuditTrail audit = new AuditTrail();
			          audit.setIp(model.getAuthorizedSession().getIpAddress());
			          audit.setModuleid(String.valueOf(getId()));
			          audit.setEntityid("");
			          audit.setLog("Successfully fetched data");
			          audit.setStatus("00");
			          audit.setUserid(Integer.valueOf(model.getAuthorizedSession().getAccount().getId()));
			          audit.setUsername(model.getAuthorizedSession().getAccount().getUserName());

			          audit.insert();
					
					return new CollectionView("00",model);  
				}else{
					ObjectState state = new ObjectState("01", "No data found");
					AuditTrail audit = new AuditTrail();
			        audit.setIp(model.getAuthorizedSession().getIpAddress());
			        audit.setModuleid(String.valueOf(getId()));
			        audit.setEntityid("");
			        audit.setLog("No accounts found");
			        audit.setStatus("01");
			        audit.setUserid(Integer.valueOf(model.getAuthorizedSession().getAccount().getId()));
			        audit.setUsername(model.getAuthorizedSession().getAccount().getUserName());

			        audit.insert();
					
					return new NoDataFoundView(state); 
				}
	      }
		  UISession u = new UISession(null);
		    u.setState(new ObjectState("TLC-3902-01"));
		    v = new SessionView(u);
		  }
		  catch (SessionNotFoundException e) {
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
		return "DAILYUMAKTRANSACTIONS";
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}

}
