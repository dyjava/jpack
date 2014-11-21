package com.my.file.web;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.HeadMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;

/**
 * HttpClient的应用范例
 * @author diyong
 *
 */
public class HttpClientUtil {

	private HttpClient client ;
	private static HttpClientUtil hct = new HttpClientUtil();
	public static HttpClientUtil getInstance(){
		return hct ;
	}
	private HttpClientUtil(){
		MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
		client = new HttpClient(connectionManager);
		HttpConnectionManagerParams managerParams = client.getHttpConnectionManager().getParams();
		// 设置连接超时时间(单位毫秒)
		managerParams.setConnectionTimeout(15000);
		// 设置读数据超时时间(单位毫秒)
		managerParams.setSoTimeout(20000);
		//每个连接端口最大连接数
		managerParams.setDefaultMaxConnectionsPerHost(5);  //very important!! 
		//总的最大连接数。是上一个参数的整数倍。
		managerParams.setMaxTotalConnections(10);  //very important!! 
//		client.getHostConfiguration().setProxy(proxyHost, proxyPort) ;//设置代理
	}
	
	/**
	 * get方法获取网页内容
	 * @param url
	 * @param code 网页编码
	 * @param bzip 是否支持压缩
	 * @return
	 */
	public String getUrlContent(String url){
		return this.getUrlContent(url, "utf-8", true) ;
	}
	public String getUrlContent(String url,String code,boolean bzip){
		GetMethod get = null ;
		StringBuffer html = new StringBuffer() ;
		int statusCode=0 ;
		try {
			get = new GetMethod(url);
			if(bzip){
				get.setRequestHeader("Accept-Encoding","gzip, deflate"); 
			}
			get.setRequestHeader("Referer",url) ;//设置referer
			get.setRequestHeader("User-Agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.2)") ;//设置UA
			get.setFollowRedirects(true);	//支持重定向
			
			statusCode = client.executeMethod(get);
			if (statusCode == HttpStatus.SC_OK) {
				//应用NIO通道读取。
				ReadableByteChannel rbc ;
//				gzip压缩
				if(bzip && get.getResponseHeader("Content-Encoding")!=null && 
						get.getResponseHeader("Content-Encoding").getValue().toLowerCase().indexOf("gzip")>-1){
					GZIPInputStream gzin = new GZIPInputStream(get.getResponseBodyAsStream());
					rbc = Channels.newChannel(gzin);
				} else {
//					非压缩
					rbc = Channels.newChannel(get.getResponseBodyAsStream());
				}
				//缓冲区大小10k
				ByteBuffer bs = ByteBuffer.allocate(10240);
				@SuppressWarnings("unused")
				int g;
				while((g = rbc.read(bs))>-1){
					// flip方法让缓冲区可以将新读入的数据写入另一个通道  
					bs.flip();
					html.append(new String(bs.array(),code)) ;
					// clear方法重设缓冲区，使它可以接受读入的数据  
					bs.clear();
				}
			}
		} catch (Exception e) {
			e.printStackTrace() ;
		} finally {
			get.releaseConnection();
		}
		return html.toString() ;
	}
	
	/**
	 * Post方法获取网页内容
	 * @param url
	 * @param map
	 * @param code
	 * @return
	 */
	public String postUrlContent(String url,Map<String,String> map){
		return this.postUrlContent(url, map, "utf-8",true) ;
	}
	public String postUrlContent(String url,Map<String,String> map,String code, boolean bzip){
		PostMethod post = new PostMethod(url);
		
		for(String key:map.keySet()){
			post.addParameter(key, map.get(key));
		}
		post.setRequestHeader("Referer",url) ;//设置referer
		post.setRequestHeader("User-Agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.2)") ;//设置UA
		post.setFollowRedirects(true);	//支持重定向
		
		StringBuffer html = new StringBuffer() ;
		try {
			int statusCode = client.executeMethod(post);
			if (statusCode == HttpStatus.SC_OK) {

				//应用NIO通道读取。
				ReadableByteChannel rbc ;
//				gzip压缩
				if(bzip && post.getResponseHeader("Content-Encoding")!=null && 
						post.getResponseHeader("Content-Encoding").getValue().toLowerCase().indexOf("gzip")>-1){
					GZIPInputStream gzin = new GZIPInputStream(post.getResponseBodyAsStream());
					rbc = Channels.newChannel(gzin);
				} else {
//					非压缩
					rbc = Channels.newChannel(post.getResponseBodyAsStream());
				}
				//缓冲区大小10k
				ByteBuffer bs = ByteBuffer.allocate(10240);
				@SuppressWarnings("unused")
				int g;
				while((g = rbc.read(bs))>-1){
					// flip方法让缓冲区可以将新读入的数据写入另一个通道  
					bs.flip();
					html.append(new String(bs.array(),code)) ;
					// clear方法重设缓冲区，使它可以接受读入的数据  
					bs.clear();
				}
			}
		} catch (HttpException e) {
			post.abort();
			e.printStackTrace();
		} catch (IOException e) {
			post.abort();
			e.printStackTrace();
		} finally {
			post.releaseConnection();
		}
		return html.toString() ;
	}
	
	public String postUrlContent(String url,String queryString){
		return this.postUrlContent(url, queryString, "utf-8",true) ;
	}
	public String postUrlContent(String url,String queryString,String code, boolean bzip){
		PostMethod post = new PostMethod(url);
		
		post.setQueryString(queryString);
		post.setRequestHeader("Referer",url) ;//设置referer
		post.setRequestHeader("User-Agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.2)") ;//设置UA
		
		StringBuffer html = new StringBuffer() ;
		try {
			int statusCode = client.executeMethod(post);
			if (statusCode == HttpStatus.SC_OK) {

				//应用NIO通道读取。
				ReadableByteChannel rbc ;
//				gzip压缩
				if(bzip && post.getResponseHeader("Content-Encoding")!=null && 
						post.getResponseHeader("Content-Encoding").getValue().toLowerCase().indexOf("gzip")>-1){
					GZIPInputStream gzin = new GZIPInputStream(post.getResponseBodyAsStream());
					rbc = Channels.newChannel(gzin);
				} else {
//					非压缩
					rbc = Channels.newChannel(post.getResponseBodyAsStream());
				}
				//缓冲区大小10k
				ByteBuffer bs = ByteBuffer.allocate(10240);
				@SuppressWarnings("unused")
				int g;
				while((g = rbc.read(bs))>-1){
					// flip方法让缓冲区可以将新读入的数据写入另一个通道  
					bs.flip();
					html.append(new String(bs.array(),code)) ;
					// clear方法重设缓冲区，使它可以接受读入的数据  
					bs.clear();
				}
			}
		} catch (HttpException e) {
			post.abort();
			e.printStackTrace();
		} catch (IOException e) {
			post.abort();
			e.printStackTrace();
		} finally {
			post.releaseConnection();
		}
		return html.toString() ;
	}
	
	
	/**
	 * 连接状态参数检测。
	 * @param url
	 * @return
	 */
	public boolean urlProbe(String url){
		HeadMethod head = new HeadMethod(url) ;
		try {
			int statusCode = client.executeMethod(head) ;
			if (statusCode  == HttpStatus.SC_OK 
					|| statusCode == HttpStatus.SC_MOVED_PERMANENTLY) {
				return true ;
			}
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false ;
	}
	public Header[] urlHeader(String url){
		HeadMethod head = new HeadMethod(url) ;
		try {
			client.executeMethod(head) ;
			return head.getRequestHeaders();
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null ;
	}

	public static void main(String[] a){
		HttpClientUtil client = HttpClientUtil.getInstance() ;
		String url = "http://strategy.intra.umessage.com.cn:8180/strategyapi/area_trade.do?pid=u01&pt=2&vt=1&lci=10000000&zip=0&type=0&zip=1" ;
		url = "http://strategy.intra.umessage.com.cn:8180/strategyapi/area_leve.do?pid=u01&vt=1&pt=1&leve=1&format=json&zip=1" ;
		String content = client.getUrlContent(url) ;
		long start = System.currentTimeMillis() ;
		content = client.getUrlContent(url) ;
		System.out.println("lenth:"+content.length() +" time:"+ (System.currentTimeMillis()-start));
		System.out.println(content.substring(0,40)) ;
		
		url = "http://strategy.intra.umessage.com.cn:8180/strategyapi/area_trade.do?pid=u01&pt=2&vt=1&lci=10000000&zip=0&type=0&zip=0" ;
		url = "http://strategy.intra.umessage.com.cn:8180/strategyapi/area_leve.do?pid=u01&vt=1&pt=1&leve=1&format=json&zip=0" ;
		start = System.currentTimeMillis() ;
		content = client.getUrlContent(url) ;
		System.out.println("lenth:"+content.length() +" time:"+ (System.currentTimeMillis()-start));
		System.out.println(content.substring(0,40)) ;
		
		url = "http://strategy.intra.umessage.com.cn:8180/strategyapi/area_leve.do" ;
		String query = "pid=u01&vt=1&pt=1&leve=1&format=json&zip=0" ;
		start = System.currentTimeMillis() ;
		content = client.postUrlContent(url, query) ;
		System.out.println("lenth:"+content.length() +" time:"+ (System.currentTimeMillis()-start));
		System.out.println(content.substring(0,40)) ;
		
		query = "pid=u01&vt=1&pt=1&leve=1&format=json&zip=1" ;
		start = System.currentTimeMillis() ;
		content = client.postUrlContent(url, query) ;
		System.out.println("lenth:"+content.length() +" time:"+ (System.currentTimeMillis()-start));
		System.out.println(content.substring(0,40)) ;
		
	}
	

}
