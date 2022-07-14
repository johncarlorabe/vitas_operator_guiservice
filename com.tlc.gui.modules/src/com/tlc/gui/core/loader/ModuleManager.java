package com.tlc.gui.core.loader;

import com.tlc.common.Logger;
import com.tlc.common.Util;
import com.tlc.gui.modules.common.UICommand;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

public class ModuleManager {
	private static URLClassLoader loader = null;

	  static {
	    try { File[] pluginfiles = new File(Util.getWorkingDirectory() + "/plugins/").listFiles(new FileFilter()
	      {
	        public boolean accept(File paramFile) {
	          Logger.LogServer(paramFile.getName() + " is  accepted = " + paramFile.getName().matches("(?i).*\\.jar$"));
	          return (paramFile.isFile()) && (paramFile.getName().matches("(?i).*\\.jar$"));
	        }
	      });
	      ArrayList pluginurls = new ArrayList();
	      File[] arrayOfFile1 = pluginfiles; int j = pluginfiles.length; for (int i = 0; i < j; i++) { File f = arrayOfFile1[i];
	        pluginurls.add(f.toURI().toURL());
	      }
	      if (pluginurls.size() > 0)
	        try {
	          loader = new URLClassLoader((URL[])pluginurls.toArray(new URL[pluginurls.size()]), Thread.currentThread().getContextClassLoader());
	          Thread.currentThread().setContextClassLoader(loader);
	          Properties p = new Properties();
	          p.load(new FileInputStream(new File(Util.getWorkingDirectory() + "/modules.properties")));
	          for (Iterator localIterator = p.keySet().iterator(); localIterator.hasNext(); ) { Object key = localIterator.next();
	            Class c = loader.loadClass(p.getProperty(key.toString()));
	            if (UICommand.class.isAssignableFrom(c))
	              CommandMap.put(key.toString(), c); }
	        }
	        catch (Exception e)
	        {
	          Logger.LogServer(e);
	        }
	    } catch (Exception e)
	    {
	      Logger.LogServer(e);
	    }
	  }

	  public static boolean initalized() {
	    return CommandMap.size() > 0;
	  }
}
