package com.tlc.encryption;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

public class ZipFile {
	private static final String UTF8 = "UTF-8";
	private static Logger logger = Logger.getLogger(ZipFile.class);
	
	public static byte[] compress(String ... files){
		ByteArrayOutputStream      output          = null;
		ZipOutputStream               zos          = null;
		OutputStream               compout         = null;
		try{
			output          = new ByteArrayOutputStream();
			zos   = new ZipOutputStream(output);
			for(int i = 0; i+1 < files.length; i += 2){
				ZipEntry ze= new ZipEntry(files[i]);
				byte[] bytes = files[i+1].getBytes(UTF8);
				zos.putNextEntry(ze);
				zos.write(bytes);
				zos.closeEntry();
			}
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}finally{
			if(compout       != null) try {compout.close();      } catch (IOException e) {logger.error(e.getMessage(),e);}
			if(zos           != null) try {zos.close();} catch (IOException e) {logger.error(e.getMessage(),e);}
			if(output != null){
				try {
					output.close();
					return output.toByteArray();
				} catch (Exception e) {
					logger.error(e.getMessage(),e);
				}
			}
		}
		return null;
	}

	public static byte[] compress(HashMap<String,Object> files){
		ByteArrayOutputStream      output          = null;
		ZipOutputStream               zos          = null;
		OutputStream               compout         = null;
		try{
			output          = new ByteArrayOutputStream();
			zos   = new ZipOutputStream(output);
			for(Entry<String,Object> file : files.entrySet()){
				ZipEntry ze= new ZipEntry(file.getKey());
				zos.putNextEntry(ze);
				if(file.getValue() instanceof byte[])
					zos.write((byte[])file.getValue());
				else if(file.getValue() instanceof String)
					zos.write(((String)file.getValue()).getBytes("UTF-8"));
				zos.closeEntry();
			}
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}finally{
			if(compout       != null) try {compout.close();      } catch (IOException e) {logger.error(e.getMessage(),e);}
			if(zos           != null) try {zos.close();} catch (IOException e) {logger.error(e.getMessage(),e);}
			if(output != null){
				try {
					output.close();
					return output.toByteArray();
				} catch (Exception e) {
					logger.error(e.getMessage(),e);
				}
			}
		}
		return null;
	}

}
