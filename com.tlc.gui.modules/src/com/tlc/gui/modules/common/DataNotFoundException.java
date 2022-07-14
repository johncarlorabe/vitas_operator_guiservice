package com.tlc.gui.modules.common;

public class DataNotFoundException extends ObjectState {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Model sender= null;
	public DataNotFoundException(Model sender) {
		super(STAT_OBJECTDOESNOTEXIST);
		this.sender = sender;
	}
	
	public Model getSender(){
		return this.sender;
	}

}
