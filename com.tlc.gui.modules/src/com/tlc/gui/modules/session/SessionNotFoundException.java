package com.tlc.gui.modules.session;

import com.tlc.gui.modules.common.ObjectState;

public final class SessionNotFoundException extends ObjectState {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UISession session;
	public SessionNotFoundException(UISession session) {
		super(SessionNotFoundException.STAT_SESSIONDOESNOTEXIST);
		this.session= session;
	}

}
