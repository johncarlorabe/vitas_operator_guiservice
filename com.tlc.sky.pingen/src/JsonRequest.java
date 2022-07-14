

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.tlc.gui.modules.common.RequestParameters;

public class JsonRequest extends RequestParameters{
	
	JSONObject params = null;
	public JsonRequest(String json){
		JSONParser p = new JSONParser();
		try {
			params = (JSONObject) p.parse(json);
		} catch (ParseException e) {	
			
		}
	}
	@Override
	public String get(String key) {
		return (String) params.get(key).toString();
	}

}
