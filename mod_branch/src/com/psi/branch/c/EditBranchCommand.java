package com.psi.branch.c;

import com.psi.branch.m.EditBranch;
import com.psi.branch.utils.AuditTrail;
import com.psi.branch.v.JsonView;
import com.tlc.common.Logger;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class EditBranchCommand extends UICommand{

	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;
		
		try {
			sess = ExistingSession.parse(this.reqHeaders);		
			if(sess.exists()) {
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
				String rafilename = this.params.get("FileName");
				String tin = this.params.get("Tin");
				String natureofbusiness = this.params.get("NatureOfBusiness");
				String grosssales = this.params.get("GrossSales");
				EditBranch reg = new EditBranch();
				reg.setGrosssales(grosssales);
				reg.setTin(tin);
				reg.setNatureofbusiness(natureofbusiness);
				reg.setBranchname(branchname);
				reg.setAddress(address);
				reg.setCity(city);
				reg.setProvince(province);
				reg.setCountry(country);
				reg.setZipcode(zipcode);
				reg.setContactnumber(contactnumber);
				//reg.setStorehours(storehours);
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
				reg.setRafilename(rafilename);
				reg.setAuthorizedSession(sess);
						if(!reg.exist()){
							reg.setState(new ObjectState("01", "Account do not exist"));
							AuditTrail audit  = new AuditTrail();
				    		audit.setIp(reg.getAuthorizedSession().getIpAddress());
				    		audit.setModuleid(String.valueOf(this.getId()));
				    		audit.setEntityid(branchname);
				    		audit.setLog(reg.getState().getMessage());
				    		audit.setStatus(reg.getState().getCode());
				    		audit.setUserid(reg.getAuthorizedSession().getAccount().getId());
				    		audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
				    		audit.setData("branchname:"+branchname+"||address:"+address+"||city:"+city+"||province:"+province+"||country:"+country+"||zipcode:"+zipcode+"||contactnumber:"+contactnumber+"||image:"+image+"||xcoordinate:"+xcoordinate+"||ycoordinate:"+ycoordinate+"||accountnumber:"+accountnumber+"||monday:"+monday+"||tuesday:"+tuesday+"||wednesday:"+wednesday+"||thursday:"+thursday+"||friday:"+friday+"||saturday:"+saturday+"||sunday:"+sunday+"||rafilename:"+rafilename+"||tin:"+tin+"||natureofbusiness:"+natureofbusiness+"||grosssales:"+grosssales);
				    		audit.setImage(image);
				    		audit.insert();
							return new JsonView(reg);
						}
						if(reg.update()){
							reg.setState(new ObjectState("00", "Account successfully edited"));
							AuditTrail audit  = new AuditTrail();
				    		audit.setIp(reg.getAuthorizedSession().getIpAddress());
				    		audit.setModuleid(String.valueOf(this.getId()));
				    		audit.setEntityid(branchname);
				    		audit.setLog(reg.getState().getMessage());
				    		audit.setStatus(reg.getState().getCode());
				    		audit.setUserid(reg.getAuthorizedSession().getAccount().getId());
				    		audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
				    		audit.setData("branchname:"+branchname+"||address:"+address+"||city:"+city+"||province:"+province+"||country:"+country+"||zipcode:"+zipcode+"||contactnumber:"+contactnumber+"||xcoordinate:"+xcoordinate+"||ycoordinate:"+ycoordinate+"||accountnumber:"+accountnumber+"||monday:"+monday+"||tuesday:"+tuesday+"||wednesday:"+wednesday+"||thursday:"+thursday+"||friday:"+friday+"||saturday:"+saturday+"||sunday:"+sunday+"||rafilename:"+rafilename+"||tin:"+tin+"||natureofbusiness:"+natureofbusiness+"||grosssales:"+grosssales);
				    		audit.setImage(image);
				    		audit.insert();
							return new JsonView(reg);
						}else{
							reg.setState(new ObjectState("02", "Unable to update"));
							AuditTrail audit  = new AuditTrail();
				    		audit.setIp(reg.getAuthorizedSession().getIpAddress());
				    		audit.setModuleid(String.valueOf(this.getId()));
				    		audit.setEntityid(branchname);
				    		audit.setLog(reg.getState().getMessage());
				    		audit.setStatus(reg.getState().getCode());
				    		audit.setUserid(reg.getAuthorizedSession().getAccount().getId());
				    		audit.setUsername(reg.getAuthorizedSession().getAccount().getUserName());
				    		audit.setData("branchname:"+branchname+"||address:"+address+"||city:"+city+"||province:"+province+"||country:"+country+"||zipcode:"+zipcode+"||contactnumber:"+contactnumber+"||image:"+image+"||xcoordinate:"+xcoordinate+"||ycoordinate:"+ycoordinate+"||accountnumber:"+accountnumber+"||monday:"+monday+"||tuesday:"+tuesday+"||wednesday:"+wednesday+"||thursday:"+thursday+"||friday:"+friday+"||saturday:"+saturday+"||sunday:"+sunday+"||rafilename:"+rafilename+"||tin:"+tin+"||natureofbusiness:"+natureofbusiness+"||grosssales:"+grosssales);
				    		audit.setImage(image);
				    		audit.insert();
							return new JsonView(reg);
						}
			}else{		
				UISession u = new UISession(null);
			    u.setState(new ObjectState("TLC-3902-01"));
			    v = new SessionView(u);
			}
		}catch (SessionNotFoundException e) {
			UISession u = new UISession(null);
		    u.setState(new ObjectState("TLC-3902-01"));
		    v = new SessionView(u);
			Logger.LogServer(e);
	} catch (Exception e) {
		UISession u = new UISession(null);
	    u.setState(new ObjectState("TLC-3902-01"));
	    v = new SessionView(u);
		Logger.LogServer(e);
	}return v;
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return "EDITBRANCH";
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 4002;
	}

}
