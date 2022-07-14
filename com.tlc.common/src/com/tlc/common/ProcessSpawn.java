package com.tlc.common;

import java.io.File;

public 	class ProcessSpawn implements Runnable{
	Class<?> klass;
	int id;
	
	public ProcessSpawn(Class<?> c, int id){
		klass = c;
		this.id = id;
	}
	
	@Override
	public void run() {
		while(true){
			ProcessBuilder selfLauncher = new ProcessBuilder("java", "-Djava.library.path=" + System.getProperty("java.library.path"), "-jar", (new File(System.getProperty("java.class.path").split(":")[0])).getAbsolutePath(), String.valueOf(id) );
			Process p = null;
			try {
				Logger.LogServer("Respawn:" + klass.getName() + " " + id);
				p = selfLauncher.start();
				p.waitFor();
				
			} catch (InterruptedException e){
				Logger.LogServer(e);
				return;
			} catch (Exception e) {
				Logger.LogServer(e);
			}finally{
				if(p != null)p.destroy();
			}
			Util.sleep(5000);
		}
	}
}
