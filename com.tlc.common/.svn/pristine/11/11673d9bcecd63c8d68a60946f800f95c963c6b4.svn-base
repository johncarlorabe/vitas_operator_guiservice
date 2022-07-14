package com.tlc.encryption;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.Security;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;

import org.bouncycastle.bcpg.ArmoredOutputStream;
import org.bouncycastle.bcpg.HashAlgorithmTags;
import org.bouncycastle.bcpg.PublicKeyAlgorithmTags;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.PGPCompressedData;
import org.bouncycastle.openpgp.PGPCompressedDataGenerator;
import org.bouncycastle.openpgp.PGPEncryptedData;
import org.bouncycastle.openpgp.PGPEncryptedDataGenerator;
import org.bouncycastle.openpgp.PGPEncryptedDataList;
import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPKeyPair;
import org.bouncycastle.openpgp.PGPLiteralData;
import org.bouncycastle.openpgp.PGPLiteralDataGenerator;
import org.bouncycastle.openpgp.PGPObjectFactory;
import org.bouncycastle.openpgp.PGPOnePassSignature;
import org.bouncycastle.openpgp.PGPOnePassSignatureList;
import org.bouncycastle.openpgp.PGPPrivateKey;
import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openpgp.PGPPublicKeyEncryptedData;
import org.bouncycastle.openpgp.PGPPublicKeyRing;
import org.bouncycastle.openpgp.PGPPublicKeyRingCollection;
import org.bouncycastle.openpgp.PGPSecretKey;
import org.bouncycastle.openpgp.PGPSecretKeyRing;
import org.bouncycastle.openpgp.PGPSecretKeyRingCollection;
import org.bouncycastle.openpgp.PGPSignature;
import org.bouncycastle.openpgp.PGPSignatureGenerator;
import org.bouncycastle.openpgp.PGPSignatureList;
import org.bouncycastle.openpgp.PGPSignatureSubpacketGenerator;
import org.bouncycastle.openpgp.PGPUtil;
import org.bouncycastle.openpgp.operator.PGPDigestCalculator;
import org.bouncycastle.openpgp.operator.bc.BcPublicKeyDataDecryptorFactory;
import org.bouncycastle.openpgp.operator.bc.BcPublicKeyKeyEncryptionMethodGenerator;
import org.bouncycastle.openpgp.operator.jcajce.JcaPGPContentSignerBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcaPGPContentVerifierBuilderProvider;
import org.bouncycastle.openpgp.operator.jcajce.JcaPGPDigestCalculatorProviderBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcaPGPKeyPair;
import org.bouncycastle.openpgp.operator.jcajce.JcePBESecretKeyDecryptorBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcePBESecretKeyEncryptorBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcePGPDataEncryptorBuilder;
import org.bouncycastle.util.io.Streams;

import com.tlc.common.Logger;
import com.tlc.common.StringUtil;

public class PgpUtil {
	private static final String ANSII = "ISO-8859-1";
	private static final Hashtable<String, String> header = new Hashtable<String, String>();
	static{
		Security.addProvider(new BouncyCastleProvider());
		header.put("Version", "TLCPG 1.01");
	}

	public static PGPPublicKey getPublicKey(String strkey){
		try{
			return getPublicKey(strkey.getBytes(ANSII)); //ANSI Encoding
		} catch (UnsupportedEncodingException e) {
			Logger.LogServer(e);
		} 
		return null;
	}
	public static PGPPublicKey getPublicKey(byte[] strkey){
		InputStream input = null;
		try{
			input = new ByteArrayInputStream(strkey);
			return getPublicKey(input);
		}finally{
			if(input != null)try {input.close();}catch (IOException e) {Logger.LogServer(e);}
		}
	}
	public static PGPPublicKey getPublicKey(File strkey){
		InputStream input = null;
		try{
			input = new BufferedInputStream(new FileInputStream(strkey));
			return getPublicKey(input);
		} catch (FileNotFoundException e) {
			Logger.LogServer(e);
		}finally{
			if(input != null)try {input.close();}catch (IOException e){Logger.LogServer(e);}
		}
		return null;
	}
	public static PGPPublicKey getPublicKey(InputStream input){
        PGPPublicKeyRingCollection pgpPub = null;
		try {
			pgpPub = new PGPPublicKeyRingCollection(PGPUtil.getDecoderStream(input));
	        @SuppressWarnings("rawtypes")
			Iterator keyRingIter = pgpPub.getKeyRings();
	        while (keyRingIter.hasNext())
	        {
	            PGPPublicKeyRing keyRing = (PGPPublicKeyRing)keyRingIter.next();
	            @SuppressWarnings("rawtypes")
				Iterator keyIter = keyRing.getPublicKeys();
	            while (keyIter.hasNext())
	            {
	                PGPPublicKey key = (PGPPublicKey)keyIter.next();
	                if (key.isEncryptionKey())
	                	return key;
	            }
	        }
		} catch (Exception e) {
			Logger.LogServer(e);
		}
		return null;
    }
	
	public static PGPSecretKey getSecretKey(String strkey){
		try {
			return getSecretKey(strkey.getBytes(ANSII)); //ANSI Encoding
		} catch (UnsupportedEncodingException e) {
			Logger.LogServer(e);
		} 
		return null;
	}
	public static PGPSecretKey getSecretKey(byte[] strkey){
		InputStream input = null;
		try{
			input = new ByteArrayInputStream(strkey);
			return getSecretKey(input);
		}finally{
			if(input != null)try {input.close();}catch (IOException e) {Logger.LogServer(e);}
		}
	}
	public static PGPSecretKey getSecretKey(File strkey){
		InputStream input = null;
		try{
			input = new BufferedInputStream(new FileInputStream(strkey));
			return getSecretKey(input);
		} catch (FileNotFoundException e) {
			Logger.LogServer(e);
		}finally{
			if(input != null)try {input.close();}catch (IOException e){Logger.LogServer(e);}
		}
		return null;
	}
	
	public static PGPSecretKey getSecretKey(InputStream input) 
    {
		try{
			PGPSecretKeyRingCollection pgpPub = new PGPSecretKeyRingCollection(PGPUtil.getDecoderStream(input));
	        @SuppressWarnings("rawtypes")
			Iterator keyRingIter = pgpPub.getKeyRings();
	        while (keyRingIter.hasNext())
	        {
	        	PGPSecretKeyRing keyRing = (PGPSecretKeyRing)keyRingIter.next();
	            @SuppressWarnings("rawtypes")
				Iterator keyIter = keyRing.getSecretKeys();
	            while (keyIter.hasNext())
	            {
	            	PGPSecretKey key = (PGPSecretKey)keyIter.next();
	                if (key.isSigningKey())
	                {
	                    return key;
	                }
	            }
	        }
		}catch(Exception e){
			Logger.LogServer(e);
		}
		return null;
    }

	public static PGPSecretKey getSecretKey(String strkey, long id){
		try {
			return getSecretKey(strkey.getBytes(ANSII), id); //ANSI Encoding
		} catch (UnsupportedEncodingException e) {
			Logger.LogServer(e);
		} 
		return null;
	}
	public static PGPSecretKey getSecretKey(byte[] strkey, long id){
		InputStream input = null;
		try{
			input = new ByteArrayInputStream(strkey);
			return getSecretKey(input, id);
		}finally{
			if(input != null)try {input.close();}catch (IOException e) {Logger.LogServer(e);}
		}
	}
	public static PGPSecretKey getSecretKey(File strkey, long id){
		InputStream input = null;
		try{
			input = new BufferedInputStream(new FileInputStream(strkey));
			return getSecretKey(input, id);
		} catch (FileNotFoundException e) {
			Logger.LogServer(e);
		}finally{
			if(input != null)try {input.close();}catch (IOException e){Logger.LogServer(e);}
		}
		return null;
	}
	public static PGPSecretKey getSecretKey(InputStream input, long id){
		try {
			PGPSecretKeyRingCollection pgpPub = new PGPSecretKeyRingCollection(PGPUtil.getDecoderStream(input));
			return pgpPub.getSecretKey(id);
		} catch (IOException e) {
			Logger.LogServer(e);
		} catch (PGPException e) {
			Logger.LogServer(e);
		}
		return null;
	}
	
	public static PGPPrivateKey getPrivateKey(PGPSecretKey secretkey, String password) throws PGPException{
		return secretkey.extractPrivateKey(new JcePBESecretKeyDecryptorBuilder().setProvider("BC").build(password.toCharArray()));
	}

	public static byte[] compress(PGPSecretKey signkey, String password, String ... files){
		ByteArrayOutputStream      output          = null;
		PGPCompressedDataGenerator compgenerator   = null;
		OutputStream               compout         = null;
		try{
			output          = new ByteArrayOutputStream();
			compgenerator   = new PGPCompressedDataGenerator(PGPCompressedDataGenerator.BZIP2);
			compout = compgenerator.open(output);
			if(signkey == null)
				addFiles(compout, files);
			else
				addFilesSign(signkey, password, compout, files);
		}catch(Exception e){
			Logger.LogServer(e);
		}finally{
			if(compout       != null) try {compout.close();      } catch (IOException e) {Logger.LogServer(e);}
			if(compgenerator != null) try {compgenerator.close();} catch (IOException e) {Logger.LogServer(e);}
			if(output != null){
				try {
					output.close();
					return output.toByteArray();
				} catch (Exception e) {
					Logger.LogServer(e);
				}
			}
		}
		return null;
	}
	public static void addFiles(OutputStream output, String ... files) throws IOException{
		if(files == null || files.length == 0) return;
		PGPLiteralDataGenerator    datagenerator   = null;
		OutputStream               liteout         = null;
		try{
			datagenerator   = new PGPLiteralDataGenerator();
			for(int i = 0; i+1 < files.length; i += 2){
				byte[] bytes = files[i+1].getBytes(ANSII);
				liteout = datagenerator.open(output, PGPLiteralData.BINARY, files[i], bytes.length, new Date());
			    liteout.write(bytes);
				liteout.close();
				liteout = null;
			}
		}finally{
			if(liteout       != null) try {liteout.close();      } catch (IOException e) {Logger.LogServer(e);}
			if(datagenerator != null) try {datagenerator.close();} catch (IOException e) {Logger.LogServer(e);}
		}
	}
	public static void addFilesSign(PGPSecretKey signkey, String password, OutputStream output, String ... files){
		if(files == null || files.length == 0) return;
		PGPSignatureGenerator          signgenerator      = null;
		PGPSignatureSubpacketGenerator subpacketGenerator = null;
		PGPLiteralDataGenerator        datagenerator      = null;
		OutputStream                   liteout            = null;
		try{
			signgenerator   = new PGPSignatureGenerator(new JcaPGPContentSignerBuilder(signkey.getPublicKey().getAlgorithm(), HashAlgorithmTags.SHA1).setProvider("BC"));
			signgenerator.init(PGPSignature.CANONICAL_TEXT_DOCUMENT, getPrivateKey(signkey, password));
			subpacketGenerator = new PGPSignatureSubpacketGenerator();
			@SuppressWarnings("unchecked")
			Iterator<String> userids = (Iterator<String>)signkey.getPublicKey().getUserIDs();
			while(userids.hasNext()){
				subpacketGenerator.setSignerUserID(false, userids.next());
				signgenerator.setHashedSubpackets(subpacketGenerator.generate());
			}
			signgenerator.generateOnePassVersion(false).encode(output);
			datagenerator   = new PGPLiteralDataGenerator();
			for(int i = 0; i+1 < files.length; i += 2){
				byte[] bytes = files[i+1].getBytes(ANSII);
				liteout = datagenerator.open(output, PGPLiteralData.BINARY, files[i], bytes.length, new Date());
			    liteout.write(bytes);
			    signgenerator.update(bytes);
				liteout.close();
				liteout = null;
			}
			signgenerator.generate().encode(output);
		} catch (Exception e) {
			Logger.LogServer(e);
		}finally{
			if(liteout       != null) try {liteout.close();      } catch (IOException e) {Logger.LogServer(e);}
			if(datagenerator != null) try {datagenerator.close();} catch (IOException e) {Logger.LogServer(e);}
		}
	}
	
	public static String encrypt(File publickey, File signkey, String password, String ... files){
		return encrypt(PgpUtil.getPublicKey(publickey), PgpUtil.getSecretKey(signkey), password, files);
	}
	
	public static String encrypt(PGPPublicKey publickey, PGPSecretKey signkey, String password, String ... files){
		ByteArrayOutputStream     output          = null;
		OutputStream              armor           = null;
		PGPEncryptedDataGenerator ciphergenerator = null;
		OutputStream              encout          = null;
		try{
			byte[] bytes = compress(signkey, password, files);
			output = new ByteArrayOutputStream();
			armor  = new ArmoredOutputStream(output, header);
			ciphergenerator = new PGPEncryptedDataGenerator(
	                          	new JcePGPDataEncryptorBuilder(PGPEncryptedData.CAST5)
	                          		.setWithIntegrityPacket(true)
	                          		.setSecureRandom(new SecureRandom())
	                          		.setProvider("BC"));
			ciphergenerator.addMethod(new BcPublicKeyKeyEncryptionMethodGenerator(publickey));
			encout = ciphergenerator.open(armor, bytes.length);
			encout.write(bytes);
		} catch (Exception e) {
			Logger.LogServer(e);
		}finally{
			if(encout          != null) try {encout.close();         } catch (IOException e) {Logger.LogServer(e);}
			if(ciphergenerator != null) try {ciphergenerator.close();} catch (IOException e) {Logger.LogServer(e);}
			if(armor           != null) try {armor.close();          } catch (IOException e) {Logger.LogServer(e);}
			if(output != null){
				try {
					output.close();
					return new String(output.toByteArray(),ANSII);
				} catch (Exception e) {
					Logger.LogServer(e);
				}
			}
		}
		return null;
	}
	
	public static String[] decrypt(PGPPublicKey verifykey, PGPSecretKey secretkey, String password, String encrypted){
		try {
			return decrypt(verifykey, secretkey, password, encrypted.getBytes(ANSII));
		} catch (UnsupportedEncodingException e) {
			Logger.LogServer(e);
		}
		return null;
	}
	public static String[] decrypt(PGPPublicKey verifykey, PGPSecretKey secretkey, String password, byte[] encrypted){
		InputStream input = null;
		try{
			input = new ByteArrayInputStream(encrypted);
			return decrypt(verifykey, secretkey, password, input);
		}finally{
			if(input != null)
				try {
					input.close();
				} catch (IOException e) {
					Logger.LogServer(e);
				}
		}
	}
	public static String[] decrypt(PGPPublicKey verifykey, PGPSecretKey secretkey, String password, File encrypted){
		InputStream input = null;
		try{
			input = new BufferedInputStream(new FileInputStream(encrypted));
			return decrypt(verifykey, secretkey, password, input);
		} catch (FileNotFoundException e) {
			Logger.LogServer(e);
		}finally{
			if(input != null)try {input.close();}catch (IOException e){Logger.LogServer(e);}
		}
		return null;
	}
	public static String[] decrypt(PGPPublicKey verifykey, PGPSecretKey secretkey, String password,  InputStream encrypted){
		ArrayList<String>    output  = null;
		InputStream          input   = null;
		try {
			output = new ArrayList<String>();
			input = PGPUtil.getDecoderStream(encrypted);
			PGPObjectFactory pgpF = new PGPObjectFactory(input);
	        Object a = null;
	        while((a = pgpF.nextObject()) != null){
	        	if(!(a instanceof PGPEncryptedDataList))
	        		continue;
	        	PGPEncryptedDataList enclist = (PGPEncryptedDataList)a;
        		@SuppressWarnings("unchecked")
				Iterator<Object> enc = (Iterator<Object>)enclist.getEncryptedDataObjects();
        		while(enc.hasNext()){
        			Object b = enc.next();
        			if(!(b instanceof PGPPublicKeyEncryptedData)){
        				Logger.LogServer("Invalid Encrypted Data : " + b.toString());
        				continue;
        			}
        			PGPPublicKeyEncryptedData pubenc = (PGPPublicKeyEncryptedData)b;
        			PGPPrivateKey privKey = getPrivateKey(secretkey, password);
        			InputStream cleardata = pubenc.getDataStream(new BcPublicKeyDataDecryptorFactory(privKey));
        			try{
        				processClearObject(verifykey, secretkey, password, output, cleardata);
        			} catch (IOException e) {
        				Logger.LogServer(e);
        				output.clear();
					} catch (SignatureException e) {
        				Logger.LogServer(e);
        				output.clear();
					}finally{
        				if(cleardata != null)
        					try {cleardata.close();} catch (IOException e) {Logger.LogServer(e);}
        			}
        		}
	        }
		} catch (PGPException e) {
			Logger.LogServer(e);
		} catch (UnsupportedEncodingException e) {
			Logger.LogServer(e);
		} catch (IOException e) {
			Logger.LogServer(e);
		}finally{
			if(input != null)
				try {input.close();} catch (IOException e) {Logger.LogServer(e);}
			if(output != null)
				return output.toArray(new String[output.size()]);
		}
		return null;
	}
	
	private static void processClearObject(PGPPublicKey verifykey, PGPSecretKey secretkey, String password, ArrayList<String> output, InputStream cleardata) throws IOException, PGPException, SignatureException{
		PGPObjectFactory pgpFact = new PGPObjectFactory(cleardata);
		
		Object obj  = pgpFact.nextObject();
		
		if(obj instanceof PGPCompressedData){
			processCompressObject(verifykey, secretkey, password, output,obj);
			return;
		}
		
		if(!(obj instanceof PGPOnePassSignatureList)){
			throw new SignatureException("Message Not Signed");
		}
		
		PGPOnePassSignature sign = ((PGPOnePassSignatureList)obj).get(0);
		sign.init(new JcaPGPContentVerifierBuilderProvider().setProvider("BC"), verifykey);
		
		while((obj = pgpFact.nextObject()) != null && obj instanceof PGPLiteralData){
			sign.update(processLiteralObject(output,obj));
		}
		
		if(!(obj instanceof PGPSignatureList)){
			throw new SignatureException("Message Not Signed Properly");
		}
		
		if(!sign.verify(((PGPSignatureList)obj).get(0))){
			throw new SignatureException("Message Not Signed Properly");
		}
	}
	private static byte[] processLiteralObject(ArrayList<String> output, Object obj){
		PGPLiteralData ld = (PGPLiteralData)obj;
        output.add(ld.getFileName());
        try {
        	byte[] ret = Streams.readAll(ld.getInputStream());
			output.add(new String(ret, ANSII));
			return ret;
		} catch (Exception e) {
			Logger.LogServer(e);
		}
        return new byte[]{};
	}
	private static void processCompressObject(PGPPublicKey verifykey, PGPSecretKey secretkey, String password, ArrayList<String> output, Object obj) throws SignatureException{
		PGPCompressedData cData = (PGPCompressedData)obj;
		InputStream cleardata = null;
		try {
			cleardata = cData.getDataStream();
			processClearObject(verifykey, secretkey, password, output,cleardata);
		} catch (PGPException e) {
			Logger.LogServer(e);
		} catch (IOException e) {
			Logger.LogServer(e);
		} catch (SignatureException e) {
			throw e;
		}finally{
			if(cleardata != null)
				try {cleardata.close();} catch (IOException e) {Logger.LogServer(e);}
		}
	}
	public static PGPSecretKey generateSecretKey(String identity, String password){
		try{
			PGPDigestCalculator sha1Calc = new JcaPGPDigestCalculatorProviderBuilder().build().get(HashAlgorithmTags.SHA1);
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA", "BC");
			keyPairGenerator.initialize(4096);
		    PGPKeyPair keyPair = new JcaPGPKeyPair(PublicKeyAlgorithmTags.RSA_GENERAL, keyPairGenerator.generateKeyPair(), new Date());
		    PGPSecretKey secretKey = new PGPSecretKey(PGPSignature.DEFAULT_CERTIFICATION
		    		                                , keyPair
		    		                                , identity
		    		                                , sha1Calc
		    		                                , null
		    		                                , null
		    		                                , new JcaPGPContentSignerBuilder(keyPair.getPublicKey().getAlgorithm(), HashAlgorithmTags.SHA1)
		    		                                , new JcePBESecretKeyEncryptorBuilder(PGPEncryptedData.CAST5, sha1Calc).setProvider("BC").build(password.toCharArray()));
		    return secretKey;
		}catch(Exception e){
			Logger.LogServer(e);
		}
		return null;
	}
	public static String exportPublicKey(PGPSecretKey secretkey){
		ByteArrayOutputStream output = null;
		OutputStream armor           = null;
		try{
			output = new ByteArrayOutputStream();
			armor  = new ArmoredOutputStream(output, header);
			secretkey.getPublicKey().encode(armor);
			armor.close();
			armor = null;
			return new String(output.toByteArray(),ANSII);
		}catch(Exception e){
			Logger.LogServer(e);
		}finally{
			if(armor != null) 
				try {armor.close();} catch (IOException e){Logger.LogServer(e);}
			if(output != null){
				try {
					output.close();
					return new String(output.toByteArray(),ANSII);
				} catch (Exception e) {
					Logger.LogServer(e);
				}
			}
		}
		return null;
	}
	public static String exportSecretKey(PGPSecretKey secretkey){
		ByteArrayOutputStream output = null;
		OutputStream armor           = null;
		try{
			output = new ByteArrayOutputStream();
			armor  = new ArmoredOutputStream(output, header);
			secretkey.encode(armor);
		}catch(Exception e){
			Logger.LogServer(e);
		}finally{
			if(armor != null) 
				try {armor.close();} catch (IOException e){Logger.LogServer(e);}
			if(output != null){
				try {
					output.close();
					return new String(output.toByteArray(),ANSII);
				} catch (Exception e) {
					Logger.LogServer(e);
				}
			}
		}		
		return null;
	}

	private PGPSecretKey secretkey = null;
	private PGPPublicKey publickey = null;
	private String passphrase   = "";
	
	public PgpUtil(String secretkey, String publickey, String passphrase){
		if(!StringUtil.isNullOrEmpty(secretkey))
			this.secretkey = getSecretKey(secretkey);
		if(!StringUtil.isNullOrEmpty(publickey))
			this.publickey = getPublicKey(publickey);
		this.passphrase = passphrase;
	}
	
	/*
	 * return = {filename01,data02 ... filenameN, dataN}
	 */	
	public String[] decrypt(String encrypted){
		return decrypt(publickey,secretkey,passphrase,encrypted);
	}
	/*
	 * return = {filename01,data02 ... filenameN, dataN}
	 */
	public String[] decrypt(byte[] encrypted){
		return decrypt(publickey,secretkey,passphrase,encrypted);
	}
	
	/*
	 * files = {filename01,data02 ... filenameN, dataN}
	 */
	public String encrypt(String ... files){
		return encrypt(publickey,secretkey,passphrase,files);
	}
}
