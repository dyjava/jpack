package com.my.file.localfile;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

/**
 * made by dyong 
 * date 2008-11-12 下午04:26:03
 **/
public class ReadLocalFileMethod {

	/**
	 * 读取.properties配置文件。
	 * @param filepath
	 * @return 返回哈西表。
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static HashMap readPropertiesFile(String filepath)
	throws IOException{
		Properties p = new Properties();
		p.load(ReadLocalFileMethod.class.getResourceAsStream(filepath));
		
		p.setProperty("root", "bbb");
		
		Enumeration enu = p.propertyNames() ;
		HashMap hashmap = new HashMap() ;
		while(enu.hasMoreElements()){
			String name = enu.nextElement().toString() ;
			hashmap.put(name,p.getProperty(name)) ;
			System.out.println("===>"+name+"  "+p.getProperty(name));
		}
		return hashmap ;
	}
	
	/**
	 * 读取文件，返回字符串
	 * @param filepath 文件的绝对路径
	 * @param PageCode 读取的文件的编码格式 utf-8 gbk
	 * @return
	 * @throws Exception
	 */
	public static String readLocalFile(String filepath)
		throws Exception{
		try {
			FileReader fr = new FileReader(filepath);
			String record = "";
			StringBuffer sb = new StringBuffer();
			BufferedReader br = new BufferedReader(fr);

			while ((record = br.readLine()) != null) {
				sb.append(record.trim());
			}
			fr.close();
			br.close();
			return sb.toString() ;
		}catch(Exception e){
			throw new Exception("readLocalFile err:"+e.getMessage()) ;
		}
	}
	public static String readLocalFile(String filepath,String code)
	throws Exception{
		try {
			InputStream is = new FileInputStream(filepath);
			InputStreamReader isr = new InputStreamReader(is,code);   
			BufferedReader br = new BufferedReader(isr);
			
			String record = "";
			StringBuffer sb = new StringBuffer();
			while ((record = br.readLine()) != null) {
				sb.append(record.trim());
			}
			is.close();
			isr.close();
			br.close();
			return sb.toString() ;
		}catch(Exception e){
			throw new Exception("readLocalFile err:"+e.getMessage()) ;
		}
	}
	
	/**
	 * 读取文件，返回字符串
	 * @param path //工程相对路径
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public static String readLocalFile2(String path,String code)
	throws Exception {
		String filepath = ReadLocalFileMethod.class.getResource(path).getFile();
		return readLocalFile(filepath,code) ;
	}
	public static String readLocalFile2(String path)
	throws IOException {
//		会有缓存，第二次调用方法不会重新读取文件。
//		getResourceAsStream将文件读入缓存，无法重新加载文件。
		InputStream is = ReadLocalFileMethod.class.getResourceAsStream(path);
		InputStreamReader isr = new InputStreamReader(is,"utf-8");   
		BufferedReader br = new BufferedReader(isr);
		
		String record = "";
		StringBuffer sb = new StringBuffer();
		while ((record = br.readLine()) != null) {
			sb.append(record.trim());
		}
		br.close();
		isr.close();
		is.close();
		return sb.toString() ;
	}
	
}
