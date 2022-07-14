package com.tlc.gui.modules.interfaces;

public interface IPendingObject extends ISearchableObject{
	public abstract boolean approve();
	public abstract boolean reject(String reason);
}