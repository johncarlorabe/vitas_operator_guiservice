package com.tlc.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPMessage;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.apache.http.HttpHost;

public class HttpUtil {
	
	public static byte[] httpPost(String url, HashMap<String, String> headers, byte[] data) throws Exception{
		return httpPost(url,  headers, data, null);
	}
	
	public static byte[] httpPost(String url, HashMap<String, String> headers, byte[] data, HttpHost proxy) throws Exception{
		InputStreamEntity reqEntity = new InputStreamEntity(new ByteArrayInputStream(data), data.length);
		reqEntity.setChunked(true);
		if(headers.containsKey("Content-Type")){
			reqEntity.setContentType(headers.get("Content-Type"));
			headers.remove("Content-Type");
		}
		return httpPost(url, null, headers, reqEntity, proxy);
	}
	
	public static byte[] httpPostXml(String url, HashMap<String, String> headers, byte[] data) throws Exception{
		headers.put("Content-Type", "text/xml; charset=utf-8");
        return httpPost(url,  headers, data, null);
	}
	
	public static byte[] httpPostXml(String url, HashMap<String, String> headers, byte[] data, HttpHost proxy) throws Exception{
        headers.put("Content-Type", "text/xml; charset=utf-8");
		return httpPost(url, headers, data, proxy);
	}
	
	public static byte[] httpPostText(String url, HashMap<String, String> params, HashMap<String, String> headers, String str, HttpHost proxy){
		InputStreamEntity reqEntity = null;
		if(!StringUtil.isNullOrEmpty(str)){
			byte[] data = null;;
			try {
				data = str.getBytes("UTF-8");
			} catch (UnsupportedEncodingException e) {
				Logger.LogServer(e);
				return null;
			}
			reqEntity = new InputStreamEntity(new ByteArrayInputStream(data), data.length);
	        reqEntity.setContentType("text/plain; charset=utf-8");
	        reqEntity.setChunked(true);
		}
		try {
			return httpPost(url, params, headers, reqEntity, proxy);
		} catch (Exception e) {
			Logger.LogServer(e);
		}
		return null;
	}
	
	public static byte[] httpPost(String url, HashMap<String, String> params, HashMap<String, String> headers, AbstractHttpEntity reqEntity){
		try {
			return httpPost( url, params, headers, reqEntity, null);
		} catch (Exception e) {
			Logger.LogServer(e);
		}
		return null;
	}
	
	public static byte[] httpPost(String url, HashMap<String, String> params, HashMap<String, String> headers, AbstractHttpEntity reqEntity, HttpHost proxy) throws ClientProtocolException, IOException{
		HttpClient httpclient = new DefaultHttpClient();
		if(proxy != null){
			httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
		}
		HttpPost req = new HttpPost(url);
		if(params != null && params.size() > 0){
			HttpParams httpparams = new BasicHttpParams();
			for(Entry<String,String> entry : params.entrySet()){
				httpparams.setParameter(entry.getKey(), entry.getValue());
			}
			req.setParams(httpparams);
		}
		
		if(headers != null)
			for(Entry<String,String> entry : headers.entrySet()){
				req.addHeader(entry.getKey(), entry.getValue());
			}
		if(reqEntity != null)
			req.setEntity(reqEntity);
		
		HttpResponse res = httpclient.execute(req);
		return EntityUtils.toByteArray(res.getEntity());
	}
	
	public static byte[] sendSoap(String url, String action, byte[] data){
		ByteArrayInputStream instream = null;
		ByteArrayOutputStream outstream = null;
		try{
			MimeHeaders header = new MimeHeaders();
			header.addHeader("SOAPAction",action);
	        SOAPMessage rp = sendSoap(url,MessageFactory.newInstance().createMessage(header, new ByteArrayInputStream(data)));
			outstream = new ByteArrayOutputStream();
			rp.writeTo(outstream);
			return outstream.toByteArray();
		}catch(Exception e){
			Logger.LogServer(e);
		}finally{
			if(outstream != null)try {outstream.close();} catch (Exception e) {}
			if(instream != null)try {instream.close();} catch (Exception e) {}
		}
		return null;
	}

	public static SOAPMessage sendSoap(String url, SOAPMessage message){
		SOAPConnection conn = null;
		try{
			conn = SOAPConnectionFactory.newInstance().createConnection();
			return conn.call(message,url);
		}catch(Exception e){
			Logger.LogServer(e);
		}finally{
			if(conn != null)try {conn.close();} catch (Exception e) {}
		}
		return null;
	}
	
}
