package com.psi.backoffice.c;

import com.psi.backoffice.m.NewRegister;
import com.psi.backoffice.util.AuditTrail;
import com.psi.backoffice.util.EmailUtils;
import com.psi.backoffice.v.JsonView;
import com.tlc.common.Logger;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class NewRegisterCommand
  extends UICommand
{
  public IView execute() {
    ExistingSession sess = null;
    SessionView v = null;
    
    try {
      sess = ExistingSession.parse(this.reqHeaders);
      if (sess.exists()) {
        String email = this.params.get("Email").toString();
        String firstname = this.params.get("FirstName").toString();
        String lastname = this.params.get("LastName").toString();
        String msisdn = this.params.get("MSISDN").toString();
        String username = this.params.get("UserName").toString();
        String userslevel = this.params.get("UsersLevel").toString();
        
        String password = "123456789";
        String accountnumber = this.params.get("AccountNumber").toString();
        String guiinterface = this.params.get("GuiInterface").toString();
        
        Logger.LogServer("guiinterface: " + guiinterface);
        
        NewRegister reg = new NewRegister();
        
        reg.setEmail(email);
        reg.setFirstname(firstname);
        reg.setLastname(lastname);
        reg.setMsisdn(msisdn);
        reg.setUsername(username);
        reg.setPassword(password);
        reg.setUserslevel(userslevel);
        reg.setAccountnumber(accountnumber);
        reg.setAuthorizedSession(sess);
        reg.setGuiinterface(guiinterface);
        reg.setCreatedby(reg.getAuthorizedSession().getAccount().getUserName());
        
        if (reg.exist()) {
          reg.setState(new ObjectState("PSI-01", "Back Office account already registered"));
          AuditTrail audit = new AuditTrail();
          audit.setIp(reg.getAuthorizedSession().getIpAddress());
          audit.setModuleid(String.valueOf(getId()));
          audit.setEntityid(username);
          audit.setLog(String.valueOf(reg.getState().getMessage()) + "|" + reg.getUsername() + "|" + reg.getFirstname() + "|" + reg.getLastname() + "|" + reg.getEmail() + "|" + reg.getMsisdn());
          audit.setStatus(reg.getState().getCode());
          audit.setUserid(Integer.valueOf(reg.getAuthorizedSession().getAccount().getId()));
          audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
          
          audit.insert();
          return new JsonView(reg);
        } 
        if (reg.isEmailExist()) {
          reg.setState(new ObjectState("PSI-02", "Email Address Already Exist"));
          AuditTrail audit = new AuditTrail();
          audit.setIp(reg.getAuthorizedSession().getIpAddress());
          audit.setModuleid(String.valueOf(getId()));
          audit.setEntityid(username);
          audit.setLog(String.valueOf(reg.getState().getMessage()) + "|" + reg.getUsername() + "|" + reg.getFirstname() + "|" + reg.getLastname() + "|" + reg.getEmail() + "|" + reg.getMsisdn());
          audit.setStatus(reg.getState().getCode());
          audit.setUserid(Integer.valueOf(reg.getAuthorizedSession().getAccount().getId()));
          audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
          
          audit.insert();
          return new JsonView(reg);
        } 
        if (reg.isMsisdnExist()) {
          reg.setState(new ObjectState("PSI-03", "Mobile Number Already Exist"));
          AuditTrail audit = new AuditTrail();
          audit.setIp(reg.getAuthorizedSession().getIpAddress());
          audit.setModuleid(String.valueOf(getId()));
          audit.setEntityid(username);
          audit.setLog(String.valueOf(reg.getState().getMessage()) + "|" + reg.getUsername() + "|" + reg.getFirstname() + "|" + reg.getLastname() + "|" + reg.getEmail() + "|" + reg.getMsisdn());
          audit.setStatus(reg.getState().getCode());
          audit.setUserid(Integer.valueOf(reg.getAuthorizedSession().getAccount().getId()));
          audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
          
          audit.insert();
          return new JsonView(reg);
        } 
        if (!reg.register()) {
          reg.setState(new ObjectState("99", "System busy"));
          AuditTrail audit = new AuditTrail();
          audit.setIp(reg.getAuthorizedSession().getIpAddress());
          audit.setModuleid(String.valueOf(getId()));
          audit.setEntityid(username);
          audit.setLog(String.valueOf(reg.getState().getMessage()) + "|" + reg.getUsername() + "|" + reg.getFirstname() + "|" + reg.getLastname() + "|" + reg.getEmail() + "|" + reg.getMsisdn());
          audit.setStatus(reg.getState().getCode());
          audit.setUserid(Integer.valueOf(reg.getAuthorizedSession().getAccount().getId()));
          audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
          
          audit.insert();
          return new JsonView(reg);
        } 
        
        reg.setState(new ObjectState("00", "Register Succesfully"));
        EmailUtils.sendEmail(email, firstname, lastname, password, username);
        AuditTrail audit = new AuditTrail();
        audit.setIp(reg.getAuthorizedSession().getIpAddress());
        audit.setModuleid(String.valueOf(getId()));
        audit.setEntityid(username);
        audit.setLog(String.valueOf(reg.getState().getMessage()) + "|" + reg.getUsername() + "|" + reg.getFirstname() + "|" + reg.getLastname() + "|" + reg.getEmail() + "|" + reg.getMsisdn());
        audit.setStatus(reg.getState().getCode());
        audit.setUserid(Integer.valueOf(reg.getAuthorizedSession().getAccount().getId()));
        audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
        
        Logger.LogServer("Log: " + reg.getState().getMessage() + "|" + reg.getUsername() + "|" + reg.getFirstname() + "|" + reg.getLastname() + "|" + reg.getEmail() + "|" + reg.getMsisdn());
        audit.insert();
        return new JsonView(reg);
      } 
      UISession u = new UISession(null);
      u.setState(new ObjectState("TLC-3902-01"));
      v = new SessionView(u);
    
    }
    catch (SessionNotFoundException e) {
      UISession u = new UISession(null);
      u.setState(new ObjectState("TLC-3902-01"));
      v = new SessionView(u);
      Logger.LogServer(e);
    } catch (Exception e) {
      Logger.LogServer(e);
    } 
    return v;
  }


  
  public int getId() { return 7001; }



  
  public String getKey() { return "NEWBACKOFFICEUSER"; }
}
