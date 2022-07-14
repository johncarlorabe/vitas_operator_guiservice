package com.psi.reports.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.DbWrapper;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

public class ReversalCollection extends ModelCollection
{
  protected String branch;
  protected String datefrom;
  protected String dateto;

  public boolean hasRows()
  {
    DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLREVERSALTRANSACTION WHERE TO_CHAR(TIMESTAMP,'YYYY-MM-DD') BETWEEN ? AND ?", new Object[] { this.datefrom, this.dateto });

    if (!r.isEmpty())
    {
      for (DataRow row : r) {
        ReportItem m = new ReportItem();
        for (String key : row.keySet()) {
          m.setProperty(key, row.getString(key).toString());
        }
        m.setProperty("AMOUNT", row.getString("AMOUNT") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("AMOUNT").toString())));
        m.setProperty("DESTBALANCEBEFORE", row.getString("DESTBALANCEBEFORE") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("DESTBALANCEBEFORE").toString())));
        m.setProperty("DESTBALANCEAFTER", row.getString("DESTBALANCEAFTER") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("DESTBALANCEAFTER").toString())));
        m.setProperty("SOURCEBALANCEBEFORE", row.getString("SOURCEBALANCEBEFORE") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("SOURCEBALANCEBEFORE").toString())));
        m.setProperty("SOURCEBALANCEAFTER", row.getString("SOURCEBALANCEAFTER") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("SOURCEBALANCEAFTER").toString())));

        add(m);
      }
    }
    return r.size() > 0;
  }

  public boolean getPrepaidCol() {
    DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLREVERSALTRANSACTION WHERE TOACCOUNT=? AND TO_CHAR(TIMESTAMP,'YYYY-MM-DD') BETWEEN ? AND ?", new Object[] { this.branch, this.datefrom, this.dateto });

    if (!r.isEmpty())
    {
      for (DataRow row : r) {
        ReportItem m = new ReportItem();
        for (String key : row.keySet()) {
          m.setProperty(key, row.getString(key).toString());
        }
        m.setProperty("AMOUNT", row.getString("AMOUNT") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("AMOUNT").toString())));
        m.setProperty("DESTBALANCEBEFORE", row.getString("DESTBALANCEBEFORE") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("DESTBALANCEBEFORE").toString())));
        m.setProperty("DESTBALANCEAFTER", row.getString("DESTBALANCEAFTER") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("DESTBALANCEAFTER").toString())));
        m.setProperty("SOURCEBALANCEBEFORE", row.getString("SOURCEBALANCEBEFORE") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("SOURCEBALANCEBEFORE").toString())));
        m.setProperty("SOURCEBALANCEAFTER", row.getString("SOURCEBALANCEAFTER") == null ? "" : LongUtil.toString(Long.parseLong(row.getString("SOURCEBALANCEAFTER").toString())));

        add(m);
      }
    }
    return r.size() > 0;
  }

  public String getBranch()
  {
    return this.branch;
  }
  public void setBranch(String branch) {
    this.branch = branch;
  }
  public String getDatefrom() {
    return this.datefrom;
  }
  public void setDatefrom(String datefrom) {
    this.datefrom = datefrom;
  }
  public String getDateto() {
    return this.dateto;
  }
  public void setDateto(String dateto) {
    this.dateto = dateto;
  }
}