package com.tlc.encryption.sim;

import java.util.Hashtable;
import java.util.concurrent.atomic.AtomicInteger;

import com.tlc.common.DataRow;
import com.tlc.common.Logger;
import com.tlc.common.SystemInfo;
import com.tlc.common.Util;

public class MessageDecryptor {
	private static Hashtable<Integer,SmsDecryptor> keylist = new Hashtable<Integer,SmsDecryptor>();
	private static AtomicInteger counter = new AtomicInteger();
	public static String decrypt(String msisdn, byte[] bytes){
		int index = bytes[0] & 0xff;
		if(keylist.contains(index)){
			return keylist.get(index).decrypt(msisdn, bytes);
		}
		try {
			SmsDecryptor decryptor = null;
			synchronized(keylist){
				if(!keylist.contains(index)){ 
					DataRow row = SystemInfo.getDb().QueryDataRow("SELECT INTERFACE FROM TBLSIMKEY WHERE STATUS = 1 AND ID = ? ", index);
					if(row.size() == 0)
						return null;
					Class<?> klass = Class.forName(Util.cast(row.get("INTERFACE"),String.class));
					decryptor = (SmsDecryptor)klass.getConstructor(int.class).newInstance(index);
					keylist.put(index,decryptor);
					Logger.LogServer("Add SmsDecryptor: " + klass.getName() + " " + index);
				}
				decryptor = keylist.get(index);
			}
			return decryptor.decrypt(msisdn, bytes);
		} catch (Exception e) {
			if(counter.incrementAndGet() < 10)
				Logger.LogServer("Failed SmsDecryptor: " + index, e);
		}
		return null;
	}
}
