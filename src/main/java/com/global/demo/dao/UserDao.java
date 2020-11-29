package com.global.demo.dao;

import org.springframework.stereotype.Component;

@Component
public class UserDao implements IUserDao{
	
	public String add(String username, String password){
		System.out.println("add [username: " + username + ", password: " + password + "]");
		int a = 1 / 0;
        return "doWithException";
	}
}
