package com.tlc.sftp;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystem;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.FileType;
import org.apache.commons.vfs2.FileUtil;
import org.apache.commons.vfs2.Selectors;
import org.apache.commons.vfs2.VFS;
import org.apache.commons.vfs2.provider.AbstractFileObject;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;

import com.jcraft.jsch.UserInfo;
import com.tlc.common.Logger;
import com.tlc.common.StringUtil;

public class SftpUtil {
	private FileSystemManager fsManager;
	private FileObject        lastaccessedfile;
	private URI               uri;
	private String            passphrase;
	private FileSystemOptions fsOptions = new FileSystemOptions();

	public SftpUtil(String uri, String passphrase){
		this.uri = URI.create(uri);
		this.passphrase = passphrase;
		init();
	}
	
	public SftpUtil(String hostname, int port, String username, String password, String path, String passphrase){
		if(StringUtil.isNullOrEmpty(path)) path = "";
		if(!path.startsWith("/")) path = "/" + path;
		this.uri = URI.create( "sftp://" 
		                    + (StringUtil.isNullOrEmpty(username)?"" : username 
		                    		                                 + (StringUtil.isNullOrEmpty(password)? "" : ":" + password) + "@") 
		                    + hostname + ":" + port + path);
		this.passphrase = passphrase;
		init();
	}
	
	private void init(){
		try {	
            this.fsManager = VFS.getManager();
            
        	ArrayList<File>   privateKeys = new ArrayList<File>();
			SftpFileSystemConfigBuilder optionbuilder = SftpFileSystemConfigBuilder.getInstance();
			
			File id_rsa = new File("id_rsa");
			if(id_rsa.exists())
				privateKeys.add(id_rsa);

			File id_dsa = new File("id_dsa");
			if(id_dsa.exists())
				privateKeys.add(id_dsa);
			
			if(privateKeys.size() > 0){
				optionbuilder.setIdentities(fsOptions, privateKeys.toArray(new File[privateKeys.size()]));
			}else{
				Logger.LogServer("Identity file id_rsa/id_dsa not found!");
			}
			
			File knownhost = new File("known_hosts"); 
			if(knownhost.exists()){
				optionbuilder.setKnownHosts(fsOptions, knownhost);
				optionbuilder.setStrictHostKeyChecking(fsOptions, "yes");
			}else{
				optionbuilder.setStrictHostKeyChecking(fsOptions, "no");
				Logger.LogServer("known_hosts file not found!");
			}
			

			UserInfo user = new UserInfo(){
				private String pass = passphrase;
				@Override
				public String getPassphrase() {
					return pass;
				}
				@Override
				public String getPassword() {
					return null;
				}
				@Override
				public boolean promptPassword(String message) {
					return false;
				}
				@Override
				public boolean promptPassphrase(String message) {
					return !StringUtil.isNullOrEmpty(pass);
				}
				@Override
				public boolean promptYesNo(String message) {
					return false;
				}
				@Override
				public void showMessage(String message) {
					}};
			
			optionbuilder.setUserInfo(fsOptions, user);

        } catch (FileSystemException ex) {
            throw new RuntimeException("failed to get fsManager from VFS", ex);
        }
	}
	
	public void upload(String filename, String input) throws UnsupportedEncodingException, IOException{
		upload(filename, input.getBytes("UTF-8"));
	}
	
	public void upload(String filename, byte[] input) throws IOException{
		FileObject remotefile = null;
		FileObject ramfile = null;
		try{
			remotefile = getFileObject(filename);
			//if(remotefile.exists())
			//	remotefile.delete(Selectors.SELECT_ALL);
			ramfile = fsManager.resolveFile("ram:/sunctionscreen" + filename);
			ramfile.createFile();
			OutputStream out = ramfile.getContent().getOutputStream();
			out.write(input);
			out.flush();
			remotefile.copyFrom(ramfile, Selectors.SELECT_SELF);
		}finally{
			if(remotefile != null) remotefile.close();
		}
	}
	
	public byte[] download(String filename) throws IOException{
		if(StringUtil.isNullOrEmpty(filename)) throw new IOException("Invalid empty filename");
		FileObject file = null;
		try{
			file = getFileObject(filename);
			return download(file);
		}finally{
			if(file!=null)file.close();
		}
	}
	
	public void download(String filename, OutputStream output) throws IOException{
		if(StringUtil.isNullOrEmpty(filename)) throw new IOException("Invalid empty filename");
		FileObject file = null;
		try{
			file = getFileObject(filename);
			download(file, output);
		}finally{
			if(file!=null)file.close();
		}
	}
	
	public void download(FileObject file, OutputStream output) throws IOException{
		if(!file.exists() && file.getType() != FileType.FILE) throw new IOException("File not Exists:" + file.getName());
		FileUtil.writeContent(file, output);
		return;
	}
	
	public byte[] download(FileObject file) throws IOException{
		if(!file.exists() && file.getType() != FileType.FILE) return null;
		return FileUtil.getContent(file);
	}
	
	public void delete(String filename) throws IOException{
		FileObject file = null;
		try{
			file = getFileObject(filename);
			delete(file);
		}finally{
			if(file != null) file.close();
		}
	}
	
	public void delete(FileObject file) throws FileSystemException{
		if(file.exists())
			file.delete(Selectors.SELECT_ALL);
	}
	
	public void rename(String filename, String newFilename) throws IOException{
		FileObject file = null;
		FileObject newFile = null;
		try{
			file = getFileObject(filename);
			newFile = getFileObject(newFilename);
			rename(file, newFile);
		}finally{
			if(newFile != null) newFile.close();
			if(file != null) file.close();
		}
	}
	
	public void rename(FileObject file, FileObject newFile) throws FileSystemException{
		if(file instanceof AbstractFileObject){
			AbstractFileObject f = (AbstractFileObject)file;
			f.moveTo(newFile);
		}
	}
	
	public String[] listFolder() throws IOException{
		return listFolder("");
	}
	
	public String[] listFolder(String directory) throws IOException{
		ArrayList<String> folders = new ArrayList<String>();
		FileObject[] list = listDirectory(directory);
		for(FileObject file : list){
			try{
				folders.add(file.getName().getBaseName());
			}finally{
				if(file != null)file.close();
			}
		}
		return folders.toArray(new String[folders.size()]);
	}
	
	public FileObject[] listDirectory() throws FileSystemException{
		return listDirectory("");
	}
	
	public FileObject[] listDirectory(String directory) throws FileSystemException{
		FileObject dir = null;
		try{
			dir = getFileObject(directory);
			if(dir.exists())
				return dir.getChildren();
		}finally{
			if(dir!= null) dir.close();
		}
		return null;
	}
	
	public FileObject getFileObject(String path) throws FileSystemException{
		if(StringUtil.isNullOrEmpty(path)) path = "";
		if(!path.startsWith("/")) path = "/" + path;
		FileObject file;
		if(fsOptions == null) 
			file = fsManager.resolveFile(uri.toString() + path);
		else
			file = fsManager.resolveFile(uri.toString() + path, fsOptions);
		lastaccessedfile = file;
		return file;
	}
	
 	public void dispose(){
 		try{
	        FileSystem fs = lastaccessedfile.getFileSystem(); // This works even if the src is closed.
	        fsManager.closeFileSystem(fs);
 		}catch(Exception e){}
	}

 	  public static class MyLogger implements com.jcraft.jsch.Logger {
 		    static java.util.Hashtable<Integer,String> name=new java.util.Hashtable<Integer,String>();
 		    static{
 		      name.put(new Integer(DEBUG), "DEBUG: ");
 		      name.put(new Integer(INFO), "INFO: ");
 		      name.put(new Integer(WARN), "WARN: ");
 		      name.put(new Integer(ERROR), "ERROR: ");
 		      name.put(new Integer(FATAL), "FATAL: ");
 		    }
 		    public boolean isEnabled(int level){
 		      return true;
 		    }
 		    public void log(int level, String message){
 		      System.err.print(name.get(new Integer(level)));
 		      System.err.println(message);
 		    }
 		  }
}
