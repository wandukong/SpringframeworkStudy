package com.mycompany.webapp.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(1)
public class Ch15Aspect2Before {
	
	private static final Logger logger = LoggerFactory.getLogger(Ch15Aspect2Before.class);
	
	@Before("execution(public * com.mycompany.webapp.controller.Ch15Controller.before*(..))")
	public void method() {
		logger.info("실행");
	}
}
