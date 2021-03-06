package com.tlc.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.sql.*;
import java.util.Properties;
import java.util.Vector;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

public class DbWrapper{
	protected static Log logger = LogFactory.getLog(DbWrapper.class);
	//private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(DbWrapper.class);
	private String Url;
	private String Username;
	private String Password;
	private String Driver = "";
	private String DataSourceClassName;
	private String Crypt = "sunev8clt1234567890";
	private DataSource ds = null;
	private EncryptedProperties configFile;

	public DbWrapper(){
		logger.info("Initialize Default Properties");
		InitialContext context;
		try {
			context = new InitialContext();
			ds = (DataSource) context.lookup("java:jboss/datasources/OracleClient");
			return;
		} catch (NamingException e){
			logger.warn(e);
		}
		
		try {
			configFile = new EncryptedProperties();	
		} catch (Exception e) {
			logger.error("EncryptedProperties receives Exception",e);
			Logger.LogDb(e);
			configFile = null;
		}
		if(configFile==null) System.exit(0);
		Driver = configFile.getProperty("DRIVER","");
		DataSourceClassName = configFile.getProperty("POOLCLASSNAME","oracle.jdbc.pool.OracleDataSource");
		Url = configFile.getProperty("connection_url","jdbc:oracle:thin:@//127.0.0.1:1521/LOCAL");
		Username = configFile.getProperty("user","ADMDBMCJ");
		Password = configFile.getProperty("password","admdbmcj");
		Crypt = configFile.getProperty("CRYPT",Crypt);
		Init();
	}
	
	public DbWrapper(String jdniName){
		logger.debug("initializing " + DbWrapper.class.getName());
		InitialContext context;
		try {
			context = new InitialContext();
			ds = (DataSource) context.lookup(jdniName);
		} catch (NamingException e) {
			logger.fatal(e);
		}
	}
	
	public DbWrapper(String url, String username, String password){
		logger.info("Initialize using url, username, password");
		Url = url;
		Username = username;
		Password = password;
		Init();
	}	
	
	public DbWrapper(String driver, String url, String username, String password){
		logger.info("Initialize using driver, url, username, password");
		Driver = driver;
		Url = url;
		Username = username;
		Password = password;
		Init();
	}
	
	public DbWrapper(File propertiesFile){
		logger.info("Initialize using properties file");
		try {
			configFile = new EncryptedProperties(propertiesFile.getAbsolutePath());
		} catch (Exception e) {
			Logger.LogDb(e);
		}
		if(configFile==null) System.exit(0);
		Driver = configFile.getProperty("DRIVER","");
		DataSourceClassName = configFile.getProperty("POOLCLASSNAME","oracle.jdbc.pool.OracleDataSource");
		Url = configFile.getProperty("connection_url","jdbc:oracle:thin:@//192.168.137.10:1521/LOCAL");
		Username = configFile.getProperty("user","ADMDBMF");
		Password = configFile.getProperty("password","ADMDBMF");
		Crypt = configFile.getProperty("CRYPT",Crypt);
		Init();

	}
	
	private void Init(){
		logger.info("Entering Init()");
		try {
			Class<?> cls;
			if(!StringUtil.isNullOrEmpty(Driver))
				Class.forName(Driver);
			if(!StringUtil.isNullOrEmpty(DataSourceClassName)){
				cls = Util.cast(Class.forName(DataSourceClassName));
				Constructor<?> cons = cls.getConstructor();
				ds = (DataSource) cons.newInstance();
				Util.invoke("setConnectionProperties",ds,new Properties(configFile));
				Util.invoke("setURL",ds,Url);
				Util.invoke("setUser",ds,Username);
				Util.invoke("setPassword",ds,Password);
				Util.invoke("setConnectionCachingEnabled",ds,true);
				Util.invoke("setConnectionCacheProperties",ds,new Properties(configFile));
				//Util.invoke("setFastConnectionFailoverEnabled",ds,true);
				Util.invoke("setConnectionCacheName",ds,configFile.getProperty("ConnectionCacheName",System.getProperty("java.class.path").split(":")[0]));
			}else{
				
			}
		} catch (Exception e) {
			logger.error(e);
			Logger.LogDb(e);
			System.exit(0);
		}
	}
	
	public String getCrypt() {
		return Crypt;
	}
	
	public void SetParams(CallableStatement stm, InOutParameter... params) throws SQLException{
		logger.info("Setting Parameters InOutParameter");
		if(params == null) return;
		for(int i = 0; i < params.length; i++){
			
			if((params[i].direction & 1) > 0){ //input
				switch(params[i].getType()){
					case java.sql.Types.NULL: //unknown
						stm.setObject(i + 1, params[i].getValue());
						break;
					case java.sql.Types.ARRAY:
						//oracle.sql.ARRAY
						stm.setArray(i + 1,createArray(stm.getConnection(),(Object[])params[i].getValue(), params[i].getName()));
						break;
					case java.sql.Types.CLOB:
						//oracle.sql.Types.CLOB
						if(params[i].getValue() instanceof String){
							String value = (String)params[i].getValue(); 
							Reader reader = new StringReader(value);
							stm.setCharacterStream(i + 1, reader, value.length());
							break;
						}
						if(params[i].getValue() instanceof Reader){
							Reader reader = (Reader)params[i].getValue();
							stm.setClob(i + 1, reader);
							break;
						}
					case java.sql.Types.BLOB:
						//oracle.sql.Types.BLOB
						if(params[i].getValue() instanceof String){
							byte[] value = ((String)params[i].getValue()).getBytes(); 
							InputStream reader = new ByteArrayInputStream(value);
							stm.setBinaryStream(i + 1, reader, value.length);
							break;
						}
						if(params[i].getValue() instanceof InputStream){
							InputStream reader = (InputStream)params[i].getValue();
							stm.setBlob(i + 1, reader);
							break;
						}
					default :
						stm.setObject(i + 1, params[i].getValue());
				}
			}
			
			if((params[i].direction & 2) > 0){ //output
				if(params[i].name == null || params[i].name.length() == 0)
					stm.registerOutParameter(i + 1, params[i].getType());
				else
					stm.registerOutParameter(i + 1, params[i].getType(), params[i].name);
			}
			
		}
	}
	
	public void SetParams(PreparedStatement stm, Object... params) throws SQLException{
		logger.info("Setting Parameters Object");
		if(params == null) return;
		for(int i = 0; i < params.length; i++){
			try{
			if(params[i] instanceof java.util.Date){
				java.sql.Timestamp dt = new java.sql.Timestamp(((java.util.Date)params[i]).getTime());
				stm.setObject(i + 1, dt);
			}else if(params[i] instanceof Reader){
				stm.setClob(i + 1, (Reader)params[i]);
			}else{
				stm.setObject(i + 1, params[i]);
			}
			}catch(SQLException e){
				Logger.LogDb(Integer.toString(i));
				throw e;
			}
		}
	}
	
	public <T> T QueryScalar(String sql,T defaultValue, Object... params){
		logger.info("QueryScalar()");
		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try{
			con = GetConnection();
			stm = con.prepareStatement(sql);
			SetParams(stm, params);
			rs = stm.executeQuery();  
            if (rs.next()) {
            	Object obj = rs.getObject(1);
            	if(obj instanceof Clob){
            		Clob clob = (Clob)obj;
	            	return Util.cast(Util.cast(getClobString(clob), defaultValue.getClass()));
            	}
            	if(obj instanceof Blob){
            		Blob blob = (Blob)obj;
	            	return Util.cast(Util.cast(getBlobBytes(blob), defaultValue.getClass()));
            	}
            	if(defaultValue != null){
            		return Util.cast(Util.cast(obj, defaultValue.getClass()));
            	}
            	return Util.cast(obj);  
            }  
		}catch(Exception e){
			try {
				logger.error(StringUtil.base64Encode(sql.getBytes("UTF-8")),e);
			} catch (UnsupportedEncodingException e1) {
				logger.error(sql,e1);
			}
			Logger.LogDb(e,sql);
		}finally{
			try {
				if (rs != null) rs.close();
                if (stm != null)stm.close();
                if (con != null)con.close();
            } catch (Exception e) {
            	logger.error("Closing Connection Failed",e);
            	/// TODO logger
            }
		}
		return defaultValue;
	}

	public int QueryUpdate(String sql,Object... params){
		Connection con = null;
		PreparedStatement stm = null;
		try{
			con = GetConnection();
			stm = con.prepareStatement(sql);
			SetParams(stm, params);
			return stm.executeUpdate();   
		}catch(Exception e){
			Logger.LogDb(e,sql);
		}finally{
			try {
                if (stm != null)stm.close();
                if (con != null)con.close();
            } catch (Exception e) {
            	Logger.LogDb(e,sql);
            }
		}
		return -1;
	}
	
	public int QueryUpdate(String sql,InOutParameter... params){
		Connection con = null;
		CallableStatement stm = null;
		try{
			con = GetConnection();
			stm = con.prepareCall(sql);
			SetParams(stm, params);
			stm.executeUpdate();
			for(int i = 0; i < params.length; i++){
				if((params[i].direction & 2) > 0){
					Object obj = stm.getObject(i + 1);
					if(obj instanceof java.sql.Array)
						params[i].setValue(((java.sql.Array)obj).getArray());
					else
						params[i].setValue(obj);
				}
			}
			return 1;
		}catch(Exception e){
			Logger.LogDb(e,sql);
		}finally{
			try {
                if (stm != null)stm.close();
                if (con != null)con.close();
            } catch (Exception e) {
            	Logger.LogDb(e,sql);
            }
		}
		return -1;
	}

	@SuppressWarnings("unchecked")
	public <T> T[] QueryArray(String sql,T defaultValue, Object... params){
		Connection con = null;
		PreparedStatement stm = null;
		Vector<T> array = new Vector<T>();
		try{
			con = GetConnection();
			stm = con.prepareStatement(sql);
			SetParams(stm, params);
			ResultSet rs=stm.executeQuery();
			while(rs.next()){
				if(defaultValue != null)
					array.add((T)Util.cast(rs.getObject(1),defaultValue.getClass()));
				else
					array.add((T)Util.cast(rs.getObject(1)));
			}
			return array.toArray((T[])java.lang.reflect.Array.newInstance(defaultValue.getClass(),0));
		}catch(Exception e){
			Logger.LogDb(e,sql);
		}finally{
			try {
                if (stm != null)stm.close();
                if (con != null)con.close();
            } catch (Exception e) {
            	Logger.LogDb(e,sql);
            }
		}
		return null;
	}
	
	private <T> Array createArray(Connection con, Object[] array, String type) throws SQLException{
		ArrayDescriptor disc = ArrayDescriptor.createDescriptor(type, con);
		return new ARRAY(disc,con, array);
		//return con.createArrayOf(type, array);
	}
	
	public DataRow QueryDataRow(String sql, Object... params){
		DataRowCollection rows = QueryDataRows(sql,params);
		if(rows != null && rows.size() > 0){
			return rows.get(0);
		}
		return new DataRow();
	}
	
	public DataRowCollection QueryDataRows(String sql, Object...params){
		DataRowCollection rows = new DataRowCollection();
		Connection con = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try{
			con = GetConnection();
			stm = con.prepareStatement(sql);
			SetParams(stm, params);
			rs = stm.executeQuery(); 
			ResultSetMetaData rsmd = rs.getMetaData();
		    int numColumns = rsmd.getColumnCount();
            while (rs.next() ) {
            	DataRow row = new DataRow();
            	for(int i = 0; i < numColumns; i++){
            		Object obj = rs.getObject(i+1);
                	if(obj instanceof Clob){
                		Clob clob = (Clob)obj;
    	            	row.put(rsmd.getColumnName(i + 1), getClobString(clob));
                	}else if(obj instanceof Blob){
                		Blob blob = (Blob)obj;
    	            	row.put(rsmd.getColumnName(i + 1), getBlobBytes(blob));
                	}else
                		row.put(rsmd.getColumnName(i + 1), obj);
            	}
            	rows.add(row);
            }
		}catch(Exception e){
			Logger.LogDb(e,sql);
		}finally{
			try {
				if (rs != null) rs.close();
                if (stm != null)stm.close();
                if (con != null)con.close();
            } catch (Exception e) {
            	Logger.LogDb(e,sql);
            }
		}
		return rows;
	}
	
	private String getClobString(Clob clob) throws SQLException, IOException{
		Reader reader = clob.getCharacterStream();
		try{
    		StringBuilder builder = new StringBuilder();
    		int charsRead = -1;
    		char[] chars = new char[100];
    		do{
    		    charsRead = reader.read(chars,0,chars.length);
    		    //if we have valid chars, append them to end of string.
    		    if(charsRead>0)
    		        builder.append(chars,0,charsRead);
    		}while(charsRead>0);
    		return builder.toString();
		}finally{
			reader.close();	
		}
	}
	
	private byte[] getBlobBytes(Blob blob) throws SQLException, IOException{
		InputStream reader = blob.getBinaryStream();
		try{
    		ByteArrayOutputStream builder = new ByteArrayOutputStream();
    		int bytessRead = -1;
    		byte[] bytes = new byte[100];
    		do{
    			bytessRead = reader.read(bytes,0,bytes.length);
    		    //if we have valid chars, append them to end of string.
    		    if(bytessRead>0)
    		        builder.write(bytes,0,bytessRead);
    		}while(bytessRead>0);
    		return builder.toByteArray();
		}finally{
			reader.close();	
		}
	}
	
	public Connection GetConnection() throws Exception{
		Connection con;
		Exception ex = null;
		for(int i = 0; i < 10; i++){
			try{
				if(ds != null){
					con = ds.getConnection();
				}else if(configFile != null){
					con = DriverManager.getConnection(Url,configFile);
				}else{
					con = DriverManager.getConnection(Url);
				}
				if(con != null) return con;
			} catch (Exception e) {
				ex = e;
				logger.error("DBConnection unavailable!",e);
				Logger.LogServer("DBConnection unavailable!");
				Logger.LogServer(e);
				Util.sleep(5000);
			}
		}
		throw new Exception("DBConnection unavailable!",ex);
	}

	public static class InOutParameter{
		public static final int INPUT_TYPE  = 1;
		public static final int OUTPUT_TYPE = 2;
		public static final int INOUT_TYPE  = 3;
		Class<?> klass;
		Object   value;
		int      direction;
		int      type;
		String   name;
		
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public InOutParameter(Object value){
			this.value = value;
			this.klass = value.getClass();
			this.direction = INPUT_TYPE;
		}
		
		public InOutParameter(Class<?> klass, int direction){
			this.klass = klass;
			try {
				this.value     = Util.cast(klass.newInstance());
			} catch (Exception e) {
				Logger.LogServer(e);
			}
			this.direction = direction;
		}
		
		public InOutParameter(Class<?> klass, int direction, int type){
			this.klass = klass;
			this.direction = direction;
			this.type = type;
		}
		
		public InOutParameter(Object value, int direction){
			this.value = value;
			this.klass = value.getClass();
			this.direction = direction;
		}
		
		public InOutParameter(Object value, int direction, int type){
			this.value = value;
			this.klass = value.getClass();
			this.direction = direction;
			this.type = type;
		}
		
		public InOutParameter(Object value, int direction, int type, String name){
			this.value = value;
			this.klass = value.getClass();
			this.direction = direction;
			this.type = type;
			this.name = name;
		}
		
		public int getDirection() {
			return direction;
		}

		public void setDirection(int direction) {
			this.direction = direction;
		}

		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}
	
		public int getType(){
			return type;
		}
		
		public void setType(int value){
			this.type = value;
		}
	}
}


