package com.psi.business.c;

import com.psi.business.m.EditBusiness;
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

public class EditBusinessCommand
  extends UICommand
{
  public IView execute() {
    ExistingSession sess = null;
    SessionView v = null;
    
    try {
      sess = ExistingSession.parse(this.reqHeaders);
      if (sess.exists()) {
        String branchname = this.params.get("BranchName");
        String address = this.params.get("Address");
        String city = this.params.get("City");
        String province = this.params.get("Province");
        String country = this.params.get("Country");
        String zipcode = this.params.get("ZipCode");
        String contactnumber = this.params.get("ContactNumber");
        String image = this.params.get("ImgProof");
        String xcoordinate = this.params.get("XCoordinate");
        String ycoordinate = this.params.get("YCoordinate");
        String accountnumber = this.params.get("AccountNumber");
        String monday = this.params.get("Monday");
        String tuesday = this.params.get("Tuesday");
        String wednesday = this.params.get("Wednesday");
        String thursday = this.params.get("Thursday");
        String friday = this.params.get("Friday");
        String saturday = this.params.get("Saturday");
        String sunday = this.params.get("Sunday");
        String iswithholdingtax = this.params.get("IsWithHoldingTax");
        
        EditBusiness reg = new EditBusiness();
        reg.setIsWithholdingtax(iswithholdingtax);
        reg.setBranchname(branchname);
        reg.setAddress(address);
        reg.setCity(city);
        reg.setProvince(province);
        reg.setCountry(country);
        reg.setZipcode(zipcode);
        reg.setContactnumber(contactnumber);
        
        reg.setImage(image);
        reg.setXordinate(xcoordinate);
        reg.setYordinate(ycoordinate);
        reg.setAccountnumber(accountnumber);
        reg.setMonday(monday);
        reg.setTuesday(tuesday);
        reg.setWednesday(wednesday);
        reg.setThursday(thursday);
        reg.setFriday(friday);
        reg.setSaturday(saturday);
        reg.setSunday(sunday);
        reg.setAuthorizedSession(sess);
        if (!reg.exist()) {
          reg.setState(new ObjectState("01", "Account do not exist"));
          AuditTrail audit = new AuditTrail();
          audit.setIp(reg.getAuthorizedSession().getIpAddress());
          audit.setModuleid(String.valueOf(getId()));
          audit.setEntityid(branchname);
          audit.setLog(reg.getState().getMessage());
          audit.setStatus(reg.getState().getCode());
          audit.setUserid(Integer.valueOf(reg.getAuthorizedSession().getAccount().getId()));
          audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
          
          audit.insert();
          return new JsonView(reg);
        } 
        if (reg.update()) {
          reg.setState(new ObjectState("00", "Account successfully edited"));
          AuditTrail audit = new AuditTrail();
          audit.setIp(reg.getAuthorizedSession().getIpAddress());
          audit.setModuleid(String.valueOf(getId()));
          audit.setEntityid(branchname);
          audit.setLog(String.valueOf(reg.getState().getMessage()) + "|" + reg.getBranchname() + "|" + reg.getContactnumber() + "|" + reg.getAddress() + "|" + reg.getZipcode());
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
        audit.setEntityid(branchname);
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



  
  public String getKey() { return "EDITBUSINESS"; }




  
  public int getId() { return 3020; }
}
