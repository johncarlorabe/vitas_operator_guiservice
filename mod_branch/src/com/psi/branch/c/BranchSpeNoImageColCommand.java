package com.psi.branch.c;

import com.psi.branch.m.BranchSpecificCollection;
import com.psi.branch.v.CollectionView;
import com.psi.branch.v.NoDataFoundView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;

public class BranchSpeNoImageColCommand extends UICommand{

	@Override
	public IView execute() {
		BranchSpecificCollection col = new BranchSpecificCollection();
		col.setId(this.params.get("Id"));
				
			if(col.hasNoImageRows()){
				return new CollectionView("00",col);  
			}else{
				ObjectState state = new ObjectState("01", "No data found");
				return new NoDataFoundView(state); 
			}		
	}

	@Override
	public String getKey() {
		return "BRANCHCOLSPECIFICNOIMAGE";
	}

	@Override
	public int getId() {
		return 0;
	}

}
