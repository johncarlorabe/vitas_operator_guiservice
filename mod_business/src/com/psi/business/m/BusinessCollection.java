package com.psi.business.m;

import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.SystemInfo;
import com.tlc.gui.modules.common.ModelCollection;
import com.tlc.gui.modules.common.ReportItem;

public class BusinessCollection
  extends ModelCollection {
  protected String accountnumber;
  
  public boolean hasRows() {
    DataRowCollection r = SystemInfo.getDb().QueryDataRows("SELECT ID, ACCOUNTNUMBER, BUSINESS, CONTACTNUMBER, ADDRESS, STATUS, CITY, PROVINCE, COUNTRY, XORDINATES, YORDINATES, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY,SATURDAY, SUNDAY, ZIPCODE, ISWITHHOLDINGTAX FROM TBLBUSINESS", new Object[0]);
    if (!r.isEmpty())
    {
      for (DataRow row : r) {
        ReportItem m = new ReportItem();
        
        DataRow compuser = SystemInfo.getDb().QueryDataRow("SELECT B.ID, B.ACCOUNTNUMBER, B.BUSINESS, B.CONTACTNUMBER, B.ADDRESS, B.STATUS, B.CITY, B.PROVINCE, B.COUNTRY, B.XORDINATES, B.YORDINATES, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY,SATURDAY, SUNDAY, B.ZIPCODE, B.ISWITHHOLDINGTAX, U.FIRSTNAME, U.LASTNAME, U.EMAIL, U.MSISDN, U.USERNAME FROM TBLBUSINESS B INNER JOIN TBLUSERS U ON U.ACCOUNTNUMBER = B.ACCOUNTNUMBER WHERE B.ACCOUNTNUMBER=? AND U.USERSLEVEL='COMPANY'", new Object[] { row.getString("ACCOUNTNUMBER") });
        
        if (!compuser.isEmpty()) {
          
          m.setProperty("Id", (row.getString("ID") == null) ? "" : row.getString("ID").toString());
          m.setProperty("AccountNumber", (row.getString("ACCOUNTNUMBER") == null) ? "" : row.getString("ACCOUNTNUMBER").toString());
          m.setProperty("Branch", (row.getString("BUSINESS") == null) ? "" : row.getString("BUSINESS").toString());
          m.setProperty("ContactNumber", (row.getString("CONTACTNUMBER") == null) ? "" : row.getString("CONTACTNUMBER").toString());
          m.setProperty("Location", (row.getString("ADDRESS") == null) ? "" : row.getString("ADDRESS").toString());
          m.setProperty("Status", (row.getString("STATUS") == null) ? "" : row.getString("STATUS").toString());
          m.setProperty("City", (row.getString("CITY") == null) ? "" : row.getString("CITY").toString());
          m.setProperty("Province", (row.getString("PROVINCE") == null) ? "" : row.getString("PROVINCE").toString());
          m.setProperty("Country", (row.getString("COUNTRY") == null) ? "" : row.getString("COUNTRY").toString());
          m.setProperty("XOrdinate", (row.getString("XORDINATES") == null) ? "" : row.getString("XORDINATES").toString());
          m.setProperty("YOrdinate", (row.getString("YORDINATES") == null) ? "" : row.getString("YORDINATES").toString());
          m.setProperty("Monday", (row.getString("MONDAY") == null) ? "" : row.getString("MONDAY").toString());
          m.setProperty("Tuesday", (row.getString("TUESDAY") == null) ? "" : row.getString("TUESDAY").toString());
          m.setProperty("Wednesday", (row.getString("WEDNESDAY") == null) ? "" : row.getString("WEDNESDAY").toString());
          m.setProperty("Thursday", (row.getString("THURSDAY") == null) ? "" : row.getString("THURSDAY").toString());
          m.setProperty("Friday", (row.getString("FRIDAY") == null) ? "" : row.getString("FRIDAY").toString());
          m.setProperty("Saturday", (row.getString("SATURDAY") == null) ? "" : row.getString("SATURDAY").toString());
          m.setProperty("Sunday", (row.getString("SUNDAY") == null) ? "" : row.getString("SUNDAY").toString());
          m.setProperty("ZipCode", (row.getString("ZIPCODE") == null) ? "" : row.getString("ZIPCODE").toString());
          m.setProperty("IsWithHoldingTax", (row.getString("ISWITHHOLDINGTAX") == null) ? "" : row.getString("ISWITHHOLDINGTAX").toString());
          m.setProperty("FirstName", (compuser.getString("FIRSTNAME") == null) ? "" : compuser.getString("FIRSTNAME").toString());
          m.setProperty("LastName", (compuser.getString("LASTNAME") == null) ? "" : compuser.getString("LASTNAME").toString());
          m.setProperty("Email", (compuser.getString("EMAIL") == null) ? "" : compuser.getString("EMAIL").toString());
          m.setProperty("Msisdn", (compuser.getString("MSISDN") == null) ? "" : compuser.getString("MSISDN").toString());
          m.setProperty("Username", (compuser.getString("USERNAME") == null) ? "" : compuser.getString("USERNAME").toString());
          m.setProperty("CompanyUserStatus", "YES");
        } else {
          
          m.setProperty("Id", (row.getString("ID") == null) ? "" : row.getString("ID").toString());
          m.setProperty("AccountNumber", (row.getString("ACCOUNTNUMBER") == null) ? "" : row.getString("ACCOUNTNUMBER").toString());
          m.setProperty("Branch", (row.getString("BUSINESS") == null) ? "" : row.getString("BUSINESS").toString());
          m.setProperty("ContactNumber", (row.getString("CONTACTNUMBER") == null) ? "" : row.getString("CONTACTNUMBER").toString());
          m.setProperty("Location", (row.getString("ADDRESS") == null) ? "" : row.getString("ADDRESS").toString());
          m.setProperty("Status", (row.getString("STATUS") == null) ? "" : row.getString("STATUS").toString());
          m.setProperty("City", (row.getString("CITY") == null) ? "" : row.getString("CITY").toString());
          m.setProperty("Province", (row.getString("PROVINCE") == null) ? "" : row.getString("PROVINCE").toString());
          m.setProperty("Country", (row.getString("COUNTRY") == null) ? "" : row.getString("COUNTRY").toString());
          m.setProperty("XOrdinate", (row.getString("XORDINATES") == null) ? "" : row.getString("XORDINATES").toString());
          m.setProperty("YOrdinate", (row.getString("YORDINATES") == null) ? "" : row.getString("YORDINATES").toString());
          m.setProperty("Monday", (row.getString("MONDAY") == null) ? "" : row.getString("MONDAY").toString());
          m.setProperty("Tuesday", (row.getString("TUESDAY") == null) ? "" : row.getString("TUESDAY").toString());
          m.setProperty("Wednesday", (row.getString("WEDNESDAY") == null) ? "" : row.getString("WEDNESDAY").toString());
          m.setProperty("Thursday", (row.getString("THURSDAY") == null) ? "" : row.getString("THURSDAY").toString());
          m.setProperty("Friday", (row.getString("FRIDAY") == null) ? "" : row.getString("FRIDAY").toString());
          m.setProperty("Saturday", (row.getString("SATURDAY") == null) ? "" : row.getString("SATURDAY").toString());
          m.setProperty("Sunday", (row.getString("SUNDAY") == null) ? "" : row.getString("SUNDAY").toString());
          m.setProperty("ZipCode", (row.getString("ZIPCODE") == null) ? "" : row.getString("ZIPCODE").toString());
          m.setProperty("IsWithHoldingTax", (row.getString("ISWITHHOLDINGTAX") == null) ? "" : row.getString("ISWITHHOLDINGTAX").toString());
          m.setProperty("FirstName", "N/A");
          m.setProperty("LastName", "N/A");
          m.setProperty("Email", "N/A");
          m.setProperty("Msisdn", "N/A");
          m.setProperty("Username", "N/A");
          m.setProperty("CompanyUserStatus", "NO");
        } 
        
        add(m);
      } 
    }
    return (r.size() > 0);
  }
  
  public String getAccountnumber() { return this.accountnumber; }

  
  public void setAccountnumber(String accountnumber) { this.accountnumber = accountnumber; }
}
