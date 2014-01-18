package com.my.file.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;

/**
 * made by dyong 
 * 读取网络资源
 **/
public class ReadWebFile {

	private String UserAgent = "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.2; .NET CLR 1.1.4322)";
	
	private boolean ProxySet = false;
	private String ProxyHost = null;
	private String ProxyPort = null;
	private String ProxyAuthenticator = null;
	
	private int ReadTimeout = 10000;
	private int ConnectTimeout = 10000;
	
	/**
	 * HttpURLConnection读取网页内容
	 * 读取URL页面内容，返回字符串。
	 * 设置UA 设置代理，设置超时和防盗链，GZIP压缩
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public String readUrlContent(String url,String code) throws IOException{
		BufferedReader in = null;
		HttpURLConnection conn = null;
		StringBuffer result = new StringBuffer();
		try {
			URL u = new URL(url);
			conn = (HttpURLConnection) u.openConnection();
//			设置UA
			conn.setRequestProperty("User-Agent", UserAgent);

//			 设置代理服务器用户名和密码
			if (ProxySet) {
				System.getProperties().setProperty("proxySet", "true");
				System.getProperties().setProperty("http.proxyHost", ProxyHost);
				System.getProperties().setProperty("http.proxyPort", ProxyPort);
				conn.setRequestProperty("Proxy-Authorization", ProxyAuthenticator);
			}
//			设置超时
			conn.setConnectTimeout(ConnectTimeout);
			conn.setReadTimeout(ReadTimeout);
//			防盗链地址 Referer
			conn.setRequestProperty("Referer",url);
//			设置gzip压缩
			conn.setRequestProperty("accept-encoding", "gzip,deflate");
			
			String encoding = conn.getContentEncoding() ;
//			gzip压缩
			if(encoding!=null && encoding.toLowerCase().indexOf("gzip") > -1){
				GZIPInputStream gzin = new GZIPInputStream(conn.getInputStream());
				
				InputStreamReader isr = new InputStreamReader(gzin, code);
				in = new java.io.BufferedReader(isr);
			} else {
//				非压缩
				in = new BufferedReader(new InputStreamReader(conn.getInputStream(),code));
			}
			
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				result.append(inputLine);
				result.append("\n");
			}
		} catch (IOException ex) {
			StringWriter sw=new StringWriter();
			ex.printStackTrace(new PrintWriter(sw,true));
			throw new IOException(sw.toString()) ;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (conn != null){
				conn.disconnect();
			}
		}
		return result.toString();
	}
	
	public String readUrlContent2(String url,String code) throws IOException{
		BufferedReader in = null;
		HttpURLConnection conn = null;
		StringBuffer result = new StringBuffer();
		java.io.InputStream is = null;
		try {
			URL u = new URL(url);
			conn = (HttpURLConnection) u.openConnection();
			is = conn.getInputStream();
			in = new BufferedReader(new InputStreamReader(is,code));
			
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				result.append(inputLine);
				result.append("\n");
			}
		} catch (IOException ex) {
			StringWriter sw=new StringWriter();
			ex.printStackTrace(new PrintWriter(sw,true));
			throw new IOException(sw.toString()) ;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (conn != null){
				conn.disconnect();
			}
		}
		return result.toString();
	}
	
	/**
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public InputStream readUrlInputStream(String url) throws IOException{
		URL u = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) u.openConnection();
//		设置UA
		conn.setRequestProperty("User-Agent", UserAgent);

//		设置代理服务器用户名和密码
		if (ProxySet) {
			System.getProperties().setProperty("proxySet", "true");
			System.getProperties().setProperty("http.proxyHost", ProxyHost);
			System.getProperties().setProperty("http.proxyPort", ProxyPort);
			conn.setRequestProperty("Proxy-Authorization", ProxyAuthenticator);
		}
		
//		设置超时
		conn.setConnectTimeout(ConnectTimeout);
		conn.setReadTimeout(ReadTimeout);
		
//		防盗链地址 Referer
		conn.setRequestProperty("Referer",url);
		conn.setRequestProperty("accept-encoding", "gzip,deflate");
		
		return conn.getInputStream();
	}
	
	/**
	 * 字节流读取网页文件
	 * 读取web页面，返回字符串
	 * @param url
	 * @param code
	 * @return
	 * @throws IOException
	 */
	public String readWebFile(String url,String code)
	throws IOException{
		URL path = new URL(url);
		InputStream is = path.openStream() ;
		InputStreamReader isr = new InputStreamReader(is,code);   
		BufferedReader br = new BufferedReader(isr);
		StringBuffer sb = new StringBuffer() ;
		String s = "" ;
		while((s=br.readLine())!=null){
			sb.append(s).append("\n") ;
		}
		return sb.toString() ;
	}
	

}
