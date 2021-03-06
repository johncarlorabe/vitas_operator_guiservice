package com.tlc.common;

import java.util.Hashtable;

import com.tlc.common.DataRow;
import com.tlc.common.SystemInfo;

public class OperatorInfo {
	private String _bankname;
	private String _bankcode;
	private String _interface;
	private String _implementation;
	private String _endpointurl;
	private String _username;
	private String _password;
	private String _wsdl_url;
	private String _channel_id;
	private String _timeout;
	
	//method
	private String _key;
	private String _method;
	private String _api_service;
	private String _api_transaction;
	
	private static Hashtable<String, Hashtable<String, String>> _table = new Hashtable<String, Hashtable<String,String>>();
	private static Hashtable<String, Hashtable<String, String>> _bank_table = new Hashtable<String, Hashtable<String,String>>();
	//private static Hashtable<String, Hashtable<String, String>> _key_table = new Hashtable<String, Hashtable<String,String>>();
	
	static {
		try {
			DataRowCollection rows = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLOPERATOR ORDER BY ID ASC");
			for (int i = 0; i < rows.size(); i++) {
				try{								
					DataRowCollection row = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLOPERATOR WHERE BANKCODE = ?" , rows.get(i).get("BANKCODE").toString());
					Hashtable<String, String> t =  new Hashtable<String, String>();
					t.put("BANKNAME", row.get(0).get("BANKNAME").toString());
					t.put("BANKCODE", row.get(0).get("BANKCODE").toString());
					t.put("ID", row.get(0).get("ID").toString());
					t.put("ENDPOINTURL", row.get(0).get("ENDPOINTURL").toString());
					t.put("TIMEOUT", row.get(0).get("TIMEOUT").toString());
					t.put("PORT", row.get(0).get("PORT").toString());
					t.put("APIMESSAGE", row.get(0).get("APIMESSAGE").toString());
					t.put("STATUS", row.get(0).get("STATUS").toString());
					t.put("CHANNELID", row.get(0).get("CHANNELID").toString());
					t.put("HOSTIP", row.get(0).get("HOSTIP").toString());
					t.put("IMPLEMENTATION", row.get(0).get("IMPLEMENTATION").toString());
					t.put("USERNAME", row.get(0).get("USERNAME").toString());
					t.put("PASSWORD", row.get(0).get("PASSWORD").toString());
					
					
					_table.put(Util.cast(row.get(0).get("BANKCODE"),String.class), t);
										
				} catch (Exception e) {
					Logger.LogServer(e);
					
				}
			}
				
				
				DataRowCollection rows_banks = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLBANKS");
				for (int ctr = 0; ctr < rows_banks.size(); ctr++) {
					try{								
						DataRowCollection row = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLBANKS WHERE BANKCODE = ?" ,
								rows_banks.get(ctr).get("BANKCODE").toString());
						Hashtable<String, String> t =  new Hashtable<String, String>();
						t.put("BANKNAME", row.get(0).get("BANKNAME").toString());
						t.put("BANKCODE", row.get(0).get("BANKCODE").toString());
						t.put("STANBICBANKNAME", row.get(0).get("STANBICBANKNAME").toString());
						
						
						_bank_table.put(Util.cast(rows_banks.get(ctr).get("BANKCODE"),String.class), t);
											
					} catch (Exception e) {
						Logger.LogServer(e);
						
					}	
				}
				
				for (int ctr = 0; ctr < rows.size(); ctr++) {
					try{	
						
						DataRowCollection row = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLOPERATORKEY WHERE BANKCODE = ?" ,
								rows.get(ctr).get("BANKCODE").toString());
						
						for (int i = 0; i < row.size(); i++) {
						
							Hashtable<String, String> t =  new Hashtable<String, String>();
							t.put("KEY", row.get(i).get("KEY").toString());
							t.put("STATUS", row.get(i).get("STATUS").toString());
							t.put("APITRANSACTION", row.get(i).get("APITRANSACTION").toString());
							t.put("APISERVICE", row.get(i).get("APISERVICE").toString());
							t.put("METHOD", row.get(i).get("METHOD").toString());
			
							_table.put(Util.cast(rows.get(ctr).get("BANKCODE") + row.get(i).get("KEY").toString(),String.class), t);
							
						}
											
					} catch (Exception e) {
						Logger.LogServer(e);
						
					}	
				}
			
		} catch (Exception e) {
			Logger.LogServer(e);
		}
		
	}
	
	
	
	public OperatorInfo(String bankcode){
		DataRow row = SystemInfo.getDb().QueryDataRow("SELECT * FROM TBLOPERATOR WHERE BANKCODE = ? ORDER BY ID ASC",bankcode);
		_bankname = row.get("BANKNAME").toString();
		_bankcode = row.get("BANKCODE").toString();
		_interface = row.get("INTERFACE").toString();
		_implementation = row.get("IMPLEMENTATION").toString();
		_endpointurl = row.get("ENDPOINTURL").toString();
		_username = row.get("USERNAME").toString();
		_password = row.get("PASSWORD").toString();
		_wsdl_url = row.get("WSDLURL").toString();
		_channel_id = row.get("CHANNELID").toString();
		_timeout = row.get("TIMEOUT").toString();
	}
	
	public OperatorInfo(String bankcode, String key){
		DataRow row = SystemInfo.getDb().QueryDataRow("SELECT TBLOPERATOR.*,TBLOPERATORKEY.* FROM TBLOPERATOR " +
				"INNER JOIN TBLOPERATORKEY " +
				"ON TBLOPERATOR.BANKCODE = TBLOPERATORKEY.BANKCODE " +
				"WHERE TBLOPERATOR.BANKCODE = ? AND KEY = ?;",bankcode, key);
		
		_bankname = row.get("BANKNAME").toString();
		_bankcode = row.get("BANKCODE").toString();
		_interface = row.get("INTERFACE").toString();
		_implementation = row.get("IMPLEMENTATION").toString();
		_endpointurl = row.get("ENDPOINTURL").toString();
		_username = row.get("USERNAME").toString();
		_password = row.get("PASSWORD").toString();
		_wsdl_url = row.get("WSDLURL").toString();
		_channel_id = row.get("CHANNELID").toString();
		_timeout = row.get("TIMEOUT").toString();
		
		//methods
		_key = key;
		_method = row.get("METHOD").toString();
		_api_service = row.get("APISERVICE").toString();
		_api_transaction = row.get("APITRANSACTION").toString();
	}
	
	public static String get(String code, String key){
		try{
		return _table.get(code).get(key).toString();
		}catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	public static String dump(){
		return _table.toString();
	}
	
	public static String getBankTable(String code, String key){
		try{
		return _bank_table.get(code).get(key).toString();
		}catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	public void setUserName(String v){
	    _username = v;
	}
	
	public void setPassword(String v){
    	_password = v;
	}

	
    public String getBankName(){
    	return _bankname;
    }
    
    public String getBankCode(){
    	return _bankcode;
    }
    
    public String getInterface(){
    	return _interface;
    }
    
    public String getImplementation(){
    	return _implementation;
    }
    
    public String getEndPointUrl(){
    	return _endpointurl;
    }
    
    public String getPassword(){
    	return _password;
    }
    
    public String getUserName(){
    	return _username;
    }
    
    public String getWsdlUrl(){
    	return _wsdl_url;
    }
    
    public String getChannelID(){
    	return _channel_id;
    }
    
    public String getTimeout(){
    	return _timeout;
    }
    
    //method accessors
    public String getKey(){
    	return _key;
    }
    
    public String getMethod(){
    	return _method;
    }
    
    public String getAPIService(){
    	return _api_service;
    }
    
    public String getAPITransaction(){
    	return _api_transaction;
    }
    
    
    public static Object getImplementation(String bankcode){
		DataRow row = SystemInfo.getDb().QueryDataRow("SELECT IMPLEMENTATION FROM TBLOPERATOR WHERE BANKCODE = ? ORDER BY ID ASC",bankcode);
		return (row.get("IMPLEMENTATION")==null) ? "" : row.get("IMPLEMENTATION");
	}
	
	public static Object getMethod(String bankcode, String key){
		DataRow row = SystemInfo.getDb().QueryDataRow("SELECT METHOD FROM TBLOPERATORKEY WHERE BANKCODE = ? AND KEY = ? AND STATUS = 1",bankcode,key);
		return (row.get("METHOD")==null) ? "" : row.get("METHOD");
	}
	
	public static Object getAPIMessage(String bankcode){
		DataRow row = SystemInfo.getDb().QueryDataRow("SELECT APIMESSAGE FROM TBLOPERATOR WHERE BANKCODE = ?",bankcode);
		return (row.get("APIMESSAGE")==null) ? "1" : row.get("APIMESSAGE");
	}
	
	public static Object getPort(String bankcode){
		DataRow row = SystemInfo.getDb().QueryDataRow("SELECT PORT FROM TBLOPERATOR WHERE BANKCODE = ?",bankcode);
		return (row.get("PORT")==null) ? "" : row.get("PORT");
	}
	
	public static Object getHostIP(String bankcode){
		DataRow row = SystemInfo.getDb().QueryDataRow("SELECT HOSTIP FROM TBLOPERATOR WHERE BANKCODE = ?",bankcode);
		return (row.get("HOSTIP")==null) ? "1" : row.get("HOSTIP");
	}
	
	public static Object getBankID(String bankcode){
		DataRow row = SystemInfo.getDb().QueryDataRow("SELECT ID FROM TBLOPERATOR WHERE BANKCODE = ?",bankcode);
		return (row.get("ID")==null) ? "1" : row.get("ID");
	}
	
	public static Object getBankName(String bankcode){
		DataRow row = SystemInfo.getDb().QueryDataRow("SELECT BANKNAME FROM TBLBANKS WHERE BANKCODE = ?",bankcode);
		return (row.get("BANKNAME")==null) ? "" : row.get("BANKNAME");
	}
	
	public static Object getStanbicBankName(String bankcode){
		DataRow row = SystemInfo.getDb().QueryDataRow("SELECT STANBICBANKNAME FROM TBLBANKS WHERE BANKCODE = ?",bankcode);
		return (row.get("STANBICBANKNAME")==null) ? "" : row.get("STANBICBANKNAME");
	}
	
	public static boolean isActive(String bankcode){
		try{
		DataRow row = SystemInfo.getDb().QueryDataRow("SELECT STATUS FROM TBLOPERATOR WHERE BANKCODE = ?",bankcode);		
		return row.get("STATUS").toString().equals("1") ? true : false;
		}catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
}
