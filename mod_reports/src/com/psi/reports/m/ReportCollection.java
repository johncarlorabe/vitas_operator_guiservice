package com.psi.reports.m;

import com.tlc.common.DataRow;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.Model;

public class ReportCollection extends Model{
	protected String id;
	protected String datefrom;
	protected String dateto;
	
	public boolean generate(){
		
		DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERID=?", this.id);
		
		return SystemInfo.getDb().QueryUpdate("INSERT INTO BAYADPOC.TBLREPORTS (DATEFROM, DATETO, USERNAME, EMAIL, USERID) VALUES(TO_DATE(?, 'YYYY-MM-DD'),TO_DATE(?, 'YYYY-MM-DD'),?,?,?)", this.datefrom,this.dateto,row.getString("USERNAME"),row.getString("EMAIL"),this.id)>0;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

}
