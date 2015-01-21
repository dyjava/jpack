package com.my.test.util;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.my.util.XmlUtils2;

public class XMLUtils2Test {

	private User u = new User() ;
	private List<Object> list = new ArrayList<Object>() ;
	public XMLUtils2Test(){
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
	public void xml2objTest(){
		String xml = XmlUtils2.obj2xml(u) ;
		System.out.println(xml) ;
		User user = XmlUtils2.xml2obj(xml, User.class) ;
		Assert.assertEquals(u.getName(), user.getName()) ;
	}

	@Test
	public void xml2obj2Test(){
		String xml = XmlUtils2.obj2xml(list) ;
		System.out.println(xml) ;
//		List<Object> list2 = XmlUtils2.xml2obj(xml, List.class) ;
		Assert.assertEquals(7, list.size()) ;
	}


}
