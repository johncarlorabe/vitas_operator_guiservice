package com.psi.reports.c;

import com.psi.reports.m.AdminAllocationHistoryCollection;
import com.psi.reports.m.AdminPrepaidTransCollection;
import com.psi.reports.m.AuditTrail;
import com.psi.reports.v.CollectionView;
import com.psi.reports.v.NoDataFoundView;
import com.tlc.common.Logger;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class AllocHistoryAdminColCommand extends UICommand{

	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;
		
		try {
			sess = ExistingSession.parse(this.reqHeaders);		
			if(sess.exists()) {
		
		
		String code = this.params.get("BranchCode").toString();
		String datefrom = this.params.get("DateFrom").toString();
		String dateto = this.params.get("DateTo").toString();
		String accounttype = this.params.get("AccountType").toString();
		String cashier = this.params.get("Cashier").toString();
		
		AdminAllocationHistoryCollection model = new AdminAllocationHistoryCollection();
						model.setBranch(code);
						model.setDatefrom(datefrom);
						model.setDateto(dateto);
						model.setAuthorizedSession(sess);
						model.setAccounttype(accounttype);
						model.setCashier(cashier);
				if(code.equals("ALL")){
					if(model.hasRows()){
						AuditTrail audit  = new AuditTrail();
			    		audit.setIp(model.getAuthorizedSession().getIpAddress());
			    		audit.setModuleid(String.valueOf(this.getId()));
			    		audit.setEntityid(code);
			    		audit.setLog("Successfully fetched data"+" ~ Date: " +datefrom+"|Type: "+accounttype);
			    		audit.setStatus("00");
			    		audit.setUserid(model.getAuthorizedSession().getAccount().getId());
			    		audit.setUsername(model.getAuthorizedSession().getAccount().getUserName());
			    		
			    		audit.insert();
						return new CollectionView("00",model);  
					}else{
							ObjectState state = new ObjectState("01", "No data found");
							AuditTrail audit  = new AuditTrail();
				    		audit.setIp(model.getAuthorizedSession().getIpAddress());
				    		audit.setModuleid(String.valueOf(this.getId()));
				    		audit.setEntityid(code);
				    		audit.setLog("No data found"+" ~ Date: " +datefrom+"|Type: "+accounttype);
				    		audit.setStatus("01");
				    		audit.setUserid(model.getAuthorizedSession().getAccount().getId());
				    		audit.setUsername(model.getAuthorizedSession().getAccount().getUserName());
				    		
				    		audit.insert();
							return new NoDataFoundView(state); 
					}
				}else{
					if(accounttype.equalsIgnoreCase("CASHIER")) {
						if(cashier.equalsIgnoreCase("ALL")) {
							if(model.getPrepaidColCashierAll()){
								AuditTrail audit  = new AuditTrail();
					    		audit.setIp(model.getAuthorizedSession().getIpAddress());
					    		audit.setModuleid(String.valueOf(this.getId()));
					    		audit.setEntityid(code);
					    		audit.setLog("Successfully fetched data"+" ~ Date: " +datefrom+"|Type: "+accounttype);
					    		audit.setStatus("00");
					    		audit.setUserid(model.getAuthorizedSession().getAccount().getId());
					    		audit.setUsername(model.getAuthorizedSession().getAccount().getUserName());
					    		
					    		audit.insert();
								return new CollectionView("00",model);  
							}else{
									ObjectState state = new ObjectState("01", "No data found");
									AuditTrail audit  = new AuditTrail();
						    		audit.setIp(model.getAuthorizedSession().getIpAddress());
						    		audit.setModuleid(String.valueOf(this.getId()));
						    		audit.setEntityid(code);
						    		audit.setLog("No data found"+" ~ Date: " +datefrom+"|Type: "+accounttype);
						    		audit.setStatus("01");
						    		audit.setUserid(model.getAuthorizedSession().getAccount().getId());
						    		audit.setUsername(model.getAuthorizedSession().getAccount().getUserName());
						    		
						    		audit.insert();
									return new NoDataFoundView(state); 
							}
						} else {
							if(model.getPrepaidColCashierSpecific()){
								AuditTrail audit  = new AuditTrail();
					    		audit.setIp(model.getAuthorizedSession().getIpAddress());
					    		audit.setModuleid(String.valueOf(this.getId()));
					    		audit.setEntityid(code);
					    		audit.setLog("Successfully fetched data"+" ~ Date: " +datefrom+"|Type: "+accounttype);
					    		audit.setStatus("00");
					    		audit.setUserid(model.getAuthorizedSession().getAccount().getId());
					    		audit.setUsername(model.getAuthorizedSession().getAccount().getUserName());
					    		
					    		audit.insert();
								return new CollectionView("00",model);  
							}else{
									ObjectState state = new ObjectState("01", "No data found");
									AuditTrail audit  = new AuditTrail();
						    		audit.setIp(model.getAuthorizedSession().getIpAddress());
						    		audit.setModuleid(String.valueOf(this.getId()));
						    		audit.setEntityid(code);
						    		audit.setLog("No data found"+" ~ Date: " +datefrom+"|Type: "+accounttype);
						    		audit.setStatus("01");
						    		audit.setUserid(model.getAuthorizedSession().getAccount().getId());
						    		audit.setUsername(model.getAuthorizedSession().getAccount().getUserName());
						    		
						    		audit.insert();
									return new NoDataFoundView(state); 
							}
						}
					}
					if(accounttype.equalsIgnoreCase("DEALER")) {
						if(model.getPrepaidColDealer()){
							AuditTrail audit  = new AuditTrail();
				    		audit.setIp(model.getAuthorizedSession().getIpAddress());
				    		audit.setModuleid(String.valueOf(this.getId()));
				    		audit.setEntityid(code);
				    		audit.setLog("Successfully fetched data"+" ~ Date: " +datefrom+"|Type: "+accounttype);
				    		audit.setStatus("00");
				    		audit.setUserid(model.getAuthorizedSession().getAccount().getId());
				    		audit.setUsername(model.getAuthorizedSession().getAccount().getUserName());
				    		
				    		audit.insert();
							return new CollectionView("00",model);  
						}else{
								ObjectState state = new ObjectState("01", "No data found");
								AuditTrail audit  = new AuditTrail();
					    		audit.setIp(model.getAuthorizedSession().getIpAddress());
					    		audit.setModuleid(String.valueOf(this.getId()));
					    		audit.setEntityid(code);
					    		audit.setLog("No data found"+" ~ Date: " +datefrom+"|Type: "+accounttype);
					    		audit.setStatus("01");
					    		audit.setUserid(model.getAuthorizedSession().getAccount().getId());
					    		audit.setUsername(model.getAuthorizedSession().getAccount().getUserName());
					    		
					    		audit.insert();
								return new NoDataFoundView(state); 
						}
					}
					
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
		return "ADMINALLOCATIONHISTORY";
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 3016;
	}

}
