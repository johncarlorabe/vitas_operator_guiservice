package com.tlc.common;


import java.text.ParseException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class LongUtil{
	private static final Log log = LogFactory.getLog(LongUtil.class);
	private static final String decimal = SystemInfo.getProperty(SystemInfo.PROP_ACCEPT_DECIMAL);
	private static final int scale = (decimal.equalsIgnoreCase("YES") ? 2: decimal.matches("^\\d+$") ? Integer.parseInt(decimal) : 0);
	private static final String format = SystemInfo.getProperty(SystemInfo.PROP_MONEY_FORMAT,"#,##0.00");
	private static final char decimalseparator = SystemInfo.getProperty(SystemInfo.PROP_DECIMAL_SEPARATOR,".").charAt(0);
	private static final char groupseparator = SystemInfo.getProperty(SystemInfo.PROP_GROUP_SEPARATOR,",").charAt(0);
	private static final LongConverter converter = new LongConverter(format, scale, decimalseparator, groupseparator);
	
	public static long divide(long dividend , long divisor){
		return converter.divide(dividend, divisor);
	}
	
	public static boolean even(long number){
		return (number % 2) == 0;
	}
	
	public static boolean odd(long number){
		return (number % 2) == 1;
	}
	public static long toLong(String value) throws ParseException{
			try {
				return converter.toLong(value);
			} catch (ParseException e) {
				log.error("value="+value, e);
				throw e;
			}
	}
	
	public static String toString(long value){
		return converter.toString(value);
	}
	
	public static long abs(long value){
		return ((value < 0L) ? -value : value);
	}
}
