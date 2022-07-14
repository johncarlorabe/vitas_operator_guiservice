package com.tlc.common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
	
	public static int id = 0;
	
	static File logServerFile = new File(Util.getWorkingDirectory() + "/Log/Server");
	static File logMomtFile = new File(Util.getWorkingDirectory() + "/Log/MOMT");
	static File logMoFile = new File(Util.getWorkingDirectory() + "/Log/MO");
	static File logMtFile = new File(Util.getWorkingDirectory() + "/Log/MT");
	static File logTransFile = new File(Util.getWorkingDirectory() + "/Log/Trans");
	static File logPpsFile = new File(Util.getWorkingDirectory() + "/Log/PPS");
	static File logDbFile = new File(Util.getWorkingDirectory() + "/Log/DB");
	static File logSmppFile = new File(Util.getWorkingDirectory() + "/Log/SMPP");
	
	static String getTimeStamp(){
		SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss.SSS");
		return f.format(new Date()); 
	}
	
	static String getLogFilename(){
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd'@'HH'00'");
		return f.format(new Date()) + "_" + id + ".log"; 
	}

	static void Log(String path, String msg) {
		Logger.Log(new File(path), msg);
	}
/*
	static void Log(File log, String msg) {
		try {
			if(!log.getParentFile().isDirectory())
				log.getParentFile().mkdirs();
			log.setReadable(true, false);
			FileWriter outFile = new FileWriter(log, true);
			outFile.append(getTimeStamp() + " " + msg + "\r\n");
			outFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
*/
	static void Log(File log, String msg) {
		try {
			if(!log.getParentFile().isDirectory())
				log.getParentFile().mkdirs();
			log.setReadable(true, false);
			OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(log, true), "UTF-8");
			BufferedWriter fbw = new BufferedWriter(writer);
			fbw.write(getTimeStamp() + " " + msg + "\r\n");
			fbw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void LogServer(String msg) {
		synchronized(Logger.logServerFile){
			Log(Logger.logServerFile.getAbsolutePath() + "/" +  getLogFilename(),msg);
		}
	}

	public static void LogServer(Throwable e) {
		synchronized(Logger.logServerFile){
			Log(Logger.logServerFile.getAbsolutePath() + "/" +  getLogFilename(),Logger.GetStackTrace(e));
		}
	}

	public static void LogServer(String msg, Throwable e) {
		synchronized(Logger.logServerFile){
			Log(Logger.logServerFile.getAbsolutePath() + "/" +  getLogFilename(), msg + "~" + Logger.GetStackTrace(e));
		}
	}

	public static void LogMomt(String msg) {
		synchronized(Logger.logMomtFile){
			Log(Logger.logMomtFile.getAbsolutePath() + "/" +  getLogFilename(),msg);
		}
	}
	
	public static void LogMo(String msg) {
		synchronized(Logger.logMoFile){
			Log(Logger.logMoFile.getAbsolutePath() + "/" +  getLogFilename(),msg);
		}
	}
	
	public static void LogMt(String msg) {
		synchronized(Logger.logMtFile){
			Log(Logger.logMtFile.getAbsolutePath() + "/" +  getLogFilename(),msg);
		}
	}
	
	public static void LogTrans(String msg) {
		synchronized(Logger.logTransFile){
			Log(Logger.logTransFile.getAbsolutePath() + "/" +  getLogFilename(),msg);
		}
	}

	public static void LogPps(String msg) {
		synchronized(Logger.logPpsFile){
			Log(Logger.logPpsFile.getAbsolutePath() + "/" +  getLogFilename(),msg);
		}
	}

	public static void LogDb(String msg) {
		synchronized(Logger.logDbFile){
			Log(Logger.logDbFile.getAbsolutePath() + "/" +  getLogFilename(),msg);
		}
	}

	public static void LogDb(String msg, String sql) {
		synchronized(Logger.logDbFile){
			try {
				Log(Logger.logDbFile.getAbsolutePath() + "/" +  getLogFilename(), msg + "~" + StringUtil.base64Encode(sql.getBytes("UTF-8")));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}

	public static void LogDb(Throwable e, String sql){
		synchronized(Logger.logDbFile){
			try {
				Log(Logger.logDbFile.getAbsolutePath() + "/" +  getLogFilename(), Logger.GetStackTrace(e) + "~" + StringUtil.base64Encode(sql.getBytes("UTF-8")));
			} catch (UnsupportedEncodingException ex) {
				e.printStackTrace();
			}
		}
	}

	public static void LogDb(String msg, Throwable e, String sql){
		synchronized(Logger.logDbFile){
			try {
				Log(Logger.logDbFile.getAbsolutePath() + "/" +  getLogFilename(), msg + "~" +  Logger.GetStackTrace(e) + "~" + StringUtil.base64Encode(sql.getBytes("UTF-8")));
			} catch (UnsupportedEncodingException ex) {
				e.printStackTrace();
			}
		}
	}

	public static void LogDb(Throwable e){
		synchronized(Logger.logDbFile){
			Log(Logger.logDbFile.getAbsolutePath() + "/" +  getLogFilename(), Logger.GetStackTrace(e));
		}
	}

	public static void LogDb(String msg, Throwable e){
		synchronized(Logger.logDbFile){
			Log(Logger.logDbFile.getAbsolutePath() + "/" +  getLogFilename(), msg + "~" +  Logger.GetStackTrace(e));
		}
	}
	
	public static void LogSmpp(String msg){
		synchronized(Logger.logSmppFile){
			Log(Logger.logSmppFile.getAbsolutePath() + "/" +  getLogFilename(), msg );
		}
	}

	static String GetStackTrace(Throwable throwable){
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
	
	public static void Save(String filename, String data){
		File log = new File(filename);
		try {
			if(!log.getParentFile().isDirectory())
				log.getParentFile().mkdirs();
			log.setReadable(true, false);
			OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(log, false), "UTF-8");
			BufferedWriter fbw = new BufferedWriter(writer);
			fbw.write(data);
			fbw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
