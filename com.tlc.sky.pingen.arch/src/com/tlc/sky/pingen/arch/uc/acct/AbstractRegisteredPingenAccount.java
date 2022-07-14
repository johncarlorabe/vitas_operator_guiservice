package com.tlc.sky.pingen.arch.uc.acct;

import com.tlc.gui.modules.session.UIAccount;

public abstract class AbstractRegisteredPingenAccount extends UIAccount {
	public abstract boolean exists();
	public abstract boolean activate(String reason);
	public abstract boolean deactivate(String reason);
	public abstract boolean lock(String reason);
	public abstract boolean unlock(String reason);
	public abstract boolean update();
}