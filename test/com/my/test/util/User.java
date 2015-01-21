package com.my.test.util;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

//json解析对象，必须是public的对象
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "user") 
public class User {
	
	public String name ;
	public int age ;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	public String toString(){
		return name+":"+age ;
	}
}
