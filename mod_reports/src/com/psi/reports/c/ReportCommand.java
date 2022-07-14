package com.psi.reports.c;

import com.psi.reports.m.ReportCollection;
import com.psi.reports.v.JsonView;
import com.psi.reports.v.NoDataFoundView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;

public class ReportCommand extends UICommand{

	@Override
	public IView execute() {
//		ExistingSession sess = null;
//		SessionView v = null;
//		
//		try {
//			sess = ExistingSession.parse(this.reqHeaders);		
//			if(sess.exists()) {
				String id = this.params.get("Id").toString();
				String datefrom = this.params.get("DateFrom").toString();
				String dateto = this.params.get("DateTo").toString();
					
				ReportCollection col = new ReportCollection();
						
				col.setId(id);
				col.setDatefrom(datefrom);
				col.setDateto(dateto);
			  //reg.setAuthorizedSession(sess);
				
				if(col.generate()){
					col.setState(new ObjectState("00", "Your transaction summary request has been sent to your email."));
					return new JsonView(col);
				}else{
					col.setState(new ObjectState("99", "System is busy"));
					return new JsonView(col);
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
	public String getKey() {
		// TODO Auto-generated method stub
		return "GENERATEREPORT";
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 2001;
	}

}
