package com.my.test.util;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.my.util.XmlUtils;

public class XMLUtilsTest {

	private User u = new User() ;
	private List<Object> list = new ArrayList<Object>() ;
	public XMLUtilsTest(){
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
	public void obj2XMLTest(){
		String xml = XmlUtils.obj2xml(u) ;
		System.out.println(xml.toString()) ;
		Assert.assertNotNull(xml);
	}
	
	@Test
	public void xml2objTest(){
		String xml = XmlUtils.obj2xml(u) ;
		System.out.println(xml) ;
		User user = XmlUtils.xml2obj(xml, User.class) ;
		Assert.assertEquals(u.getName(), user.getName()) ;
	}

	@Test
	public void obj2xml2Test(){
		String xml = XmlUtils.obj2xml(list) ;
		System.out.println(xml) ;
		Assert.assertTrue(xml.length()>0);
	}

	@Test
	public void xml2obj2Test(){
		String xml = XmlUtils.obj2xml(list) ;
		System.out.println(xml) ;
		List<Object> list2 = XmlUtils.xml2obj(xml, List.class) ;
		Assert.assertEquals(7, list2.size()) ;
	}

//	@Test
//	public void json2XML3Test(){
//		List<User> list = new ArrayList<User>() ;
//		User u = new User() ;
//		u.setAge(12) ;
//		u.setName("dyong") ;
//		list.add(u);
//		u = new User() ;
//		u.setAge(5) ;
//		u.setName("ff") ;
//		list.add(u);
//		
//		String json = JsonUtil.obj2json(list) ;
//
//		System.out.println("==========json2XML3Test==========") ;
//		System.out.println(json) ;
//		String xml = JsonUtil.listJson2XML(json,"Result") ;
//		System.out.println(xml) ;
//	}

}
