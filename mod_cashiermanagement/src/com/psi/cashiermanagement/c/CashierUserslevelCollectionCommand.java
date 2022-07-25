package com.psi.cashiermanagement.c;

import com.psi.cashiermanagement.m.UserslevelCollection;
import com.psi.cashiermanagement.v.CollectionView;
import com.psi.cashiermanagement.v.NoDataFoundView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;

public class CashierUserslevelCollectionCommand extends UICommand{

	@Override
	public IView execute() {
				
				UserslevelCollection col = new UserslevelCollection();
				
				if(col.hasRows()){
					return new CollectionView("00",col);  
				}else{
						ObjectState state = new ObjectState("01", "No data found");
						return new NoDataFoundView(state); 
				}
			
	}

	@Override
	public String getKey() {
		return "CASHIERUSERSLEVEL";
	}

	@Override
	public int getId() {
		return 0;
	}

}
