package com.mycompany.webapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mycompany.webapp.dao.Ch13BoardDao1;

public class Ch13Service1 {

	private static final Logger logger = LoggerFactory.getLogger(Ch13Service1.class);

	private Ch13BoardDao1 ch13BoardDao1; // dao 객체를 injection 해줘야한다.
	
	public Ch13Service1() {
		logger.info("Ch13Service1() 생성자 실행");
	}
	
	public Ch13Service1(Ch13BoardDao1 ch13BoardDao1) { // 생성자 주입 방식
		logger.info("Ch13Service1(Ch13BoardDao1 ch13BoardDao1) 생성자2 실행");
		this.ch13BoardDao1 = ch13BoardDao1;
	}
	
	public void setCh13BoardDao1(Ch13BoardDao1 ch13BoardDao1) { // setter 주입 방식
		logger.info("setCh13BoardDao1() setter 실행");
		this.ch13BoardDao1 = ch13BoardDao1;
	}
	
	public void method1() {
		logger.info("method1() 실행");
		ch13BoardDao1.update();
	}
}
