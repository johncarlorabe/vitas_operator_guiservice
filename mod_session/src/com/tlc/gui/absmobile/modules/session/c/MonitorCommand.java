package com.tlc.gui.absmobile.modules.session.c;

import com.tlc.gui.absmobile.modules.session.m.ExistingSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.SessionNotFoundException;
import com.tlc.gui.modules.session.UISession;


public class MonitorCommand extends UICommand {

	@Override
	public IView execute() {
		ExistingSession sess=null;
		SessionView v= null;
		try {
			sess =ExistingSession.parse(this.reqHeaders);
			sess.monitor();
			v = new SessionView(sess);
			
		} catch (SessionNotFoundException e) {
			UISession u = new UISession(null);
			u.setState(new ObjectState("TLC-3902-01"));
			v = new SessionView(u);
		}
		return v;
	}

	@Override
	public String getKey() {
		return "MONITOR";
	}

	@Override
	public int getId() {
		return 3902;
	}

}
