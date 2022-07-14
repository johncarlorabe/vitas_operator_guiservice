package com.psi.accountmanagement.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;
import com.tlc.gui.modules.session.UISession;

public class UserAccountCollection extends ModelCollection {
	protected String username;
	protected String userslevel;
	
	@Override
	public boolean hasRows() {
		UISession sess = this.getAuthorizedSession();
		DataRowCollection rows = null;
		DataRow userslevel = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERNAME=?", sess.getAccount().getUserName());
		if(userslevel.getString("USERSLEVEL").equals("MANAGER")){
			rows = SystemInfo.getDb().QueryDataRows("SELECT BR.BRANCH,U.* FROM TBLUSERS U INNER JOIN TBLBRANCHES BR ON U.ACCOUNTNUMBER = BR.ACCOUNTNUMBER WHERE USERSLEVEL = 'CASHIER' AND USERNAME = ? AND U.ACCOUNTNUMBER = ?", new Object[]{this.username,userslevel.getString("ACCOUNTNUMBER")});
		}else{
			rows = SystemInfo.getDb().QueryDataRows("SELECT BR.BRANCH,U.* FROM TBLUSERS U INNER JOIN TBLBRANCHES BR ON U.ACCOUNTNUMBER = BR.ACCOUNTNUMBER WHERE USERSLEVEL = ? AND USERNAME = ?", new Object[]{this.userslevel,this.username});
		}
		if(!rows.isEmpty()){
			for(DataRow row:rows){
				
				ReportItem m = new ReportItem();
				m.setProperty("FirstName", row.getString("FIRSTNAME")==null ? "" : row.getString("FIRSTNAME").toString().toUpperCase());
				m.setProperty("LastName", row.getString("LASTNAME")==null ? "" : row.getString("LASTNAME").toString().toUpperCase());
				m.setProperty("Email", row.getString("EMAIL")==null ? "" : row.getString("EMAIL").toString());
				m.setProperty("Status", row.getString("STATUS")==null ? "" : row.getString("STATUS").toString());
				m.setProperty("Locked", row.getString("LOCKED")==null ? "" : row.getString("LOCKED").toString());
				m.setProperty("MiddleName", row.getString("MIDDLENAME")==null ? "" : row.getString("MIDDLENAME").toString().toUpperCase());
				m.setProperty("Msisdn", row.getString("MSISDN")==null ? "" : row.getString("MSISDN").toString());
				m.setProperty("City", row.getString("CITY")==null ? "" : row.getString("CITY").toString());
				m.setProperty("Province", row.getString("PROVINCE")==null ? "" : row.getString("PROVINCE").toString());
				m.setProperty("Country", row.getString("COUNTRY")==null ? "" : row.getString("COUNTRY").toString());
				m.setProperty("UserId", row.getString("USERID")==null ? "" : row.getString("USERID").toString());
				m.setProperty("BranchName", row.getString("BRANCH")==null? "" : row.getString("BRANCH").toString());
				add(m);
			}
		}
		return rows.size()>0;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String userName) {
		this.username = userName;
	}
	
	public String getUserslevel(){
		return userslevel;
	}
	public void setUserslevel(String usersLevel){
		this.userslevel = usersLevel;
	}
}
