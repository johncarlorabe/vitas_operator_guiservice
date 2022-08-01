package com.psi.business.c;

import com.psi.business.m.NewBusiness;
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
import java.io.IOException;
import org.json.simple.parser.ParseException;



public class NewBusinessCommand
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
        String monday = this.params.get("Monday");
        String tuesday = this.params.get("Tuesday");
        String wednesday = this.params.get("Wednesday");
        String thursday = this.params.get("Thursday");
        String friday = this.params.get("Friday");
        String saturday = this.params.get("Saturday");
        String sunday = this.params.get("Sunday");
        String accountnumber = this.params.get("AccountNumber");
        String iswithholdingtax = this.params.get("IsWithHoldingTax");
        
        NewBusiness reg = new NewBusiness();
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
        reg.setMonday(monday);
        reg.setTuesday(tuesday);
        reg.setWednesday(wednesday);
        reg.setThursday(thursday);
        reg.setFriday(friday);
        reg.setSaturday(saturday);
        reg.setSunday(sunday);
        reg.setAccountnumber(accountnumber);
        reg.setAuthorizedSession(sess);
        
        try {
          if (reg.exist()) {
            reg.setState(new ObjectState("01", "Account already registered"));
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
          if (reg.contactexist()) {
            reg.setState(new ObjectState("01", "Contact Number already registered"));
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
          if (reg.register(accountnumber)) {
            AuditTrail audit = new AuditTrail();
            audit.setIp(reg.getAuthorizedSession().getIpAddress());
            audit.setModuleid(String.valueOf(getId()));
            audit.setEntityid(branchname);
            audit.setLog(String.valueOf(reg.getState().getMessage()) + "|" + reg.getBranchname() + "|" + reg.getContactnumber() + "|" + reg.getAddress());
            audit.setStatus(reg.getState().getCode());
            audit.setUserid(Integer.valueOf(reg.getAuthorizedSession().getAccount().getId()));
            audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
            
            audit.insert();
            return new JsonView(reg);
          } 
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
        catch (IOException e) {
          e.printStackTrace();
          reg.setState(new ObjectState("99", "System busy"));
          return new JsonView(reg);
        } catch (ParseException e) {
          e.printStackTrace();
          reg.setState(new ObjectState("99", "System busy"));
          return new JsonView(reg);
        } 
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



  
  public String getKey() { return "NEWBUSINESS"; }




  
  public int getId() { return 3010; }
}
