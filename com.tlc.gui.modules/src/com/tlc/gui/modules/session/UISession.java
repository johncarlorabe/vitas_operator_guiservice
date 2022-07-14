package com.tlc.gui.modules.session;

import java.util.Date;


import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.common.PluginHeaders;

public  class UISession extends Model{

	public static final String PROP_TOKEN="TOKEN";
	public static final String PROP_ACCOUNT="ACCOUNT";
	
	private UIAccount account=null;
	protected String id=null;
	protected String ipAddress;
	protected Token token;
	

	public String getIpAddress() {
		return ipAddress;
	}


	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}


	public void setId(String id) {
		this.id = id;
	}


	
	
	public UISession (Token t)  {
		if(t!=null)this.props.put(PROP_TOKEN, t);
		this.token = t;
	
	}
	
	public String getId(){
		return this.id;
	}
	public UIAccount getAccount(){
		return this.account;
	}
	
	public void setAccount(UIAccount acct){
		this.props.put(PROP_ACCOUNT, acct);
		this.account = acct;
	}
	
	public void setToken(Token t){
		if(props.containsKey(PROP_TOKEN)) this.props.remove(PROP_TOKEN);
		if(t!=null)this.props.put(PROP_TOKEN, t);
		this.token = t;
	}
	
	public Token getToken(){
		return this.token;
	}
	
	public boolean expired(){
		return this.token==null  ; // || this.token.ExpirationDate.getTime() < new Date().getTime();
	}
	
	public boolean valid(){
		return this.ipAddress.equals(this.token.IpAddress) && this.id.equals(this.token.SessionID);
	}
}