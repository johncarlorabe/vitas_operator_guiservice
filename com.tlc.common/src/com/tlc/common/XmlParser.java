package com.tlc.common;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Hashtable;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.tlc.common.Logger;

public class XmlParser {
	
	private HashMap<String, String> _table = new HashMap<String, String>();;
	
	DefaultHandler handler = new DefaultHandler(){
		
		String tagname=null;
		public void startElement(String uri, String localName,String qName, 
	               Attributes attributes) throws SAXException {
		
			tagname=qName.toLowerCase();
		}
	
		public void endElement(String uri, String localName,
			String qName) throws SAXException {	
		}
		
	
		public void characters(char ch[], int start, int length) throws SAXException {
			
			if((new String(ch,start,length)).trim().length() != 0){
				_table.put(tagname, new String(ch,start,length).trim());
			}
		}
	};
	
	public XmlParser(String xml){
		try {
			
			InputSource in = null;
			in = new InputSource(new StringReader(xml));
			
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			saxParser.parse(in, handler);		
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Logger.LogServer(e);
		}
	}
	
	public String get(String key){
		try{
			
			return _table.get(key).toString();
		
		}catch(Exception e){
			return null;
		}
	}
	
	public void empty(){
		_table.clear();
	}
}
