package com.tlc.gui.absmobile.modules.session.c;

import com.tlc.common.Logger;
import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.m.NewSession;
import com.tlc.gui.absmobile.modules.session.m.SessionPassword;
import com.tlc.gui.absmobile.modules.session.v.GenericJsonView;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class ChangePasswordCommand extends UICommand{

	@Override
	public IView execute() {
		SessionPassword cpwd = new SessionPassword(this.params.get("OLDPASS"));
		SessionPassword newPassword = new SessionPassword(this.params.get("NEWPASS"));
		Logger.LogServer(cpwd.toString());
		Logger.LogServer(newPassword.toString());
		IView v=null;
		try {
			UISession sess = ExistingSession.parse(this.reqHeaders);
			cpwd.setAuthorizedSession(sess);
			if(cpwd.correct()){
				cpwd.change(newPassword);
				cpwd.send();
			}
			v= new GenericJsonView(cpwd);
		} catch (SessionNotFoundException e) {
			UISession u = new UISession(null);
			u.setState(new ObjectState("TLC-3904-01"));
			v = new SessionView(u);
		}	 
		return v;
	}

	@Override
	public String getKey() {
		return "CPWD";
	}

	@Override
	public int getId() {
		return 3904;
	}

}
