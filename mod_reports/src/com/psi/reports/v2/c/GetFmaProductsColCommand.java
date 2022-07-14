package com.psi.reports.v2.c;

import com.psi.reports.v.CollectionView;
import com.psi.reports.v.NoDataFoundView;
import com.psi.reports.v2.m.GetFmaProductsCollection;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;

public class GetFmaProductsColCommand extends UICommand{

	@Override
	public IView execute() {

		GetFmaProductsCollection model = new GetFmaProductsCollection();
				String accountnumber = this.params.get("AccountNumber");
				String type = this.params.get("Type");
				model.setAccountnumber(accountnumber);
				model.setType(type);
				if(accountnumber.equals("ALL")){
					if(model.hasRows()){
						return new CollectionView("00",model);  
					}else{
							ObjectState state = new ObjectState("01", "No data found");
							return new NoDataFoundView(state); 
					}
				}else{
					if(model.hasRowsperaccount()){
						return new CollectionView("00",model);  
					}else{
							ObjectState state = new ObjectState("01", "No data found");
							return new NoDataFoundView(state); 
					}
				}
				
	}

	@Override
	public String getKey() {
		return "GETFMAPRODUCTS";
	}

	@Override
	public int getId() {
		return 0;
	}

}
