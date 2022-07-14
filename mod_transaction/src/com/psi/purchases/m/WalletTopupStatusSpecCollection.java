package com.psi.purchases.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class WalletTopupStatusSpecCollection extends ModelCollection{
	
	protected String id;
	@Override
	public boolean hasRows() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLCASHINTRANSACTIONS WHERE ID = ? ",this.id);	     
	     
		if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
		         ReportItem m = new ReportItem();
		         m.setProperty("ReferenceImage", row.getString("REFERENCEIMAGE") == null ? "" : row.getString("REFERENCEIMAGE").toString());
		         
		         add(m);
	    	 }
	     }
		 return r.size() > 0;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

}
