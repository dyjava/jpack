package com.my.test.file.local;

import java.io.IOException;
import java.util.HashMap;

import junit.framework.Assert;

import org.junit.Test;

import com.my.file.local.ReadLocalFile;

public class ReadLocalFileTest {
	private String path = "e:/img/" ;
	private String code = "gbk" ;
	
	@Test
	public void readPropertiesFileTest() {
		try {
			HashMap<String, String> hashmap = ReadLocalFile.readPropertiesFile("/log4j.properties") ;
			
			System.out.println("=======readPropertiesFileTest========") ;
			for(String map:hashmap.keySet()){
				System.out.println(map+":"+hashmap.get(map)) ;
			}
			Assert.assertNotNull(hashmap) ;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void readLocalFileTest() {
		try {
			String content = ReadLocalFile.readLocalFile(path+"build.info") ;
			
			System.out.println("========:"+content) ;
			Assert.assertNotNull(content) ;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void readLocalFile2Test() {
		try {
			String content = ReadLocalFile.readLocalFile2(path+"build.info") ;
			System.out.println("========:"+content) ;
			Assert.assertNotNull(content) ;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void readLocalFile3Test() {
		try {
			String content = ReadLocalFile.readLocalFile3("/com/my/config/log4j.properties") ;
			System.out.println("========:"+content) ;
			Assert.assertNotNull(content) ;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void readLocalFile4Test() {
		try {
			String content = ReadLocalFile.readLocalFile4("/com/my/config/log4j.properties", code) ;
			System.out.println("========:"+content) ;
			Assert.assertNotNull(content) ;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
}
