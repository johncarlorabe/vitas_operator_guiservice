package com.tlc.encryption.sim;

import java.util.Arrays;

import com.tlc.common.DataCipher;
import com.tlc.common.DbWrapper;
import com.tlc.common.Logger;
import com.tlc.common.StringUtil;
import com.tlc.common.SystemInfo;


public class TdeaKey implements SmsDecryptor{
	private int id = 22;
	private DataCipher decrypter;
	//TODO Enable Disable nonce check;
	protected static boolean checknonce = true;
	private static DbWrapper db = SystemInfo.getDb();
	
	public TdeaKey(int id){
		this.id = id;
		byte[] key = StringUtil.hexDecode(db.QueryScalar("SELECT DECRYPT(KEY,?,ID) FROM TBLSIMKEY WHERE ID = ?","020202020202020202020202020202020202020202020202", db.getCrypt(), id));
		this.decrypter = DataCipher.get3DESDecrypter(key);
	}
	
	public String decrypt(String msisdn, byte[] encrypted){
		try {
			byte[] bytes = decrypter.transform(encrypted,1,encrypted.length - 1);
			bytes = DataCipher.removeBitPadding(bytes, 8);
			bytes = unpack(bytes);
			byte[] plain = new byte[bytes.length - 1];
			System.arraycopy(bytes, 1, plain, 0, plain.length);
			String message = decodeString(plain);
			if(isValidNonce(msisdn,bytes[0])){
				return message;
			}else{
				return "NONCE " + bytes[0] + " " + message;
			}
		} catch (Exception e) {
			Logger.LogServer("Module Oberthur Exception:", e);
		}
		return "";
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
		
		int intNonce = nonce & 0xff;
		int intHex   = Integer.parseInt(hex, 16);
		
		if(intNonce < intHex + 0x20 && intNonce > intHex || intNonce + 0x100 < intHex + 0x20 && intNonce + 0x100 > intHex){
			hex = String.format("%02X", nonce);
			db.QueryUpdate("UPDATE TBLNONCE SET NONCE = ? WHERE MSISDN = ? AND SIMID = ?", hex, msisdn, id);
			return true;
		}
		
		return false;
	}
		
    private static byte[] unpack(byte[] packed) {
        int unpackedLen = (packed.length * 8) / 7;
        byte[] unpacked = new byte[unpackedLen];
        int pos = 0;
        int i = 0;
        while (i < packed.length) {
            int mask = 0x7f;
            int jmax = (i + 8) > packed.length ? (packed.length - i) : 8;
            for (int j = 0; j < jmax; j++) {
                int b1 = (int) packed[i + j] & mask;
                int b2 = 0x0;
                try {
                    b2 = (int) packed[(i + j) - 1] & 0x00ff;
                } catch (ArrayIndexOutOfBoundsException x) {
                }
                unpacked[pos++] = (byte) ((b1 << j) | (b2 >>> (8 - j)));
                mask >>= 1;
            }
            i += 7;
        }
        if(pos < unpacked.length){
        	byte temp = (byte)((packed[packed.length - 1] >> 1) & 0x7f);
        	if(temp != (byte)0)
        		unpacked[pos++] = temp;
        	else
        		unpacked = Arrays.copyOf(unpacked, pos);
        }
        return unpacked;
    }

    private static String decodeString(byte[] b) {
		if (b == null) {
			return "";
		}

		char[] table = CHAR_TABLE;
		StringBuffer buf = new StringBuffer();

		for (int i = 0; i < b.length; ++i) {
			int code = b[i] & 0xFF;
			if (code == 27) {
				table = EXT_CHAR_TABLE;
			} else {
				buf.append((code >= table.length) ? '?' : table[code]);

				table = CHAR_TABLE;
			}
		}

		return buf.toString();
	}
    
	private static final char[] CHAR_TABLE = { '@', 163, '$', 165, 232, 233,
		249, 236, 242, 199, '\n', 216, 248, '\r', 197, 229, 916, '_', 934,
		915, 923, 937, 928, 936, 931, 920, 926, ' ', 198, 230, 223, 201,
		' ', '!', '"', '#', 164, '%', '&', '\'', '(', ')', '*', '+', ',',
		'-', '.', '/', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
		':', ';', '<', '=', '>', '?', 161, 'A', 'B', 'C', 'D', 'E', 'F',
		'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
		'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 196, 214, 209, 220, 167, 191,
		'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
		'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
		228, 246, 241, 252, 224 };

	private static final char[] EXT_CHAR_TABLE = { '\0', '\0', '\0', '\0',
		'\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0',
		'\0', '\0', '\0', '\0', '\0', '^', '\0', '\0', '\0', '\0', '\0',
		'\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0',
		'\0', '\0', '\0', '{', '}', '\0', '\0', '\0', '\0', '\0', '\\',
		'\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0',
		'\0', '[', '~', ']', '\0', '|', '\0', '\0', '\0', '\0', '\0', '\0',
		'\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0',
		'\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0',
		'\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', 8364, '\0', '\0',
		'\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0',
		'\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0',
		'\0', '\0' };

}
