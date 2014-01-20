package com.my.util;

import java.util.List;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

public class JsonUtil<T> {
	
	/**
	 * 对象转json
	 * @param obj
	 * @return
	 */
	public static String obj2json(Object obj){
		if(obj instanceof List){
			JSONArray json = JSONArray.fromObject(obj) ;
			return json.toString() ;
		} else {
			JSONObject json = JSONObject.fromObject(obj) ;
			return json.toString() ;
		}
	}
	
	/**
	 * JSON转对象
	 * @param json
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T json2Obj(String json,Class<T> clazz){
		JSONObject jsonObject = JSONObject.fromObject(json) ;
		return (T) JSONObject.toBean(jsonObject,clazz) ;
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public static <T> List<T> json2ObjList(String json,Class<T> clazz){
		JSONArray jsonObject = JSONArray.fromObject(json) ;
		return (List<T>) JSONArray.toList(jsonObject,clazz) ;
	}

	@SuppressWarnings({ "unchecked" })
	public static <T> T[] json2ObjArray(String json,Class<T> clazz){
		JSONArray jsonObject = JSONArray.fromObject(json) ;
		return (T[]) JSONArray.toArray(jsonObject,clazz) ;
	}
	
	/**
	 * 对象转XML
	 * @param obj
	 * @return
	 */
	public static String obj2XML(Object obj){
		XMLSerializer xml = new XMLSerializer() ;
		xml.setRootName("Result") ;
		xml.setTypeHintsEnabled(false) ;
		xml.setTypeHintsCompatibility(false) ;
		JSON json ;
		if(obj instanceof List){
			json = JSONArray.fromObject(obj) ;
			return xml.write(json,"gbk") ;
		} else {
			json = JSONObject.fromObject(obj) ;
			return xml.write(json,"gbk") ;
		}
	}

	/**
	 * 对象json转xml
	 * @param jsonstr
	 * @param rootname
	 * @return
	 */
	public static String objJson2XML(String jsonstr,String rootname){
		XMLSerializer xml = new XMLSerializer() ;
		xml.setRootName(rootname) ;
		xml.setTypeHintsEnabled(false) ;
		xml.setTypeHintsCompatibility(false) ;
		xml.setElementName("list") ;
		
		JSON json = JSONObject.fromObject(jsonstr) ;
		return xml.write(json,"gbk") ;
	}
	/**
	 * 数组json转xml
	 * @param jsonstr
	 * @param rootname
	 * @return
	 */
	public static String listJson2XML(String jsonstr,String rootname){
		XMLSerializer xml = new XMLSerializer() ;
		xml.setRootName(rootname) ;
		xml.setTypeHintsEnabled(false) ;
		xml.setTypeHintsCompatibility(false) ;
		xml.setElementName("list") ;
		
		JSON json = JSONArray.fromObject(jsonstr) ;
		return xml.write(json,"gbk") ;
	}
}
