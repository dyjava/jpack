package com.my.file.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.zip.GZIPInputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;

/**
 * made by dyong 
 * 读取网络资源，NIO
 **/
public class ReadWebFileNIO {

	private HttpClient client ;
	private static ReadWebFileNIO hct = new ReadWebFileNIO();
	public static ReadWebFileNIO getInstance(){
		return hct ;
	}
	private ReadWebFileNIO(){
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
     * 
     * @param url
     * @param code
     * @return
     * @throws IOException
     */
    public String readUrlContent(String url)
    		throws IOException{
		return readUrlContent(url,"UTF-8",true) ;
    }
    public String readUrlContent(String url,String code,boolean usegzip)
    		throws IOException {
		GetMethod get = null ;
		StringBuffer html = new StringBuffer() ;
		int statusCode=0 ;
		try {
			get = new GetMethod(url);
			if(usegzip){
				get.setRequestHeader("Accept-Encoding","gzip, deflate"); 
			}
			get.setRequestHeader("User-Agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.2)") ;//设置UA
			get.setFollowRedirects(true);	//支持重定向
			statusCode = client.executeMethod(get);
//			get.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, readTimeout);

			if (statusCode == HttpStatus.SC_OK) {
				//NIO
				ReadableByteChannel channel ;
//				gzip压缩
				if(usegzip && get.getResponseHeader("Content-Encoding")!=null && 
						get.getResponseHeader("Content-Encoding").getValue().toLowerCase().indexOf("gzip")>-1){
//				    System.out.println("gzip") ;
					GZIPInputStream gzin = new GZIPInputStream(get.getResponseBodyAsStream());
					channel = Channels.newChannel(gzin);
				} else {
//					非压缩
					channel = Channels.newChannel(get.getResponseBodyAsStream());
				}
				ByteBuffer bs = ByteBuffer.allocate(20480);
				while(channel.read(bs)>-1){
					bs.flip() ;
					html.append(new String(bs.array(),code)) ;
					bs.clear() ;
				}
			}
		} catch (Exception e) {
			StringWriter sw=new StringWriter();
			e.printStackTrace(new PrintWriter(sw,true));
			throw new IOException(sw.toString()) ;
		} finally {
			get.releaseConnection();
		}
		return html.toString() ;
	}

}
