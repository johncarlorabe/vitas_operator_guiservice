package com.tlc.gui.absmobile.modules.session.c;

import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;

public class LogoutCommand extends UICommand {

	
	@Override
	public IView execute() {
		ExistingSession sess;
		SessionView v=null;
		try {
			sess = ExistingSession.parse(this.reqHeaders);
			if(sess.exists()){
				sess.terminate();
				v= new SessionView(sess);
			}else{
				UISession u = new UISession(null);
				u.setState(new ObjectState("TLC-3903-01"));
				v = new SessionView(u);
			}
		} catch (SessionNotFoundException e) {
			UISession u = new UISession(null);
			u.setState(new ObjectState("TLC-3903-01"));
			v = new SessionView(u);
		}
		return v;
	}

	@Override
	public String getKey() {
		return "LOGOUT";
	}

	@Override
	public int getId() {
		return 3903;
	}

}
