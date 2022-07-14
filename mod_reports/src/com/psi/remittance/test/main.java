package com.psi.remittance.test;

import com.psi.reports.c.SummaryCollectionCommand;
import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.PluginHeaders;

public class main {

	public static void main(String[] args) {
		//test();
		String rr = "";
		DataRowCollection row = SystemInfo.getDb().QueryDataRows("SELECT ACCOUNTNUMBER FROM TBLBRANCHES WHERE STATUS=1 AND KEYACCOUNT = ?", "181234591346");
		if(!row.isEmpty()){
			for(DataRow r:row){
				rr += "'"+r.getString("ACCOUNTNUMBER")+"',";
			}
		}
		
		StringBuilder query = new StringBuilder("BEGIN\n");
		query.append("UPDATE TBLBUSINESS SET STATUS=1 WHERE ACCOUNTNUMBER=?; \n");
		query.append("UPDATE TBLBRANCHES SET STATUS=1 WHERE KEYACCOUNT=? AND STATUS = 0; \n");
		query.append("UPDATE TBLUSERS SET LOCKED = 'NO',STATUS='ACTIVE' WHERE ACCOUNTNUMBER IN ("+rr+"''"+"); \n");
		query.append("UPDATE TBLPOSUSERS SET PASSWORD='123456789' WHERE ACCOUNTNUMBER IN ("+rr+"''"+"); \n");
		query.append("COMMIT;\nEXCEPTION WHEN OTHERS THEN\n	ROLLBACK;\n RAISE;\nEND;");
		SystemInfo.getDb().QueryUpdate(query.toString(),"181234591346","181234591346");	
		System.out.println(rr+"''");
		 
	}

	public static void test() {
		System.out.println("START");
		String data = "{\"DateFrom\":\"2018-01-14\",\"DateTo\":\"2018-01-22\",\"AccountNumber\":\"226123459149\"}";
		JsonRequest json = new JsonRequest(data);

		String auth = "VExDLlNIRVJXSU46OXZwaWRkY2Y0bnI3MTdzcDhyMDEyNTQzbTU6ck8wQUJYTnlBQ0ZqYjIwdWRHeGpMbWQxYVM1dGIyUjFiR1Z6TG5ObGMzTnBiMjR1Vkc5clpXNnoyK1NBQnNBOGxnSUFCVWtBQmxWelpYSkpaRXdBRGtWNGNHbHlZWFJwYjI1RVlYUmxkQUFRVEdwaGRtRXZkWFJwYkM5RVlYUmxPMHdBQ1Vsd1FXUmtjbVZ6YzNRQUVreHFZWFpoTDJ4aGJtY3ZVM1J5YVc1bk8wd0FDVk5sYzNOcGIyNUpSSEVBZmdBQ1RBQUlWWE5sY201aGJXVnhBSDRBQW5od0FBQUFBWE55QUE1cVlYWmhMblYwYVd3dVJHRjBaV2hxZ1FGTFdYUVpBd0FBZUhCM0NBQUFBVXNFQjl5RmVIUUFDVEV5Tnk0d0xqQXVNWFFBR2psMmNHbGtaR05tTkc1eU56RTNjM0E0Y2pBeE1qVTBNMjAxZEFBTFZFeERMbE5JUlZKWFNVND1AMTI3LjAuMC4x";

		PluginHeaders h = new PluginHeaders();

		SummaryCollectionCommand pndgmessage = new SummaryCollectionCommand();
		h.put("authorization", auth);
		pndgmessage.setRequest(json);
		pndgmessage.setHeaders(h);

		IView v = pndgmessage.execute();
		System.out.println(v.render());
		System.out.println("END");
	}

}
