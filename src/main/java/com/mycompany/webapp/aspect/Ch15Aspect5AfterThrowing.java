package com.mycompany.webapp.aspect;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class Ch15Aspect5AfterThrowing {
	private static final Logger logger = LoggerFactory.getLogger(Ch15Aspect5AfterThrowing.class);

	@AfterThrowing(
		pointcut = "execution(public * com.mycompany.webapp.controller.Ch15Controller.afterThrowing(..))", 
		throwing = "e" // 리턴값
	)
	public void method(Throwable e) { // 위 코드의 returning 동일한 이름으로 매개변수로를 사용해야한다.
		logger.info("실행");
		logger.info("예외 메시지 : " + e.getMessage());
	}
}
