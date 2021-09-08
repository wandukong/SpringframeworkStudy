package com.mycompany.webapp.service;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mycompany.webapp.dao.Ch13BoardDao2;

@Service // 기본 생성자를 이용해서 객체 생성
public class Ch13Service2 {

	private static final Logger logger = LoggerFactory.getLogger(Ch13Service2.class);
	
	//@Autowired //필드에 작성하면, 자동으로 주입됨
	@Resource // @Autowired와 똑같지만, @Resource를 더 많이 사용
	private Ch13BoardDao2 ch13BoardDao2;
	
	/*
	@Autowired  // 자동으로 호출시킴
	@Resource 
	public void setCh13BoardDao2(Ch13BoardDao2 ch13BoardDao2) { // 스프링이 관리하는 @Repository가 붙어 생성된 객체를 자동 주입됨.
		logger.info("setCh13BoardDao2() setter 실행");
		this.ch13BoardDao2 = ch13BoardDao2;
	}
	*/
	
	public Ch13Service2() {
		logger.info("실행");
	}
	
	public void method1() {
		logger.info("method1()실행");
		ch13BoardDao2.update();
	}
}
