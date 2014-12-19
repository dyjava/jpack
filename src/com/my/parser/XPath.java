package com.my.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

/**
 * made by dyong 
 * 
 **/
public class XPath {
	private HtmlCleaner cleaner = new HtmlCleaner();
	private static XPath xpath = new XPath() ;
	private TagNode rootNode ;
	
	public static XPath getInstens(String content){
		xpath.rootNode = xpath.cleaner.clean(content);
		return xpath ;
	}

	public List<String> getHref(String content,String xpath) throws IOException, XPatherException{
//		TagNode rootNode = cleaner.clean(content);

		Object[] tns = rootNode.evaluateXPath(xpath) ;
		if(tns==null || tns.length==0){
			return new ArrayList<String>() ;
		}
		List<String> list = new ArrayList<String>() ;
		for(Object obj:tns){
			String msg = ((TagNode)obj).getAttributeByName("href");
			list.add(msg) ;
		}
		return list;
		
	}
	

	public List<String> getHtml(String content,String xpath) throws IOException, XPatherException{
//		TagNode rootNode = cleaner.clean(content);

		Object[] tns = rootNode.evaluateXPath(xpath) ;
		if(tns==null || tns.length==0){
			return new ArrayList<String>() ;
		}
		List<String> list = new ArrayList<String>() ;
		for(Object obj:tns){
			String msg = cleaner.getInnerHtml((TagNode)obj);
			msg = msg.replaceAll("<.*?>", "").trim() ;
			list.add(msg) ;
		}
		return list;
		
	}
	
	public HashMap<String,List<String>> getHtml(String content,List<String> xpath) throws IOException, XPatherException{
//		TagNode rootNode = cleaner.clean(content);
//		String ss = cleaner.getInnerHtml((TagNode)rootNode.evaluateXPath("//div[@id='htmlContent']")[0]) ;
		HashMap<String,List<String>> result = new HashMap<String,List<String>>() ;
		for(String item:xpath){
			Object[] tns = rootNode.evaluateXPath(item) ;
			if(tns==null || tns.length==0){
				result.put(item,new ArrayList<String>()) ;
			}
			List<String> list = new ArrayList<String>() ;
			for(Object obj:tns){
				String msg = cleaner.getInnerHtml((TagNode)obj);
				list.add(msg) ;
			}
			result.put(item, list) ;
		}
		return result;
		
	}
}
