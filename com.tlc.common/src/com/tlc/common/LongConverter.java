package com.tlc.common;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class LongConverter {
	private static final Log log = LogFactory.getLog(LongConverter.class);
	private ThreadLocal<DecimalFormat> format;
	private int scale;
	
	public LongConverter(final String format, int scale){
		this.format = new ThreadLocal<DecimalFormat>(){
			@Override
			protected DecimalFormat initialValue() {
				DecimalFormat decimal = new DecimalFormat(format);
				decimal.setParseBigDecimal(true);
				return decimal;
			}
		};
		this.scale = scale;
	}
	
	public LongConverter(final String format, final  int scale, final char decimalseparator, final char groupseparator){
		final DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(Locale.getDefault());
		symbols.setDecimalSeparator(decimalseparator);
		symbols.setGroupingSeparator(groupseparator);
		this.format = new ThreadLocal<DecimalFormat>(){
			@Override
			protected DecimalFormat initialValue() {
				DecimalFormat decimal = new DecimalFormat(format,symbols);
				decimal.setParseBigDecimal(true);
				return decimal;
			}
		};
		this.scale = scale;
	}
	
	public long toLong(String value) throws ParseException{
		try{
			if(StringUtil.isNullOrEmpty(value)) return 0l;
			BigDecimal num = (BigDecimal)this.format.get().parse(value);
			return num.setScale(scale, BigDecimal.ROUND_HALF_EVEN).unscaledValue().longValue();
		}catch(ParseException e){
			log.error("value=" + value,e);
			throw e;
		}
	}
	
	public String toString(long value){
		return format.get().format(BigDecimal.valueOf(value, scale));
	}
	
	public long divide(long dividend , long divisor){
		return BigDecimal.valueOf(dividend).divide(BigDecimal.valueOf(divisor), scale, BigDecimal.ROUND_HALF_EVEN).unscaledValue().longValue();
	}
	
	public long multiply(long multiplier , long multiplicand){
		return BigDecimal.valueOf(multiplier,scale).multiply(BigDecimal.valueOf(multiplicand,scale)).unscaledValue().longValue();
	}
	
}
