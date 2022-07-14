package com.psi.serviceconfig.m;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;

@SuppressWarnings("serial")
public class GroupServicesCollection extends ModelCollection{
	private String accountnumber;
	
	public GroupServicesCollection(String accountnumber) {
		this.accountnumber = accountnumber;
	}
	@Override
	public boolean hasRows() {
		Services service = new Services();
		service.setServices(SystemInfo.getDb().QueryArray("SELECT SERVICES FROM TBLSERVICESCONFIG WHERE ACCOUNTNUMBER = ?",new Integer(0), this.accountnumber));
		this.add(service);
		return true;
	}
	

}
