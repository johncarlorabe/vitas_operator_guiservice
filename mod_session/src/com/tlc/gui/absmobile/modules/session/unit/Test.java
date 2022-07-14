package com.tlc.gui.absmobile.modules.session.unit;


import com.psi.ibc.common.HashFunction;
import com.tlc.common.StringUtil;
import com.tlc.gui.absmobile.modules.session.c.LoginCommand;
import com.tlc.gui.absmobile.modules.session.c.MonitorCommand;
import com.tlc.gui.absmobile.modules.session.m.MACBinding;
import com.tlc.gui.modules.common.PluginHeaders;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.session.Token;



public class Test {

	public static void main(String[] args){
		macbinding();
	}
	
	public static void login(){
		Token t= new Token();
		LoginCommand c = new LoginCommand();
		PluginHeaders h = new PluginHeaders();
		h.put("Authorization", StringUtil.base64Encode("ABSCBN:Jm19970801".getBytes()));
		h.put("Session", StringUtil.base64Encode("dGVsY29tbGl2ZWNvbnRlbnRpbmMu@127.0.0.1".getBytes()));
		c.setHeaders(h);
		IView v = c.execute();
		System.out.println(v.render());
	}
	
	public static void monitor(){
		String auth = "QUJTQ0JOOmRHVnNZMjl0YkdsMlpXTnZiblJsYm5ScGJtTXU6ck8wQUJYTnlBQ0ZqYjIwdWRHeGpMbWQxYVM1dGIyUjFiR1Z6TG5ObGMzTnBiMjR1Vkc5clpXNnoyK1NBQnNBOGxnSUFCVWtBQmxWelpYSkpaRXdBRGtWNGNHbHlZWFJwYjI1RVlYUmxkQUFRVEdwaGRtRXZkWFJwYkM5RVlYUmxPMHdBQ1Vsd1FXUmtjbVZ6YzNRQUVreHFZWFpoTDJ4aGJtY3ZVM1J5YVc1bk8wd0FDVk5sYzNOcGIyNUpSSEVBZmdBQ1RBQUlWWE5sY201aGJXVnhBSDRBQW5od0FBQUF6M055QUE1cVlYWmhMblYwYVd3dVJHRjBaV2hxZ1FGTFdYUVpBd0FBZUhCM0NBQUFBVWljeitMTmVIUUFDVEV5Tnk0d0xqQXVNWFFBSEdSSFZuTlpNamwwWWtkc01scFhUblppYmxKc1ltNVNjR0p0VFhWMEFBWkJRbE5EUWs0PUAxMjcuMC4wLjE=";
		PluginHeaders h = new PluginHeaders();
		MonitorCommand cmd = new MonitorCommand();
		h.put("authorization", auth);
		cmd.setHeaders(h);
		IView v = cmd.execute();
		System.out.println(v.render());
	}
	
	public static void macbinding(){
		try {
			com.psi.ibc.common.SessionToken session = new com.psi.ibc.common.SessionToken()
					.parse("rO0ABXNyAB9jb20ucHNpLmliYy5jb21tb24uU2Vzc2lvblRva2VuAAAAAAAAAAECAANMAA1hY2NvdW50TnVtYmVydAASTGphdmEvbGFuZy9TdHJpbmc7TAAOZXhwaXJhdGlvbkRhdGV0ABBMamF2YS91dGlsL0RhdGU7TAAHc2Vzc2lvbnEAfgABeHB0AAw4MzQ1ODk3NzcxMjJzcgAOamF2YS51dGlsLkRhdGVoaoEBS1l0GQMAAHhwdwgAAAFZ2NpfZnh0ABRPVEUzTURFeU5qQXdNREF3TURBPQ==");
			if(session==null ){
				System.out.println("walang laman session");
				
			}
			System.out.println("session"+session);
			String hashKey = HashFunction.hashSHA256("rO0ABXNyAB9jb20ucHNpLmliYy5jb21tb24uU2Vzc2lvblRva2VuAAAAAAAAAAECAANMAA1hY2NvdW50TnVtYmVydAASTGphdmEvbGFuZy9TdHJpbmc7TAAOZXhwaXJhdGlvbkRhdGV0ABBMamF2YS91dGlsL0RhdGU7TAAHc2Vzc2lvbnEAfgABeHB0AAw4MzQ1ODk3NzcxMjJzcgAOamF2YS51dGlsLkRhdGVoaoEBS1l0GQMAAHhwdwgAAAFZ2NpfZnh0ABRPVEUzTURFeU5qQXdNREF3TURBPQ==");
			String decryptedToken = com.psi.ibc.common.Token.doAESDecryption(hashKey, "kkPjfT2VQMJxhFAyiyjjdDgsiwNcBzNhvQsDyba+qWpJXFsSch3Jd/DWWXuZCnw/eCLr3CviaLyyY6armUMgmaWEsm+bC+pp4NQx4o0JU2M=");
			System.out.println("dddd"+decryptedToken);
			System.out.println("hash:"+hashKey);
			if(StringUtil.isNullOrEmpty(decryptedToken )){
				System.out.println("walang laman token");
				
			}
			MACBinding macBinding = new MACBinding(
					session.getAccountNumber(), decryptedToken,"test");
			System.out.println(session.getAccountNumber());
				
//			if (this.isExpired(session, MAX_TIME_IN_HOURS)) {
//				// session is expired
//				Logger.LogServer("Pasok expired");
//				this.setState(new ObjectState("TLC-3901-12"));
//				started=false;
//			}
			if (!macBinding.exist()) {
					// ip/mac not exist...
				System.out.println("not binding");
			} 

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
