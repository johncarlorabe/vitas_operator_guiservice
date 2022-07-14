
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.PluginHeaders;
import com.tlc.gui.modules.common.RequestParameters;
import com.tlc.sky.pingen.c.GroupDetailsCommand;
import com.tlc.sky.pingen.c.GroupModulesCommand;
import com.tlc.sky.pingen.c.ModuleCommand;
import com.tlc.sky.pingen.c.NewPingenCommand;
import com.tlc.sky.pingen.c.SearchGroupCommand;


public class main {
	
	
	public static void main(String[] args){
		
		
		regUser();
		
		
		
	}
	
	
	public static void regUser(){
		
		
	//String data="{\"GROUPID\":\"18\"}";
String data = "{\"PASSWORDEXPIRY\":\"90\",\"PASSWORDHISTORY\":\"4\",\"SEARCHRANGE\":\"30\",\"SESSIONTIMEOUT\":\"222\",\"MINPASSWORD\":\"7\",\"GROUPNAME\":\"ADMIN\",\"GROUPID\":\"18\",\"MODULE\":[[\"2400\",\"2410\",\"2420\",\"1120\",\"1130\"]]}}";
		
		//String data = "{\"Username\":\"aasdsa\",\"Firstname\":\"asdsad\",\"Lastname\":\"asdsad\",\"Msisdn\":\"09996906020\",\"Email\":\"sher@yahoo.com\",\"Department\":\"asdsa\",\"Userlevel\":\"asdsadas\",\"Status\":\"1\",\"Password\":\"123\"}";

		JsonRequest json  = new JsonRequest(data);
		
//String auth = "VExDLlNIRVJXSU46OXZwaWRkY2Y0bnI3MTdzcDhyMDEyNTQzbTU6ck8wQUJYTnlBQ0ZqYjIwdWRHeGpMbWQxYVM1dGIyUjFiR1Z6TG5ObGMzTnBiMjR1Vkc5clpXNnoyK1NBQnNBOGxnSUFCVWtBQmxWelpYSkpaRXdBRGtWNGNHbHlZWFJwYjI1RVlYUmxkQUFRVEdwaGRtRXZkWFJwYkM5RVlYUmxPMHdBQ1Vsd1FXUmtjbVZ6YzNRQUVreHFZWFpoTDJ4aGJtY3ZVM1J5YVc1bk8wd0FDVk5sYzNOcGIyNUpSSEVBZmdBQ1RBQUlWWE5sY201aGJXVnhBSDRBQW5od0FBQUFBWE55QUE1cVlYWmhMblYwYVd3dVJHRjBaV2hxZ1FGTFdYUVpBd0FBZUhCM0NBQUFBVXNFQjl5RmVIUUFDVEV5Tnk0d0xqQXVNWFFBR2psMmNHbGtaR05tTkc1eU56RTNjM0E0Y2pBeE1qVTBNMjAxZEFBTFZFeERMbE5JUlZKWFNVND1AMTI3LjAuMC4x";

		  PluginHeaders h = new PluginHeaders();
		
		GroupModulesCommand pndgmessage = new GroupModulesCommand();
		  //h.put("authorization", auth);
		pndgmessage.setRequest(json);
		  pndgmessage.setHeaders(h);
		  
		  IView v = pndgmessage.execute();
		  System.out.println(v.render());
		
	}
	
	
	
	
	

}
