package com.psi.reports.c;

import com.psi.reports.m.AllBranches;
import com.psi.reports.m.TerritoryCollection;
import com.psi.reports.v.CollectionView;
import com.psi.reports.v.NoDataFoundView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;

public class TerritoryColCommand extends UICommand{

	@Override
	public IView execute() {
			TerritoryCollection model = new TerritoryCollection();
		
			if(model.hasRows()){
				return new CollectionView("00",model);  
			}else{
					ObjectState state = new ObjectState("01", "No data found");
					return new NoDataFoundView(state); 
			}
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return "ALLTERRITORIES";
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}

}
