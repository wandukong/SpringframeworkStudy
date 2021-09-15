package com.mycompany.webapp.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class Ch15Aspect3After {
	private static final Logger logger = LoggerFactory.getLogger(Ch15Aspect3After.class);

	@After("execution(public * com.mycompany.webapp.controller.Ch15Controller.after*(..))")
	public void method() {
		logger.info("실행");
	}
}
