package com.tlc.sky.pingen.arch.uc.acct;

import com.tlc.sky.pingen.arch.beans.PingenGroup;

public abstract class AbstractNewPingenGroup extends PingenGroup {

	public AbstractNewPingenGroup(String name) {
		super(name);
	}
	
	public abstract boolean register();

}
