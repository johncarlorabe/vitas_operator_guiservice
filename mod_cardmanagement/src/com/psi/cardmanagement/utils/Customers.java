package com.psi.cardmanagement.utils;

import com.tlc.gui.modules.common.Model;

public class Customers extends Model{
	
	protected String email;
	protected String firstname;
	protected String lastname;
	protected String midname;
	protected String msisdn;
	protected String password;
	protected String branch;
	protected String manager;
	protected String username;
	protected String id;
	protected String accountnumber;
	protected String status;
	protected String city;
	protected String specificaddress;
	protected String reason;
	protected String grade;
	protected String postalcode;
	protected String coordinates;
	protected String type;
	protected String nationality;
	protected String gender;
	protected String placeofbirth;
	protected String dateofbirth;
	protected String idcardtype;
	protected String idnumber;
	protected String permanentaddress;
	protected String presentaddress;
	protected String occupation;
	protected String mothermaiden;
	protected String locked;
	protected String salary;
	protected String sourceoffund;
	protected String purposeoftrans;
	protected String purposepayment;
	protected String purposepurchase;
	protected String purposetransfer;
	protected String purposeother;
	protected String remarks;
	protected String searchedvalue;
	
	protected String presentcity;
	protected String presentprovince;
	protected String permanentcity;
	protected String permanentprovince;
	protected String accounttype;
	
	protected String expirydate;
	protected String cardnumber;
	
	
	protected String datamsisdn;
	protected String dataemail;
	protected String alternateMsisdn;
	
	public static final String PROP_REJECTREASON="REJECTREASON";
	protected String[] rejectreason;
	
	public static final String PROP_REASONEN="REASONEN";
	protected String[] reasonen;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getMidname() {
		return midname;
	}
	public void setMidname(String midname) {
		this.midname = midname;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAccountnumber() {
		return accountnumber;
	}
	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getSpecificAddress() {
		return specificaddress;
	}
	public void setSpecificAddress(String specificaddress) {
		this.specificaddress = specificaddress;
	}
	
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	public String getPostalCode() {
		return postalcode;
	}
	public void setPostalCode(String postalcode) {
		this.postalcode = postalcode;
	}
	
	public String getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPlaceofbirth() {
		return placeofbirth;
	}
	public void setPlaceofbirth(String placeofbirth) {
		this.placeofbirth = placeofbirth;
	}
	public String getDateofbirth() {
		return dateofbirth;
	}
	public void setDateofbirth(String dateofbirth) {
		this.dateofbirth = dateofbirth;
	}
	public String getIdcardtype() {
		return idcardtype;
	}
	public void setIdcardtype(String idcardtype) {
		this.idcardtype = idcardtype;
	}
	public String getIdnumber() {
		return idnumber;
	}
	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}
	public String getPermanentaddress() {
		return permanentaddress;
	}
	public void setPermanentaddress(String permanentaddress) {
		this.permanentaddress = permanentaddress;
	}
	public String getPresentaddress() {
		return presentaddress;
	}
	public void setPresentaddress(String presentaddress) {
		this.presentaddress = presentaddress;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String getMothermaiden() {
		return mothermaiden;
	}
	public void setMothermaiden(String mothermaiden) {
		this.mothermaiden = mothermaiden;
	}
	
	public String getLocked() {
		return locked;
	}
	public void setLocked(String locked) {
		this.locked = locked;
	}
	
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	
	public String getSourceOfFund() {
		return sourceoffund;
	}
	public void setSourceOfFund(String sourceoffund) {
		this.sourceoffund = sourceoffund;
	}
	
	public String getPurposeOfTrans() {
		return purposeoftrans;
	}
	public void setPurposeOfTrans(String purposeoftrans) {
		this.purposeoftrans = purposeoftrans;
	}
	
	public String getPurposePayment() {
		return purposepayment;
	}
	public void setPurposePayment(String purposepayment) {
		this.purposepayment = purposepayment;
	}
	
	public String getPurposePurchase() {
		return purposepayment;
	}
	public void setPurposePurchase(String purposepurchase) {
		this.purposepurchase = purposepurchase;
	}
	
	public String getPurposeTransfer() {
		return purposepayment;
	}
	public void setPurposeTransfer(String purposetransfer) {
		this.purposetransfer = purposetransfer;
	}
	
	public String getPurposeOther() {
		return purposepayment;
	}
	public void setPurposeOther(String purposeother) {
		this.purposeother = purposeother;
	}
	
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getPresentCity() {
		return presentcity;
	}
	public void setPresentCity(String presentcity) {
		this.presentcity = presentcity;
	}
	
	public String getPresentProvince() {
		return presentprovince;
	}
	public void setPresentProvince(String presentprovince) {
		this.presentprovince = presentprovince;
	}
	
	public String getPermanentCity() {
		return permanentcity;
	}
	public void setPermanentCity(String permanentcity) {
		this.permanentcity = permanentcity;
	}
	
	public String getPermanentProvince() {
		return permanentprovince;
	}
	public void setPermanentProvince(String permanentprovince) {
		this.permanentprovince = permanentprovince;
	}
	
	public String getAccountType() {
		return accounttype;
	}
	public void setAccountType(String accounttype) {
		this.accounttype = accounttype;
	}
	
	public String[] getRejectReason() {
		return rejectreason;
	}
	public void setRejectReason(String[] rejectreason) {
		this.props.put(PROP_REJECTREASON, rejectreason);
		this.rejectreason = rejectreason;
	}
	
	public String[] getReasonEn() {
		return reasonen;
	}
	public void setReasonEn(String[] reasonen) {
		this.props.put(PROP_REASONEN, reasonen);
		this.reasonen = reasonen;
	}
	public String getExpirydate() {
		return expirydate;
	}
	public void setExpirydate(String expirydate) {
		this.expirydate = expirydate;
	}
	public String getCardnumber() {
		return cardnumber;
	}
	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}
	public String getDatamsisdn() {
		return datamsisdn;
	}
	public void setDatamsisdn(String datamsisdn) {
		this.datamsisdn = datamsisdn;
	}
	public String getDataemail() {
		return dataemail;
	}
	public void setDataemail(String dataemail) {
		this.dataemail = dataemail;
	}
	public String getAlternateMsisdn() {
		return alternateMsisdn;
	}
	public void setAlternateMsisdn(String alternateMsisdn) {
		this.alternateMsisdn = alternateMsisdn;
	}
	public String getSearchedvalue() {
		return searchedvalue;
	}
	public void setSearchedvalue(String searchedvalue) {
		this.searchedvalue = searchedvalue;
	}
	
}
