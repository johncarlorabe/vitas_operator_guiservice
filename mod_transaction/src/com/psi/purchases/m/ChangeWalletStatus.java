package com.psi.purchases.m;

import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.Model;

public class ChangeWalletStatus extends Model{
	
	protected String id;
	protected String linkid;
	protected String reference;
	protected String dateofdeposit;
	protected String timeofdeposit;
	protected String receipt;
	protected String bankname;
	protected String bankcode;
	protected String password;
	protected String details;
	protected String depositchannel;
	
	public boolean update(){
		return SystemInfo.getDb().QueryUpdate("UPDATE TBLCASHINTRANSACTIONS SET DEPOSITCHANNEL=?, DESCRIPTION=?, STATUS='PAID',BANKNAME=?,BANKBRANCHCODE=?,DATEOFDEPOSIT = TO_DATE(?,'YYYY-MM-DD'),TIMEOFDEPOSIT=?,REFERENCEIMAGE=? WHERE TYPE='DIRECALLOC' AND ID = ?",this.depositchannel, this.details, this.bankname,this.bankcode,this.dateofdeposit,this.timeofdeposit,this.receipt, this.id)>0;
	}
	
	public boolean exist(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLCASHINTRANSACTIONS WHERE TYPE='DIRECALLOC' AND ID = ?", this.id).size()>0;
	}
	public boolean validate(){
		return SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLUSERS WHERE USERID =? AND PASSWORD=ENCRYPT(?,?,USERNAME)", this.linkid,this.password,SystemInfo.getDb().getCrypt()).size()>0;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getLinkid() {
		return linkid;
	}
	public void setLinkid(String linkid) {
		this.linkid = linkid;
	}

	public String getDateofdeposit() {
		return dateofdeposit;
	}

	public void setDateofdeposit(String dateofdeposit) {
		this.dateofdeposit = dateofdeposit;
	}

	public String getTimeofdeposit() {
		return timeofdeposit;
	}

	public void setTimeofdeposit(String timeofdeposit) {
		this.timeofdeposit = timeofdeposit;
	}

	public String getReceipt() {
		return receipt;
	}

	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}

	public String getBankname() {
		return bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	public String getBankcode() {
		return bankcode;
	}

	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getDepositchannel() {
		return depositchannel;
	}

	public void setDepositchannel(String depositchannel) {
		this.depositchannel = depositchannel;
	}
	
	
}
