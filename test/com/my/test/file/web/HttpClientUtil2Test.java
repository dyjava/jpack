package com.my.test.file.web;


import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.apache.commons.httpclient.Header;
import org.junit.Test;

import com.my.file.web.HttpClientUtil2;

public class HttpClientUtil2Test {
	private HttpClientUtil2 client ;
	private String url = "http://www.baidu.com/" ;
	public HttpClientUtil2Test(){
		client = HttpClientUtil2.getInstance() ;
	}

	@Test
	public void getUrlContentTest(){
		String content = client.getUrlContent(url) ;
		Assert.assertNotNull(content) ;
	}

	@Test
	public void postUrlContentTest(){
		Map<String, String> map = new HashMap<String, String>() ;
		String content = client.postUrlContent(url, map) ;
		Assert.assertNotNull(content) ;
	}
	

}
