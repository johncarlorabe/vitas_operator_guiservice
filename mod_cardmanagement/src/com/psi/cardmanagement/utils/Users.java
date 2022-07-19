package com.psi.cardmanagement.utils;

import com.tlc.gui.modules.common.Model;

public class Users extends Model{
	
	protected String email;
	protected String firstname;
	protected String lastname;
	protected String midname;
	protected String msisdn;
	protected String country;
	protected String province;
	protected String city;
	protected String code;
	protected String password;
	protected String branch;
	protected String manager;
	protected String id;
	protected String userid;
	protected String userslevel;
	protected String dateofbirth;
	protected String natureofwork;
	protected String sourceoffund;
	protected String placeoofbirth;
	protected String url;
	protected String status;
	protected String locked;
	
	public String getIsLocked() {
		return locked;
	}
	public void setIsLocked(String isLocked) {
		this.locked = isLocked;
	}
	
	public String getStatus(){
		return status;
	}
	
	public void setStatus(String status){
		this.status = status;
	}
	
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
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUserslevel() {
		return userslevel;
	}
	public void setUserslevel(String userslevel) {
		this.userslevel = userslevel;
	}
	public String getDateofbirth() {
		return dateofbirth;
	}
	public void setDateofbirth(String dateofbirth) {
		this.dateofbirth = dateofbirth;
	}
	public String getNatureofwork() {
		return natureofwork;
	}
	public void setNatureofwork(String natureofwork) {
		this.natureofwork = natureofwork;
	}
	public String getSourceoffund() {
		return sourceoffund;
	}
	public void setSourceoffund(String sourceoffund) {
		this.sourceoffund = sourceoffund;
	}
	public String getPlaceoofbirth() {
		return placeoofbirth;
	}
	public void setPlaceoofbirth(String placeoofbirth) {
		this.placeoofbirth = placeoofbirth;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	

}
