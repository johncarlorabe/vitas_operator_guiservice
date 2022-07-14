package com.tlc.gui.absmobile.modules.session.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

public class HttpClientHelper {

	public byte[] httpGet(String url, HashMap<String, String> params, HashMap<String, String> headers, HttpHost proxy) throws IOException{
		HttpClient httpclient = new DefaultHttpClient();
		if(proxy != null){
			httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
		}
		HttpGet req = new HttpGet(url);
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
		
		HttpResponse res = httpclient.execute(req);
		return EntityUtils.toByteArray(res.getEntity());
	}
	
	public byte[] httpDelete(String url, HashMap<String, String> params, HashMap<String, String> headers, HttpHost proxy) throws ClientProtocolException, IOException {
		HttpClient httpclient = new DefaultHttpClient();
		
		if (proxy != null) {
			httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
		}
		
		HttpDelete req = new HttpDelete(url);
		if (params != null && params.size() > 0) {
			HttpParams httpparams = new BasicHttpParams();
			for (Entry<String, String> entry : params.entrySet()) {
				httpparams.setParameter(entry.getKey(), entry.getValue());
			}
			req.setParams(httpparams);
		}
		
		if (headers != null) {
			for (Entry<String, String> entry : headers.entrySet()) {
				req.addHeader(entry.getKey(), entry.getValue());
			}
		}
		
		
		HttpResponse res = httpclient.execute(req);
		return EntityUtils.toByteArray(res.getEntity());
		
	}
	
	public byte[] httpPost(String url, HashMap<String, String> params, HashMap<String, String> headers, HttpHost proxy, AbstractHttpEntity entity) throws IOException {
		HttpClient httpclient = new DefaultHttpClient();
		
		if (proxy != null) {
			httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
		}
		
		HttpPost req = new HttpPost(url);
		if (params != null && params.size() > 0) {
			HttpParams httpparams = new BasicHttpParams();
			for (Entry<String, String> entry : params.entrySet()) {
				httpparams.setParameter(entry.getKey(), entry.getValue());
			}
			req.setParams(httpparams);
		}
		
		if (headers != null) {
			for (Entry<String, String> entry : headers.entrySet()) {
				req.addHeader(entry.getKey(), entry.getValue());
			}
		}
		
		if (entity != null) {
			req.setEntity(entity);
		}
		
		HttpResponse res = httpclient.execute(req);
		return EntityUtils.toByteArray(res.getEntity());
	}
	
	public byte[] httpDeleteWithBody(String url, StringEntity entity, HashMap<String, String> headers, HttpHost proxy,HashMap<String, String> params) throws ClientProtocolException, IOException {
		HttpClient httpclient = new DefaultHttpClient();
		
		if (proxy != null) {
			httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
		}
		
		HttpDeleteWithBody req = new HttpDeleteWithBody(url);
		if (params != null && params.size() > 0) {
			HttpParams httpparams = new BasicHttpParams();
			for (Entry<String, String> entry : params.entrySet()) {
				httpparams.setParameter(entry.getKey(), entry.getValue());
			}
			req.setParams(httpparams);
		}
		
		if (headers != null) {
			for (Entry<String, String> entry : headers.entrySet()) {
				req.addHeader(entry.getKey(), entry.getValue());
			}
		}
		
		if (entity != null) {
			req.setEntity(entity);
		}
		HttpResponse res = httpclient.execute(req);
		return EntityUtils.toByteArray(res.getEntity());
		
	}
	
	public byte[] httpPut(String url, HashMap<String, String> params, HashMap<String, String> headers, HttpHost proxy, AbstractHttpEntity entity) throws IOException {
		HttpClient httpclient = new DefaultHttpClient();
		
		if (proxy != null) {
			httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
		}
		
		HttpPut req = new HttpPut(url);
		if (params != null && params.size() > 0) {
			HttpParams httpparams = new BasicHttpParams();
			for (Entry<String, String> entry : params.entrySet()) {
				httpparams.setParameter(entry.getKey(), entry.getValue());
			}
			req.setParams(httpparams);
		}
		
		if (headers != null) {
			for (Entry<String, String> entry : headers.entrySet()) {
				req.addHeader(entry.getKey(), entry.getValue());
			}
		}
		
		if (entity != null) {
			req.setEntity(entity);
		}
		
		HttpResponse res = httpclient.execute(req);
		return EntityUtils.toByteArray(res.getEntity());
	}
}
