package com.tlc.keymgt;

public interface IKeyStatusDelegate {

	void onExpiredKeySupplied();
	void onValidKeySupplied(String args[]);
	void onInvalidKeySupplied();
}
