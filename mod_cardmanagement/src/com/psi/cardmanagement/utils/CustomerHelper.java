package com.psi.cardmanagement.utils;


import com.tlc.common.DataRow;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.Model;

public class CustomerHelper extends Model {
	
	private String password;
	private String username;
	private String id;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public boolean exists(){
		DataRow row = this.db.QueryDataRow("SELECT DECRYPT(PASSWORD,? , USERNAME) AS PASSWORD FROM SOLBPH.TBLUSERS WHERE PASSWORD = ENCRYPT( ? ,?, USERNAME) AND USERID = ?",SystemInfo.getDb().getCrypt(), this.password, SystemInfo.getDb().getCrypt(),this.id);
		return row.size()>0;	
	}
	

}
