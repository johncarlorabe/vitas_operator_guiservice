package com.tlc.common;

import java.io.File;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class SystemInfo {

	public static final String PROP_CURRENCY_TYPE       = "CURRENCYTYPE";
	public static final String PROP_HOST_IP             = "HOSTIP";
	public static final String PROP_MIN_PASSWORD        = "MINPASSWORD";
	public static final String PROP_MAX_PASSWORD        = "MAXPASSWORD";
	public static final String PROP_MIN_ALIAS           = "MINALIAS";
	public static final String PROP_MAX_ALIAS           = "MAXALIAS";
	public static final String PROP_ACCEPT_DECIMAL      = "ACCEPTDECIMAL";
	public static final String PROP_COUNTRY_CODE        = "COUNTRYCODE";
	public static final String PROP_INVALID_PASS_COUNT  = "INVALIDPASSWORDCOUNT";
	public static final String PROP_FAILED_TRANS_COUNT  = "FAILEDTRANSFERCOUNT";
	public static final String PROP_RPRE_DAYS_COUNT     = "RPREDAYCOUNT";
	public static final String PROP_MSISDN_TO_ALIAS     = "MSISDNTOALIAS";
	public static final String PROP_DATE_FORMAT         = "DATEFORMAT";
	public static final String PROP_TIME_FORMAT         = "TIMEFORMAT";
	public static final String PROP_MONEY_FORMAT        = "MONEYFORMAT";
	public static final String PROP_DECIMAL_SEPARATOR   = "DECIMALSEPARATOR";
	public static final String PROP_GROUP_SEPARATOR     = "GROUPSEPARATOR";
	
	public static final String INTERDEALERTRANSFER      = "INTERDEALERTRANSFER";
	public static final String NUMBERFORMAT        		= "NUMBERFORMAT";
	public static final String MINORAGE					= "MINORAGE";
	
	
	
	private static Properties property = new Properties();
	private static DbWrapper db = null;
	private static Hashtable<String,String> language = new Hashtable<String,String>();
	private static Hashtable<String,String> accountType = new Hashtable<String,String>();
	private static List<String> hostips = new ArrayList<String>();
	private static ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();

	static{

		if(System.getProperty("java.libary.path") ==null){
			System.setProperty("java.libary.path", (new File(Util.getWorkingDirectory())).getParentFile() + "/dbclient");
		}
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
		      public void run() {
		    	   Util.stopReferenceGenerator();
		    	  Logger.LogServer("Application Ends...");
		    	  addAuditTrail("AUTO", "SYSTEM", "Stopping " + System.getProperty("java.class.path").split(":")[0], hostips.get(0));
		    	  SystemInfo.getExecutor().shutdownNow();
		    	  Util.sleep(2000);
		    	  Runtime.getRuntime().halt(0);
		      }
		    });
		db = new DbWrapper();
		try {
			Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces();
			while(ifaces.hasMoreElements() ){
				NetworkInterface iface = ifaces.nextElement();
				Enumeration<InetAddress> addresses = iface.getInetAddresses();
				while( addresses.hasMoreElements()){
					InetAddress addr = addresses.nextElement();
					if( addr instanceof Inet4Address && !addr.isLoopbackAddress() ){
						hostips.add(addr.getHostAddress());
					}
				}
			}
			if(hostips.size() > 0)
				property.put("HOSTIP", hostips.get(0));
		} catch (Exception e) {
			Logger.LogServer(e);
			Runtime.getRuntime().halt(0);
		}
		addAuditTrail("AUTO", "SYSTEM", "Starting " + System.getProperty("java.class.path").split(":")[0], hostips.get(0));
		
		DataRowCollection rows = db.QueryDataRows("SELECT * FROM TBLSYSTEMINFO");	
		if(rows != null && rows.size() > 0){
			try{
				DataRow row = rows.getFirst();
				if(row != null){
					for(String key : row.keySet()){
						if(StringUtil.isNullOrEmpty(Util.cast(row.get(key),String.class))){
							row.put(key, "");
						} else {
							row.put(key,row.get(key) + "");
						}
					}
				}
				property.putAll(row);
			}catch(Exception e){
				Logger.LogServer(e);
				Runtime.getRuntime().halt(0);
			}
		}
		rows = db.QueryDataRows("SELECT LANGUAGE, DESCRIPTION FROM TBLLANGUAGE");
		if(rows != null && rows.size() > 0){
			for(DataRow row : rows){
				try{
					String lang = Util.cast(row.get("LANGUAGE"),String.class);
					String desc = Util.cast(row.get("DESCRIPTION"),String.class);
					language.put(lang, desc);
				}catch(Exception e){
					Logger.LogServer(e);
				}
			}
		}else{
			Logger.LogServer("TBLLANGUAGE not configured!");
		}
		if(!language.containsKey("E")){
			language.put("E", "ENGLISH");
		}
		rows = db.QueryDataRows("SELECT ACCOUNTTYPE, DESCRIPTION FROM TBLACCOUNTTYPE");
		if(rows != null && rows.size() > 0){
			for(DataRow row : rows){
				try{
					String lang = Util.cast(row.get("ACCOUNTTYPE"),String.class);
					String desc = Util.cast(row.get("DESCRIPTION"),String.class);
					accountType.put(lang, desc);
				}catch(Exception e){
					Logger.LogServer(e);
				}
			}
		}else{
			Logger.LogServer("TBLACCOUNTTYPE not configured!");
		}
	}
	
	private static SimpleDateFormat dateformat = new SimpleDateFormat(SystemInfo.getProperty(SystemInfo.PROP_TIME_FORMAT,"dd-MMM-yyyy HH:mm:ss"));
	private static SimpleDateFormat timestampformat = new SimpleDateFormat(SystemInfo.getProperty(SystemInfo.PROP_DATE_FORMAT,"dd-MMM-yyyy HH:mm:ss"));
	
	
	public static void init(){
		
	}
	
	private static boolean addAuditTrail(String userid, String username, String log, String ip){
		String query = "INSERT INTO TBLZAUDITTRAIL (USERNAME,USERID,LOG,IP) VALUES(?,?,?,?)";
		boolean ret = (db.QueryUpdate(query, username, userid, log, ip) > 0);
		return ret;
	}
	
	public static String getProperty(String key){
		return property.getProperty(key);
	}
	
	public static Object get(String key){
		if(!property.containsKey(key)){
			Logger.LogServer("TblSystemInfo." + key + " not available");
			return null;
		}
		return property.get(key);
	}
	
	@SuppressWarnings("unchecked")
	public static <E> E getProperty(String key, E defaultValue){
		if(!property.containsKey(key)){
			Logger.LogServer("TblSystemInfo." + key + " not available");
			return defaultValue;
		}
		Object value = property.get(key);
		if(value == null) return defaultValue;
		return (E)Util.cast(value, defaultValue.getClass());
	}
	
	public static DbWrapper getDb() {
		return db;
	}

	public static ThreadPoolExecutor getExecutor() {
		return executor;
	}
	
	public static Hashtable<String,String> getlanguage(){
		return language;
	}
	
	public static Hashtable<String,String> getAccountType(){
		return accountType;
	}
	
	public synchronized static String formatDate(Date dt){
		return dateformat.format(dt);
	}
	
	public synchronized static String formatTimestamp(Date dt){
		return timestampformat.format(dt);
	}
	
	public static String getTransId(int id) throws Exception{
		String transid = db.QueryScalar("SELECT GETREFERENCEID(?) FROM DUAL", "", id);
		if(StringUtil.isNullOrEmpty(transid)){
			return Util.getReferenceId(id);
		}
		return transid;
	}
	protected static final String sqlInsertLog = "INSERT INTO TBLTRANSACTIONLOG(REFERENCEID, TIMESTAMP, KEY, MSISDN, SOURCE, ERRORCODE, ERRORDESCRIPTION)\n" + 
            "VALUES(?, ?, ?, ?, ?, ?, ?)";
	public static boolean insertTransactionLog(String referenceid, Timestamp date, String key, String originator, String source, int errorcode, String errordescription){
		return 1 == db.QueryUpdate(sqlInsertLog, referenceid, date, key, originator, source, errorcode, errordescription);
	}
	
	public static String[] getIpAddresses(){
		return hostips.toArray(new String[]{});
	}
	
}
