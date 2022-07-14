package com.tlc.gui.modules.session;

import java.util.Date;

import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.interfaces.AbstractPassword;

public class UIAccount extends Model{
	
	public static final String PROP_ID="ID";
	public static final String PROP_PASSWORD="PASSWORD";
	public static final String PROP_USERNAME="USERNAME";
	public static final String PROP_MSISDN="MSISDN";
	public static final String PROP_FIRSTNAME="FIRSTNAME";
	public static final String PROP_LASTNAME="LASTNAME";
	public static final String PROP_EMAIL="EMAIL";
	public static final String PROP_CREATEDBY="CREATEDBY";
	public static final String PROP_MODIFIEDBY="MODIFIEDBY";
	public static final String PROP_DATEMODIFIED="DATEMODIFIED";
	public static final String PROP_DATECREATED="DATECREATED";
	public static final String PROP_LASTLOGOUT="LASTLOGOUT";
	public static final String PROP_LASTLOGIN="LASTLOGIN";
	public static final String PROP_LASTBLOCKED="LASTBLOCKED";
	public static final String PROP_LASTPASSWORDCHANGE="LASTPASSWORDCHANGE";
	public static final String PROP_LOCKED="LOCKED";
	public static final String PROP_GROUP="GROUP";
	public static final String PROP_ALLOWEDIP="ALLOWEDIP";
	public static final String PROP_DEPARTMENT="DEPARTMENT";
	public static final String PROP_INVALIDPASSWORDCOUNT="INVALIDPASSWORDCOUNT";
	public static final String PROP_STATUS="STATUS";
	
	protected int id;
	protected String username;
	protected UIPassword password;
	protected String msisdn;
	protected String firstName;
	protected String lastName;
	protected String email;
	protected UIAccount createdBy;
	protected UIAccount modifiedBy;
	protected Date dateModified;
	protected Date dateCreated;
	protected Date lastLogout;
	protected Date lastLogin;
	protected Date lastBlocked;
	protected Date lastPasswordChange;
	protected String locked;
	protected UIGroup group;
	protected String allowedIp;
	protected String department;
	protected int invalidPasswordCount;
	protected String status;
	
	

	public void setUsername(String username) {
		this.props.put(PROP_USERNAME,username);
		this.username = username;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.props.put(PROP_FIRSTNAME,firstName);
		this.firstName = firstName;
	}
	
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.props.put(PROP_MSISDN,msisdn);
		this.msisdn = msisdn;
	}	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.props.put(PROP_LASTNAME,lastName);
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.props.put(PROP_EMAIL,email);
		this.email = email;
	}
	public UIAccount getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(UIAccount createdBy) {
		this.props.put(PROP_CREATEDBY,createdBy.getUserName());
		this.createdBy = createdBy;
	}
	public UIAccount getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(UIAccount modifiedBy) {
		this.props.put(PROP_MODIFIEDBY,modifiedBy.getUserName());
		this.modifiedBy = modifiedBy;
	}
	public Date getDateModified() {
		return dateModified;
	}
	public void setDateModified(Date dateModified) {
		this.props.put(PROP_DATEMODIFIED,dateModified.toString());
		this.dateModified = dateModified;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.props.put(PROP_DATECREATED,dateCreated.toString());
		this.dateCreated = dateCreated;
	}
	public Date getLastLogout() {
		return lastLogout;
	}
	public void setLastLogout(Date lastLogout) {
		this.props.put(PROP_LASTLOGOUT,lastLogout.toString());
		this.lastLogout = lastLogout;
	}
	public Date getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Date lastLogin) {
		this.props.put(PROP_LASTLOGIN,lastLogin.toString());
		this.lastLogin = lastLogin;
	}
	public Date getLastBlocked() {
		return lastBlocked;
	}
	public void setLastBlocked(Date lastBlocked) {
		this.props.put(PROP_LASTBLOCKED,lastBlocked.toString());
		this.lastBlocked = lastBlocked;
	}
	public Date getLastPasswordChange() {
		return lastPasswordChange;
	}
	public void setLastPasswordChange(Date lastPasswordChange) {
		this.props.put(PROP_LASTPASSWORDCHANGE,lastPasswordChange.toString());
		this.lastPasswordChange = lastPasswordChange;
	}
	public String getLocked() {
		return locked;
	}
	public void setLocked(String locked) {
		this.props.put(PROP_LOCKED,locked.toString());
		this.locked = locked;
	}
	public UIGroup getGroup() {
		return group;
	}
	public void setGroup(UIGroup group) {
		this.props.put(PROP_GROUP,group);
		this.group = group;
	}
	public String getAllowedIp() {
		return allowedIp;
	}
	public void setAllowedIp(String allowedIp) {
		this.props.put(PROP_ALLOWEDIP,allowedIp);
		this.allowedIp = allowedIp;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.props.put(PROP_DEPARTMENT,department);
		this.department = department;
	}
	public int getInvalidPasswordCount() {
		return invalidPasswordCount;
	}
	public void setInvalidPasswordCount(int invalidPasswordCount) {
		this.props.put(PROP_INVALIDPASSWORDCOUNT,invalidPasswordCount);
		this.invalidPasswordCount = invalidPasswordCount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setPassword(UIPassword password) {
		this.props.put(PROP_PASSWORD, password);
		this.password = password;
	}
	public void setId(int id){
		this.props.put(PROP_ID, id);
		this.id = id;
	}
	public int getId(){
		return this.id;
	}
	
	public String getUserName(){
		return this.username;
	}
	
	public UIPassword getPassword(){
		return this.password;
	}
	
}