package com.my.test.file.web;


import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.apache.commons.httpclient.Header;
import org.junit.Test;

import com.my.file.web.HttpClientUtil;

public class HttpClientUtilTest {
	private HttpClientUtil client ;
	private String url = "http://www.baidu.com/" ;
	public HttpClientUtilTest(){
		client = HttpClientUtil.getInstance() ;
	}

	@Test
	public void test() {
		Header[] hd = client.urlHeader(url) ;
		Assert.assertNotNull(hd) ;
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
