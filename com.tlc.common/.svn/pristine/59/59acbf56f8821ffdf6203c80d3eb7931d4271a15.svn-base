package com.tlc.common;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Vector;


public abstract class AbstractGenericPool<T>  implements Runnable, Closeable {
	private Vector<T> PoolAvailable = null;
	private Vector<T> PoolUsed = null;
	private int MinimumPoolSize = 20;     //minimum number in PoolAvailable
	private int MaximumPoolSize = 100;    //Maximum number in PoolUsed
	private int CleanTime = 5000 * 1;    //Cleaning Time Thread
//	private int InactiveTime = 20000 * 1; //Inactive Time
	private Thread Cleaner = null;
	private boolean shutdown = false;

	public AbstractGenericPool(){
		PoolAvailable = new Vector<T>();
		PoolUsed      = new Vector<T>();
		Cleaner       = new Thread(this); 
		Cleaner.start();
	}
	
	public AbstractGenericPool(int min, int max){
		PoolAvailable = new Vector<T>();
		PoolUsed      = new Vector<T>();
		MinimumPoolSize = min;
		MaximumPoolSize = max;
		Cleaner       = new Thread(this); 
		Cleaner.start();
	}
	
	public AbstractGenericPool(int min, int max, int cleanTime, int inactiveTime){
		PoolAvailable   = new Vector<T>();
		PoolUsed        = new Vector<T>();
		MinimumPoolSize = min;
		MaximumPoolSize = max;
		CleanTime       = cleanTime;
//		InactiveTime    = inactiveTime;
		Cleaner         = new Thread(this); 
		Cleaner.start();
	}
	
	protected abstract T getInstance() throws Exception;
	
	@Override
	public void run() {
		//Cleaner Thread
		try{
			while(true){
				synchronized(this){
					this.wait(CleanTime);
					if(shutdown){
						break;
					}
				}
				synchronized(PoolAvailable){
					T obj = null;
					while(PoolAvailable.size() > MinimumPoolSize){
						obj = PoolAvailable.firstElement();
						PoolAvailable.remove(obj);
						try{
							Method m = obj.getClass().getDeclaredMethod("close");
							if(m != null){
								m.setAccessible(true);
								m.invoke(obj);
							}
						} catch (NoSuchMethodException e){
							
						}
					}
					try{
						if(PoolUsed.size() >= MaximumPoolSize)
							Logger.LogServer("Warning! Maximum Pool Size reached of " + this.getClass().getName() +  " in Used = " + PoolUsed.size());
					}catch(Exception e1){
						Logger.LogServer(e1);
					}
				}
			}
		}catch(Exception e){
			Logger.LogServer(e);
		}finally{
			synchronized(PoolAvailable){
				for(T obj : PoolAvailable){
					try {
						PoolAvailable.remove(obj);
						Method m = obj.getClass().getMethod("close");
						m.setAccessible(true);
						if(m != null) m.invoke(obj);
					} catch (NoSuchMethodException e){
					} catch (Exception e) {
						Logger.LogServer(e);
					}
				}
				for(T obj : PoolUsed){
					try {
						PoolUsed.remove(obj);
						Method m = obj.getClass().getMethod("close");
						m.setAccessible(true);
						if(m != null) m.invoke(obj);
					} catch (NoSuchMethodException e){
					} catch (Exception e) {
						Logger.LogServer(e);
					}
				}
			}
		}
	}

	@Override
	public void close() throws IOException {
		shutdown = true;
		this.notify();
	}
	
	protected T checkOut(){
		T obj = null;
		for(int i = 0; i < 5; i++){
			synchronized(PoolAvailable){
				// Logger.LogServer(PoolAvailable.size() + ":"  + PoolUsed.size());
				if(PoolAvailable.size() == 0 && PoolUsed.size() < MaximumPoolSize){
					try {
						obj = getInstance();
						PoolUsed.add(obj);
					} catch (Exception e) {
						Logger.LogServer( i + "", e);
					}
				}else{
					while(PoolUsed.size() >= MaximumPoolSize){
						try {
							PoolAvailable.wait();
						} catch (InterruptedException e1) {
							Logger.LogServer(e1);
						}
					}
					obj = PoolAvailable.firstElement();
					PoolAvailable.remove(obj);
					try {
						Method m = obj.getClass().getMethod("isClosed");
						m.setAccessible(true);
						if(m != null && (Boolean)(m.invoke(obj))) continue;
						PoolUsed.add(obj);
					} catch (NoSuchMethodException e){
						PoolUsed.add(obj);
					} catch (Exception e) {
						Logger.LogServer(e);
						try{
							Method m = obj.getClass().getMethod("close");
							m.setAccessible(true);
							if(m != null) m.invoke(obj);
						} catch (Exception e1) {
							Logger.LogServer(e1);
						}
					}
				}
			}
			if(obj != null) break;
		}
		return obj;
	}
	
	protected void checkIn(T obj){
		if(obj != null) {
			synchronized(PoolAvailable){
				try {
					Method m = obj.getClass().getMethod("isClosed");
					m.setAccessible(true);
					if(m == null || !(Boolean)(m.invoke(obj)))
						PoolAvailable.add(obj);
						PoolAvailable.notify();
				} catch (NoSuchMethodException e){
					PoolAvailable.add(obj);
					PoolAvailable.notify();
				} catch (Exception e) {
					Logger.LogServer(e);
					try{
						Method m = obj.getClass().getMethod("close");
						m.setAccessible(true);
						if(m != null) m.invoke(obj);
					} catch (Exception e1) {
						Logger.LogServer(e1);
					}
				}finally{
					PoolUsed.remove(obj);
				}
			}
		}
	}
	
}
