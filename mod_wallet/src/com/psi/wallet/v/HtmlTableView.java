
package com.psi.wallet.v;

import org.json.simple.JSONObject;

import com.tlc.gui.modules.common.Model;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;
import com.tlc.gui.modules.common.ReportView;

public class HtmlTableView extends ReportView {

	public HtmlTableView(String code, ModelCollection data) {
		super(code, data);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String render() {
		// TODO Auto-generated method stub
		
		StringBuilder tbl = new StringBuilder();

		
		
		tbl.append("<table class=\"dtreports display\" style=\"width:100%\">");
		tbl.append("<thead><tr class=\"ui-widget-header\">");
		
		
	
		Model h = this.rows.get(0);
		
		ReportItem itm = (ReportItem)h;
		for(String key: itm.keys()){

			tbl.append("<th>").append(key).append("</th>");
		}
		tbl.append("</tr></thead>");
		tbl.append("<tbody>");
		for(int x = 0;x<this.rows.size();x++){
			tbl.append("<tr>");
			Model model=this.rows.get(x);
			for(String keys : model.keys()){
		
				tbl.append("<td>").append(this.rows.get(x).getObject(keys)).append("</td>");
			}
			
			tbl.append("</tr>");
			
		}
	
		tbl.append("</tbody>");
		tbl.append("</table>");
		
		JSONObject obj = new JSONObject();
		obj.put("Message","Successfully Fetched Data");
		obj.put("Code",this.getCode().toString());
		obj.put("Data",tbl.toString());
		
				
		return obj.toString();
		
		
	}

}
