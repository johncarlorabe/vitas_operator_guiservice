package com.psi.backoffice.c;

import com.psi.backoffice.m.ManageRegisteredUser;
import com.psi.backoffice.util.AuditTrail;
import com.psi.backoffice.v.JsonView;
import com.tlc.common.Logger;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class EditRegisteredCommand
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
        String id = this.params.get("Id").toString();
        String userslevel = this.params.get("UsersLevel").toString();
        
        ManageRegisteredUser reg = new ManageRegisteredUser();
        
        reg.setEmail(email);
        reg.setFirstname(firstname);
        reg.setLastname(lastname);
        reg.setMsisdn(msisdn);
        reg.setId(id);
        reg.setUserslevel(userslevel);
        reg.setAuthorizedSession(sess);
        
        if (reg.isEmailExist()) {
          reg.setState(new ObjectState("PSI-02", "Email address already exist"));
          AuditTrail audit = new AuditTrail();
          audit.setIp(reg.getAuthorizedSession().getIpAddress());
          audit.setModuleid(String.valueOf(getId()));
          audit.setEntityid(id);
          audit.setLog("Email Address already exist: |" + reg.getFirstname() + "|" + reg.getLastname() + "|" + reg.getEmail() + "|" + reg.getMsisdn());
          audit.setStatus("01");
          audit.setUserid(Integer.valueOf(reg.getAuthorizedSession().getAccount().getId()));
          audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
          
          audit.insert();
          return new JsonView(reg);
        } 
        if (reg.isMsisdnExist()) {
          reg.setState(new ObjectState("PSI-03", "Mobile Number already exist"));
          AuditTrail audit = new AuditTrail();
          audit.setIp(reg.getAuthorizedSession().getIpAddress());
          audit.setModuleid(String.valueOf(getId()));
          audit.setEntityid(id);
          audit.setLog("Mobile Number already exist: |" + reg.getFirstname() + "|" + reg.getLastname() + "|" + reg.getEmail() + "|" + reg.getMsisdn());
          audit.setStatus("01");
          audit.setUserid(Integer.valueOf(reg.getAuthorizedSession().getAccount().getId()));
          audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
          
          audit.insert();
          return new JsonView(reg);
        } 
        if (reg.exist()) {
          if (reg.update()) {
            reg.setState(new ObjectState("00", "Updated Succesfully"));
            AuditTrail audit = new AuditTrail();
            audit.setIp(reg.getAuthorizedSession().getIpAddress());
            audit.setModuleid(String.valueOf(getId()));
            audit.setEntityid(id);
            audit.setLog("Edited successfully|" + reg.getFirstname() + "|" + reg.getLastname() + "|" + reg.getEmail() + "|" + reg.getMsisdn());
            audit.setStatus("00");
            audit.setUserid(Integer.valueOf(reg.getAuthorizedSession().getAccount().getId()));
            audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
            
            audit.insert();
            return new JsonView(reg);
          } 
          reg.setState(new ObjectState("99", "System busy"));
          AuditTrail audit = new AuditTrail();
          audit.setIp(reg.getAuthorizedSession().getIpAddress());
          audit.setModuleid(String.valueOf(getId()));
          audit.setEntityid(id);
          audit.setLog("System is currently busy, please try again: |" + reg.getFirstname() + "|" + reg.getLastname() + "|" + reg.getEmail() + "|" + reg.getMsisdn());
          audit.setStatus("99");
          audit.setUserid(Integer.valueOf(reg.getAuthorizedSession().getAccount().getId()));
          audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
          
          audit.insert();
          return new JsonView(reg);
        } 
        
        reg.setState(new ObjectState("PSI-01", "Account do not exist"));
        AuditTrail audit = new AuditTrail();
        audit.setIp(reg.getAuthorizedSession().getIpAddress());
        audit.setModuleid(String.valueOf(getId()));
        audit.setEntityid(id);
        audit.setLog("Account do not exist: |" + reg.getFirstname() + "|" + reg.getLastname() + "|" + reg.getEmail() + "|" + reg.getMsisdn());
        audit.setStatus("01");
        audit.setUserid(Integer.valueOf(reg.getAuthorizedSession().getAccount().getId()));
        audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
        
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


  
  public int getId() { return 7002; }



  
  public String getKey() { return "EDITBACKOFFICEUSER"; }
}
