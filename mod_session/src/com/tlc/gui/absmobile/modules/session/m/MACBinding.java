package com.tlc.gui.absmobile.modules.session.m;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.psi.ibc.common.Logger;
import com.tlc.common.SystemInfo;

public class MACBinding {
	@SuppressWarnings("unused")
	private String ip;
	private Object mac;
	private String accountNumber;
	private String username;

	public MACBinding(String accountNumber, String content, String username) {
		try {
			this.accountNumber = accountNumber;
			this.username = username;
			JSONObject object = (JSONObject) new JSONParser().parse(content);
			if (object != null) {
				if (object.containsKey("ip"))
					this.ip = object.get("ip").toString();

				if (object.containsKey("mac"))
					this.mac = object.get("mac");
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean exist() {
		try {
			int count = SystemInfo
					.getDb()
					.QueryScalar(
							"SELECT COUNT(1) FROM TBLMACBINDING WHERE ACCOUNTNUMBER = ? AND TERMINALID = ?",
							0, new Object[] { this.accountNumber, this.mac });
			if (count > 0)
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}
}