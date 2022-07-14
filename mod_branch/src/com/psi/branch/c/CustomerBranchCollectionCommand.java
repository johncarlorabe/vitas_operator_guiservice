package com.psi.branch.c;

import com.psi.branch.m.CustomerBranchCollection;
import com.psi.branch.v.CollectionView;
import com.psi.branch.v.NoDataFoundView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;

public class CustomerBranchCollectionCommand extends UICommand{

	@Override
	public IView execute() {
//		ExistingSession sess = null;
//		SessionView v = null;
//		
//		try {
//			sess = ExistingSession.parse(this.reqHeaders);		
//			if(sess.exists()) {
				CustomerBranchCollection col = new CustomerBranchCollection();


			  //reg.setAuthorizedSession(sess);
				
				if(col.hasRows()){
					return new CollectionView("00",col);  
				}else{
						ObjectState state = new ObjectState("01", "No data found");
						return new NoDataFoundView(state); 
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
		return "CUSTOMERBRANCHESCOLLECT";
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}

}
