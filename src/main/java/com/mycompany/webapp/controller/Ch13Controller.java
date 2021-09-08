package com.mycompany.webapp.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mycompany.webapp.service.Ch13Service;
import com.mycompany.webapp.service.Ch13Service1;
import com.mycompany.webapp.service.Ch13Service2;
import com.mycompany.webapp.service.Ch13Service5;

@Controller
@RequestMapping("/ch13")
public class Ch13Controller {

	private static final Logger logger = LoggerFactory.getLogger(Ch13Controller.class);

	private Ch13Service1 ch13Service1; // 의존성 주입이 일어나서, 참조할 객체가 생성되어야 한다.

	@Resource // @Resource를 쓰면 더 편리하다. - 스프링이 관리하는 beanname으로 객체를 찾을 수 있다.
	private Ch13Service2 ch13Service2;
	
	@Resource(name="ch13Service4") // 지정된 name으로 빈 객체 찾음
	private Ch13Service ch13Service; // interface 사용 -> Ch13Service13의 객체가 주입이됨
	
	
	// 빈이 컨트롤러를 기본 생성자를 사용하여 컨트롤러 객체를 생성하기 때문에
	// 컨트롤러에서는 생성자 주입 방식을 사용할 수 없다.

	public void setCh13Service1(Ch13Service1 ch13Service1) {
		logger.info("setCh13Service1() setter 실행");
		this.ch13Service1 = ch13Service1;
	}
	
	/*
	//@Autowired // 자동으로 실행
	@Resource
	public void setCh13Service2(Ch13Service2 ch13Service2) { // 스프링이 관리하고 있는 @Service로 생성된 ch13Sevice2 객체를 자동으로 주입함
		logger.info("setCh13Service2() setter 실행");
		this.ch13Service2 = ch13Service2;
	}
	*/
	
	@RequestMapping("/content")
	public String content() {
		logger.info("cotent 실행");
		return "ch13/content";
	}

	@GetMapping("/request1")
	public String request1() {
		logger.info("request1 실행");
		ch13Service1.method1();
		return "redirect:/ch13/content";
	}
	
	@GetMapping("/request2")
	public String request2() {
		logger.info("request2 실행");
		ch13Service2.method1();
		return "redirect:/ch13/content";
	}
	
	@GetMapping("/request3")
	public String request3() {
		logger.info("request2 실행");
		ch13Service.method2();
		return "redirect:/ch13/content";
	}
}
