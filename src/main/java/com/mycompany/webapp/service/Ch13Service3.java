package com.mycompany.webapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class Ch13Service3 implements Ch13Service {
	
	private static final Logger logger = LoggerFactory.getLogger(Ch13Service3.class);
	
	@Override
	public void method2() {
		logger.info("method2 실행");
	}
}
