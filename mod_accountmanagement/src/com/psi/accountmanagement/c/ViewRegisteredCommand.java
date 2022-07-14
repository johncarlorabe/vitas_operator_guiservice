package com.psi.accountmanagement.c;

import com.psi.accountmanagement.m.RegisteredCollection;
import com.psi.accountmanagement.v.CollectionView;
import com.psi.accountmanagement.v.NoDataFoundView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;

public class ViewRegisteredCommand extends UICommand{

	@Override
	public IView execute() {
//		ExistingSession sess = null;
//		SessionView v = null;
//		
//		try {
//			sess = ExistingSession.parse(this.reqHeaders);		
//			if(sess.exists()) {
				String id = this.params.get("Id").toString();
				String userslevel = this.params.get("UsersLevel").toString();
				
				RegisteredCollection col = new RegisteredCollection();
						
				col.setId(id);
				col.setUserslevel(userslevel);
			  //col.setAuthorizedSession(sess);
				
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
	public int getId() {
		// TODO Auto-generated method stub
		return 1030;
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return "VIEWACCOUNTDETAILS";
	}

}
