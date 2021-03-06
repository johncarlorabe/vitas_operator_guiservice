package com.tlc.common;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class DataRow extends LinkedHashMap<String,Object>{
	private static final long serialVersionUID = 1L;
	public Object getValue(int index){
		Iterator<Object> a = this.values().iterator();
		for(int i = 0; a.hasNext(); i ++){
			if(i==index) return a.next();
			a.next();
		}
		return null;
	}
	public String getKey(int index){
		Iterator<String> a = this.keySet().iterator();
		for(int i = 0; a.hasNext(); i ++){
			if(i==index) return a.next();
			a.next();
		}
		return null;
	}
	
	public Object get(int index) {
		Iterator<Object> a = this.values().iterator();
		for(int i = 0; a.hasNext(); i ++){
			if(i==index) return a.next();
			a.next();
		}
		return null;
	}
	
	@Override
	public Object get(Object key) {
		if(this.containsKey(key))
			return super.get(key);
		Logger.LogServer(new Exception("No column name : " + key));
		return null;
	}
	
	public String getString(int index){
		return Util.cast(get(index),String.class);
	}
	public String getString(Object key){
		return Util.cast(get(key),String.class);
	}
	public Long getLong(int index){
		return Util.cast(get(index),Long.class);
	}
	public Long getLong(Object key){
		return Util.cast(get(key),Long.class);
	}
	public Integer getInteger(int index){
		return Util.cast(get(index),Integer.class);
	}
	public Integer getInteger(Object key){
		return Util.cast(get(key),Integer.class);
	}
	public Timestamp getTimestamp(int index){
		return Util.cast(get(index),Timestamp.class);
	}
	public Timestamp getTimestamp(Object key){
		return Util.cast(get(key),Timestamp.class);
	}
	public Date getDate(int index){
		return Util.cast(get(index),Date.class);
	}
	public Date getDate(Object key){
		return Util.cast(get(key),Date.class);
	}
}
