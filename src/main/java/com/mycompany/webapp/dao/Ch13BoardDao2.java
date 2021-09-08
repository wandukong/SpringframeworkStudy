package com.mycompany.webapp.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.mycompany.webapp.service.Ch13Service2;

@Repository //  기본 생성자를 이용해서 객체 생성
public class Ch13BoardDao2 {
	private static final Logger logger = LoggerFactory.getLogger(Ch13BoardDao2.class);

	public Ch13BoardDao2() {
		logger.info("Ch13BoardDao2() 생성자 실행");
	}
	
	public void update() {
		logger.info("update 실행");
	}
}
