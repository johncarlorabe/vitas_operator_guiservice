package com.tlc.sky.pingen.arch.uc.acct;

import com.tlc.gui.modules.session.UIPassword;

public abstract class AbstractRegisteredPingenPassword extends UIPassword {
		public abstract boolean exists();
		public abstract boolean reset();
		public abstract boolean change(UIPassword pwd);
}