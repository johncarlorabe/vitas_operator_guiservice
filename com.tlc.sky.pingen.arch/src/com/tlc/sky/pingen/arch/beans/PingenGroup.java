package com.tlc.sky.pingen.arch.beans;

import com.tlc.gui.modules.session.UIGroup;

public class PingenGroup extends UIGroup {
	
	public static final String PROP_STATUS="ACCOUNTSTATUS";
	public static final String PROP_HOMEPAGE="HOMEPAGE";
	public static final String PROP_GUIINTERFACE="GUIINTERFACE";
	
	protected String accountStatus;
	protected String homepage;
	protected Integer[] notifications;
	protected String guiinterface;

	public PingenGroup(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	public Integer[] getModules(){
		return this.modules;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.props.put(PROP_STATUS, accountStatus);
		this.accountStatus = accountStatus;
	}
	
	public String getHomePage() {
		return homepage;
	}

	public void setHomePage(String homepage) {
		this.props.put(PROP_HOMEPAGE, homepage);
		this.homepage = homepage;
	}
	
	public Integer [] getNotifications() {
		return this.notifications;
	}

	public void setNotifications(Integer[] notifications) {
		this.props.put(PROP_STATUS, notifications);
		this.notifications = notifications;
	}

	public String getGuiinterface() {
		return guiinterface;
	}

	public void setGuiinterface(String guiinterface) {
		this.props.put(PROP_GUIINTERFACE, guiinterface);
		this.guiinterface = guiinterface;
	}
	
	

}
