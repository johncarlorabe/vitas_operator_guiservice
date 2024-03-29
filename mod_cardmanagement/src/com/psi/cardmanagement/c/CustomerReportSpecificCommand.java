package com.psi.cardmanagement.c;

import com.psi.cardmanagement.m.CustomerReportCollection;
import com.psi.cardmanagement.utils.AuditTrail;
import com.psi.cardmanagement.v.CollectionView;
import com.psi.cardmanagement.v.NoDataFoundView;
import com.tlc.common.Logger;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class CustomerReportSpecificCommand extends UICommand {

	@Override
	public IView execute() {
		ExistingSession sess = null;
		SessionView v = null;

		try {
			sess = ExistingSession.parse(this.reqHeaders);
			if (sess.exists()) {

				String type = this.params.get("Type").toString();
				String datefrm = this.params.get("DateFrom").toString();
				String dateto = this.params.get("DateTo").toString();
				String accountnumber = this.params.get("AccountNumber").toString();
				
				CustomerReportCollection model = new CustomerReportCollection();

				model.setType(type);
				model.setDatefrom(datefrm);
				model.setDateto(dateto);
				model.setAccountnumber(accountnumber);
				model.setAuthorizedSession(sess);

				if (type.equals("ALL")) {
					if (model.hasRows()) {
						AuditTrail audit = new AuditTrail();
						audit.setIp(model.getAuthorizedSession().getIpAddress());
						audit.setModuleid(String.valueOf(this.getId()));
						audit.setEntityid(type);
						audit.setLog("Successfully fetched data");
						audit.setStatus("00");
						audit.setUserid(model.getAuthorizedSession().getAccount().getId());
						audit.setUsername(model.getAuthorizedSession().getAccount().getUserName());

						audit.insert();
						return new CollectionView("00", model);
					} else {
						ObjectState state = new ObjectState("01", "No data found");
						AuditTrail audit = new AuditTrail();
						audit.setIp(model.getAuthorizedSession().getIpAddress());
						audit.setModuleid(String.valueOf(this.getId()));
						audit.setEntityid(type);
						audit.setLog("No data found");
						audit.setStatus("01");
						audit.setUserid(model.getAuthorizedSession().getAccount().getId());
						audit.setUsername(model.getAuthorizedSession().getAccount().getUserName());

						audit.insert();
						return new NoDataFoundView(state);
					}
				} else {
					if (model.filterByType()) {
						AuditTrail audit = new AuditTrail();
						audit.setIp(model.getAuthorizedSession().getIpAddress());
						audit.setModuleid(String.valueOf(this.getId()));
						audit.setEntityid(type);
						audit.setLog("Successfully fetched data");
						audit.setStatus("00");
						audit.setUserid(model.getAuthorizedSession().getAccount().getId());
						audit.setUsername(model.getAuthorizedSession().getAccount().getUserName());

						audit.insert();
						return new CollectionView("00", model);
					} else {
						ObjectState state = new ObjectState("01", "No data found");
						AuditTrail audit = new AuditTrail();
						audit.setIp(model.getAuthorizedSession().getIpAddress());
						audit.setModuleid(String.valueOf(this.getId()));
						audit.setEntityid(type);
						audit.setLog("No data found");
						audit.setStatus("01");
						audit.setUserid(model.getAuthorizedSession().getAccount().getId());
						audit.setUsername(model.getAuthorizedSession().getAccount().getUserName());

						audit.insert();
						return new NoDataFoundView(state);
					}
				}

			} else {
				UISession u = new UISession(null);
				u.setState(new ObjectState("TLC-3902-01"));
				v = new SessionView(u);
			}
		} catch (SessionNotFoundException e) {
			UISession u = new UISession(null);
			u.setState(new ObjectState("TLC-3902-01"));
			v = new SessionView(u);
			Logger.LogServer(e);
		} catch (Exception e) {
			UISession u = new UISession(null);
			u.setState(new ObjectState("TLC-3902-01"));
			v = new SessionView(u);
			Logger.LogServer(e);
		}
		return v;
	}

	@Override
	public String getKey() {
		return "CARDTRANSACTIONHISTORY";
	}

	@Override
	public int getId() {
		return 7200;
	}

}
