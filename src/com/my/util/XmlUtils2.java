package com.my.util;

import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class XmlUtils2 {

	@SuppressWarnings("unchecked")
	public static <T> T xml2obj(String xml,Class<T> type){
		T obj = null;
		try {
			JAXBContext context = JAXBContext.newInstance(type) ;
			Unmarshaller unmarshaller = context.createUnmarshaller() ;
			obj = (T) unmarshaller.unmarshal(new StringReader(xml)) ;
		} catch (JAXBException e) {
			e.printStackTrace() ;
		}
		return obj ;
	}
	public static String obj2xml(Object obj){
		return obj2xml(obj, null) ;
	}
	private static final String root = "allresult" ;
	public static String obj2xml(Object obj, List<String> reg){
		if(reg==null){
			reg = new ArrayList<String>() ;
		}
		StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n") ;
		
		objectItem2XML(obj, xml, reg, root) ;
		return xml.toString() ;
	}
	@SuppressWarnings("unchecked")
	private static void objectItem2XML(Object obj, StringBuffer xml, List<String> reg, String parentname) {
//		String name = obj.getClass().getSimpleName() ;
//		System.out.println("========"+name); 
		//基础数据类型，直接输出
		if(isBase(obj)){
			apendXML(obj, xml,parentname) ;
		} else if(obj instanceof Collection){
			//集合对象，循环输出
			Collection<Object> c = (Collection<Object>)obj ;
			xml.append("<"+parentname+">\r\n") ;
			for(Object item:c){
				String itemname = parentname+"_item" ;
				objectItem2XML(item, xml, reg, itemname) ;
			}
			xml.append("</"+parentname+">\r\n") ;
		} else if(obj instanceof Map){
			//集合对象，循环输出
			Map<Object,Object> map = (Map<Object,Object>)obj ;
			xml.append("<"+parentname+">\r\n") ;
			for(Object key:map.keySet()){
				Object value = map.get(key) ;
				objectItem2XML(value, xml, reg, key.toString()) ;
			}
			xml.append("</"+parentname+">\r\n") ;
		} else if(obj instanceof Object){
			//对象循环输出属性
			//获取所有属性
			Field[] fs = obj.getClass().getDeclaredFields();	//获取私有属性。
			xml.append("<"+parentname+">\r\n") ;
			for(Field f:fs){
				try {
					f.setAccessible(true);//设置私有、保护变量的可以访问权限。
					//获取属性名和属性值
					String key = f.getName() ;
					Object values = f.get(obj) ;
					//对属性名进行过滤
					if(reg.size()>0 && !reg.contains(key.toLowerCase())){
						continue ;
					}
					objectItem2XML(values, xml, reg, key) ;
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			xml.append("</"+parentname+">\r\n") ;
		}
	}
	
	private static void apendXML(Object obj, StringBuffer xml, String tagname){
		xml.append("<").append(tagname).append(">") ;
		if(isString(obj)){
			xml.append("<![CDATA[").append(obj).append("]]>") ;
		} else {
			xml.append(obj) ;
		}
		xml.append("</").append(tagname).append(">") ;
	}
	
	private static boolean isBase(Object obj){
		if(isNum(obj) || isString(obj) || isBoolean(obj)){
			return true ;
		}
		return false ;
	}
	private static boolean isNum(Object obj){
		if(obj instanceof Integer || obj instanceof Long || obj instanceof Double){
			return true ;
		}
		return false ;
	}
	private static boolean isString(Object obj){
		if(obj instanceof String){
			return true ;
		}
		return false ;
	}
	private static boolean isBoolean(Object obj){
		if(obj instanceof Boolean){
			return true ;
		}
		return false ;
	}
	
	
}
