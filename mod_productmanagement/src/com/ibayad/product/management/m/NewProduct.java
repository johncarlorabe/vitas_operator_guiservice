package com.ibayad.product.management.m;

import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.session.UISession;

public class NewProduct extends Products{
	
	public boolean create(){
		UISession sess = this.getAuthorizedSession();
		StringBuilder query = new StringBuilder("BEGIN\n");
					  query.append("INSERT INTO TBLPRODUCTHISTORY (CREATEDBY,TIMESTAMP,STATUS,SKU,NAME,PROVIDER) VALUES(?,SYSDATE,?,?,?,?);\n");
					  query.append("INSERT INTO TBLPRODUCTS (PRODUCTNAME,PRODUCTCODE,NETWORK,IBAYADCODE,TELCOTAG,MINAMOUNT,MAXAMOUNT,TYPE,DESCRIPTION) VALUES(?,?,?,?,?,?,?,?,?);\n");				  
					  query.append("INSERT INTO IBAYADPH.TBLIBAYADPRODUCTS (PRODUCTNAME,SKU,INTERFACE,STATUS,BRAND,MINAMOUNT,MAXAMOUNT,TYPE) VALUES(?,?,?,1,?,?,?,?);\n");
					  query.append("INSERT INTO TBLSERVICES (NAME,TYPE,STATUS,CATEGORY) VALUES(?,?,0,?);\n");
					  query.append("INSERT INTO FMA.TBLPRODUCTS (PRODUCTCODE,PRODUCTNAME,DESCRIPTION,AMOUNT,IBAYADCODE,STATUS) VALUES(?,?,?,?,?,1);\n");
					  query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;\n");
					  
		return SystemInfo.getDb().QueryUpdate(query.toString(), 
				sess.getAccount().getUserName(),
				"NEW",
				this.sku,
				this.name,
				this.provider,
				this.name,
				this.sku,
				this.category,
				"IB"+this.sku,
				this.sku,
				this.amount/100,
				this.amount/100,
				this.type,
				this.description,
				this.name,
				"IB"+this.sku,
				this.productinterface,
				this.category,
				this.amount/100,
				this.amount/100,
				this.type,
				this.name,
				this.type,
				this.category,
				this.sku,
				this.name,
				this.description,
				this.amount/100,
				"IB"+this.sku)>0;
	}
	
	public boolean exist(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLPRODUCTS WHERE PRODUCTNAME=? AND PRODUCTCODE = ?", this.name,this.sku).size()>0;
	}

}
