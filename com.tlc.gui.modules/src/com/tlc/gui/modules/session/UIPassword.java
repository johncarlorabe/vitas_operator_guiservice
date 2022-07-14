package com.tlc.gui.modules.session;

import java.util.Date;

import com.tlc.gui.modules.common.Model;


public  class UIPassword extends Model{
	
	public static final String PROP_DEFAULT = "DEFAULT";
	public static final String PROP_EXPIRED = "EXPIRED";
	public static final String PROP_VALID = "VALID";
	public static final String PROP_LASTCHANGED = "LASTCHANGED";
	
	protected String value = null;
	protected boolean _default=false;
	protected boolean isExpired = false;
	protected boolean isValid = true;
	protected Date lastChanged=null;
	
	public boolean valid(){
		return this.isValid;
	}
	public void setValid(boolean isValid){
		this.props.put(PROP_VALID, isValid?1:0);
		this.isValid = isValid;
	}
	public boolean isDefault(){
		return _default;
	}
	
	public void setDefault(boolean isDefault){
		this.props.put(PROP_DEFAULT, isDefault?1:0);
		this._default = isDefault;
	}
	
	public boolean expired(){
		return isExpired;
	}
	
	public void setExpired(boolean expired){
		this.props.put(PROP_EXPIRED, expired?1:0);
		this.isExpired = expired;
	}
	
	
	public String getValue(){
		return this.value;
	}
	
	
	
	
	public Date getLastChanged() {
		return lastChanged;
	}

	public void setLastChanged(Date lastChanged) {
		this.props.put(PROP_LASTCHANGED, lastChanged.toString());
		this.lastChanged = lastChanged;
	}

	public UIPassword(String value){
		this.value=value;
	}
	
	public UIPassword(){
		super();
	}
	
	@Override
	public String toString() {
		return this.value;
	}
	public boolean equals(UIPassword pwd) {
		return this.toString().equals(pwd.toString());
	}
	
}
