package com.psi.serviceconfig.m;

import com.tlc.gui.modules.common.Model;

public class Services extends Model{

	public static final String PROP_SERVICES="SERVICES";
	protected Integer[] services;
	
	public Integer[] getServices() {
		return services;
	}
	public void setServices(Integer[] services) {
		this.props.put(PROP_SERVICES,services);
		this.services = services;
	}
	
	
}
