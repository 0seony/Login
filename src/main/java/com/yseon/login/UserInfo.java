package com.yseon.login;

public class UserInfo {
	int no;
	String id;
	String pwd;
	String name;
	String birthday;
	String address;
	String created;
	String updated;
	
	UserInfo(String id, String pwd, String name, String birthday, String address, String created, String updated){
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.birthday = birthday;
		this.address = address;
		this.created = created;
		this.updated = updated;
	}
	
	UserInfo(){
		
	}
	
	UserInfo(int no,String id, String name, String birthday, String address, String updated){
		this.no = no;
		this.id = id;
		this.name = name;
		this.birthday = birthday;
		this.address = address;
		this.updated = updated;
	}
	

}
