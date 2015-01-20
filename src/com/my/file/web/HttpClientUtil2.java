package com.my.file.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * HttpClient的应用
 * @author diyong
 *
 */
public class HttpClientUtil2 {
	private static final Logger logger = Logger.getLogger(HttpClientUtil2.class);
	
	private CloseableHttpClient client ;
	private static HttpClientUtil2 hct = new HttpClientUtil2();
	public static HttpClientUtil2 getInstance(){
		return hct ;
	}
	private HttpClientUtil2(){
//		client = HttpClients.createDefault();
		
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
		connectionManager.setMaxTotal(1000);    		//设置连接池线程最大数量  
		connectionManager.setDefaultMaxPerRoute(50);	//设置单个路由最大的连接线程数量  
        
        //创建http request的配置信息  
        RequestConfig requestConfig = RequestConfig.custom()
        		.setConnectionRequestTimeout(3000)
        		.setSocketTimeout(3000)
        		.build();  
        //设置重定向策略  
        LaxRedirectStrategy redirectStrategy = new LaxRedirectStrategy();  
        //初始化httpclient客户端  
        client = HttpClients.custom()
        		.setConnectionManager(connectionManager)
        		.setDefaultRequestConfig(requestConfig)
//        		.setUserAgent(NewsConstant.USER_AGENT)
        		.setRedirectStrategy(redirectStrategy)
        		.build();
//		client.getParams().setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET,"UTF-8") ;
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
		long start = System.currentTimeMillis() ;
		StringBuffer buf = new StringBuffer("HttpClientUtil|getUrlContent") ;
		buf.append("|").append(url) ;
		
		StringBuffer html = new StringBuffer() ;
		HttpGet get = new HttpGet(url);  
        CloseableHttpResponse response = null;
		try {
			if(bzip){
				get.setHeader("Accept-Encoding","gzip, deflate") ; 
			}
        	// 执行get请求.
        	response = client.execute(get) ;
        	//获得响应的消息实体  
        	HttpEntity entity = response.getEntity();
        	//获取响应状态码
        	int statuscode = response.getStatusLine().getStatusCode();
        	if (statuscode == HttpStatus.SC_OK) {
        		InputStream in ;
//				gzip压缩
				if(response.getLastHeader("Content-Encoding")!=null && 
						response.getLastHeader("Content-Encoding").getValue().toLowerCase().indexOf("gzip")>-1){
					in = new GZIPInputStream(entity.getContent()) ;
				} else {
//					非压缩
					in = entity.getContent() ;
				}
        		BufferedReader br = new BufferedReader(new InputStreamReader(in,code)) ;
    			String inputLine;
    			while ((inputLine = br.readLine()) != null) {
    				html.append(inputLine);
    				html.append("\n");
    			}
//        		html = EntityUtils.toString(entity) ;
        	}
        	//关闭httpEntity流
        	EntityUtils.consume(entity);
	        
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw, true));
			String title = "HttpClientUtil ERROR <br/>"+url ;
			logger.info(title+sw.toString()) ;
		} finally{
			if(null != response){
				try {
					//关闭response
					response.close();
				} catch (IOException e) {
                    e.printStackTrace();  
                }  
            }  
        }

		buf.append("|").append(html.length())
		.append("|").append(System.currentTimeMillis()-start) ;
		logger.info(buf.toString()) ;
		
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
		long start = System.currentTimeMillis() ;
		StringBuffer buf = new StringBuffer("HttpClientUtil|postUrlContent") ;
		buf.append("|").append(url) ;

		String html = "" ;
		HttpPost post = new HttpPost(url);  
        CloseableHttpResponse response = null;
		try {
			List<NameValuePair> nvps = new ArrayList <NameValuePair>();
			for(String key : map.keySet()) {
				nvps.add(new BasicNameValuePair(key, map.get(key)));
			}
			post.setEntity(new UrlEncodedFormEntity(nvps,code));
			if(bzip){
				post.setHeader("Accept-Encoding","gzip, deflate") ; 
			}
        	// 执行get请求.
        	response = client.execute(post) ;
        	//获得响应的消息实体  
        	HttpEntity entity = response.getEntity();
        	//获取响应状态码
        	int statuscode = response.getStatusLine().getStatusCode();
        	if (statuscode == HttpStatus.SC_OK) {
        		//InputStream inputStream = entity.getContent();
        		html = EntityUtils.toString(entity) ;
        	}
        	//关闭httpEntity流
        	EntityUtils.consume(entity);
	        
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw, true));
			String title = "HttpClientUtil ERROR <br/>"+url;
			logger.info(title+sw.toString()) ;
		} finally{
			if(null != response){
				try {
					//关闭response
					response.close();
				} catch (IOException e) {
                    e.printStackTrace();  
                }  
            }  
        }  

		buf.append("|").append(html.length())
		.append("|").append(System.currentTimeMillis()-start) ;
		logger.info(buf.toString()) ;
		
		return html.toString() ;
	}
	
	public static void main(String[] a){
		HttpClientUtil2 client = HttpClientUtil2.getInstance() ;
		String url = "http://strategy.intra.umessage.com.cn:8180/strategyapi/area_trade.do?pid=u01&pt=2&vt=1&lci=10000000&type=1&zip=0" ;
//		url = "http://strategy.intra.umessage.com.cn:8180/WebDataHandler/weather.search?pid=u01&lcc=%E5%8D%97%E4%BA%AC&zip=1" ;
		String content = client.getUrlContent(url) ;
		long start = System.currentTimeMillis() ;
//		content = client.getUrlContent(url) ;
		System.out.println("lenth:"+content.length() +" time:"+ (System.currentTimeMillis()-start));
		System.out.println(content) ;
		
	}
	
}
