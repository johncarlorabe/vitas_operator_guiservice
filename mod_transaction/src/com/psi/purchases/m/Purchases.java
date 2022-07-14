package com.psi.purchases.m;

import java.text.ParseException;

import com.psi.purchases.util.Prepaid;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.common.ObjectState;

public class Purchases extends Model{
	protected String id;
	protected String brand;
	protected String amount;
	protected String msisdn;
	protected String reference;
	protected String isoverride;
	protected String overrideusername;
	protected String overridepassword;
	protected String macaddress;
	
	public boolean purchase() throws ParseException, Exception {
		Prepaid load = new Prepaid();
		String id = "";
		if(this.isoverride.equals("1")){
			id = SystemInfo.getDb().QueryScalar("SELECT USERID FROM TBLUSERS WHERE USERNAME=?", "", this.overrideusername);
		}else{
			id= this.id;
		}
		
		if(load.purchase(id, this.brand, this.amount, this.msisdn,this.reference,this.macaddress )){
			this.setState(new ObjectState(load.getState().getCode(),load.getState().getMessage()));
			return true;
		}else{
			this.setState(new ObjectState(load.getState().getCode(),load.getState().getMessage()));
			return false;
		}
	}
	public boolean validateoverride(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERNAME=? AND PASSWORD=ENCRYPT(?,?,USERNAME)", this.overrideusername,this.overridepassword,SystemInfo.getDb().getCrypt()).size()>0;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
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
