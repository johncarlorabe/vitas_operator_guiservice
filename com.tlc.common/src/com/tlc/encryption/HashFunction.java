package com.tlc.encryption;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tlc.common.StringUtil;

public class HashFunction {
	private static Log log = LogFactory.getLog(HashFunction.class);
	private static ThreadLocal<MessageDigest> md2 = new ThreadLocal<MessageDigest>(){
		@Override
		protected MessageDigest initialValue() {
			try {
				return MessageDigest.getInstance("MD2");
			} catch (NoSuchAlgorithmException e) {
				log.error(e);
			}
			return null;
		}
	};

	private static ThreadLocal<MessageDigest> md5 = new ThreadLocal<MessageDigest>(){
		@Override
		protected MessageDigest initialValue() {
			try {
				return MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {
				log.error(e);
			}
			return null;
		}
	};

	private static ThreadLocal<MessageDigest> sha1 = new ThreadLocal<MessageDigest>(){
		@Override
		protected MessageDigest initialValue() {
			try {
				return MessageDigest.getInstance("SHA-1");
			} catch (NoSuchAlgorithmException e) {
				log.error(e);
			}
			return null;
		}
	};

	private static ThreadLocal<MessageDigest> sha256 = new ThreadLocal<MessageDigest>(){
		@Override
		protected MessageDigest initialValue() {
			try {
				return MessageDigest.getInstance("SHA-256");
			} catch (NoSuchAlgorithmException e) {
				log.error(e);
			}
			return null;
		}
	};

	private static ThreadLocal<MessageDigest> sha384 = new ThreadLocal<MessageDigest>(){
		@Override
		protected MessageDigest initialValue() {
			try {
				return MessageDigest.getInstance("SHA-384");
			} catch (NoSuchAlgorithmException e) {
				log.error(e);
			}
			return null;
		}
	};
	
	private static ThreadLocal<MessageDigest> sha512 = new ThreadLocal<MessageDigest>(){
		@Override
		protected MessageDigest initialValue() {
			try {
				return MessageDigest.getInstance("SHA-512");
			} catch (NoSuchAlgorithmException e) {
				log.error(e);
			}
			return null;
		}
	};
	
	public static String hashMD2(String value) throws UnsupportedEncodingException{
		return StringUtil.base64Encode(md2.get().digest(value.getBytes("UTF-8")));
	}
	public static String hashMD5(String value) throws UnsupportedEncodingException{
		return StringUtil.base64Encode(md5.get().digest(value.getBytes("UTF-8")));
	}
	public static String hashSHA1(String value) throws UnsupportedEncodingException{
		return StringUtil.base64Encode(sha1.get().digest(value.getBytes("UTF-8")));
	}
	public static String hashSHA256(String value) throws UnsupportedEncodingException{
		return StringUtil.base64Encode(sha256.get().digest(value.getBytes("UTF-8")));
	}
	public static String hashSHA384(String value) throws UnsupportedEncodingException{
		return StringUtil.base64Encode(sha384.get().digest(value.getBytes("UTF-8")));
	}
	public static String hashSHA512(String value) throws UnsupportedEncodingException{
		return StringUtil.base64Encode(sha512.get().digest(value.getBytes("UTF-8")));
	}
}
