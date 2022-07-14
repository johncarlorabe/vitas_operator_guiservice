package com.tlc.encryption;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PasswordGenerator {
	private static Log log = LogFactory.getLog(PasswordGenerator.class);
	
	public static final int UPPER_ALPHA = 1;
	public static final int LOWER_ALPHA = 2;
	public static final int NUMERIC_CHAR = 4;
	public static final int SYMBOL1_CHAR = 8;
	private static final String upper =   "ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String lower =   "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz";
	private static final String numeric = "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";
	private static final String symbol1 = " !\"$%^&*()-_=+[{]};:'@#~|,<.>/? !\"$%^&*()-_=+[{]};:'@#~|,<.>/? !\"$%^&*()-_=+[{]};:'@#~|,<.>/? !\"$%^&*()-_=+[{]};:'@#~|,<.>/?";
	private static final String uppernumeric = upper + numeric;
	private static final int    uppernumericlen = uppernumeric.length();
	
	private static ThreadLocal<SecureRandom> randomgenerator = new ThreadLocal<SecureRandom>(){
		@Override
		protected SecureRandom initialValue() {
			return new SecureRandom();
		}
	};
	
	private static ThreadLocal<KeyPairGenerator> rsakeypairgenerator = new ThreadLocal<KeyPairGenerator>(){
		@Override
		protected KeyPairGenerator initialValue() {
			try {
				return KeyPairGenerator.getInstance("RSA");
			} catch (Exception e) {
				log.error(e);
			}
			return null;
		}
	};
	
	public static String generatePassword(int len, int option){
		if(option <= 0)
			option = 15; 
		StringBuilder password = new StringBuilder();
		StringBuilder allsymbol = new StringBuilder();
		if((option & UPPER_ALPHA) == UPPER_ALPHA)
			allsymbol.append(upper);
		if((option & LOWER_ALPHA) == LOWER_ALPHA)
			allsymbol.append(lower);
		if((option & NUMERIC_CHAR) == NUMERIC_CHAR)
			allsymbol.append(numeric);
		if((option & SYMBOL1_CHAR) == SYMBOL1_CHAR)
			allsymbol.append(symbol1);
		int n = allsymbol.length();
		for(int i = 0; i < len; i++){
			password.append(allsymbol.charAt(randomgenerator.get().nextInt(n)));
		}
		return password.toString();
	}
	
	public static String generatePassword(int len){
		StringBuilder password = new StringBuilder();
		for(int i = 0; i < len; i++){
			password.append(uppernumeric.charAt(randomgenerator.get().nextInt(uppernumericlen)));
		}
		return password.toString();
	}
	
	public static byte[] generateKey(int bits){
		byte[] bytes = new byte[bits / 8];
		randomgenerator.get().nextBytes(bytes);
		return bytes;
	}
	
	public static byte[] generateKey(int bits, String password) throws InvalidKeySpecException, NoSuchAlgorithmException{
		return generateKey(bits, new byte[]{0,0,0,0,0,0,0,0}, password);
	}
	
	public static byte[] generateKey(int bits, byte[] salt, String password) throws NoSuchAlgorithmException, InvalidKeySpecException{
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, bits);
		SecretKey tmp = factory.generateSecret(spec);
		return tmp.getEncoded();
	}
	 
	public static KeyPair generateRSAKey(int bits) throws NoSuchAlgorithmException{
		KeyPairGenerator rsagenerator = rsakeypairgenerator.get();
		rsagenerator.initialize(bits);
        return rsagenerator.genKeyPair();
	}
	
	public static byte[] getKey2Bytes(Key key) throws NoSuchAlgorithmException, IOException{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream objout = new ObjectOutputStream(out);
		objout.writeObject(key);
		return out.toByteArray();
	}

}
