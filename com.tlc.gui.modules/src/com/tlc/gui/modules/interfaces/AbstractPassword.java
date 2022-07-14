package com.tlc.gui.modules.interfaces;

import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.session.UIPassword;

public abstract class AbstractPassword extends UIPassword{
	
		
		public abstract boolean reset();
		public abstract boolean change(UIPassword pwd);
				
}