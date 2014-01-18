package com.my.test.util;

//json解析对象，必须是public的对象
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
