package com.tlc.gui.core.loader;

import java.util.Hashtable;

import com.tlc.gui.modules.common.UICommand;

public class CommandMap {
	
	  private static Hashtable<String, Class<?>> map = new Hashtable();

	  public static void put(String key, Class<?> value) { map.put(key, value); }

	  public static UICommand newInstance(String key)
	  {
	    try {
	      return (UICommand)((Class)map.get(key)).newInstance(); } catch (Exception e) {
	    }
	    return null;
	  }

	  public static boolean containsKey(String key)
	  {
	    return map.containsKey(key);
	  }

	  public static int size() {
	    return map.size();
	  }
}
