package com.my.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * made by dyong 
 * 写入本地文件
 **/
public class WriteFile {

	private static int ReadTimeout = 10000;
	private static int ConnectTimeout = 10000;
	
	/**
	 * 写入数据
	 * @param filePath
	 * @param text
	 * @param isNewFile
	 * @return
	 */
	public static boolean StringWriteToFile(String filePath,String text,boolean isNewFile){
		boolean b = false ;
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(filePath, !isNewFile), false);
			pw.println(text);
			pw.close();
			b = true;
		}catch (Exception e) {
			System.out.println("err:"+e.getMessage());
		}
		return b ;
	}
	public static boolean StringWriteToFile(String filePath,String[] text){
		boolean b = false ;
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(filePath, false), false);
			for(int i=0;i<text.length;i++){
				pw.println(text[i]);
			}
			pw.close();
			b = true;
		}catch (Exception e) {
			System.out.println("err:"+e.getMessage());
		}
		return b ;
	}
	public static boolean StringWriteToFile(String filePath,String text){
		return StringWriteToFile(filePath,text,false) ;
	}
	
	public static boolean StringWriteToFile(InputStream is,String filePath,int bufferSize)
	throws IOException{
		FileOutputStream os = new FileOutputStream(filePath);
		BufferedInputStream bis = new BufferedInputStream(is);
		BufferedOutputStream bos = new BufferedOutputStream(os);//用流复制
		boolean b = false ;
		try{
			byte[] b1=new byte[1024*bufferSize];//设一个1024*bufferSize字节的数组 
			int read=0;
			int bytes = 0 ;
			while((read=bis.read(b1))!=-1){
				bos.write(b1,0,read);//从数组里往文件里写数据 
				bytes+= read;
			}
			System.out.println("all bytes is :"+bytes);
			b = true ;
		}catch(IOException e){
			throw e;
		}finally{
			bis.close();
			bos.close();
		}
		return b ;
	}
	
	/**
	 * 网络文件下载
	 * 设置超时、盗链
	 * @param url
	 * @param localPath
	 * @throws IOException
	 */
	public static void downFile(String url,String localPath) throws IOException{
		BufferedOutputStream bos = null ;
		
		HttpURLConnection conn = null;
		InputStream is = null;
		BufferedInputStream bis = null ;
		try {
			FileOutputStream os = new FileOutputStream(localPath);
			bos = new BufferedOutputStream(os);
			
			URL u = new URL(url);
			conn = (HttpURLConnection) u.openConnection();
//			设置超时
			conn.setConnectTimeout(ConnectTimeout);
			conn.setReadTimeout(ReadTimeout);
			
//			防盗链地址 Referer
			conn.setRequestProperty("Referer",url);
			is = conn.getInputStream();
			bis = new BufferedInputStream(is);
			
			byte[] b1=new byte[1024*10];//设一个1024*10字节的数组 
			int read=0;
			int bytes = 0 ;
			while((read=bis.read(b1))!=-1){
				bos.write(b1,0,read);
				bytes+= read;
			}
			System.out.println("file size:"+bytes) ;
		} catch (IOException ex) {
			StringWriter sw=new StringWriter();
			ex.printStackTrace(new PrintWriter(sw,true));
			throw new IOException(sw.toString()) ;
		} finally {
			if (conn != null){
				conn.disconnect();
			}
			if(bis!=null){
				bis.close() ;
			}
			if(bos!=null){
				bos.close() ;
			}
		}
	}

}
