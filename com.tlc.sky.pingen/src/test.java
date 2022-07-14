import org.json.simple.JSONArray;

import com.tlc.gui.modules.session.UIGroup;
import com.tlc.sky.pingen.arch.beans.PingenGroup;


public class test {

	

	public static void main(String[] args) {
		
		int id = 23;
		Integer[] arr = {1,2,3,4};

		StringBuilder b = new StringBuilder();
		String modules = "INSERT INTO TBLGROUPMODULES (MODULEID, GROUPID) VALUES";
	
		
		PingenGroup g = new PingenGroup(null);
		g.setModules(arr);
		JSONArray jsonarr = new JSONArray();


		
			for(Integer m : g.getModules()){
				jsonarr.add(m);
			}
				

			for(int i = 0; i < jsonarr.size() ; i++){
				b.append(modules);
				b.append("("+arr[i]+","+id+")");
				b.append(";\n");
			}
		
			
			 String sql = "BEGIN\n"
					    + "UPDATE TBLUSERSLEVEL SET SESSIONTIMEOUT = ?, PASSWORDEXPIRY = ?, MINPASSWORD = ?, PASSWORDHISTORY = ?,  SEARCHRANGE = ? WHERE USERSLEVEL = ?;\n"
					    + "DELETE FROM TBLGROUPMODULES WHERE GROUPID = ?;\n"
					    + b
					    + "END;\n";
			
				System.out.println(sql.toString());
		
			
		

	}

}
