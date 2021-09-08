package com.mycompany.webapp.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Ch13BoardDao1 {
	private static final Logger logger = LoggerFactory.getLogger(Ch13BoardDao1.class);
	
	
	public Ch13BoardDao1() {
		logger.info("생성자 실행");
	}
	
	public void update() {
		logger.info("update 실행");
	}
}
