package com.tlc.gui.modules.common;

public abstract class ReportView extends ObjectState implements IView {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected ModelCollection rows = null;
	public ReportView(String code,ModelCollection data){
		super(code);
		this.rows = data;
	}

	@Override
	public String toString() {
		return this.render();	
	}	
}
