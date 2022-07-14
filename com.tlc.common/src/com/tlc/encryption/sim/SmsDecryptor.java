package com.tlc.encryption.sim;

public interface SmsDecryptor {
	public String decrypt(String msisdn, byte[] encrypted);
}
