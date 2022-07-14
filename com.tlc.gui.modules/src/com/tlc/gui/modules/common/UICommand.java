package com.tlc.gui.modules.common;

public abstract class UICommand {
	
	protected PluginHeaders reqHeaders;
	protected PluginHeaders resHeaders;
	protected RequestParameters params;
	public UICommand(PluginHeaders headers,RequestParameters params){
		this.reqHeaders = headers;
		this.params = params;
	}
	public UICommand(){
		super();
	}
	public void setRequest(RequestParameters params){
		this.params = params;
	}
	
	public void setHeaders(PluginHeaders headers){
		this.reqHeaders = headers;
		
	}
	
	public PluginHeaders getResponseHeaders(){
		return this.resHeaders;
	}
	
	/**
	 * 
	 * @return the rendered value
	 */
	public abstract IView execute();
	/**
	 * 
	 * @return the keyword to identify the controller
	 */
	public abstract String getKey();
	
	/**
	 * 
	 * @return the module Id of the current command
	 */
	public abstract int getId();
}
