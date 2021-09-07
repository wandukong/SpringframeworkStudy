package com.mycompany.webapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class Ch13Service2 {

	private static final Logger logger = LoggerFactory.getLogger(Ch13Service2.class);
	
	
	public Ch13Service2() {
		logger.info("실행");
	}
}
