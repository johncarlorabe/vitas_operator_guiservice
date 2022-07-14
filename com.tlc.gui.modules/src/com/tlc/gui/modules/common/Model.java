package com.tlc.gui.modules.common;

import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.tlc.common.DbWrapper;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ObjectState;
import com.tlc.gui.modules.session.UISession;
import com.tlc.common.DbWrapper;
import com.tlc.common.SystemInfo;

public class Model {

	public static final String SQL_HEADER= "BEGIN\n ";
	public static final String SQL_FOOTER=
			"\nCOMMIT;\n"+
			"EXCEPTION\n" +
				"WHEN NO_DATA_FOUND THEN\n" +
				"	ROLLBACK; \n" +
				"WHEN OTHERS THEN\n" +
				"  ROLLBACK;\n" +
				"  RAISE;\n" +	
				"END;"; 
	
	
	protected DbWrapper db = SystemInfo.getDb();
	protected Map<String, Object> props=new LinkedHashMap<String,Object>();
	private ObjectState state;
	private UISession authorizedSession;
	private String objectName=this.getClass().getName();
	private int recordId;
	
	
	
	
	public int getRecordId() {
		return recordId;
	}

	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}

	public UISession getAuthorizedSession() {
		return authorizedSession;
	}

	public void setAuthorizedSession(UISession authorizedSession) {
		this.authorizedSession = authorizedSession;
	}

	public String getString(String key){
		return this.props.get(key).toString();
	}
	
	public Object getObject(String key){
		return this.props.get(key);
	}
	
	protected void removeProperty(String key){
		this.props.remove(key);
	}
	
	public Set<String> keys(){
		return this.props.keySet();
	}
	
	public ObjectState getState(){
		return state;
	}
	
	public void setState(ObjectState s){
		this.state =s;
	}
	
	public void setQualifiedName(String tag){
		this.objectName = tag;
	}
	
	public String getQualifiedName(){
		return this.objectName;
	}
}
