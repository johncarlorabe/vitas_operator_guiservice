package com.tlc.gui.modules.interfaces;

public interface IRegisteredObject extends ISearchableObject{
	boolean save();
	boolean activate();
	boolean deactivate();	
}