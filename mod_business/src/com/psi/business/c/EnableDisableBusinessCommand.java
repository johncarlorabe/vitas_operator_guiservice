package com.psi.business.c;

import com.psi.business.m.EnableDisableBusiness;
import com.psi.business.util.AuditTrail;
import com.psi.business.v.JsonView;
import com.tlc.common.Logger;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class EnableDisableBusinessCommand
  extends UICommand
{
  public IView execute() {
    ExistingSession sess = null;
    SessionView v = null;
    
    try {
      sess = ExistingSession.parse(this.reqHeaders);
      if (sess.exists()) {
        String accountnumber = this.params.get("AccountNumber");
        String status = this.params.get("Status");
        
        EnableDisableBusiness reg = new EnableDisableBusiness();
        
        reg.setAccountnumber(accountnumber);
        reg.setStatus(status);
        reg.setAuthorizedSession(sess);
        if (!reg.exist()) {
          reg.setState(new ObjectState("01", "Account do not exist"));
          AuditTrail audit = new AuditTrail();
          audit.setIp(reg.getAuthorizedSession().getIpAddress());
          audit.setModuleid(String.valueOf(getId()));
          audit.setEntityid(accountnumber);
          audit.setLog(reg.getState().getMessage());
          audit.setStatus(reg.getState().getCode());
          audit.setUserid(Integer.valueOf(reg.getAuthorizedSession().getAccount().getId()));
          audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
          
          audit.insert();
          return new JsonView(reg);
        } 
        if (reg.update()) {
          reg.setState(new ObjectState("00", "Successfully change branch status"));
          AuditTrail audit = new AuditTrail();
          audit.setIp(reg.getAuthorizedSession().getIpAddress());
          audit.setModuleid(String.valueOf(getId()));
          audit.setEntityid(accountnumber);
          audit.setLog(reg.getState().getMessage());
          audit.setStatus(reg.getState().getCode());
          audit.setUserid(Integer.valueOf(reg.getAuthorizedSession().getAccount().getId()));
          audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
          
          audit.insert();
          return new JsonView(reg);
        } 
        reg.setState(new ObjectState("02", "Unable to update"));
        AuditTrail audit = new AuditTrail();
        audit.setIp(reg.getAuthorizedSession().getIpAddress());
        audit.setModuleid(String.valueOf(getId()));
        audit.setEntityid(accountnumber);
        audit.setLog(reg.getState().getMessage());
        audit.setStatus(reg.getState().getCode());
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
      UISession u = new UISession(null);
      u.setState(new ObjectState("TLC-3902-01"));
      v = new SessionView(u);
      Logger.LogServer(e);
    }  return v;
  }



  
  public String getKey() { return "ENABLEDISABLEBUSINESS"; }




  
  public int getId() { return 3020; }
}
