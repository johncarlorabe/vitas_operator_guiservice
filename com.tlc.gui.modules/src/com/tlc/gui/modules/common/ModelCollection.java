package com.tlc.gui.modules.common;

import java.util.ArrayList;

import com.tlc.gui.modules.session.UISession;

public abstract class ModelCollection extends ArrayList<Model>{

	private UISession authorizedSession;
	public UISession getAuthorizedSession() {
		return authorizedSession;
	}

	public void setAuthorizedSession(UISession authorizedSession) {
		this.authorizedSession = authorizedSession;
	}

	public abstract boolean hasRows();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}