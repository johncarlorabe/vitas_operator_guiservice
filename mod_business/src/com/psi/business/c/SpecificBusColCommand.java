package com.psi.business.c;

import com.psi.business.m.SpecificBusCollection;
import com.psi.business.util.AuditTrail;
import com.psi.business.v.CollectionView;
import com.psi.business.v.NoDataFoundView;
import com.tlc.common.Logger;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class SpecificBusColCommand
  extends UICommand
{
  public IView execute() {
    ExistingSession sess = null;
    SessionView v = null;
    try {
      sess = ExistingSession.parse(this.reqHeaders);
      if (sess.exists()) {
        
        SpecificBusCollection col = new SpecificBusCollection();
        col.setId(this.params.get("Id"));
        col.setAuthorizedSession(sess);
        
        if (col.hasRows()) {
          AuditTrail audit = new AuditTrail();
          audit.setIp(col.getAuthorizedSession().getIpAddress());
          audit.setModuleid(String.valueOf(getId()));
          audit.setEntityid(col.getId());
          audit.setLog("Successfully fetched data ~ " + col.getId());
          audit.setStatus("00");
          audit.setUserid(Integer.valueOf(col.getAuthorizedSession().getAccount().getId()));
          audit.setUsername(col.getAuthorizedSession().getAccount().getUserName());
          
          audit.insert();
          return new CollectionView("00", col);
        } 
        AuditTrail audit = new AuditTrail();
        audit.setIp(col.getAuthorizedSession().getIpAddress());
        audit.setModuleid(String.valueOf(getId()));
        audit.setEntityid(col.getId());
        audit.setLog("Successfully fetched data ~ " + col.getId());
        audit.setStatus("00");
        audit.setUserid(Integer.valueOf(col.getAuthorizedSession().getAccount().getId()));
        audit.setUsername(col.getAuthorizedSession().getAccount().getUserName());
        
        audit.insert();
        
        ObjectState state = new ObjectState("01", "No data found");
        return new NoDataFoundView(state);
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



  
  public String getKey() { return "BUSINESSCOLLECTIONSPECIFIC"; }



  
  public int getId() { return 3021; }
}
