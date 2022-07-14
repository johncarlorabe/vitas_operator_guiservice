package com.tlc.gui.absmobile.modules.session.utils;

import com.tlc.common.EncryptedProperties;
import com.tlc.common.Util;

public class OtherProperties {

	private static String token;
	private static String type;
	private static String url;
	private static String balance_url;
	private static String load_url;
	private static String remit_url;
	private static String claim_url;
	private static String bills_url;
	private static String cashin_url;
	private static String cashinapproved_url;
	private static String cashinreject_url;
	private static String branch_url;
	
	static {
		try {
			EncryptedProperties prop = new EncryptedProperties(Util.getWorkingDirectory() + "/bayadpoc.properties");
			token = prop.getProperty("token");
			type = prop.getProperty("type");
			url = prop.getProperty("url");
			balance_url = prop.getProperty("balance_url");
			load_url = prop.getProperty("load_url");
			remit_url = prop.getProperty("remit_url");
			claim_url = prop.getProperty("claim_url");
			bills_url = prop.getProperty("bills_url");
			cashin_url = prop.getProperty("cashin_url");
			cashinapproved_url = prop.getProperty("cashinapproved_url");
			cashinreject_url = prop.getProperty("cashinreject_url");
			branch_url = prop.getProperty("branch_url");
		} catch (Exception e) {
			
		}
	}


	public String getToken() {
		return token;
	}
	public String getUrl() {
		return url;
	}
	public String getType() {
		return type;
	}
	public String getBalance_url() {
		return balance_url;
	}
	public String getLoad_url() {
		return load_url;
	}
	public String getRemit_url() {
		return remit_url;
	}
	public String getClaim_url() {
		return claim_url;
	}
	public String getBills_url() {
		return bills_url;
	}
	public String getCashin_url() {
		return cashin_url;
	}
	public String getCashinapproved_url() {
		return cashinapproved_url;
	}
	public String getCashinreject_url() {
		return cashinreject_url;
	}
	public String getBranch_url() {
		return branch_url;
	}
	
	
}
