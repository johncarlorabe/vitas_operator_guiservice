package com.tlc.keymgt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import com.jcraft.jsch.Logger;
import com.tlc.common.StringUtil;
import com.tlc.common.SystemInfo;
import com.tlc.common.Util;
import com.tlc.encryption.EncryptFunction;

public class KeyManager implements Runnable{
	private static volatile byte[] mek; 
	private static Object syncObject= new Object();
	private static Class<?> superSecret=null;
	private static String hashCode=null;
	private static byte[] primaryMaster=null;
	private static boolean completed=false;
	private static byte[] kek;
	
	private  IKeyStatusDelegate context=null;
	private String args[];
	private static byte[] salt = {(byte) 0x03,(byte) 0x03,(byte) 0x03,(byte) 0x03,(byte) 0x03,(byte) 0x03,(byte) 0x03,(byte) 0x03};
	
	static{
		superSecret = KeyLoader.loadClass("com.tlc.security.keymgt.keys.SuperSecret");
		Class<?> hashObject =  KeyLoader.loadClass("com.tlc.security.keymgt.MasterHash");
		hashCode = (String) Util.invoke("get", hashObject);		
		kek= (byte[]) Util.invoke("get", superSecret);
		try {
			primaryMaster = StringUtil.base64Decode(new String(Files.readAllBytes(new File(Util.getWorkingDirectory()+"/keys/ctskd.init").toPath())));
			KeyLoader.dispose();
			
		} catch (IOException e) {
			System.exit(1);
		}finally{
			File m = new File(Util.getWorkingDirectory() + "/keys/ctskm.jar");
			m.delete();
			File s = new File(Util.getWorkingDirectory() + "/keys/ctsks.jar");
			s.delete();
			//File d = new File(Util.getWorkingDirectory() + "/keys/ctskd.init");
			//d.delete();
			
		}
	}
	public KeyManager(IKeyStatusDelegate delegate,String args[]){
		this.args = args;
		this.context = delegate;
	}
	
	
	@Override
	public void run() {
		int timeout = 0;
		complete:
		while(!completed && timeout<120){
			File[] keys = (new File(Util.getWorkingDirectory() + "/keys/")).listFiles(new FileFilter(){
				@Override
				public boolean accept(File paramFile) {
					return paramFile.isFile() && paramFile.getName().matches("(?i).*\\.key$");
				}});
			
			if(keys!=null){
				for(File f:keys){
					try {
						BufferedReader br = new BufferedReader(new FileReader(f.getAbsolutePath()));
						if (process(br.readLine())){
							br.close();
							break complete;
						}
						br.close();
					} catch (IOException e) {
					}finally{
						f.delete();	
					}
				}
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			timeout++;
		}
		if(timeout>=120){
			context.onInvalidKeySupplied();
		}
		if(completed){
			String[] tmp = decrypt();
			if(tmp==null){		
				context.onInvalidKeySupplied();
			}else{
				//TODO: SWITCH FROM OLD TO NEW VERSION
				SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmm");
				//SimpleDateFormat df = new SimpleDateFormat("yyMMddHH");
				try {
					Date d  = df.parse(tmp[1]);
					if(d.after(new Date())){
						context.onValidKeySupplied(this.args);
					}else{
						context.onExpiredKeySupplied();
					}
					
				} catch (ParseException e) {
					context.onInvalidKeySupplied();
				}
				
			}
			
		}
	}
	
	private boolean process(String component){
		component = component.trim();
		synchronized (syncObject) {
			if(mek==null){
				mek = StringUtil.hexDecode(component);
			}else{
				mek = xor(mek,StringUtil.hexDecode(component));
			}
			try {
				
				String hash = com.tlc.encryption.HashFunction.hashMD5(StringUtil.hexEncode(mek));
				
				if(hash.equals(hashCode)){
					completed = true;
				}
				
			} catch (UnsupportedEncodingException e) {
			
			}
		}
		return completed;
	}
	public static String getMek(){
		return decrypt()[0];
	}
	
	/*public static byte[] getConfigKey() {
	    try {
	      SecretKeyFactory kf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
	      SecretKey key = kf.generateSecret(new PBEKeySpec(getMek().toCharArray(), salt, 65536, 128));
	      String p = new String(EncryptFunction.decryptAES(primaryMaster, key.getEncoded()));
	      SecretKey returnKey = kf.generateSecret(new PBEKeySpec(p.toCharArray(), salt, 65536, 128));
	      return returnKey.getEncoded(); } catch (Exception e) {
	    }
	    return null;
	  }

	  public static String getDek()
	  {
	    return (String)SystemInfo.getDb().QueryScalar("SELECT DECRYPT(DEK,?) FROM TBLSYSTEMINFO", null, new Object[] { getMek() });
	  }
	
	*/
	public static byte[] getConfigKey(){
		try{
			String p = new String( EncryptFunction.decryptAES(primaryMaster,StringUtil.base64Decode(getMek())));
			return StringUtil.base64Decode(p);
		}catch(Exception e){
			return null;
		}
		
	}
	
	public static String getDek(){
		String encdek = SystemInfo.getDb().QueryScalar("SELECT DEK FROM TBLSYSTEMINFO",null);
		if(encdek!=null){
			
			try {
				byte[] enc = com.tlc.encryption.EncryptFunction.decryptAES(StringUtil.base64Decode(encdek), StringUtil.base64Decode(getMek()));
				String plain = new String(enc);
				encdek = null;
				return plain;
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				
			} catch (NoSuchPaddingException e) {
				// TODO Auto-generated catch block
				
			} catch (InvalidAlgorithmParameterException e) {
				// TODO Auto-generated catch block
				
			} catch (IllegalBlockSizeException e) {
				// TODO Auto-generated catch block
				
			} catch (BadPaddingException e) {
				// TODO Auto-generated catch block
				
			}
			
		}
		return null;
		
	}
	
	
	private static String[] decrypt(){
		try{
			byte[] enc = com.tlc.encryption.EncryptFunction.decryptAES(mek, kek);
			String plain = new String(enc);
			enc = null;
			return plain.split(":");
		}catch(Exception e){
			return null;
		}
	}
	
	public static byte[] xor(byte[] b1,byte[] b2){
		byte[] ret = new byte[b1.length];
		for(int x=0;x<ret.length;x++){
			ret[x]= (byte) (b1[x] ^ b2[x]);
		}
		return ret;
	}

}
