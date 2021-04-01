package com.example.demo;

import java.util.LinkedList;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	
	List<User> list=new LinkedList<User>();

	@RequestMapping(method = RequestMethod.GET,value = "/hello")
	public String hello(@RequestParam String firstName, @RequestParam String lastName) {
		list.add(new User(firstName,lastName));
		System.out.println(firstName);
		String rez= "Здравствуйте " + firstName.substring(0,1)+"."+lastName +"!";
		return rez;
	}

	
	@RequestMapping(method = RequestMethod.POST,value = "/hello")
	public void hello(@RequestBody User user) {
		System.out.println(user.firstName);
		list.add(user);
	}
	
	@RequestMapping("/list")
	public List<User> list(){
		return list;
	}
	
	@RequestMapping("/get/{firstName}/{lastName}")
	public User get(@PathVariable String firstName,@PathVariable String lastName){
		for(User user:list) {
			if(user.firstName.equals(firstName) && user.lastName.endsWith(lastName))
				return user;
		}
		return null;
	}

}
