package com.psi.business.c;

import com.psi.business.m.NewBusinessUser;
import com.psi.business.util.AuditTrail;
import com.psi.business.util.EmailUtils;
import com.psi.business.v.JsonView;
import com.tlc.common.Logger;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class NewBusinessUserCommand
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
        String accountnumber = this.params.get("AccountNumber");
        
        String password = "123456789";
        
        NewBusinessUser reg = new NewBusinessUser();
        
        reg.setEmail(email);
        reg.setFirstname(firstname);
        reg.setLastname(lastname);
        reg.setMsisdn(msisdn);
        reg.setUsername(username);
        reg.setPassword(password);
        reg.setAccountnumber(accountnumber);
        reg.setAuthorizedSession(sess);
        
        reg.setCreatedby(reg.getAuthorizedSession().getAccount().getUserName());
        
        if (!reg.exist()) {
          if (reg.register()) {
            reg.setState(new ObjectState("00", "Register Succesfully"));
            EmailUtils.send(email, firstname, lastname, password, username);
          } else {
            reg.setState(new ObjectState("99", "System busy"));
          } 
          AuditTrail audit = new AuditTrail();
          audit.setIp(reg.getAuthorizedSession().getIpAddress());
          audit.setModuleid(String.valueOf(getId()));
          audit.setEntityid(username);
          audit.setLog(String.valueOf(reg.getState().getMessage()) + "|" + reg.getUsername() + "|" + reg.getFirstname() + "|" + reg.getLastname() + "|" + reg.getEmail() + "|" + reg.getMsisdn() + "|" + reg.getAccountnumber());
          audit.setStatus(reg.getState().getCode());
          audit.setUserid(Integer.valueOf(reg.getAuthorizedSession().getAccount().getId()));
          audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
          
          audit.insert();
        } else {
          reg.setState(new ObjectState("01", "Account already registered"));
          
          AuditTrail audit = new AuditTrail();
          audit.setIp(reg.getAuthorizedSession().getIpAddress());
          audit.setModuleid(String.valueOf(getId()));
          audit.setEntityid(username);
          audit.setLog(String.valueOf(reg.getState().getMessage()) + "|" + reg.getUsername() + "|" + reg.getFirstname() + "|" + reg.getLastname() + "|" + reg.getEmail() + "|" + reg.getMsisdn() + "|" + reg.getAccountnumber());
          audit.setStatus(reg.getState().getCode());
          audit.setUserid(Integer.valueOf(reg.getAuthorizedSession().getAccount().getId()));
          audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
          
          audit.insert();
        } 
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


  
  public int getId() { return 7000; }



  
  public String getKey() { return "NEWBUSINESSUSER"; }
}
