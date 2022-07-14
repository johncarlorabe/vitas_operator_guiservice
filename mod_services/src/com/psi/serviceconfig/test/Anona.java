package com.psi.serviceconfig.test;

import com.tlc.common.DataRowCollection;
import com.tlc.common.SystemInfo;

public class Anona {

	public static void main(String[] args) {
		System.out.println("PASOk");
		DataRowCollection rows = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLBRANCHES WHERE STATUS = 1");
		int counter =0;
		System.out.println("pasok2");

	}

}
