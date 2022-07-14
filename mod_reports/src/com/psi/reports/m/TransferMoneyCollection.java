package com.psi.reports.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.LongUtil;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;


public class TransferMoneyCollection
  extends ModelCollection
{
  protected String branch;
  protected String datefrom;
  protected String dateto;
  
  public boolean hasRows() {
    DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLTRANSACTIONSTRANSFERMONEY WHERE TO_CHAR(TIMESTAMP,'YYYY-MM-DD') BETWEEN ? AND ?", new Object[] { this.datefrom, this.dateto });
    
    if (!r.isEmpty())
    {
      for (DataRow row : r) {
        ReportItem m = new ReportItem();
        m.setProperty("REFERENCEID", (row.getString("REFERENCEID") == null) ? "" : row.getString("REFERENCEID").toString());
        m.setProperty("TIMESTAMP", (row.getString("TIMESTAMP") == null) ? "" : row.getString("TIMESTAMP").toString());
        m.setProperty("MOBILENUMBER", (row.getString("MOBILENUMBER") == null) ? "" : row.getString("MOBILENUMBER").toString());
        m.setProperty("TYPE", (row.getString("TYPE") == null) ? "" : row.getString("TYPE").toString());
        m.setProperty("AMOUNT", (row.getString("AMOUNT") == null) ? "" : LongUtil.toString(Long.parseLong(row.getString("AMOUNT").toString())));
        m.setProperty("ACCOUNTNUMBER", (row.getString("ACCOUNTNUMBER") == null) ? "" : row.getString("ACCOUNTNUMBER").toString());
        m.setProperty("SOURCEBALANCEBEFORE", (row.getString("SOURCEBALANCEBEFORE") == null) ? "" : LongUtil.toString(Long.parseLong(row.getString("SOURCEBALANCEBEFORE").toString())));
        m.setProperty("SOURCEBALANCEAFTER", (row.getString("SOURCEBALANCEAFTER") == null) ? "" : LongUtil.toString(Long.parseLong(row.getString("SOURCEBALANCEAFTER").toString())));
        m.setProperty("BRANCH", (row.getString("BRANCH") == null) ? "" : row.getString("BRANCH").toString());
        m.setProperty("BRANCHCODE", (row.getString("BRANCHCODE") == null) ? "" : row.getString("BRANCHCODE").toString());
        m.setProperty("CASHIER", (row.getString("CASHIER") == null) ? "" : row.getString("CASHIER").toString());
        m.setProperty("BRAND", (row.getString("BRAND") == null) ? "" : row.getString("BRAND").toString());
        m.setProperty("PRODUCTNAME", (row.getString("PRODUCTNAME") == null) ? "" : row.getString("PRODUCTNAME").toString());
        m.setProperty("MESSAGE", (row.getString("MESSAGE") == null) ? "" : row.getString("MESSAGE").toString());
        m.setProperty("TPAID", (row.getString("TPAID") == null) ? "" : row.getString("TPAID").toString());
        m.setProperty("DISCOUNT", (row.getString("DISCOUNT") == null || row.getString("DISCOUNT") == "") ? "" : LongUtil.toString(Long.parseLong(row.getString("DISCOUNT").toString())));
        m.setProperty("CHARGES", (row.getString("CHARGES") == null || row.getString("CHARGES") == "") ? "" : LongUtil.toString(Long.parseLong(row.getString("CHARGES").toString())));
        add(m);
      } 
    }
    return (r.size() > 0);
  }
  
  public boolean getPrepaidCol() {
    DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT * FROM TBLTRANSACTIONSTRANSFERMONEY WHERE ACCOUNTNUMBER=? AND TO_CHAR(TIMESTAMP,'YYYY-MM-DD') BETWEEN ? AND ?", new Object[] { this.branch, this.datefrom, this.dateto });
    
    if (!r.isEmpty())
    {
      for (DataRow row : r) {
        ReportItem m = new ReportItem();
        m.setProperty("REFERENCEID", (row.getString("REFERENCEID") == null) ? "" : row.getString("REFERENCEID").toString());
        m.setProperty("TIMESTAMP", (row.getString("TIMESTAMP") == null) ? "" : row.getString("TIMESTAMP").toString());
        m.setProperty("MOBILENUMBER", (row.getString("MOBILENUMBER") == null) ? "" : row.getString("MOBILENUMBER").toString());
        m.setProperty("TYPE", (row.getString("TYPE") == null) ? "" : row.getString("TYPE").toString());
        m.setProperty("AMOUNT", (row.getString("AMOUNT") == null) ? "" : LongUtil.toString(Long.parseLong(row.getString("AMOUNT").toString())));
        m.setProperty("ACCOUNTNUMBER", (row.getString("ACCOUNTNUMBER") == null) ? "" : row.getString("ACCOUNTNUMBER").toString());
        m.setProperty("SOURCEBALANCEBEFORE", (row.getString("SOURCEBALANCEBEFORE") == null) ? "" : LongUtil.toString(Long.parseLong(row.getString("SOURCEBALANCEBEFORE").toString())));
        m.setProperty("SOURCEBALANCEAFTER", (row.getString("SOURCEBALANCEAFTER") == null) ? "" : LongUtil.toString(Long.parseLong(row.getString("SOURCEBALANCEAFTER").toString())));
        m.setProperty("BRANCH", (row.getString("BRANCH") == null) ? "" : row.getString("BRANCH").toString());
        m.setProperty("BRANCHCODE", (row.getString("BRANCHCODE") == null) ? "" : row.getString("BRANCHCODE").toString());
        m.setProperty("CASHIER", (row.getString("CASHIER") == null) ? "" : row.getString("CASHIER").toString());
        m.setProperty("BRAND", (row.getString("BRAND") == null) ? "" : row.getString("BRAND").toString());
        m.setProperty("PRODUCTNAME", (row.getString("PRODUCTNAME") == null) ? "" : row.getString("PRODUCTNAME").toString());
        m.setProperty("MESSAGE", (row.getString("MESSAGE") == null) ? "" : row.getString("MESSAGE").toString());
        m.setProperty("TPAID", (row.getString("TPAID") == null) ? "" : row.getString("TPAID").toString());
        m.setProperty("DISCOUNT", (row.getString("DISCOUNT") == null || row.getString("DISCOUNT") == "") ? "" : LongUtil.toString(Long.parseLong(row.getString("DISCOUNT").toString())));
        m.setProperty("CHARGES", (row.getString("CHARGES") == null || row.getString("CHARGES") == "") ? "" : LongUtil.toString(Long.parseLong(row.getString("CHARGES").toString())));
        add(m);
      } 
    }
    return (r.size() > 0);
  }


  
  public String getBranch() { return this.branch; }

  
  public void setBranch(String branch) { this.branch = branch; }

  
  public String getDatefrom() { return this.datefrom; }

  
  public void setDatefrom(String datefrom) { this.datefrom = datefrom; }

  
  public String getDateto() { return this.dateto; }

  
  public void setDateto(String dateto) { this.dateto = dateto; }
}
