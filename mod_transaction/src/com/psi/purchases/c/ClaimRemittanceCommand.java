package com.psi.purchases.c;

import com.psi.purchases.m.ClaimRemittance;
import com.psi.purchases.v.JsonView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;

public class ClaimRemittanceCommand extends UICommand{

	@Override
	public IView execute() {
		String ordernumber = this.params.get("OrderNumber").toString();
		String username = this.params.get("UserName").toString();
		
		ClaimRemittance claimremit = new ClaimRemittance();
		
		claimremit.setOrdernumber(ordernumber);
		claimremit.setUsername(username);
		
		if(!claimremit.exist()){
			claimremit.setState(new ObjectState("01","Order Number doest not exist"));
			return new JsonView(claimremit);
		}
		if(!claimremit.update()){
			claimremit.setState(new ObjectState("99","System is currently busy, please try again later"));
			return new JsonView(claimremit);
		}
		claimremit.setState(new ObjectState("00","Successful"));
		return new JsonView(claimremit);
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return "CLAIMREMITTANCE";
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}

}
