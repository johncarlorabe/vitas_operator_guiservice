package com.ibayad.product.management.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class ProductCollection extends ModelCollection{
	protected String type;
	@Override
	public boolean hasRows() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT P.ID,P.PRODUCTNAME,P.PRODUCTCODE,P.NETWORK,P.IBAYADCODE,P.TELCOTAG,NVL(MINAMOUNT,0) MINAMOUNT,NVL(MAXAMOUNT,0) MAXAMOUNT,BUNDLED,P.ACCOUNTFORMAT,P.FIXEDFEE,P.PERCENTFEE,P.TYPE,P.DESCRIPTION,(SELECT MAX(TIMESTAMP) FROM TBLPRODUCTHISTORY WHERE ID = P.ID) TIMESTAMP,(SELECT MAX(CREATEDBY) FROM TBLPRODUCTHISTORY WHERE ID = P.ID) CREATEDBY,(SELECT MAX(STATUS) FROM TBLPRODUCTHISTORY WHERE ID = P.ID) STATUS,(SELECT MAX(PROVIDER) FROM TBLPRODUCTHISTORY WHERE ID = P.ID) PROVIDER FROM TBLPRODUCTS P WHERE TYPE IN ('EMONEY','AIRTIME','INSURANCE')");
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
	    		 ReportItem m = new ReportItem();
	    		 for (String key : row.keySet()) {
			 	        m.setProperty(key, row.getString(key).toString());
			 	 }
		         m.setProperty("MINAMOUNT", row.getString("MINAMOUNT") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("MINAMOUNT").toString())*100));
		         m.setProperty("MAXAMOUNT", row.getString("MAXAMOUNT") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("MAXAMOUNT").toString())*100));
		         
		         add(m);
	    	 }
	     }
	     return r.size() > 0;
	}
	public boolean hasRowspertype() {
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT P.ID,P.PRODUCTNAME,P.PRODUCTCODE,P.NETWORK,P.IBAYADCODE,P.TELCOTAG,NVL(MINAMOUNT,0) MINAMOUNT,NVL(MAXAMOUNT,0) MAXAMOUNT,BUNDLED,P.ACCOUNTFORMAT,P.FIXEDFEE,P.PERCENTFEE,P.TYPE,P.DESCRIPTION,(SELECT MAX(TIMESTAMP) FROM TBLPRODUCTHISTORY WHERE ID = P.ID) TIMESTAMP,(SELECT MAX(CREATEDBY) FROM TBLPRODUCTHISTORY WHERE ID = P.ID) CREATEDBY,(SELECT MAX(STATUS) FROM TBLPRODUCTHISTORY WHERE ID = P.ID) STATUS,(SELECT MAX(PROVIDER) FROM TBLPRODUCTHISTORY WHERE ID = P.ID) PROVIDER FROM TBLPRODUCTS P WHERE TYPE = ?",this.type);
	     
	     if (!r.isEmpty())
	     {
	    	 for(DataRow row: r){
	    		 ReportItem m = new ReportItem();
	    		 for (String key : row.keySet()) {
			 	        m.setProperty(key, row.getString(key).toString());
			 	 }
	    		 m.setProperty("MINAMOUNT", row.getString("MINAMOUNT") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("MINAMOUNT").toString())*100));
		         m.setProperty("MAXAMOUNT", row.getString("MAXAMOUNT") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("MAXAMOUNT").toString())*100));
		         
		         add(m);
	    	 }
	     }
	     return r.size() > 0;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
