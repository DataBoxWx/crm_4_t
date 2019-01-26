package com.bjpowernode.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class JunController {
	@RequestMapping(value="/jump.do")
	public String jum(String target){
		
		return target;
	}
}
