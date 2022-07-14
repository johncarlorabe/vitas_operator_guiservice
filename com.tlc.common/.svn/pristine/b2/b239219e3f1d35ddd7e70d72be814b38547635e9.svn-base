package com.tlc.encryption;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EncryptFunction {
	private static Log log = LogFactory.getLog(EncryptFunction.class);
	
	private static ThreadLocal<Cipher> aes = new ThreadLocal<Cipher>(){
		@Override
		protected Cipher initialValue() {
			try {
				return Cipher.getInstance("AES/CBC/PKCS5Padding");
			} catch (Exception e) {
				log.error(e);
			}
			return null;
		}
	};
	
	private static ThreadLocal<Cipher> desede = new ThreadLocal<Cipher>(){
		@Override
		protected Cipher initialValue() {
			try {
				return Cipher.getInstance("DESede/CBC/NoPadding");
			} catch (Exception e) {
				log.error(e);
			}
			return null;
		}
	};
	
	private static ThreadLocal<Cipher> rsa = new ThreadLocal<Cipher>(){
		@Override
		protected Cipher initialValue() {
			try {
				return Cipher.getInstance("RSA/ECB/PKCS1Padding");
			} catch (Exception e) {
				log.error(e);
			}
			return null;
		}
	};
	
	private static ThreadLocal<KeyFactory> rsakeyfactory = new ThreadLocal<KeyFactory>(){
		@Override
		protected KeyFactory initialValue() {
			try {
				return KeyFactory.getInstance("RSA");
			} catch (Exception e) {
				log.error(e);
			}
			return null;
		}
	};

	public static byte[] decryptAES(byte[] data, byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException{
		SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
		Cipher cipher = aes.get();
		cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
		return cipher.doFinal(data);
	}
	
	public static byte[] encryptAES(byte[] data, byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException{
		SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
		Cipher cipher = aes.get();
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
		return cipher.doFinal(data);
	}

	public static byte[] decrypt3DES(byte[] data, byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException{
		SecretKeySpec skeySpec = new SecretKeySpec(key, "DESede");
		Cipher cipher = desede.get();
		cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
		return cipher.doFinal(data);
	}
	
	public static byte[] encrypt3DES(byte[] data, byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException{
		SecretKeySpec skeySpec = new SecretKeySpec(key, "DESede");
		Cipher cipher = desede.get();
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
		return cipher.doFinal(data);
	}
	
	public static byte[] decryptRSA(byte[] data, byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException, IOException{
		EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(key);
		RSAPrivateKey prvKey = (RSAPrivateKey)rsakeyfactory.get().generatePrivate(privateKeySpec);
		int blocksize = prvKey.getModulus().bitLength()/8;
		Cipher cipher = rsa.get();
		cipher.init(Cipher.DECRYPT_MODE, prvKey);
		byte[] bytes = new byte[blocksize];
		ByteArrayOutputStream buff = new ByteArrayOutputStream();
    	ByteBuffer b = ByteBuffer.wrap(data, 0, data.length);
    	while(b.remaining() > 0){
    		int remaining = b.remaining();
    		if(remaining > bytes.length){
    			b.get(bytes,0,bytes.length);
    			buff.write(cipher.doFinal(bytes));
    		}
    		else{
    			b.get(bytes,0,remaining);
    			buff.write(cipher.doFinal(bytes,0,remaining));
    		}
    	}
    	return buff.toByteArray();
	}
	
	public static byte[] encryptRSA(byte[] data, byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException, IOException{
		EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(key);
		RSAPublicKey pubkey = (RSAPublicKey)rsakeyfactory.get().generatePublic(publicKeySpec);
		int blocksize = pubkey.getModulus().bitLength()/8;
		Cipher cipher = rsa.get();
		cipher.init(Cipher.ENCRYPT_MODE, pubkey);
		byte[] bytes = new byte[blocksize - 11];
		ByteArrayOutputStream buff = new ByteArrayOutputStream();
    	ByteBuffer b = ByteBuffer.wrap(data, 0, data.length);
    	while(b.remaining() > 0){
    		int remaining = b.remaining();
    		if(remaining > bytes.length){
    			b.get(bytes,0,bytes.length);
    			buff.write(cipher.doFinal(bytes));
    		}
    		else{
    			b.get(bytes,0,remaining);
    			buff.write(cipher.doFinal(bytes,0,remaining));
    		}
    	}
    	return buff.toByteArray();
	}

}
