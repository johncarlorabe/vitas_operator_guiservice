package com.tlc.sky.pingen.m;

import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.sky.pingen.arch.beans.PingenGroup;

public class GroupModulesCollection extends ModelCollection{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	
	public GroupModulesCollection(String id) {
		this.id = id;
	}


	@Override
	public boolean hasRows() {
			PingenGroup group = new PingenGroup(null);
			group.setModules(SystemInfo.getDb().QueryArray("SELECT MODULEID FROM TBLGROUPMODULESVITAS WHERE GROUPID=?",new Integer(0),this.id));
			group.setNotifications(SystemInfo.getDb().QueryArray("SELECT MODULEID FROM TBLNOTIFICATIONMGR WHERE GROUPID=?",new Integer(0),this.id));
			this.add(group);
			return true;
		
	}

}
