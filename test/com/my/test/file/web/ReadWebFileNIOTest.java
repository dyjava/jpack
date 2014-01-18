package com.my.test.file.web;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;

import com.my.file.web.ReadWebFileNIO;

public class ReadWebFileNIOTest {
	private String url = "http://www.baidu.com/" ;
//	private String code = "gbk" ;
	private ReadWebFileNIO instance ;
	
	public ReadWebFileNIOTest(){
		instance = ReadWebFileNIO.getInstance() ;
	}

	@Test
	public void readUrlContentTest() {
		try {
			String content = instance.readUrlContent(url) ;
			System.out.println(content) ;
			Assert.assertNotNull(content) ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
