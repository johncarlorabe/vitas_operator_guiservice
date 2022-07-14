package com.tlc.gui.absmobile.modules.session.c;

import com.tlc.common.Logger;
import com.tlc.gui.absmobile.modules.session.m.NewSession;
import com.tlc.gui.absmobile.modules.session.v.SessionView;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.UICommand;
import com.tlc.gui.modules.session.Token;
import com.tlc.gui.modules.session.UISession;

public class LoginCommand extends UICommand {

	@Override
	public IView execute() {

		NewSession sess =  NewSession.parse(this.reqHeaders);
		Logger.LogServer("panget mo:"+sess.start());
		
		if(!sess.getAccount().getPassword().valid()){
			sess.setToken(null);
		}
		SessionView v = new SessionView(sess);
		return v;
	}

	@Override
	public String getKey() {
		return "LOGN";
	}

	@Override
	public int getId() {
		return 3901;
	}

}
