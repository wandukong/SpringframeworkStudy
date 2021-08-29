package com.mycompany.webapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // @RequestMapping 을 사용하려면, @Controller가 필요하다.
@RequestMapping("/ch01")
public class Ch01Controller {
	private static final Logger logger = LoggerFactory.getLogger(Ch01Controller.class);

	@RequestMapping("/content") // @RequestMapping : 클라이언트가 해당 url로 요청을 하면 해당 메소드를 실행시킨다. 
	public String home() {
		logger.info("ch01 실행");
		return "ch01/content"; // jsp 경로
	}
}
