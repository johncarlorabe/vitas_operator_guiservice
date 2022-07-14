package com.tlc.gui.modules.interfaces;

public interface IRegisteredAccount extends IRegisteredObject {
	
	public abstract boolean lock();
	public abstract boolean unlock();
}
