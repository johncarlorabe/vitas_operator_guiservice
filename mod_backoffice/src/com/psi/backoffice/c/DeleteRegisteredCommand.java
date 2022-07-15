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

public class DeleteRegisteredCommand
  extends UICommand
{
  public IView execute() {
    ExistingSession sess = null;
    SessionView v = null;
    
    try {
      sess = ExistingSession.parse(this.reqHeaders);
      if (sess.exists()) {
        String id = this.params.get("Id").toString();
        
        ManageRegisteredUser reg = new ManageRegisteredUser();
        
        reg.setId(id);
        reg.setAuthorizedSession(sess);
        
        if (!reg.exist()) {
          reg.setState(new ObjectState("01", "Account do not exists"));
          AuditTrail audit = new AuditTrail();
          audit.setIp(reg.getAuthorizedSession().getIpAddress());
          audit.setModuleid(String.valueOf(getId()));
          audit.setEntityid(id);
          audit.setLog("Account do noot exist: " + id);
          audit.setStatus(reg.getState().getCode());
          audit.setUserid(Integer.valueOf(reg.getAuthorizedSession().getAccount().getId()));
          audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
          
          audit.insert();
          return new JsonView(reg);
        } 
        if (reg.delete()) {
          reg.setState(new ObjectState("00", "Delete Succesfully"));
          AuditTrail audit = new AuditTrail();
          audit.setIp(reg.getAuthorizedSession().getIpAddress());
          audit.setModuleid(String.valueOf(getId()));
          audit.setEntityid(id);
          audit.setLog("Successfully deleted: " + id);
          audit.setStatus(reg.getState().getCode());
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
        audit.setLog("System is currently busy : " + id);
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
      Logger.LogServer(e);
    } 
    return v;
  }



  
  public String getKey() { return "DELETEBACKOFFICEUSER"; }




  
  public int getId() { return 7003; }
}
