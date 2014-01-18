package com.my.test.file.web;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;

import com.my.file.web.ReadWebFile;

public class ReadWebFileTest {
	private String url = "http://www.baidu.com/" ;
	private String code = "gbk" ;
	private ReadWebFile instance ;
	
	public ReadWebFileTest(){
//		url = "http://dev.yesky.com/TLimages/css/yesky-inverse1-0605.css" ;
//		url = "http://www.16k.cn/book/showbooklist.aspx?page=2" ;
		
		instance = new ReadWebFile() ;
	}

	@Test
	public void readUrlContentTest() {
		try {
			String content = instance.readUrlContent(url, code) ;
			Assert.assertNotNull(content) ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void readUrlContent2Test(){
		try {
			String content = instance.readUrlContent2(url, code) ;
			Assert.assertNotNull(content) ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void readWebFileTest(){
		try {
			String content = instance.readWebFile(url, code) ;
			Assert.assertNotNull(content) ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
