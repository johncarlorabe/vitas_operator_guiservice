package com.psi.reports.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class HomekingHistoryCollection extends ModelCollection{
	protected String datefrom;
	protected String dateto;
	protected String id;

	@Override
	public boolean hasRows() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM VWHOMEKINGTRANSACTIONS WHERE TO_CHAR(TIMESTAMP,'YYYY-MM-DD') BETWEEN ? AND ?",this.datefrom,this.dateto);
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
	    		 ReportItem m = new ReportItem();
	    		 for (String key : row.keySet()) {
			 	        m.setProperty(key, row.getString(key).toString());
			 	 }
		         m.setProperty("AMOUNT", row.getString("AMOUNT") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("AMOUNT").toString())));
		         m.setProperty("TOTALAMOUNTPAID", row.getString("TOTALAMOUNTPAID") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("TOTALAMOUNTPAID").toString())));
		         m.setProperty("COUPONDISCOUNT", row.getString("COUPONDISCOUNT") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("COUPONDISCOUNT").toString())));
		         m.setProperty("DISCOUNT", row.getString("DISCOUNT") == null || row.getString("DISCOUNT") == "" ? "" : LongUtil.toString(Long.parseLong(row.getString("DISCOUNT").toString())));
			     m.setProperty("DISCOUNTEDAMOUNT", row.getString("DISCOUNTEDAMOUNT") == null || row.getString("DISCOUNTEDAMOUNT") == "" ? "" : LongUtil.toString(Long.parseLong(row.getString("DISCOUNTEDAMOUNT").toString())));
		         add(m);
	    	 }
	     }
	     return r.size() > 0;
	}

	public String getDatefrom() {
		return datefrom;
	}

	public void setDatefrom(String datefrom) {
		this.datefrom = datefrom;
	}

	public String getDateto() {
		return dateto;
	}

	public void setDateto(String dateto) {
		this.dateto = dateto;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	

}
