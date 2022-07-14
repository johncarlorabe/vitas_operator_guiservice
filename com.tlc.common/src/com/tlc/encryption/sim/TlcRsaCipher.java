package com.tlc.encryption.sim;

import java.util.Arrays;

import com.tlc.common.DataCipher;
import com.tlc.common.DbWrapper;
import com.tlc.common.Logger;
import com.tlc.common.StringUtil;

public class TlcRsaCipher implements SmsDecryptor{
	private int id = 100;
	private static DataCipher decrypter = null;
	//TODO Enable Disable nonce check;
	private static boolean checknonce = true;
	private static DbWrapper db = null;
	private static int nonceLenght = db.QueryScalar("SELECT DATA_LENGTH FROM USER_TAB_COLUMNS WHERE TABLE_NAME = 'TBLNONCE' AND COLUMN_NAME = 'NONCE'", 0) / 2;

	public TlcRsaCipher(int id){
		this.id = id;
		byte[] key = StringUtil.base64Decode(db.QueryScalar("SELECT DECRYPT(KEY,?,ID) FROM TBLSIMKEY WHERE ID = ?","", db.getCrypt(), id));
		decrypter = DataCipher.getRSADecrypter(key);
	}
	
	public String decrypt(String msisdn, byte[] encrypted){
		try {
			String decoded64 = new String(encrypted,"UTF-8");
			byte[] decoded = StringUtil.base64Decode(decoded64.substring(1));
			byte[] bytes = decrypter.transform(decoded);
			bytes = xor(bytes);
			String message = new String(bytes, 1, bytes.length - 1,"UTF8");
			if(isValidNonce(msisdn,bytes[0])){
				return message;
			}else{
				Logger.LogServer("INVALID NONCE " + bytes[0]);
				return "";
			}
		} catch (Exception e) {
			Logger.LogServer("TLCSmsCipher Exception:", e);
		}
		return "";
	}
	
	public static byte[] xor(byte[] data){
		for(int i = 0; i < data.length; i ++){
			data[i] = (byte)(data[i] ^ 0x87);
		}
		return data;
	}

	private boolean isValidNonce(String msisdn, byte nonce){
		if(!checknonce){
			return true;
		}
		
		String hex = db.QueryScalar("SELECT NONCE FROM TBLNONCE WHERE MSISDN = ? AND SIMID = ?", "", msisdn, id);
		if(StringUtil.isNullOrEmpty(hex)){
			hex = String.format("%02X", nonce);
			db.QueryUpdate("INSERT INTO TBLNONCE(MSISDN,NONCE,SIMID) VALUES(?,?,?)", msisdn,hex,id);
			return true;
		}
		
		byte[] dbNonce = StringUtil.hexDecode(hex);
		Arrays.sort(dbNonce);
		if(Arrays.binarySearch(dbNonce, nonce) > 0)
			return false;
		hex += String.format("%02X", nonce);
		if(dbNonce.length >= nonceLenght)
			hex = hex.substring(2);
		
		db.QueryUpdate("UPDATE TBLNONCE SET NONCE = ? WHERE MSISDN = ? AND SIMID = ?", hex, msisdn, id);
		
		return true;
	}

}
