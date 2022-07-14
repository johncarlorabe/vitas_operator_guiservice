package com.tlc.gui.modules.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Properties;

import com.tlc.common.StringUtil;
import com.tlc.common.Util;




public class ObjectState extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public final static String STAT_SUCCESS_GENERIC="00";
	public final static String ERR_SYSTEMBUSY_GENERIC="99";
	public final static String STAT_OBJECTDOESNOTEXIST="TLC-1001";
	public final static String STAT_MCOMACCOUNTDOESNOTEXIST="TLC-1002";
	public final static String STAT_UIACCOUNTDOESNOTEXIST="TLC-1003";
	public final static String STAT_SESSIONDOESNOTEXIST = "TLC-1004";
	
	private String code;
	private String message;
	static Properties props = new Properties();
	private static Hashtable<String, String> messages = new Hashtable<String,String>();
	static{
		
		try {
			props.load(new FileInputStream(new File(Util.getWorkingDirectory()+"/messages.properties")));
			//props.put(STAT_SUCCESS_GENERIC, "success");
			//props.put(ERR_SYSTEMBUSY_GENERIC, "system is busy");
		} catch (Exception e) {
			props=null;
		}		
	}
	
	
	public static ObjectState busy(){
		return new ObjectState(ERR_SYSTEMBUSY_GENERIC);
	}
	
	public static ObjectState success(){
		return new ObjectState(STAT_SUCCESS_GENERIC);
	}
	
	public ObjectState(String code,String message){
		this.code = code;
		if(props.containsKey(code)){
			this.message =StringUtil.HTMLDecode(props.getProperty(code));
		}else{
			this.message = message;
		}
	}
	
	public ObjectState(String code){
		this.code = code;
		if(props.containsKey(code)){
			this.message =StringUtil.HTMLDecode(props.getProperty(code));
		}
	}
	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
	
}
