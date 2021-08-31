package com.mycompany.webapp.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mycompany.webapp.dto.Ch04Member;
import com.mycompany.webapp.validator.Ch04MemberEmailValidator;
import com.mycompany.webapp.validator.Ch04MemberIdValidator;
import com.mycompany.webapp.validator.Ch04MemberPasswordValidator;
import com.mycompany.webapp.validator.Ch04MemberTelValidator;

@Controller
@RequestMapping("/ch04")
public class Ch04Controller {
	private static final Logger logger = LoggerFactory.getLogger(Ch04Controller.class);
	
	@RequestMapping("/content")
	public String content() {
		logger.info("ch04 실행");
		return "ch04/content";
	}
	
	@PostMapping("/method1")
	public String method1(){
		logger.info("method1 실행");
		return "redirect:/ch04/content";
	}
	
	@InitBinder("joinForm")
	public void joinFormSetValidate(WebDataBinder binder) {
		logger.info("실행");
		//binder.setValidator(new Ch04MemeberJoinFormValidator());
		
		binder.addValidators(
			new Ch04MemberIdValidator(),
			new Ch04MemberPasswordValidator(),
			new Ch04MemberEmailValidator(),
			new Ch04MemberTelValidator()
		);
	}
	
	// 스프링 프레임워크가 클래스의 첫글자를 소문자로 바꾼 이름으로 해당 객체를 내부적으로 관리하고 있다. => 같은 클래스를 사용하는 경우 같은 유효성검사를 하게된다. ex) 회원가입 vs 로그인
	// => 유효성 검사를 구분하기 위해 새로운 이름을 부여해준다 : @ModelAttribute("")
	@PostMapping("/join")
	public String join(@ModelAttribute("joinForm") @Valid Ch04Member member, BindingResult bindingResult){ // 컨트롤러는 올바른 데이터만을 받도록 한다. 
	// public String join(@ModelAttribute("joinForm") @Valid Ch04Member member, Errors errors){	 BindingResult 대신 Errors 사용 가능, 메소드도 같음.
		logger.info("join 실행");
		if(bindingResult.hasErrors()) {
			logger.info("다시 입력폼 제공 + 에러 메시지");
			return "ch04/content"; // forward
		}
		logger.info("정상 요청 처리 후 응답 제공");
		return "redirect:/ch04/content"; // redirect
	}
	
	@InitBinder("loginForm")
	public void loginFormSetValidate(WebDataBinder binder) {
		logger.info("실행");
		
		binder.addValidators(
			new Ch04MemberIdValidator(),
			new Ch04MemberPasswordValidator()
		);
	}
	
	@PostMapping("/login")
	public String login(@ModelAttribute("loginForm") @Valid Ch04Member member, Errors errors){ // 컨트롤러는 올바른 데이터만을 받도록 한다.
		logger.info("login 실행");
		if(errors.hasErrors()) {
			logger.info("다시 입력폼 제공 + 에러 메시지");
			return "ch04/content";
		}
		logger.info("정상 요청 처리 후 응답 제공");
		return "redirect:/ch04/content";
	}
}
