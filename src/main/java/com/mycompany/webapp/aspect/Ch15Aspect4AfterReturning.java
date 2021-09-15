package com.mycompany.webapp.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class Ch15Aspect4AfterReturning {
private static final Logger logger = LoggerFactory.getLogger(Ch15Aspect3After.class);
	
	@AfterReturning(
		pointcut="execution(public * com.mycompany.webapp.controller.Ch15Controller.afterReturning(..))",
		returning="returnValue"	// 리턴값 받아오기
	)
	public void method(String returnValue) { // 위 코드의 returning 동일한 이름으로 매개변수로를 사용해야한다.
		logger.info("실행");
		logger.info("리턴값 : "+ returnValue);
	}
}
