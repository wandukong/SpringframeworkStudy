package com.mycompany.webapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mycompany.webapp.dto.Ch04Member;

@Controller
@RequestMapping("/ch04")
public class Ch04Controller {
	private static final Logger logger = LoggerFactory.getLogger(Ch04Controller.class);
	
	@RequestMapping("/content")
	public String content() {
		logger.info("ch04 실행");
		return "ch04/content";
	}
	
	@PostMapping("/method1")
	public String method1(){
		logger.info("method1 실행");
		return "redirect:/ch04/content";
	}
	
	@PostMapping("/method2")
	public String method2(Ch04Member member){
		logger.info("method2 실행");
		logger.info("mid: " + member.getMid());
		logger.info("mpassword: " + member.getMpassword());
		logger.info("memail: " + member.getMemail());
		logger.info("mtel: " + member.getMtel());
		return "redirect:/ch04/content";
	}
}
