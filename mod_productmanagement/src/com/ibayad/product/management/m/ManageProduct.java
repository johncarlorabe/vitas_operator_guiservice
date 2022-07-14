package com.ibayad.product.management.m;

import com.tlc.common.DataRow;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.session.UISession;

public class ManageProduct extends Products{
	
	public boolean update(){
		UISession sess = this.getAuthorizedSession();
		DataRow r = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLPRODUCTS WHERE ID =?", this.id);
		StringBuilder query = new StringBuilder("BEGIN\n");
					  query.append("UPDATE TBLPRODUCTS SET PRODUCTNAME=?,PRODUCTCODE=?,NETWORK=?,IBAYADCODE=?,TELCOTAG=?,MINAMOUNT=?,MAXAMOUNT=?,DESCRIPTION=?,TYPE=? WHERE ID =?;\n");
					  query.append("INSERT INTO TBLPRODUCTHISTORY (CREATEDBY,TIMESTAMP,STATUS,SKU,NAME,PROVIDER,ID) VALUES(?,SYSDATE,?,?,?,?,?);\n");
					  query.append("UPDATE IBAYADPH.TBLIBAYADPRODUCTS SET PRODUCTNAME=?,SKU =?,INTERFACE=?,STATUS = 1,BRAND=?,MINAMOUNT=?,MAXAMOUNT=?,TYPE=? WHERE SKU = ? AND PRODUCTNAME=?;\n");
					  query.append("UPDATE TBLSERVICES SET NAME=?,STATUS=0,CATEGORY=? WHERE NAME = ?;\n");
					  query.append("UPDATE FMA.TBLPRODUCTS SET PRODUCTCODE=?,PRODUCTNAME=?,DESCRIPTION=?,AMOUNT=?,IBAYADCODE=? WHERE PRODUCTCODE=? AND PRODUCTNAME=? ;\n");
					  query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;\n");
					  
		return SystemInfo.getDb().QueryUpdate(query.toString(), 
				this.name,
				this.sku,
				this.category,
				"IB"+this.sku,
				this.sku,
				this.amount/100,
				this.amount/100,
				this.description,
				this.type,
				this.id,
				sess.getAccount().getUserName(),
				"UPDATE",
				this.sku,
				this.name,
				this.provider,
				this.id,
				this.name,
				"IB"+this.sku,
				this.productinterface,
				this.category,
				this.amount/100,
				this.amount/100,
				this.type,
				r.getString("IBAYADCODE"),
				r.getString("PRODUCTNAME"),
				this.name,
				this.category,
				r.getString("PRODUCTNAME"),
				this.sku,
				this.name,
				this.description,
				this.amount/100,
				"IB"+this.sku,
				r.getString("PRODUCTCODE"),
				r.getString("PRODUCTNAME"))>0;
	}
	
	public boolean exist(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLPRODUCTS WHERE PRODUCTNAME<>? AND PRODUCTCODE<>? AND ID =?", this.name,this.sku,this.id).size()>0;
	}

}
