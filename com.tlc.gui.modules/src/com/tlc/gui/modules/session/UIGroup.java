package com.tlc.gui.modules.session;

import com.tlc.gui.modules.common.Model;

public class UIGroup extends Model {
	
	public static final String PROP_PWDEXPIRY="PASSWORDEXPIRY";
	public static final String PROP_PWDHISTORY="PASSWORDHISTORY";
	public static final String PROP_SEARCHRANGE="SEARCHRANGE";
	public static final String PROP_SESSIONTIMEOUT="SESSIONTIMEOUT";
	public static final String PROP_MINPASSWORD="MINPASSWORD";
	public static final String PROP_MAXALLOCDAY="MAXALLOCATIONPERDAY";
	public static final String PROP_NAME="GROUPNAME";
	public static final String PROP_ID="GROUPID";
	public static final String PROP_MODULES="MODULES";
	public static final String PROP_HOMEPAGE="HOMEPAGE";
	public static final String PROP_GUIINTERFACE="GUIINTERFACE";

	protected int passwordExpiry=0;
	protected int passwordHistory;
	protected int searchRange;
	protected int sessionTimeout;
	protected int minPassword;
	protected int maxAllocactionPerDay;
	protected String name;
	protected int id;
	protected Integer[] modules;
	protected String homepage;
	protected String guiinterface;
	
	public void setGuiinterface(String guiinterface) {
		this.props.put(PROP_GUIINTERFACE,guiinterface);
		this.guiinterface = guiinterface;
	}
	
	public void setHomePage(String homepage) {
		this.props.put(PROP_HOMEPAGE,homepage);
		this.homepage = homepage;
	}
	
	public UIGroup(String name){
		this.props.put(PROP_NAME, name);
		this.name = name;
	}
	
	public int getPasswordExpiry() {
		return passwordExpiry;
	}
	public void setPasswordExpiry(int passwordExpiry) {
		this.props.put(PROP_PWDEXPIRY, passwordExpiry);
		this.passwordExpiry = passwordExpiry;
	}
	public int getPasswordHistory() {
		return passwordHistory;
	}
	public void setPasswordHistory(int passwordHistory) {
		this.props.put(PROP_PWDHISTORY, passwordHistory);
		this.passwordHistory = passwordHistory;
	}
	public int getSearchRange() {
		return searchRange;
	}
	public void setSearchRange(int searchRange) {
		this.props.put(PROP_SEARCHRANGE,searchRange);
		this.searchRange = searchRange;
	}
	public int getSessionTimeout() {
		return sessionTimeout;
	}
	public void setSessionTimeout(int sessionTimeout) {
		this.props.put(PROP_SESSIONTIMEOUT,sessionTimeout);
		this.sessionTimeout = sessionTimeout;
	}
	public int getMinPassword() {
		return minPassword;
	}
	public void setMinPassword(int minPassword) {
		this.props.put(PROP_MINPASSWORD,minPassword);
		this.minPassword = minPassword;
	}
	public int getMaxAllocactionPerDay() {
		return maxAllocactionPerDay;
	}
	public void setMaxAllocactionPerDay(int maxAllocactionPerDay) {
		this.props.put(PROP_MAXALLOCDAY,maxAllocactionPerDay);
		this.maxAllocactionPerDay = maxAllocactionPerDay;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.props.put(PROP_NAME,name);
		this.name = name;
	}
	
	public void setId(int id){
		this.props.put(PROP_ID,id);
		this.id =id;
	}
	
	public int getId(){
		return this.id;
	}
	
	public void setModules(Integer[] modules){
		this.props.put(PROP_MODULES,modules);
		this.modules = modules;
	}
}