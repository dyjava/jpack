package com.my.test.file.local;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.my.file.local.ReadLocalFileNIO;

public class ReadLocalFileNIOTest {
	private String path = "e:/img/" ;
//	private String code = "gbk" ;
	
	@Test
	public void readLocalFileTest() {
		try {
			ArrayList<String> list = ReadLocalFileNIO.readLocalFile(path+"build.info") ;
			
			this.out(list) ;
			Assert.assertNotNull(list) ;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void readLocalFile2Test() {
		try {
			ArrayList<String> list = ReadLocalFileNIO.readLocalFile2("/com/my/config/log4j.properties") ;
			
			this.out(list) ;
			Assert.assertNotNull(list) ;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void out(List<String> list){
		System.out.println("====================") ;
		for(String s : list){
			System.out.println(s) ;
		}
	}
	
}
