package com.tlc.common;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEParameterSpec;

public class EncryptedProperties extends Properties {
	private static final long serialVersionUID = 1L;
	private Cipher encrypter, decrypter;
	private static byte[] salt = {(byte) 0x03,(byte) 0x03,(byte) 0x03,(byte) 0x03,(byte) 0x03,(byte) 0x03,(byte) 0x03,(byte) 0x03};
	private final static String password = "dGxjLmNvbS5waA==";
	private SecretKey key;
	private PBEParameterSpec param;
	private String path;
	public EncryptedProperties() throws Exception {
		this(null);
	}	
	public EncryptedProperties(String propertiesPath) throws Exception {
		if(StringUtil.isNullOrEmpty(propertiesPath)){
			path = Util.getWorkingDirectory() + "/config.properties";
		}else{
			path = propertiesPath;
		}
		File f = new File(path);
		if(!f.exists()){
			f.createNewFile();
		}
		path = f.getAbsolutePath();
		param = new javax.crypto.spec.PBEParameterSpec(salt, 20);
		SecretKeyFactory kf = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
		key = kf.generateSecret(new javax.crypto.spec.PBEKeySpec(password.toCharArray()));
		encrypter = Cipher.getInstance("PBEWithMD5AndDES/CBC/PKCS5Padding");
		decrypter = Cipher.getInstance("PBEWithMD5AndDES/CBC/PKCS5Padding");
		encrypter.init(Cipher.ENCRYPT_MODE, key, param);
		decrypter.init(Cipher.DECRYPT_MODE, key, param);
		load();
	}	
	private void load() throws IOException{
		RandomAccessFile str = null;
		byte[] bytes = null;
		try{
			str = new RandomAccessFile(path,"r");
			bytes = new byte[(int)str.length()];
			str.read(bytes);
		}
		catch(IOException e){
			Logger.LogServer(e);
			throw e;
		}finally{
			if(str!=null){
				try{
					str.close();
				}catch(Exception e){
					Logger.LogServer(e);
				}
			}	
		}
		ByteArrayInputStream memstream = new ByteArrayInputStream(bytes);
		try {
			this.load(memstream);
		} catch (IOException e) {
			Logger.LogServer(e);
			throw e;
		}
	}
	public String getProperty(String key) {
		String value = super.getProperty(key);
		String dec = "";
		try {
			if(!StringUtil.isNullOrEmpty(value)){
				dec = decrypt(value);
				return dec;
			}
			return value;
		} catch( Exception e ) {
			Logger.LogServer("Couldn't decrypt: " + key);
		}finally{
		}
		setProperty(key, value);
		save();
		return value;
	}
	
	@Override
	public synchronized Object put(Object key, Object value) {
		String val = (String)value;
		if(val == null) return super.put(key, value);
		try{
			decrypt(val);
			return super.put(key, value);
		}catch(Exception e){}
		try {
			Object ret = super.put(key, encrypt(val));
			save();
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.put(key, value);
	}
	private synchronized String decrypt(String str) throws Exception {
		try{
			byte[] dec = StringUtil.base64Decode(str);
			byte[] utf8 = decrypter.doFinal(dec);
			return new String(utf8, "UTF-8");
		}catch(Exception e){
			decrypter.init(Cipher.DECRYPT_MODE, key, param);
			throw e;
		}
	}
	private synchronized String encrypt(String str) throws Exception {
		try{
			byte[] utf8 = str.getBytes("UTF-8");
			byte[] enc = encrypter.doFinal(utf8);
			return StringUtil.base64Encode(enc);
		}catch(Exception e){
			encrypter.init(Cipher.ENCRYPT_MODE, key, param);
			throw e;
		}
	}	
	public void save(String key, String value){
		this.setProperty(key, value);
		save();
	}
	public synchronized void save(){
		FileOutputStream out = null;
		try{
			out = new FileOutputStream(path,false);
			this.store(out, "Encrypted File!" );
		}catch(Exception e){
			Logger.LogServer(e);
		}finally{
			if(out!=null){
				try{
					out.close();
				}catch(Exception e){
					Logger.LogServer(e);
				}
			}
		}
	}
}
