package com.tlc.gui.modules.session;

import com.tlc.common.StringUtil;
import com.tlc.gui.modules.common.PluginHeaders;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;



public  class Token implements Serializable {

	private static final long serialVersionUID = -5486540482500608874L;
	  public String SessionID = null;
	  public String IpAddress = null;
	  public String Username = null;
	  public int UserId = 0;
	  public Date ExpirationDate = null;

	  public String toString()
	  {
	    try
	    {
	      ByteArrayOutputStream out = new ByteArrayOutputStream();
	      ObjectOutputStream o = new ObjectOutputStream(out);
	      o.writeObject(this);
	      return StringUtil.base64Encode(out.toByteArray());
	    }
	    catch (IOException ex) {
	    }
	    return null;
	  }

	  public void extend(int seconds)
	  {
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(this.ExpirationDate);
	    cal.add(13, seconds);
	    this.ExpirationDate = cal.getTime();
	  }

	  public static Token parse(String v)
	  {
	    Token ret = new Token();
	    try
	    {
	      ByteArrayInputStream bInp = new ByteArrayInputStream(StringUtil.base64Decode(v));

	      ObjectInputStream o = new ObjectInputStream(bInp);
	      ret = (Token)o.readObject();
	    } catch (IOException e) {
	      return null;
	    } catch (ClassNotFoundException e) {
	      return null;
	    }

	    return ret;
	  }

	  public static Token _new(PluginHeaders h) {
	    if (h.containsKey("session")) {
	      Token t = new Token();
	      String sess = new String(StringUtil.base64Decode((String)h.get("session")));
	      String[] sessInfo = sess.split("@");
	      t.SessionID = sessInfo[0];
	      t.IpAddress = sessInfo[1];
	      t.ExpirationDate = new Date();
	      return t;
	    }
	    return null;
	  }

}
