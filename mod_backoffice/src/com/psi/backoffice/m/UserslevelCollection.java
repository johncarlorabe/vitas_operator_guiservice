package com.psi.backoffice.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;


public class UserslevelCollection
  extends ModelCollection
{
  public boolean hasRows() {
    DataRowCollection rows = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLUSERSLEVEL WHERE ACCOUNTSTATUS = 'ACTIVE' AND ISOPERATOR=0 ", new Object[0]);
    if (!rows.isEmpty())
    {
      for (DataRow row : rows) {
        ReportItem m = new ReportItem();
        for (String key : row.keySet()) {
          m.setProperty(key, row.getString(key).toString());
        }
        add(m);
      } 
    }
    return (rows.size() > 0);
  }
}
