package com.tlc.common;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.mail.internet.AddressException;


import org.apache.commons.mail.ByteArrayDataSource;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;
import org.apache.commons.mail.HtmlEmail;

public class Util {

	/*
	 * Get the working directory
	 */
	public static String getWorkingDirectory(){
		return (new File(System.getProperty("java.class.path").split(":")[0])).getAbsoluteFile().getParentFile().getPath();
	}
	
	public static Class<?> getClass(Type type) throws Exception{
		//throw new Exception("Not Implemented yet");
		return null;
	}
	
	/*
	 * Get the Stack trace of a throwable
	 */
	public static String GetStackTrace(Throwable throwable){
		Writer writer = null;
		PrintWriter printWriter = null;
		try{
			writer = new StringWriter();
			printWriter = new PrintWriter(writer);
			throwable.printStackTrace(printWriter);
			return writer.toString();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(writer != null)
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if(printWriter != null) printWriter.close();
		}
		return "";
	}

	@SuppressWarnings("unchecked")
	public static <E> E cast(Object o){
		return (E) o;
	}

	@SuppressWarnings("unchecked")
	public static <E> E cast(Object obj, Class<E> klass){
		Class<E> wrapper = (Class<E>)WRAPPER_TYPES.get(klass);
		if(wrapper!= null) klass = wrapper;
		if(Number.class.isAssignableFrom(klass)){
			Constructor<E> m = null;
			try {
				m = klass.getConstructor(String.class);
				if(obj != null) {
					return (E) m.newInstance(obj.toString());
				}else{
					return (E) m.newInstance("0");
				}
			} catch (Exception e) {
				Logger.LogServer(e);
				try {
					return (E) m.newInstance("0");
				} catch (Exception e1) {
					Logger.LogServer(e1);
				}
			}
		}else if(String.class.isAssignableFrom(klass)){
			if(obj != null){
				return (E)obj.toString();
			}
			return (E)"";
		}
		return (E) obj;
	}

	private static final Map<Class<?>,Class<?>> PRIMITIVE_TYPES = initPrimitiveTypes();
	private static final Map<Class<?>,Class<?>> WRAPPER_TYPES = initWrapperTypes();
	private static Map<Class<?>,Class<?>> initWrapperTypes(){
		HashMap<Class<?>,Class<?>> ret = new HashMap<Class<?>,Class<?>>();
		ret.put(boolean.class  , Boolean.class);
		ret.put(char.class     , Character.class);
		ret.put(byte.class     , Byte.class);
		ret.put(short.class    , Short.class);
		ret.put(int.class      , Integer.class);
		ret.put(long.class     , Long.class);
		ret.put(float.class    , Float.class);
		ret.put(double.class   , Double.class);
		ret.put(void.class     , Void.class);
		return ret;
	}
	private static Map<Class<?>,Class<?>> initPrimitiveTypes(){
		HashMap<Class<?>,Class<?>> ret = new HashMap<Class<?>,Class<?>>();
		ret.put(Boolean.class  , boolean.class);
		ret.put(Character.class, char.class);
		ret.put(Byte.class     , byte.class);
		ret.put(Short.class    , short.class);
		ret.put(Integer.class  , int.class);
		ret.put(Long.class     , long.class);
		ret.put(Float.class    , float.class);
		ret.put(Double.class   , double.class);
		ret.put(Void.class     , void.class);
		return ret;
	}
		
	public static boolean isWrapperType(Class<?> clazz) {
	    return WRAPPER_TYPES.containsKey(clazz);   
	}
	
	/*
	 * Invoke method
	 */
	public static Object invoke(String methodName, Object obj, Object... param){
		try {
			Vector<Class<?>> t = new Vector<Class<?>>();
			for(Object o : param){
				Class<?> klass = o.getClass();
				Class<?> primitive = PRIMITIVE_TYPES.get(klass);
				if(primitive != null){
					t.add(primitive);
				}else{
					t.add(klass);
				}
			}
			Class<?>[] j = new Class<?>[t.size()] ;
			t.toArray(j); 
			Method method;
			if(obj instanceof Class<?>){
				try{
					method = ((Class<?>)obj).getDeclaredMethod(methodName,j);
				}catch(NoSuchMethodException e){
					method = ((Class<?>)obj).getDeclaredMethods()[0];
				}
			}else{
				method = obj.getClass().getDeclaredMethod(methodName,j);
			}
			if(method != null){
				method.setAccessible(true);
				return method.invoke(obj, param);
			}
		} catch (Exception e) {
			Logger.LogServer(e);
		}
		return null;
	}
	
	/*
	 * Set Protected Field
	 */
	public static void setField(String fieldName, Object obj, Object value){
		try {
			Field field = obj.getClass().getDeclaredField(fieldName);
			if(field != null){
				field.setAccessible(true);
				field.set(obj, value);
			}
		} catch (Exception e) {
			Logger.LogServer(e);
		}
	}
	
	/*
	 * Get Protected Field
	 */
	public static Object getField(String fieldName, Object obj){
		try {
			Field field = obj.getClass().getDeclaredField(fieldName);
			if(field != null){
				field.setAccessible(true);
				return field.get(obj);
			}
		} catch (Exception e) {
			Logger.LogServer(e);
		}
		return null;
	}
	
	/*
	 * Get Protected Field
	 */
	public static Object getField(String fieldName, Class<?> klass){
		Field m;
		try {
			m = klass.getField(fieldName);
			m.setAccessible(true);
			if(m != null) return m.get(klass);
		} catch (Exception e) {
			Logger.LogServer(e);
		}
		return null;
	}
	
	public static void sleep(long millis){
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
	
	public static boolean sendMail(String host
			                     , int port
			                     , String username
			                     , String password
			                     , String from
			                     , String to
			                     , String subject
			                     , String body){
		try{
			Email email = new SimpleEmail();
			email.setHostName(host);
			email.setSmtpPort(port);
			email.setAuthenticator(new DefaultAuthenticator(username, password));
			email.setTLS(true);
			email.setFrom(from);
			email.setSubject(subject);
			email.setMsg(body);
			email.addTo(to);
			email.send();	
			return true;
		}catch(Exception e){
			Logger.LogServer(e);
		}
		return false;
	}
	
	public static boolean sendMail(String host
            , int port
            , String username
            , String password
            , String from
            , String to
            , String subject
            , String body
            , String...attachments) throws Exception{
		try{

			MultiPartEmail email = new MultiPartEmail();
			email.setHostName(host);
			email.setSmtpPort(port);
			if(!StringUtil.isNullOrEmpty(username))
				email.setAuthenticator(new DefaultAuthenticator(username, password));
			email.setTLS(true);
			email.setFrom(from);
			email.setSubject(subject);
			email.setMsg(body);
			if(to.indexOf(";") > 0){
				String tos[] = to.split(";");
				for(String t : tos){
					if(!StringUtil.isNullOrEmpty(t.trim()))
						email.addTo(t);
				}
			}else{
				email.addTo(to);
			}
			for(int i = 0, len = attachments.length; i + 2 < len; i+=3){
				email.attach(new ByteArrayDataSource(attachments[i].getBytes("UTF-8"), "text/csv")
			                , attachments[i+1], attachments[i+2]);
			}
			email.send();	
			return true;
		}catch(EmailException e){
			if(e.getCause() != null && e.getCause() instanceof AddressException){
				throw e;
			}
			Logger.LogServer(e);
		}catch(Exception e){
			Logger.LogServer(e);
		}
		return false;
	}
	
	public static boolean sendMail(String host
            , int port
            , String username
            , String password
            , String from
            , String to
            , String subject
            , String body
            , byte[] attachmentdata
            , String attachmenttype
            , String attachmentfilename
            , String attachmentdescription) throws Exception{
		try{

			MultiPartEmail email = new MultiPartEmail();
			email.setHostName(host);
			email.setSmtpPort(port);
			if(!StringUtil.isNullOrEmpty(username))
				email.setAuthenticator(new DefaultAuthenticator(username, password));
			email.setTLS(true);
			email.setFrom(from);
			email.setSubject(subject);
			email.setMsg(body);
			if(to.indexOf(";") > 0){
				String tos[] = to.split(";");
				for(String t : tos){
					if(!StringUtil.isNullOrEmpty(t.trim()))
						email.addTo(t);
				}
			}else{
				email.addTo(to);
			}
			email.attach(new ByteArrayDataSource(attachmentdata, attachmenttype) , attachmentfilename, attachmentdescription);
			email.send();	
			return true;
		}catch(EmailException e){
			if(e.getCause() != null && e.getCause() instanceof AddressException){
				throw e;
			}
			Logger.LogServer(e);
		}catch(Exception e){
			Logger.LogServer(e);
		}
		return false;
	}
	public static boolean sendHtmlMail(String host
            , int port
            , String username
            , String password
            , String from
            , String to
            , String subject
            , String body){
		try{
			// Create the email message
			  HtmlEmail email = new HtmlEmail();
			  email.setHostName(host);
			  email.setSmtpPort(port);
			  email.setAuthenticator(new DefaultAuthenticator(username, password));
			  email.setTLS(true);
			  email.addTo(to);
			  email.setFrom(from);
			  email.setSubject(subject);
			    
			  // set the html message
			  email.setHtmlMsg(body);

			// set the alternative message
			  email.setTextMsg("Your email client does not support HTML messages");
			  
			  // send the email
			  email.send();
		return true;
		}catch(Exception e){
		Logger.LogServer(e);
		}
		return false;
	}
	
	private static int refid = 0;
	private static int relunch = 0;
	private static Object refidlock = new Object();
	private static Date date = null;
	private static Timer timer = new Timer();
	private static EncryptedProperties properties = null;
	private static boolean close = false;

	private static void init() {
		final SimpleDateFormat dtFormat = new SimpleDateFormat("yyMMdd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		date = calendar.getTime();
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				synchronized (date) {
					date.setTime(date.getTime() + 1000 * 60 * 60 * 24);
					properties.save("date", dtFormat.format(date));
					properties.save("number", "0");
					properties.save("refid", "0");
					relunch = 0;
					refid = 0;
				}
			}
		}, calendar.getTime(), 1000 * 60 * 60 * 24);
		try {
			properties = new EncryptedProperties(getWorkingDirectory() + "/dynamic" + Logger.id + ".properties");
		} catch (Exception e) {
			Logger.LogServer(e);
		}
		String strdate = properties.getProperty("date",dtFormat.format(date));
		Date dtFile = null;
		try {
			dtFile = dtFormat.parse(strdate);
		} catch (Exception e) {
			dtFile = date;
		}
		synchronized (date) {
			if (dtFile.before(date)) {
				properties.save("date", dtFormat.format(date));
				properties.save("number", "0");
				properties.save("refid", "0");
				relunch = 0;
				refid = 0;
			} else {
				properties.save("date", dtFormat.format(date));
				refid = Integer.parseInt(properties.getProperty("refid", "0"));
				relunch = Integer.parseInt(properties.getProperty("number", "0"));
				if(refid == 0)
					relunch++;
				properties.save("number", String.valueOf(relunch));
			}
		}
	}
	
	@Deprecated
	public static String getReferenceId(int id) throws Exception{
		SimpleDateFormat fDate = new SimpleDateFormat("yyMMdd");
		DecimalFormat fNumber = new DecimalFormat("000000");
		synchronized (refidlock) {
			if(close) throw new Exception("ReferenceId Generator are stopped.");
			if(date == null) init();
			synchronized (date) {
				return id + fDate.format(date) + relunch + fNumber.format(refid++);
			}
		}
	}
	
	public static String getPassword(int len){
		SecureRandom rgen = new SecureRandom();
		byte decision, numValue;
		char charValue;
		
		StringBuilder sb = new StringBuilder();
		
		while(sb.length() < len){
			decision = (byte)rgen.nextInt(2);
			numValue = (byte)rgen.nextInt(10);
			charValue = (char)(rgen.nextInt(25) + 65);
			sb.append( (decision%2 == 0) ? ( charValue + "" ) : ( numValue + "") );
		}
		
		return sb.toString();
	}
	
	public static void stopReferenceGenerator(){
		synchronized (refidlock){
			close = true;
		}
		if(timer == null) return;
		if(properties == null) return;
		timer.cancel();
		properties.save("refid", Integer.toString(refid++));
	}
	
    public static <T> T isNull(T data, T replace){
    	if(data == null) return replace;
    	return data;
    }
    
    public static ReferenceId getReferenceIdObject() throws Exception{
		synchronized (refidlock) {
			if(close) throw new Exception("ReferenceId Generator are stopped.");
			if(date == null) init();
			synchronized (date) {
				return new ReferenceId((Date)date.clone(),relunch,refid);
			}
		}
    }
    
    public static class ReferenceId{
    	private Date date;
    	private int relunch;
		private int refid;
    	public ReferenceId(Date date, int relunch, int refid){
    		this.date = date;
    		this.relunch = relunch;
    		this.refid = refid;
    	}
		public Date getDate() {
			return date;
		}
		public int getRelunch() {
			return relunch;
		}
    	public int getRefid() {
			return refid;
		}
    }

}
