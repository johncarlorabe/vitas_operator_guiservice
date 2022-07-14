package com.psi.purchases.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

@SuppressWarnings("serial")
public class ProductCollection extends ModelCollection{
 protected String ndc;
	@Override
	public boolean hasRows() {
		
		DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT PRODUCTNAME,PRODUCTCODE,MINAMOUNT,NETWORK,TELCOTAG,MAXAMOUNT FROM TBLNDCLIST NDC INNER JOIN TBLGSMNETWORK GSM ON NDC.GSMNETWORKID = GSM.ID INNER JOIN TBLPRODUCTS P ON GSM.NAME = P.NETWORK WHERE NDC = ? ORDER BY PRODUCTNAME,MINAMOUNT ",this.ndc);	     
		     if (!r.isEmpty())
		     {
		    	 for(DataRow row: r){
			         ReportItem m = new ReportItem();
			         m.setProperty("ProductName", row.getString("PRODUCTNAME") == null ? "" : row.getString("PRODUCTNAME").toString());
			         m.setProperty("ProductCode", row.getString("PRODUCTCODE") == null ? "" : row.getString("PRODUCTCODE").toString());
			         m.setProperty("Amount", row.getString("MINAMOUNT") == null ? "" : row.getString("MINAMOUNT").toString());
			         m.setProperty("Network", row.getString("NETWORK") == null ? "" : row.getString("NETWORK").toString());
			         m.setProperty("TelcoTag", row.getString("TELCOTAG") == null ? "" : row.getString("TELCOTAG").toString());
			         m.setProperty("MaxAmount", row.getString("MAXAMOUNT") == null ? "" : row.getString("MAXAMOUNT").toString());
			         
			         add(m);
		    	 }
		     }
		     return r.size()>0;
			
	}
	public String getNdc() {
		return ndc;
	}
	public void setNdc(String ndc) {
		this.ndc = ndc;
	}

}
