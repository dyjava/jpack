package com.my.test.file;


import java.io.IOException;


import org.junit.Test;

import com.my.file.WriteFile;

public class WriteFileTest {
	private String path = "e:/data/" ;
	
	@Test
	public void StringWriteToFileTest() {
		String filePath = path+"StringWriteToFile.txt";
		String text = "这是测试的，hello。";
		WriteFile.StringWriteToFile(filePath, text) ;
//		Assert.assertNotNull(hd) ;
	}

	@Test
	public void StringWriteToFile1Test() {
		String filePath = path+"StringWriteToFile1.txt";
		String[] text = {"测试多行输出1。","测试多行输出2。","测试多行输出3。"};
		WriteFile.StringWriteToFile(filePath, text) ;
//		Assert.assertNotNull(hd) ;
	}
	@Test
	public void downFileTest() {
		String filePath = path+"StringWriteToFile2.txt";
		String url = "http://www.baidu.com";
		try {
			WriteFile.downFile(url, filePath) ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		Assert.assertNotNull(hd) ;
	}
}
