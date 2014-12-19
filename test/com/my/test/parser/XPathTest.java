package com.my.test.parser;


import java.io.File;
import java.util.List;

import org.junit.Test;

import com.my.file.WriteFile;
import com.my.file.web.HttpClientUtil2;
import com.my.parser.XPath;

public class XPathTest {

	@Test
	public void test() {
		String url = "http://www.lz13.cn/ganenlizhi/20296.html" ;
		url = "http://www.lz13.cn/ganenlizhi/8290.html" ;
		url = "http://www.lz13.cn/lizhimingyan/17127.html";
		String path = "e:/data/txt/" ;
		File dir = new File(path) ;
		if(!dir.exists()){
			dir.mkdirs() ;
		}
		
		int i=0 ;
		while(url.trim().length()>0){
			i++ ;
			if(i==100){
				return ;
			}
			String content = HttpClientUtil2.getInstance().getUrlContent(url,"utf-8",false) ;
			XPath test = XPath.getInstens(content) ;
			
			try{
				String xp = ".//*[@id='node-8890']/div[2]/p[1]/strong" ;
				xp = ".//*[@id='node-8890']/div[1]/h2" ;
				List<String> list = test.getHtml(content,xp) ;
				String title = list.get(0).trim() ;
				System.out.println(title) ;
				String filePath = path + title+".txt" ;
				
				System.out.println("=============1===========") ;
				list = test.getHtml(content,".//*[@id='node-8890']/div[2]/p") ;
				for(String item:list){
					System.out.println(item) ;
				}
				WriteFile.StringWriteToFile(filePath, list.toArray(new String[0])) ;
				
		
				System.out.println("==========2=============") ;
				list = test.getHref(content,".//*[@id='node-8890']/div[2]/div[6]/a[2]") ;
				url = list.get(0).trim() ;
				System.out.println(url) ;
			} catch (Exception e){
//				e.printStackTrace() ;
				System.out.println("ERROR:"+e.getMessage()) ;
			}
			try {
				Thread.sleep(1*1000) ;
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}

}
