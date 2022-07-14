package com.tlc.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat{
	private ThreadLocal<java.text.DateFormat> localdateformat;
	
	public DateFormat(final String format){
		localdateformat = new ThreadLocal<java.text.DateFormat>(){
			@Override
			protected java.text.DateFormat initialValue() {
				return new SimpleDateFormat(format);
			}
		};
	}
	
	public String format(Date dt){
		return localdateformat.get().format(dt);
	}
	
	public Date parse(String dt) throws ParseException{
		return localdateformat.get().parse(dt);
	}
}
