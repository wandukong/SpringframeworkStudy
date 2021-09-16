package com.mycompany.webapp.exeception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Component // 객체로 생성해서 관리하도록 설정 - 이 클래스로 미리 객체를 만들어 놓아라. 
@ControllerAdvice // 모든 컨트롤러에게 영향을 미치도록 설정
public class Ch10ExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(Ch10ExceptionHandler.class);
	
	public Ch10ExceptionHandler() {
		logger.info("Exception Handler 객체 생성");
	}
	
	//예외 처리자 설정
	@ExceptionHandler
	public String handleNullPointerException(NullPointerException e) { // 해당 예외를 처리하는데 사용할 것인가
		logger.info("handleException 실행");
		e.printStackTrace();
		return "error/500_null";
	}
	
	@ExceptionHandler
	public String handleClassCastException(ClassCastException e) { 
		logger.info("handleClassCastException 실행");
		e.printStackTrace();
		return "error/500_cast";
	}
	

	@ExceptionHandler
	public String handleRuntimeException(RuntimeException e) { 
		logger.info("handleRuntimeException 실행");
		e.printStackTrace();
		return "error/500";
	}
	
	@ExceptionHandler
	public String handleSoldOutException (Chap10SoldOutException e) { 
		logger.info("handleSoldOutException 실행");
		e.printStackTrace();
		return "error/soldout";
	}
	
	@ExceptionHandler
	public String handleNotFoundAccountException (Ch16NotFoundAccountException e, Model model) { 
		logger.info("handleNotFoundAccountException 실행");
		e.printStackTrace();
		model.addAttribute("error", e.getMessage());
		return "error/notFoundAccount";
	}
}
