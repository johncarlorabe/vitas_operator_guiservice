 package com.psi.business.test;
 
 import com.tlc.gui.modules.common.RequestParameters;
 import org.json.simple.JSONObject;
 import org.json.simple.parser.JSONParser;
 import org.json.simple.parser.ParseException;
 
 public class JsonRequest extends RequestParameters
 {
   JSONObject params = null;
 
   public JsonRequest(String json) { JSONParser p = new JSONParser();
     try {
       this.params = ((JSONObject)p.parse(json));
     }
     catch (ParseException localParseException)
     {
     } }
 
   public String get(String key)
   {
     return this.params.get(key).toString();
   }
 }
