package com.psi.purchases.c;

import com.psi.purchases.m.WalletTopupStatusSpecCollection;
import com.psi.purchases.v.CollectionView;
import com.psi.purchases.v.NoDataFoundView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;

public class WalletTopupStatusSpecCommand extends UICommand{

	@Override
	public IView execute() {
		WalletTopupStatusSpecCollection col = new WalletTopupStatusSpecCollection();
		col.setId(this.params.get("Id"));
				
			if(col.hasRows()){
				return new CollectionView("00",col);  
			}else{
				ObjectState state = new ObjectState("01", "No data found");
				return new NoDataFoundView(state); 
			}		
	}

	@Override
	public String getKey() {
		return "WALLETCOLIMAGE";
	}

	@Override
	public int getId() {
		return 0;
	}

}
