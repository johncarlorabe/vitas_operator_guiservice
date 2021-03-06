package com.tlc.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/**
 * 
 * @author Mark Anthony C. Moreno
 * DataCipher is thread safe and uses Object pool to increase performance.
 * 
 * Example
 * 
 * byte[] key = new byte[]{0x01,0x01,0x01,0x01,0x01,0x01,0x01,0x01,0x01,0x01,0x01,0x01,0x01,0x01,0x01,0x01};
 * byte[] data = "Test Message".getBytes("UTF-8");
 * DataCipher cipher = DataCipher.getAESEncrypter(key);
 * byte[] encrypted = null;
 * try{
 * 		encrypted = cipher.transform(data);
 * }catch(Exception e){
 * 		e.printStackTrace();
 * }
 *
 */

public class DataCipher extends AbstractGenericPool<Cipher>{
	//private static final int PAD_NOPAD  = 0;
	//private static final int PAD_PKCS1  = 1;
	//private static final int PAD_PKCS5  = 2;
	//private static final int PAD_PKCS7  = 3;
	//private static final int PAD_SSL3   = 3;
	//private static final int PAD_BITPAD = 4;
	
	private Key key;
	private AlgorithmParameterSpec param;
	private IvParameterSpec iv;
	private String algo;
	private int mode;
	private int blocksize = 8;

	private DataCipher(String algo, int mode, Key key, AlgorithmParameterSpec param){
		this.algo = algo;
		this.mode = mode;
		this.key = key;
		this.param = param;
		if(mode == Cipher.DECRYPT_MODE && key instanceof RSAPrivateKey){
			RSAPrivateKey m = (RSAPrivateKey)key;
			blocksize = m.getModulus().bitLength()/8;
		}
		if(mode == Cipher.ENCRYPT_MODE && key instanceof RSAPublicKey){
			RSAPublicKey m = (RSAPublicKey)key;
			blocksize = m.getModulus().bitLength()/8;
		}
	}
	
	private DataCipher(String algo, int mode, Key key){
		this.algo = algo;
		this.mode = mode;
		this.key = key;
		this.param = null;

		if(mode == Cipher.DECRYPT_MODE && key instanceof RSAPrivateKey){
			RSAPrivateKey m = (RSAPrivateKey)key;
			blocksize = m.getModulus().bitLength()/8;
		}
		if(mode == Cipher.ENCRYPT_MODE && key instanceof RSAPublicKey){
			RSAPublicKey m = (RSAPublicKey)key;
			blocksize = m.getModulus().bitLength()/8;
		}
	}
	
	public byte[] transform(byte[] data) throws Exception{
		if(mode == Cipher.ENCRYPT_MODE) return encrypt(data,0,data.length);
		return decrypt(data,0,data.length);
	}
	
	public byte[] transform(byte[] data, int index, int lenght) throws Exception{
		if(mode == Cipher.ENCRYPT_MODE) return encrypt(data, index, lenght);
		return decrypt(data, index, lenght);
	}
	
	private byte[] decrypt(byte[] str, int index, int lenght) throws Exception {
		Cipher decrypter = null;
		try{
			decrypter = checkOut();
			if(!algo.startsWith("RSA"))
				return decrypter.doFinal(str, index, lenght);
			ByteArrayOutputStream buff = new ByteArrayOutputStream();
			int len ;
			if(algo.startsWith("RSA")){
				len = blocksize;
			}else{
				len = decrypter.getBlockSize();
			}
	    	byte[] bytes = new byte[len];
	    	ByteBuffer b = ByteBuffer.wrap(str, index, lenght);
	    	while(b.remaining() > 0){
	    		int remaining = b.remaining();
	    		if(remaining > bytes.length){
	    			b.get(bytes,0,bytes.length);
	    			buff.write(decrypter.doFinal(bytes));
	    		}
	    		else{
	    			b.get(bytes,0,remaining);
	    			buff.write(decrypter.doFinal(bytes,0,remaining));
	    		}
	    	}
	    	return buff.toByteArray();
		}catch(Exception e){
			if(algo.startsWith("RSA")){
				decrypter.init(mode, key);
			}else if(param == null){
				if(this.iv == null)this.iv = new IvParameterSpec(new byte[decrypter.getBlockSize()]);
				decrypter.init(Cipher.DECRYPT_MODE, key, iv);
			}else
				decrypter.init(Cipher.DECRYPT_MODE, key, param);
			throw e;
		}finally{
			checkIn(decrypter);
		}
	}
	
	private byte[] encrypt(byte[] str, int index, int lenght) throws Exception {
		Cipher encrypter = null;
		try{
			encrypter = checkOut();
			if(algo.startsWith("RSA")){
				byte[] bytes = new byte[blocksize - 11];
				ByteArrayOutputStream buff = new ByteArrayOutputStream();
		    	ByteBuffer b = ByteBuffer.wrap(str, index, lenght);
		    	while(b.remaining() > 0){
		    		int remaining = b.remaining();
		    		if(remaining > bytes.length){
		    			b.get(bytes,0,bytes.length);
		    			buff.write(encrypter.doFinal(bytes));
		    		}
		    		else{
		    			b.get(bytes,0,remaining);
		    			buff.write(encrypter.doFinal(bytes,0,remaining));
		    		}
		    	}
		    	return buff.toByteArray();
			}
			return encrypter.doFinal(str, index, lenght);
		}catch(Exception e){
			if(algo.startsWith("RSA")){
				encrypter.init(mode, key);
			}if(param == null){
				if(this.iv == null)this.iv = new IvParameterSpec(new byte[encrypter.getBlockSize()]);
				encrypter.init(Cipher.ENCRYPT_MODE, key, iv);
			}else
				encrypter.init(Cipher.ENCRYPT_MODE, key, param);
			throw e;
		}finally{
			checkIn(encrypter);
		}
	}
	
	@Override
	protected Cipher getInstance() throws Exception {
		Cipher cipher = Cipher.getInstance(algo);
		if(algo.startsWith("RSA")){
			cipher.init(mode, key);
		}else if(param == null){
			if(this.iv == null)this.iv = new IvParameterSpec(new byte[cipher.getBlockSize()]);
			cipher.init(mode, key, iv);
		}else{
			cipher.init(mode, key, param);
		}
		return cipher;
	}
	
	public static DataCipher getAESEncrypter(byte key[]){
		try {
			SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
			return new DataCipher("AES/CBC/PKCS5Padding",Cipher.ENCRYPT_MODE, skeySpec);
		} catch (Exception ex) {
			Logger.LogServer(ex);
		}
		return null;
	}
	
	public static DataCipher getAESDecrypter(byte key[]){
		try {
			SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
			return new DataCipher("AES/CBC/PKCS5Padding",Cipher.DECRYPT_MODE, skeySpec);
		} catch (Exception ex) {
			Logger.LogServer(ex);
		}
		return null;
	}
	
	public static DataCipher get3DESEncrypter(byte key[]){
		try {
			SecretKeySpec skeySpec = new SecretKeySpec(key, "DESede");
			return new DataCipher("DESede/CBC/NoPadding",Cipher.ENCRYPT_MODE, skeySpec);
		} catch (Exception ex) {
			Logger.LogServer(ex);
		}
		return null;
	}
	
	public static DataCipher get3DESDecrypter(byte key[]){
		try {
			SecretKeySpec skeySpec = new SecretKeySpec(key, "DESede");
			return new DataCipher("DESede/CBC/NoPadding",Cipher.DECRYPT_MODE, skeySpec);
		} catch (Exception ex) {
			Logger.LogServer(ex);
		}
		return null;
	}
	
	public static DataCipher getDESEncrypter(byte key[]){
		try {
			SecretKeySpec skeySpec = new SecretKeySpec(key, "DES");
			return new DataCipher("DES/CBC/PKCS5Padding",Cipher.ENCRYPT_MODE, skeySpec);
		} catch (Exception ex) {
			Logger.LogServer(ex);
		}
		return null;
	}
	
	public static DataCipher getDESDecrypter(byte key[]){
		try {
			SecretKeySpec skeySpec = new SecretKeySpec(key, "DES");
			return new DataCipher("DES/CBC/PKCS5Padding",Cipher.DECRYPT_MODE, skeySpec);
		} catch (Exception ex) {
			Logger.LogServer(ex);
		}
		return null;
	}
	
	public static DataCipher getRSAEncrypter(BigInteger exponent, BigInteger modulus){
		KeyFactory fact;
		PublicKey pubKey = null;
		try {
			fact = KeyFactory.getInstance("RSA");
			pubKey = fact.generatePublic(new RSAPublicKeySpec(modulus, exponent));
			return new DataCipher("RSA/ECB/PKCS1Padding",Cipher.ENCRYPT_MODE, pubKey);
		} catch (Exception ex) {
			Logger.LogServer(ex);
		}
		return null;
	}
	
	public static DataCipher getRSAEncrypter(byte[] publickey){
		try {
			return new DataCipher("RSA/ECB/PKCS1Padding",Cipher.ENCRYPT_MODE, getRsaPublicKey(publickey));
		} catch (Exception ex) {
			Logger.LogServer(ex);
		}
		return null;
	}
	
	public static DataCipher getRSADecrypter(BigInteger exponent, BigInteger modulus){
		KeyFactory fact;
		PrivateKey prvKey = null;
		try {
			fact = KeyFactory.getInstance("RSA");
			prvKey = fact.generatePrivate(new RSAPrivateKeySpec(modulus, exponent));
			return new DataCipher("RSA/ECB/PKCS1Padding",Cipher.DECRYPT_MODE, prvKey);
		} catch (Exception ex) {
			Logger.LogServer(ex);
		}
		return null;
	}
	
	public static DataCipher getRSADecrypter(byte[] privatekey){
		try {
			return new DataCipher("RSA/ECB/PKCS1Padding",Cipher.DECRYPT_MODE, getRsaPrivateKey(privatekey));
		} catch (Exception ex) {
			Logger.LogServer(ex);
		}
		return null;
	}

	public static byte[] removeBitPadding(byte[] data, int blocksize){
		for(int i = data.length - 1; i >= 0 ; i--){
			if(data[i] == (byte)0x80){
				return Arrays.copyOf(data, i);
			}
		}
		return null;
	}
	
	public static byte[] addBitPadding(byte[] data, int blocksize){
		byte[] ret = Arrays.copyOf(data, blocksize * (1 + data.length / blocksize));
		ret[data.length] = (byte)0x80;
		return ret;
	}

	public static KeyPair getGeneratedRSAKey(int bits) throws NoSuchAlgorithmException{
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(bits);
        return keyGen.genKeyPair();
	}
	
	public static byte[] getKey2Bytes(Key key) throws NoSuchAlgorithmException, IOException{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream objout = new ObjectOutputStream(out);
		objout.writeObject(key);
		return out.toByteArray();
	}
	
	public static byte[] getPublicKey2Bytes(RSAPublicKey key) throws NoSuchAlgorithmException, IOException{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream objout = new ObjectOutputStream(out);
		objout.writeObject(key);
		objout.flush();
		return out.toByteArray();
	}
	
	public static byte[] getRsaPublicKeyBytes(KeyPair pair){
		return pair.getPublic().getEncoded();
	}
	
	public static byte[] getRsaPrivateKeyBytes(KeyPair pair){
		return pair.getPrivate().getEncoded();
	}
	
	public static PublicKey getRsaPublicKey(byte[] key) throws NoSuchAlgorithmException, InvalidKeySpecException{
        KeyFactory generator = KeyFactory.getInstance("RSA");
		EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(key);
        return generator.generatePublic(publicKeySpec);
	}
	
	public static PrivateKey getRsaPrivateKey(byte[] key) throws NoSuchAlgorithmException, InvalidKeySpecException{
        KeyFactory generator = KeyFactory.getInstance("RSA");
        EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(key);
        return generator.generatePrivate(privateKeySpec);
	}
	
	 public static KeyPair createKeyPair(byte[] encodedPrivateKey, byte[] encodedPublicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(encodedPrivateKey);
        KeyFactory generator = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = generator.generatePrivate(privateKeySpec);
        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedPublicKey);
        PublicKey publicKey = generator.generatePublic(publicKeySpec);
        return new KeyPair(publicKey, privateKey);
	}
}
