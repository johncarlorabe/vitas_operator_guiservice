package com.tlc.keymgt;



import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

import com.tlc.common.Logger;
import com.tlc.common.Util;

public class KeyLoader {
	private static URLClassLoader loader = null;
	static{
		try{
			File[] pluginfiles = (new File(Util.getWorkingDirectory() + "/keys/")).listFiles(new FileFilter(){
				@Override
				public boolean accept(File paramFile) {
					return paramFile.isFile() && paramFile.getName().matches("(?i).*\\.jar$");
				}});
			ArrayList<URL> pluginurls = new ArrayList<URL>();
			for(File f: pluginfiles){
				pluginurls.add(f.toURI().toURL());
			}
			if(pluginurls.size() > 0){
				try{
					loader = new URLClassLoader(pluginurls.toArray(new URL[pluginurls.size()]),Thread.currentThread().getContextClassLoader());
					Thread.currentThread().setContextClassLoader(loader);
				}catch(Exception e){
					Logger.LogServer(e);
				}
			}
		}catch(Exception e){
			Logger.LogServer(e);
		}
	}
	
	public static Class<?> loadClass(String classname){
		Class<?> clazz = null;
		synchronized(loader){
			if(loader == null) return null;
			try{
				
				clazz = loader.loadClass(classname);
			} catch (Exception e) {
				Logger.LogServer("Warning Cannot find ~" + classname);
			}
		}
		return clazz;
	}
	
	public static void dispose(){
		try {
			loader.close();
			loader = null;
		} catch (IOException e) {
		}
		
	}
}
