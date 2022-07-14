package com.psi.business.c;

import com.psi.business.m.SpecificBusCollection;
import com.psi.business.v.CollectionView;
import com.psi.business.v.NoDataFoundView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;

public class SpecificBusColCommand extends UICommand{

	@Override
	public IView execute() {
		SpecificBusCollection col = new SpecificBusCollection();
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
		return "BUSINESSCOLLECTIONSPECIFIC";
	}

	@Override
	public int getId() {
		return 0;
	}

}
