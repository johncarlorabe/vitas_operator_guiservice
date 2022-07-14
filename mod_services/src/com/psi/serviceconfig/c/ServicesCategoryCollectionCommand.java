package com.psi.serviceconfig.c;

import com.psi.serviceconfig.m.ServicesCategoryCollection;
import com.psi.serviceconfig.v.CollectionView;
import com.psi.serviceconfig.v.NoDataFoundView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;

public class ServicesCategoryCollectionCommand extends UICommand{

	@Override
	public IView execute() {
//		ExistingSession sess = null;
//		SessionView v = null;
//		
//		try {
//			sess = ExistingSession.parse(this.reqHeaders);		
//			if(sess.exists()) {
				String type = this.params.get("Type");
				ServicesCategoryCollection col = new ServicesCategoryCollection();
			  //col.setAuthorizedSession(sess);
				col.setType(type);
				if(col.hasRows()){
					return new CollectionView("00",col);  
				}else{
						ObjectState state = new ObjectState("01", "NO data found");
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
		return "SERVICESCATEGORIES";
	}

	@Override
	public int getId() {
		return 0;
	}

}
