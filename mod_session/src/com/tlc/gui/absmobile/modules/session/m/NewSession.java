package com.tlc.gui.absmobile.modules.session.m;

import java.util.Calendar;
import java.util.Date;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.psi.ibc.common.HashFunction;
import com.tlc.absmobile.bp.session.AbstractNewSession;
import com.tlc.common.DataRow;
import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;
import com.tlc.common.DbWrapper.InOutParameter;
import com.tlc.common.StringUtil;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.PluginHeaders;
import com.tlc.gui.modules.session.Token;
import com.tlc.gui.modules.session.UIAccount;
import com.tlc.gui.modules.session.UIGroup;
import com.tlc.gui.modules.session.UIPassword;

public class NewSession extends AbstractNewSession{



	protected int status;
	
	public static NewSession parse(PluginHeaders h){
		NewSession ret = new NewSession(Token._new(h));
		byte[] auth = StringUtil.base64Decode(h.get("authorization"));
		byte[] sess = StringUtil.base64Decode(h.get("session"));
		String sessInfo[] = new String(sess).split("@");
		String auths[] = new String(auth).split(":");
		
		ret.setAccount(new UIAccount());
		ret.getAccount().setUsername(auths[0]);
		ret.getAccount().setPassword(new UIPassword(auths[1]));
		ret.setTokenurl(auths[2]);
		ret.setSessionurl(auths[3]);
		ret.getToken().Username = auths[0];
		ret.setIpAddress(sessInfo[1]);
		ret.setId(sessInfo[0]);
		return ret;
	}
	
	public NewSession(Token t) {
		super(t);
	}
	public static final String PROP_ISFISRTLOGON="ISFIRSTLOGON";
	public static final String PROP_EMAIL="EMAIL";
	public static final String PROP_ACCOUNTNUMBER="ACCOUNTNUMBER";
	public static final String PROP_SESSIONID = "SESSIONID";
	public static final String PROP_BRANCHCODE = "BRANCHCODE";
	public static final String PROP_TERMINAL = "TERMINALID";
	public static final String PROP_BRANCHNAME = "BRANCHNAME";
	public static final String PROP_ISAPIUSER = "ISAPIUSER";
	public static final String PROP_TOKENURL="TOKENURL";
	public static final String PROP_SESSIONURL="SESSIONURL";
	public static final String PROP_MACADDRESS = "MACADDRESS";
	public static final String PROP_BRANCH_COUNTRY = "BRANCHCOUNTRY";
	  public static final String PROP_IS_COMPANY = "ISCOMPANY";
	  public static final String PROP_GUI = "GUI";
	static final int MAX_TIME_IN_HOURS = 1;
	
	public String tokenurl;
	public String sessionurl;
	public String accountnumber;
	public String sessionid;
	public String isfirstlogon;
	public String email;
	public String branchcode;
	public String terminalid;
	public String branchname;
	public String isapiuser;
	public String macaddress;
	public String branchcountry;
	public String iscompany;
    public String gui;
	
	@Override
	public boolean start() {
		this.setState(new ObjectState(ObjectState.ERR_SYSTEMBUSY_GENERIC));
	
		StringBuilder query = new StringBuilder("");
		query.append("DECLARE\n");
		query.append("	vMODULEID 				NUMBER;\n");
		query.append("	vEXISTS 				NUMBER;\n");
		query.append("	vTMPPWD 				VARCHAR(50);\n");
		query.append("	vUSERNAME				VARCHAR(50);\n");
		query.append("	vPASSWORD				VARCHAR(100);\n");
		query.append("	vIPADDRESS				VARCHAR(20);\n");
		query.append("	vDEFAULTPASS			VARCHAR(20);\n");
		query.append("	vTOTALINVALIDPASSWORD 	NUMBER;\n");
		query.append("	vINVALIDCOUNT			NUMBER;\n");
		query.append("	vEXPIRATIONDAYS			NUMBER;\n");
		query.append("	vLOCKED					VARCHAR(3);\n");
		query.append("	vLASTNAME				VARCHAR(50);\n");
		query.append("	vFIRSTNAME				VARCHAR(50);\n");
		query.append("	vALLOWEDIP				VARCHAR(50);\n");
		query.append("	vSTATUS					VARCHAR(10);\n");
		query.append("	vLASTLOGIN				DATE;\n");
		query.append("	vGROUPID				NUMBER;\n");
		query.append("	vGROUPNAME				VARCHAR(50);\n");
		query.append("	vEXPIRED				NUMBER;\n");
		query.append("	vSESSIONID				VARCHAR(50);\n");
		query.append("	vLOGINSTATUS			NUMBER;\n");
		query.append("	vSTATUSCODE				VARCHAR(20);\n");
		query.append("	vCRYPT					VARCHAR(50);\n");
		query.append("	vACCOUNTSTATUS			VARCHAR(50);\n");
		query.append(SQL_HEADER);
		query.append("	vMODULEID	:=3901;\n");
		query.append("	vUSERNAME	:=?;\n");
		query.append("	vPASSWORD	:=?;\n");
		query.append("	vSESSIONID	:=?;\n");
		query.append("	vIPADDRESS	:=?;\n");
		query.append("	vCRYPT		:=?;\n");
		query.append("	vSTATUSCODE:='TLC-3901-02';\n");
		query.append("  vLOGINSTATUS:=2;\n"); //INVALID PASSWORD
		
		query.append("SELECT WU.USERID,DECRYPT(PASSWORD,vCRYPT,USERNAME),WU.INVALIDPASSWORDCOUNT,U.PASSWORDEXPIRY,WU.LOCKED,LASTNAME,FIRSTNAME,LASTLOGIN,U.ID AS GROUPID,U.USERSLEVEL AS GROUPNAME,ALLOWEDIP,STATUS,U.ACCOUNTSTATUS\n" );
		query.append("INTO   vEXISTS,vTMPPWD,vINVALIDCOUNT,vEXPIRATIONDAYS,vLOCKED,vLASTNAME,vFIRSTNAME,vLASTLOGIN,vGROUPID,vGROUPNAME,vALLOWEDIP,vSTATUS,vACCOUNTSTATUS\n");
		query.append("FROM TBLUSERS WU \n");
		query.append("    INNER JOIN TBLUSERSLEVEL U ON U.USERSLEVEL=WU.USERSLEVEL\n");
		
		query.append("WHERE UPPER(USERNAME)=UPPER(vUSERNAME);\n");
		query.append("	IF vACCOUNTSTATUS='INACTIVE' THEN\n");
		query.append("  	vLOGINSTATUS:=10;\n"); //INACTIVE USERSLEVEL
		query.append("		vSTATUSCODE:='TLC-3901-10';\n");
		query.append("		GOTO EXITPOINT;\n");
		query.append("	END IF;\n");
		
		query.append("	IF vEXISTS<=0 THEN\n");
		query.append("  	vLOGINSTATUS:=9;\n"); //INVALID USERNAME
		query.append("		vSTATUSCODE:='TLC-3901-09';\n");
		query.append("		GOTO EXITPOINT;\n");
		query.append("	END IF;\n");
		
		query.append("	IF vSTATUS<>'ACTIVE' THEN\n");
		query.append(" 			vLOGINSTATUS:=8;\n"); //INACTIVE
		query.append("			vSTATUSCODE:='TLC-3901-08';\n");
		query.append("			GOTO EXITPOINT;\n");
		query.append("	END IF;\n");
		
		query.append("	IF vALLOWEDIP<>vIPADDRESS AND vALLOWEDIP<> '0' THEN\n");
		query.append(" 			vLOGINSTATUS:=7;\n"); //INVALID IP
		query.append("			vSTATUSCODE:='TLC-3901-07';\n");
		query.append("			GOTO EXITPOINT;\n");
		query.append("	END IF;\n");
		
		query.append("	IF vLOCKED<>'NO' THEN\n");
		query.append("			vLOGINSTATUS:=6;\n"); //LOCKED
		query.append("			vSTATUSCODE:='TLC-3901-06';\n");
		query.append("			GOTO EXITPOINT;\n");
		query.append("	END IF;\n");
		
		query.append("	IF vPASSWORD<>vTMPPWD THEN\n");
		query.append("		vLOGINSTATUS:=5;\n"); //INVALID PASSWORD
		query.append("		vSTATUSCODE:='TLC-3901-05';\n");
		query.append("		UPDATE TBLUSERS SET SESSIONID=NULL,LASTLOGIN=SYSDATE,INVALIDPASSWORDCOUNT=INVALIDPASSWORDCOUNT+1 WHERE USERID=vEXISTS;\n");
		query.append("		SELECT INVALIDPASSWORDCOUNT INTO vTOTALINVALIDPASSWORD FROM TBLSYSTEMINFO;\n");
		query.append("		IF vTOTALINVALIDPASSWORD<=vINVALIDCOUNT THEN\n");
		query.append("			vLOGINSTATUS:=4;\n"); //INVALIDPASSWORD,ACCOUNTLOCKED
		query.append("			vSTATUSCODE:='TLC-3901-04';\n");
		query.append("			UPDATE TBLUSERS SET SESSIONID=NULL,LOGINSTATUS=vLOGINSTATUS,LOCKED='YES' WHERE USERID=vEXISTS;\n");
		query.append("		END IF;\n");
		query.append("		GOTO EXITPOINT;\n");
		query.append("	END IF;\n");
		
		query.append("	vLOGINSTATUS:=1;\n"); //ACTIVE
		query.append("	vSTATUSCODE:='00';\n");
		query.append("	SELECT UIDEFAULTPASSWORD INTO vDEFAULTPASS FROM TBLSYSTEMINFO;\n");
		query.append("	IF vDEFAULTPASS=vPASSWORD THEN\n");
		query.append("		vLOGINSTATUS:=3;\n"); //IS DEFAULT PASSWORD
		query.append("		vSTATUSCODE:='TLC-3901-03';\n");
		query.append("	END IF;\n");
		query.append("	SELECT COUNT(1) INTO vEXPIRED FROM TBLUSERPASSWORDHISTORY WHERE PASSWORD = ENCRYPT(vPASSWORD,vCRYPT,USERNAME) AND TIMESTAMP <=SYSDATE-vEXPIRATIONDAYS;\n");
		query.append("	IF vEXPIRED<>0 THEN\n");
		query.append("			vLOGINSTATUS:=2;\n"); //EXPIRED
		query.append("			vSTATUSCODE:='TLC-3901-02';\n");
		query.append("	END IF;\n");
		query.append("	UPDATE TBLUSERS SET SESSIONID=vSESSIONID,LASTLOGIN=SYSDATE,INVALIDPASSWORDCOUNT=0,LOGINSTATUS=vLOGINSTATUS WHERE USERID=vEXISTS;\n");		
		query.append("	INSERT INTO TBLAUDITTRAIL(USERNAME,USERID,SESSIONID,MODULE,IP,STATUS) VALUES(vUSERNAME,vEXISTS,vSESSIONID,vMODULEID,vIPADDRESS,vSTATUSCODE);\n");
		query.append("<<EXITPOINT>>\n");
		query.append("SELECT vLOGINSTATUS,vSTATUSCODE,vEXISTS,vLASTNAME,vFIRSTNAME,vGROUPNAME,vGROUPID,vLASTLOGIN INTO ?,?,?,?,?,?,?,? FROM DUAL;\n");
		query.append(SQL_FOOTER);
		
		InOutParameter vUserName= new InOutParameter(this.getAccount().getUserName());
		vUserName.setDirection(InOutParameter.INPUT_TYPE);
		vUserName.setName(null);
		vUserName.setType(java.sql.Types.VARCHAR);
		
		InOutParameter vPassword= new InOutParameter(this.getAccount().getPassword().toString());
		vPassword.setDirection(InOutParameter.INPUT_TYPE);
		vPassword.setName(null);
		vPassword.setType(java.sql.Types.VARCHAR);
		
		InOutParameter vSessionId= new InOutParameter(this.id);
		vSessionId.setDirection(InOutParameter.INPUT_TYPE);
		vSessionId.setName(null);
		vSessionId.setType(java.sql.Types.VARCHAR);
		
		InOutParameter vIpAddress= new InOutParameter(this.token.IpAddress);
		vIpAddress.setDirection(InOutParameter.INPUT_TYPE);
		vIpAddress.setName(null);
		vIpAddress.setType(java.sql.Types.VARCHAR);
		
		InOutParameter vCrypt= new InOutParameter(this.db.getCrypt());
		vCrypt.setDirection(InOutParameter.INPUT_TYPE);
		vCrypt.setName(null);
		vCrypt.setType(java.sql.Types.VARCHAR);
						
		InOutParameter vLoginStatus = new InOutParameter("");
		vLoginStatus.setDirection(InOutParameter.OUTPUT_TYPE);
		vLoginStatus.setName(null);
		vLoginStatus.setType(java.sql.Types.NUMERIC);
		
		InOutParameter vStatusCode = new InOutParameter("");
		vStatusCode.setDirection(InOutParameter.OUTPUT_TYPE);
		vStatusCode.setName(null);
		vStatusCode.setType(java.sql.Types.VARCHAR);
		
		InOutParameter vUserId = new InOutParameter("");
		vUserId.setDirection(InOutParameter.OUTPUT_TYPE);
		vUserId.setName(null);
		vUserId.setType(java.sql.Types.NUMERIC);
		
		InOutParameter vLastName = new InOutParameter("");
		vLastName.setDirection(InOutParameter.OUTPUT_TYPE);
		vLastName.setName(null);
		vLastName.setType(java.sql.Types.VARCHAR);
		
		InOutParameter vFirstName = new InOutParameter("");
		vFirstName.setDirection(InOutParameter.OUTPUT_TYPE);
		vFirstName.setName(null);
		vFirstName.setType(java.sql.Types.VARCHAR);
		
		InOutParameter vGroupName = new InOutParameter("");
		vGroupName.setDirection(InOutParameter.OUTPUT_TYPE);
		vGroupName.setName(null);
		vGroupName.setType(java.sql.Types.VARCHAR);
		
		
		InOutParameter vGroupId = new InOutParameter("");
		vGroupId.setDirection(InOutParameter.OUTPUT_TYPE);
		vGroupId.setName(null);
		vGroupId.setType(java.sql.Types.NUMERIC);
		
		InOutParameter vLastLogin = new InOutParameter("");
		vLastLogin.setDirection(InOutParameter.OUTPUT_TYPE);
		vLastLogin.setName(null);
		vLastLogin.setType(java.sql.Types.DATE);
		
		int  res =  db.QueryUpdate(query.toString()
											,vUserName
											,vPassword
											,vSessionId
											,vIpAddress
											,vCrypt
											,vLoginStatus
											,vStatusCode
											,vUserId
											,vLastName
											,vFirstName
											,vGroupName
											,vGroupId
											,vLastLogin											
											);
		boolean started = res>0;
		if(started){
			started = false;
			//if(vLoginStatus.getValue()!=null) this.status = Integer.parseInt(vLoginStatus.getValue().toString());
			if(vStatusCode.getValue()!=null){
				this.setState(new ObjectState(vStatusCode.getValue().toString()));
			}
			if(vUserId.getValue()!=null){
				int loginStatus =Integer.parseInt(vLoginStatus.getValue().toString());
				this.getAccount().setPassword(new UIPassword(""));
				this.getAccount().getPassword().setExpired(loginStatus==2);
				this.getAccount().getPassword().setDefault(loginStatus==3);
				this.getAccount().getPassword().setValid(loginStatus<4);
				
				this.getAccount().setId(Integer.parseInt(vUserId.getValue().toString()));
				this.getAccount().setLastLogin((Date) vLastLogin.getValue());
				this.getAccount().setLastName(vLastName.getValue().toString());
				this.getAccount().setFirstName(vFirstName.getValue().toString());
				this.getAccount().setGroup(new UIGroup(vGroupName.getValue().toString()));
				this.getAccount().getGroup().setId(Integer.parseInt(vGroupId.getValue().toString()));
				this.getAccount().setState(new ObjectState(vStatusCode.getValue().toString()));
				this.setState(new ObjectState(vStatusCode.getValue().toString()));
				this.getToken().UserId = this.getAccount().getId();
				DataRow rr = db.QueryDataRow("SELECT * FROM TBLUSERS WHERE USERID=?", this.getAccount().getId());
				this.setGui((String)this.db.QueryScalar("SELECT LOWER(DECRYPT(FQN,?,ACCOUNTNUMBER)) FROM ADMDBMC.TBLACCOUNTINFO WHERE ID = ?", "", new Object[] { this.db.getCrypt(), this.db.QueryScalar("SELECT ROOT FROM ADMDBMC.TBLACCOUNTINFO WHERE ACCOUNTNUMBER = ?", "", new Object[] { rr.getString("ACCOUNTNUMBER") }) }));
				this.setAccountnumber(rr.getString("ACCOUNTNUMBER"));
				this.setSessionid(rr.getString("SESSIONID"));
				this.setIsfirstlogon(rr.getString("ISFIRSTLOGON"));
				this.setEmail(rr.getString("EMAIL"));
				if(!rr.isEmpty()){
					DataRow rrr = db.QueryDataRow("SELECT U.SESSIONID,B.ACCOUNTNUMBER,U.USERID,PO.BRANCHCODE,U.TERMINAL,B.BRANCH,U.ISAPIUSER,B.COUNTRY FROM TBLUSERS U INNER JOIN TBLBRANCHES B ON U.ACCOUNTNUMBER = B.ACCOUNTNUMBER INNER JOIN TBLPOSUSERS PO ON PO.USERID=U.USERNAME WHERE U.USERID=?", this.getAccount().getId());
					
					if(rr.getString("USERSLEVEL").equals("CASHIER")){
						this.setTerminalid(rrr.getString("TERMINAL"));
						this.setBranchcode(rrr.getString("BRANCHCODE"));
						this.setBranchname(rrr.getString("BRANCH"));
						this.setBranchcountry(rrr.getString("COUNTRY"));
						db.QueryUpdate("INSERT INTO TBLSHIFTS (CASHIERID,BRANCH,TERMINALID,ACCOUNTNUMBER,TIMEIN,SESSIONID) VALUES(?,?,?,?,SYSDATE,?)", rrr.getString("USERID"),rrr.getString("BRANCHCODE"),rrr.getString("TERMINAL"),rrr.getString("ACCOUNTNUMBER"),rrr.getString("SESSIONID"));
					}		
					if(rr.getString("USERSLEVEL").equals("MANAGER")){
						this.setIsapiuser(rrr.getString("ISAPIUSER"));
						this.setBranchname(rrr.getString("BRANCH"));
					}
					
				}
				
				DataRow r = db.QueryDataRow("SELECT MINPASSWORD,MAXALLOCUSER,NEWPASSWORDEXPIRY,PASSWORDCHANGE,PASSWORDEXPIRY,PASSWORDHISTORY,SEARCHRANGE,SESSIONTIMEOUT,USERSLEVEL, HOMEPAGE,ISCOMPANY FROM TBLUSERSLEVEL WHERE USERSLEVEL=?",this.getAccount().getGroup().getName());
				if(r!=null && !r.isEmpty()){
					this.setIscompany(r.getString("ISCOMPANY"));
					this.getAccount().getGroup().setMinPassword(r.getInteger("MINPASSWORD"));
					this.getAccount().getGroup().setMaxAllocactionPerDay(r.getInteger("MAXALLOCUSER"));
					this.getAccount().getGroup().setPasswordExpiry(r.getInteger("PASSWORDEXPIRY"));
					this.getAccount().getGroup().setPasswordHistory(r.getInteger("PASSWORDHISTORY"));
					this.getAccount().getGroup().setSearchRange(r.getInteger("SEARCHRANGE"));
					this.getAccount().getGroup().setSessionTimeout(r.getInteger("SESSIONTIMEOUT"));
					this.getAccount().getGroup().setHomePage(r.getString("HOMEPAGE"));
					this.getAccount().getGroup().setName(r.getString("USERSLEVEL"));
					this.getAccount().getGroup().setState(new ObjectState("00"));
					this.getAccount().getGroup().setModules(db.QueryArray("SELECT MODULEID FROM TBLGROUPMODULESVITAS WHERE GROUPID=?",new Integer(0),this.getAccount().getGroup().getId()));
					started = true;
				}
				if(this.getAccount().getPassword().valid()){
					this.setState(new ObjectState("00"));
				}
				DataRow terminaluser = db.QueryDataRow("SELECT * FROM TBLUSERS WHERE USERID=? AND ISTERMINAL=1", Integer.parseInt(vUserId.getValue().toString()));
				Logger.LogServer("query"+terminaluser.toString());
				if(terminaluser != null
						&& terminaluser.size() > 0){
					try {
						Logger.LogServer("session:"+this.sessionurl);
						Logger.LogServer("token:"+this.tokenurl);
						com.psi.ibc.common.SessionToken session = new com.psi.ibc.common.SessionToken()
								.parse(this.sessionurl);
						Logger.LogServer("session:"+session);
						Logger.LogServer("executing session");
						if(session==null){
							this.setState(new ObjectState("TLC-3901-14"));
							return false;
						}else{						
							String hashKey = HashFunction.hashSHA256(this.sessionurl);
							String decryptedToken = com.psi.ibc.common.Token.doAESDecryption(hashKey, this.tokenurl);
							Logger.LogServer("session:"+this.sessionurl);
							Logger.LogServer("hash:"+hashKey);
							Logger.LogServer("token:"+this.tokenurl);
							Logger.LogServer("token:"+decryptedToken);
							Logger.LogServer("accountnumber:"+session.getAccountNumber());
							if(StringUtil.isNullOrEmpty(decryptedToken)){
								this.setState(new ObjectState("TLC-3901-13"));
								return false;
							}else{
								MACBinding macBinding = new MACBinding(session.getAccountNumber(), decryptedToken,rr.getString("USERNAME"));
							    			    
								if (this.isExpired(session, MAX_TIME_IN_HOURS)) {
										this.setState(new ObjectState("TLC-3901-12"));
										return false;
									}
								if(!session.getAccountNumber().equals(rr.getString("ACCOUNTNUMBER"))){
									this.setState(new ObjectState("TLC-3901-15"));
									return false;
								}
								if (!macBinding.exist()) {
									this.setState(new ObjectState("TLC-3901-11"));
									return false;
								} 
								JSONObject object = (JSONObject) new JSONParser().parse(decryptedToken);
								if (object != null) {									
									if (object.containsKey("mac"))
										this.setMacaddress(object.get("mac").toString());
									
								}
								Logger.LogServer("Macbinding part");
							}
						}

					return true;
					} catch (Exception e) {
						e.printStackTrace();
						Logger.LogServer(e);
					}
				}else{
					this.setMacaddress(rr.getString("TERMINAL"));
				}
			}else{
				if(res!=-1){
					this.setState(new ObjectState(ObjectState.STAT_UIACCOUNTDOESNOTEXIST));
					this.removeProperty("TOKEN");
				}
			}
			
		}
		return started;
	}
	protected boolean isExpired(com.psi.ibc.common.SessionToken session,
			int interval) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(session.getExpirationDate());
		calendar.add(Calendar.MINUTE, interval);

		Date currentExpiration = calendar.getTime();
		if (currentExpiration.getTime() > new Date().getTime())
			return Boolean.FALSE;

		return Boolean.TRUE;
	}
	public String getIsfirstlogon() {
		return isfirstlogon;
	}

	public void setIsfirstlogon(String isfirstlogon) {
		this.props.put(PROP_ISFISRTLOGON,isfirstlogon);
		this.isfirstlogon = isfirstlogon;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.props.put(PROP_EMAIL,email);
		this.email = email;
	}

	public String getAccountnumber() {
		return accountnumber;
	}

	public void setAccountnumber(String accountnumber) {
		this.props.put(PROP_ACCOUNTNUMBER,accountnumber);
		this.accountnumber = accountnumber;
	}
	public String getSessionid() {
		return sessionid;
	}
	public void setSessionid(String sessionid) {
		this.props.put(PROP_SESSIONID,sessionid);
		this.sessionid = sessionid;
	}
	public String getBranchcode() {
		return branchcode;
	}
	public void setBranchcode(String branchcode) {
		this.props.put(PROP_BRANCHCODE,branchcode);
		this.branchcode = branchcode;
	}
	public String getTerminalid() {
		return terminalid;
	}
	public void setTerminalid(String terminalid) {
		this.props.put(PROP_TERMINAL,terminalid);
		this.terminalid = terminalid;
	}
	public String getBranchname() {
		return branchname;
	}
	public void setBranchname(String branchname) {
		this.props.put(PROP_BRANCHNAME, branchname);
		this.branchname = branchname;
	}
	public String getIsapiuser() {
		return isapiuser;
	}
	public void setIsapiuser(String isapiuser) {
		this.props.put(PROP_ISAPIUSER, isapiuser);
		this.isapiuser = isapiuser;
	}
	public String getTokenurl() {
		return tokenurl;
	}
	public void setTokenurl(String tokenurl) {
		this.props.put(PROP_TOKENURL,tokenurl);
		this.tokenurl = tokenurl;
	}
	public String getSessionurl() {
		return sessionurl;
	}
	public void setSessionurl(String sessionurl) {
		this.props.put(PROP_SESSIONURL,sessionurl);
		this.sessionurl = sessionurl;
	}
	public String getMacaddress() {
		return macaddress;
	}
	public void setMacaddress(String macaddress) {
		this.props.put(PROP_MACADDRESS,macaddress);
		this.macaddress = macaddress;
	}

	public String getBranchcountry() {
		return branchcountry;
	}

	public void setBranchcountry(String branchcountry) {
		this.props.put(PROP_BRANCH_COUNTRY,branchcountry);
		this.branchcountry = branchcountry;
	}		
	public String getIscompany() {
	    return this.iscompany;
	  }

	  public void setIscompany(String iscompany) {
	    this.props.put(PROP_IS_COMPANY, iscompany);
	    this.iscompany = iscompany;
	  }

	  public String getGui()
	  {
	    return this.gui;
	  }
	  public void setGui(String gui) {
	    this.props.put(PROP_GUI, gui);
	    this.gui = gui;
	  }
}