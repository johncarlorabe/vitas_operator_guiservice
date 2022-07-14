package com.tlc.gui.modules.common;



public abstract class NormalView  implements IView {
	protected Model data=null;
	public NormalView(Model m){
		
		this.data = m;
	}
	@Override
	public String toString() {
		return this.render();	
	}	
}