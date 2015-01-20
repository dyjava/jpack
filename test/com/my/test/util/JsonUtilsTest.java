package com.my.test.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import com.my.util.JsonUtils;

public class JsonUtilsTest {

	private Map<String,Object> map = new HashMap<String,Object>();
	private User u = new User() ;
	private List<Object> list = new ArrayList<Object>() ;
	public JsonUtilsTest(){
		map.put("name", "json");
		map.put("bool", Boolean.TRUE);
		map.put("int", new Integer(1));
		map.put("arr", new String[] { "a", "b" });
		map.put("func", "function(i){ return this.arr[i]; }");
		
		u.setAge(12) ;
		u.setName("dyong") ;

		list.add("first");
		list.add("second");
		list.add("北京");
		list.add("上海");
		list.add(125);
		list.add(1.2);
		list.add(true);
	}

	@Test
	public void bean2jsonTest(){
		String json = JsonUtils.bean2json(map) ;
		System.out.println(json.toString()) ;
		
		@SuppressWarnings("unchecked")
		Map<String,Object> map2 = JsonUtils.json2bean(json, HashMap.class) ;
		
		Assert.assertEquals(map.size(),map2.size()) ;
		Assert.assertEquals(map.get("int"),map2.get("int")) ;
	}

	@Test
	public void bean2json2Test(){
		String json = JsonUtils.bean2json(u) ;
		System.out.println(json.toString()) ;
		
		User u2 = JsonUtils.json2bean(json, User.class) ;
		
		Assert.assertEquals(u.getAge() , u2.getAge()) ;
	}
	
	@Test
	public void bean2json3Test(){
		
		String json = JsonUtils.bean2json(list) ;
		System.out.println(json.toString()) ;
		
		List<Object> list2 = JsonUtils.json2bean(json, List.class) ;
		Assert.assertEquals(list.size(), list2.size()) ;
	}

	@Test
	public void json2ListTest(){
		
		String json = JsonUtils.bean2json(list) ;
		System.out.println(json.toString()) ;
		
		List<Object> list2 = JsonUtils.json2List(json, Object.class) ;
		Assert.assertEquals(list.size(), list2.size()) ;
	}

}
