package com.psi.reports.c;

import com.psi.reports.m.AllBranches;
import com.psi.reports.v.CollectionView;
import com.psi.reports.v.DataView;
import com.psi.reports.v.NoDataFoundView;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class GetAllBranches extends UICommand{

	@Override
	public IView execute() {
//		ExistingSession sess = null;
//
//		SessionView v = null;
//
//		try {
//
//			sess = ExistingSession.parse(this.reqHeaders);
//
//			if (sess.exists()) {

				AllBranches model = new AllBranches();
			//	model.setAuthorizedSession(sess);

				if(model.hasRows()){
					return new CollectionView("00",model);  
				}else{
						ObjectState state = new ObjectState("01", "No data found");
						return new NoDataFoundView(state); 
				}
				
//
//			} else {
//				UISession u = new UISession(null);
//				u.setState(new ObjectState("TLC-3902-01"));
//				v = new SessionView(u);
//
//			}
//
//		} catch (SessionNotFoundException e) {
//			UISession u = new UISession(null);
//			u.setState(new ObjectState("TLC-3902-01"));
//			v = new SessionView(u);
//
//		}
//		return v;
	}

	@Override
	public String getKey() {
		return "GETBRANCHES";
	}

	@Override
	public int getId() {
		return 0;
	}

}
