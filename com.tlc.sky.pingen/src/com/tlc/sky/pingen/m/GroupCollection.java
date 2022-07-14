package com.tlc.sky.pingen.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.sky.pingen.arch.beans.PingenGroup;

public class GroupCollection extends ModelCollection{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public boolean hasRows() {
		DataRowCollection users =  SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLUSERSLEVEL WHERE ISOPERATOR = 0");
		if(users!=null && users.size()>0){
			for(DataRow r: users){
				PingenGroup i = new PingenGroup(r.getString("USERSLEVEL"));
				int x = 0;
				for(x=0;x<users.size();x++){
					i.setId(r.getInteger("ID"));
					i.setName(r.getString("USERSLEVEL"));
				}
				this.add(i);
			}
			
			return true;
		}
		return false;
	}
	
	public boolean hasRowsGuiInterface(String guiinterface) {
		DataRowCollection users =  SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLUSERSLEVEL WHERE ISOPERATOR = 0 AND GUIINTERFACE=?",guiinterface);
		if(users!=null && users.size()>0){
			for(DataRow r: users){
				PingenGroup i = new PingenGroup(r.getString("USERSLEVEL"));
				int x = 0;
				for(x=0;x<users.size();x++){
					i.setId(r.getInteger("ID"));
					i.setName(r.getString("USERSLEVEL"));
				}
				this.add(i);
			}
			
			return true;
		}
		return false;
	}


	
}
