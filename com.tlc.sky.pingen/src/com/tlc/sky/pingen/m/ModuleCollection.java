package com.tlc.sky.pingen.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

public class ModuleCollection extends ModelCollection{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public boolean hasRows() {
		DataRowCollection modules =  SystemInfo.getDb().QueryDataRows("SELECT * from TBLMODULEVITAS WHERE ISALLOW=1 ORDER BY MODULE ASC");
		if( modules!=null && modules.size()>0){
			
			for(DataRow r: modules){ 
		        ReportItem i = new ReportItem();
		        i.setProperty(r.getKey(0), r.getString(0));
		        i.setProperty(r.getKey(1), r.getString(1));
		        this.add(i);
		        
			}
			return true;
		}
		return false;
	}
	
	public boolean hasRowsGUiInterface(String guiinterface) {
		DataRowCollection modules =  SystemInfo.getDb().QueryDataRows("SELECT * from TBLMODULEVITAS WHERE ISALLOW=1 AND GUIINTERFACE=? ORDER BY MODULE ASC",guiinterface);
		if( modules!=null && modules.size()>0){
			
			for(DataRow r: modules){ 
		        ReportItem i = new ReportItem();
		        i.setProperty(r.getKey(0), r.getString(0));
		        i.setProperty(r.getKey(1), r.getString(1));
		        this.add(i);
		        
			}
			return true;
		}
		return false;
	}
	

}
