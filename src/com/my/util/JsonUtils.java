package com.my.util;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class JsonUtils {

	public static String bean2json(Object bean) {
		Gson gson = new GsonBuilder()  
//        .excludeFieldsWithoutExposeAnnotation() //不导出实体中没有用@Expose注解的属性  
        .enableComplexMapKeySerialization() //支持Map的key为复杂对象的形式  
        .serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss")//时间转化为特定格式    
//        .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)//会把字段首字母大写,注:对于实体上使用了@SerializedName注解的不会生效.  
        .setFieldNamingStrategy(new MyFieldNamingStrategy())//设置字段名重命名
        .setPrettyPrinting() //对json结果格式化.  
//        .setVersion(1.0)    //有的字段不是一开始就有的,会随着版本的升级添加进来,那么在进行序列化和返序列化的时候就会根据版本号来选择是否要序列化.  
//                            //@Since(版本号)能完美地实现这个功能.还的字段可能,随着版本的升级而删除,那么  
//                            //@Until(版本号)也能实现这个功能,GsonBuilder.setVersion(double)方法需要调用.
        .create();
		
		return gson.toJson(bean);
	}

	public static String bean2json(Object bean, final List<String> reg) {
        final String regtitle = "|status|warning|count|total|total_found|usedTime|error|errors|limit|pn|rn|zip|uuid|matches|list|"+
        "item|id|name|";
        //创建一个带过滤条件的gson对象       
        Gson gson = new GsonBuilder()
        .enableComplexMapKeySerialization() //支持Map的key为复杂对象的形式  
        .serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss")//时间转化为特定格式 
        .setExclusionStrategies(new ExclusionStrategy() {
        	/*** 设置要过滤的属性*/
        	@Override
        	public boolean shouldSkipField(FieldAttributes attr) {
        		//我们只过滤User类的id属性，而Type类的id属性还是要输出的                   
        		boolean b = false;
        		b = regtitle.contains("|" + attr.getName().toLowerCase() + "|") ;
        		if(!b){
        			b = reg.contains( attr.getName() );
        		}
        		
        		//这里，如果返回true就表示此属性要过滤，否则就输出                    
        		return !b;
        	}
        	/*** 设置要过滤的类*/
        	@Override
        	public boolean shouldSkipClass(Class<?> clazz) {
        		//这里，如果返回true就表示此类要过滤，否则就输出                    
        		return false;
        	}
        })
        //设置字段名重命名
        .setFieldNamingStrategy(new MyFieldNamingStrategy())
        .setPrettyPrinting() //对json结果格式化.
        .create();
        
		return gson.toJson(bean);
	}

	public static <T> T json2bean(String json, Class<T> type) {
		Gson gson = new GsonBuilder().create();
//		Gson gson = new GsonBuilder().
//		registerTypeAdapter(java.sql.Date.class,new SQLDateDeserializer()).
//		registerTypeAdapter(java.util.Date.class, new UtilDateDeserializer()).setDateFormat(DateFormat.LONG).create();
		return gson.fromJson(json, type);
	}
	public static <T> List<T> json2List(String json, Class<T> type) {
		Type listType = new TypeToken<ArrayList<T>>() {}.getType();
		Gson gson = new GsonBuilder().create();
		return gson.fromJson(json, listType);
	}
	
	public static void main(String[] ar){
		String resultset = "{\"1\":[\"region\",\"price\",\"open\"],\"3\":[\"x\",\"y\",\"style\",\"introduction\"]}" ;
		Hashtable<String, List<String>> table = JsonUtils.json2bean(resultset , Hashtable.class) ;
		System.out.println(table.size());
	}
}

class MyFieldNamingStrategy implements FieldNamingStrategy{
	@Override
	public String translateName(Field f) {
//		System.out.println(f.getName());
		if ("total".equals(f.getName())) {
            return "total_found";
        }
		if ("errors".equals(f.getName())) {
            return "error";
        }
        return f.getName();
	}
	
}