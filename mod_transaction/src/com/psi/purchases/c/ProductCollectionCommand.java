package com.psi.purchases.c;

import com.psi.purchases.m.ProductCollection;
import com.psi.purchases.v.CollectionView;
import com.psi.purchases.v.NoDataFoundView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;

public class ProductCollectionCommand extends UICommand{

	@Override
	public IView execute() {
//		ExistingSession sess = null;
//		SessionView v = null;
//		
//		try {
//			sess = ExistingSession.parse(this.reqHeaders);		
//			if(sess.exists()) {
		
				String ndc = this.params.get("Ndc");
				
				ProductCollection topup = new ProductCollection();
				topup.setNdc(ndc);

				
			  //reg.setAuthorizedSession(sess);
				if(topup.hasRows()){
					return new CollectionView("00",topup);  
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
		// TODO Auto-generated method stub
		return "PRODUCTCOLLECTION";
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}

}
