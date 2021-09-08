package com.mycompany.webapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class Ch13Service4 implements Ch13Service {
	
	private static final Logger logger = LoggerFactory.getLogger(Ch13Service4.class);
	
	@Override
	public void method2() {
		logger.info("method2 실행");
	}
}
