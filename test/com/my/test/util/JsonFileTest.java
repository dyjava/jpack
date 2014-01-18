package com.my.test.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import com.my.util.JsonFile;

public class JsonFileTest {

	@Test
	public void objectTest(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("name", "json");
		map.put("bool", Boolean.TRUE);
		map.put("int", new Integer(1));
		map.put("arr", new String[] { "a", "b" });
		map.put("func", "function(i){ return this.arr[i]; }");
		
		String json = JsonFile.obj2json(map) ;
		System.out.println(json.toString()) ;
		
		@SuppressWarnings("unchecked")
		Map<String,Object> map2 = JsonFile.json2Obj(json, HashMap.class) ;
		
		Assert.assertEquals(map.size(),map2.size()) ;
		Assert.assertEquals(map.get("int"),map2.get("int")) ;
	}

	@Test
	public void object2Test(){
		User u = new User() ;
		u.setAge(12) ;
		u.setName("dyong") ;
		
		String json = JsonFile.obj2json(u) ;
		System.out.println(json.toString()) ;
		
		User u2 = JsonFile.json2Obj(json, User.class) ;
		
		Assert.assertEquals(u.getAge() , u2.getAge()) ;
	}
	
	@Test
	public void listTest(){
		List<Object> list = new ArrayList<Object>() ;
		list.add("first");
		list.add("second");
		list.add("北京");
		list.add("上海");
		list.add(125);
		list.add(1.2);
		list.add(true);
		
		String json = JsonFile.obj2json(list) ;
		System.out.println(json.toString()) ;
		
		List<Object> list2 = JsonFile.json2ObjList(json, Object.class) ;
		Assert.assertEquals(list.size(), list2.size()) ;
		
		System.out.print("======= start ===========") ;
		for(Object us :list2){
			System.out.print(us.toString()+"\t") ;
		}
		System.out.println("======= end ===========") ;
	}

	@Test
	public void list2Test(){
		List<User> list = new ArrayList<User>() ;
		User u = new User() ;
		u.setAge(12) ;
		u.setName("dyong") ;
		list.add(u);
		u = new User() ;
		u.setAge(5) ;
		u.setName("ff") ;
		list.add(u);
		
		String json = JsonFile.obj2json(list) ;
		System.out.println(json.toString()) ;
		
		List<User> list2 = JsonFile.json2ObjList(json, User.class) ;
		Assert.assertEquals(list.size(), list2.size()) ;
		
		System.out.print("======= start2 ===========") ;
		for(User us :list2){
			System.out.print(us.toString()+"\t") ;
		}
		System.out.println("======= end2 ===========") ;

	}

	/**
	 * 未调通
	 */
	public void list3Test(){
		String[] list = {"test","测试","1234","true"} ;
		
		String json = JsonFile.obj2json(list) ;
		System.out.println(json.toString()) ;
		
		String[] list2 = JsonFile.json2ObjArray(json, String.class) ;
		Assert.assertEquals(list.length, list2.length) ;
		
		System.out.print("======= start3 ===========") ;
		for(String us :list2){
			System.out.print(us.toString()+"\t") ;
		}
		System.out.println("======= end3 ===========") ;

	}
}
