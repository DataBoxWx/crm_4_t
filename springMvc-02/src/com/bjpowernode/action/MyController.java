package com.bjpowernode.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value={"/user"})
public class MyController {
	
	@RequestMapping(value={"/first.do"})
	public ModelAndView doShow(){
		ModelAndView mv = new ModelAndView();
		mv.addObject("msg", "my two springMvc");
		mv.setViewName("show");
		return mv;
		
	}
}
