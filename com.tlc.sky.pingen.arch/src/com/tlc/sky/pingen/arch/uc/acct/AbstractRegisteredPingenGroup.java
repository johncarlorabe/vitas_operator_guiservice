package com.tlc.sky.pingen.arch.uc.acct;

import com.tlc.gui.modules.session.UIGroup;
import com.tlc.sky.pingen.arch.beans.PingenGroup;

public abstract class AbstractRegisteredPingenGroup extends PingenGroup {

	public AbstractRegisteredPingenGroup(String name) {
		super(name);
	}
	
	public abstract boolean exists();
	public abstract boolean deactivate();
	public abstract boolean update();
}