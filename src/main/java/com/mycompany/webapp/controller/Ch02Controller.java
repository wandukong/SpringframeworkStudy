package com.mycompany.webapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/ch02")
public class Ch02Controller {
	private static final Logger logger = LoggerFactory.getLogger(Ch02Controller.class);
	
	@RequestMapping("/content")
	public String content() {
		logger.info("ch02 실행");
		return "ch02/content";
	}
	
	/*
	 * 요청 방식(메소드)에 따라 다른 메소드가 실행된다.
	 * */
	//@RequestMapping(value="/method1",method=RequestMethod.GET)
	@GetMapping("/method")
	public String method1() {
		logger.info("method1 실행");
		return "redirect:/ch02/content";
	}
	
	//@RequestMapping(value="/method",method=RequestMethod.POST)
	@PostMapping("/method")
	public String method2() {
		logger.info("method2 실행");
		return "redirect:/ch02/content";
	}
	
	//@RequestMapping(value="/method",method=RequestMethod.PUT)
	@PutMapping("/method")
	public String method3() {
		logger.info("method3 실행");
		return "redirect:/ch02/content";
	}
	
	//@RequestMapping(value="/method",method=RequestMethod.DELETE)
	@DeleteMapping("/method")
	public String method4() {
		logger.info("method4 실행");
		return "redirect:/ch02/content";
	}
	
	@GetMapping("/modelandview")
	public ModelAndView method5() {
		logger.info("실행");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ch02/modelAndView");
		return modelAndView;
	}
	
	@PostMapping("/login1")
	public String login1() {
		return "ch02/loginResult";
	}
	
	@PostMapping("/login2")
	public String login2() {
		return "redirect:/ch01/content";
	}
	
	@GetMapping("/boardlist")
	public String boardList() {
		return "ch02/boardList";
	}
	
	@GetMapping("/boardwriteform")
	public String boardWriteForm() {
		return "ch02/boardWriteForm";
	}
	
	@PostMapping("/boardwrite")
	public String boardWrite() {
		return "redirect:/ch02/boardlist";
	}
}
