package com.psi.purchases.m;

import java.text.ParseException;

import com.psi.purchases.util.Bill;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.common.ObjectState;

public class BillsPayment extends Model{
	protected String ordernumber;
	protected String trackingnumber;
	protected String id;
	protected String billertag;
	protected String isoverride;
	protected String overrideusername;
	protected String overridepassword;
	protected String macaddress;
	
	public boolean billspayment() throws ParseException, Exception {
		Bill bill = new Bill();
		String id = "";
		if(this.isoverride.equals("1")){
			id = SystemInfo.getDb().QueryScalar("SELECT USERID FROM TBLUSERS WHERE USERNAME=?", "", this.overrideusername);
		}else{
			id = this.id;
		}
		if(bill.billspayment(this.ordernumber, this.trackingnumber, id, this.billertag,this.macaddress)){
			bill.setState(new ObjectState(bill.getState().getCode(),bill.getState().getMessage()));
			return true;
		}else{
			this.setState(bill.getState());
			return false;
		}
	}
	public boolean validateoverride(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERNAME=? AND PASSWORD=ENCRYPT(?,?,USERNAME)", this.overrideusername,this.overridepassword,SystemInfo.getDb().getCrypt()).size()>0;
	}
	public String getOrdernumber() {
		return ordernumber;
	}
	public void setOrdernumber(String ordernumber) {
		this.ordernumber = ordernumber;
	}
	public String getTrackingnumber() {
		return trackingnumber;
	}
	public void setTrackingnumber(String trackingnumber) {
		this.trackingnumber = trackingnumber;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBillertag() {
		return billertag;
	}
	public void setBillertag(String billertag) {
		this.billertag = billertag;
	}
	public String getIsoverride() {
		return isoverride;
	}
	public void setIsoverride(String isoverride) {
		this.isoverride = isoverride;
	}
	public String getOverrideusername() {
		return overrideusername;
	}
	public void setOverrideusername(String overrideusername) {
		this.overrideusername = overrideusername;
	}
	public String getOverridepassword() {
		return overridepassword;
	}
	public void setOverridepassword(String overridepassword) {
		this.overridepassword = overridepassword;
	}
	public String getMacaddress() {
		return macaddress;
	}
	public void setMacaddress(String macaddress) {
		this.macaddress = macaddress;
	}

}
