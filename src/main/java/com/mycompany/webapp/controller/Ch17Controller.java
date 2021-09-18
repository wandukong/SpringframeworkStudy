package com.mycompany.webapp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mycompany.webapp.dto.Ch14Member;
import com.mycompany.webapp.service.Ch14MemberService;
import com.mycompany.webapp.service.Ch14MemberService.JoinResult;


@Controller
@RequestMapping("/ch17")
public class Ch17Controller {
	private static final Logger logger = LoggerFactory.getLogger(Ch17Controller.class);
	@RequestMapping("/content")
	public String content(Model model) {
		return "ch17/content";
	}
	
	@RequestMapping("/loginForm")
	public String loginForm() {
		return "ch17/loginForm";
	}

	@RequestMapping("/adminAction")
	public String adminActinon() {
		logger.info("adminActinon 실행");
		return "redirect:/ch17/content";
	}
	
	@RequestMapping("/managerAction")
	public String managerAction() {
		logger.info("managerAction 실행");
		return "redirect:/ch17/content";
	}
	
	@RequestMapping("/userAction")
	public String userAction() {
		logger.info("userAction 실행");
		return "redirect:/ch17/content";
	}
	
	@RequestMapping("/error403")
	public String error403() {
		logger.info("error403 실행");
		return "/ch17/error403";
	}
	
	@RequestMapping("/joinForm")
	public String joinForm() {
		return "ch17/joinForm";
	}
	
	@Resource
	private Ch14MemberService memberService;
	
	@PostMapping("/join")
	public String join(Ch14Member member, Model model) {
		logger.info("join 실행");
		member.setMenabled(1);
		
		// 패스워드 암호화
		String mpassword = member.getMpassword();
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		mpassword = "{bcrypt}"+passwordEncoder.encode(mpassword);
		member.setMpassword(mpassword);
		
		//회원 가입 처리
		JoinResult jr = memberService.join(member);
		if(jr == JoinResult.SUCCESS) {
			return "redirect:/ch17/loginForm";
		}else if(jr == JoinResult.DUPLICATED) {
			model.addAttribute("error", "중복된 아이디가 있습니다.");
			return "ch17/joinForm";
		}else { // 예외 발생시
			model.addAttribute("error", "회원 가입이 실패되었습니다. 다시 시도해 주세요.");
			return "ch17/joinForm";
		}
	}
	
	@RequestMapping(value="/userInfo", produces="application/json; charset=UTF-8")
	@ResponseBody
	public String userInfo(Authentication authentication) {
		logger.info("userInfo 실행");
		
		/*
		// Spring Security가 인증 정보를 저장하는 컨테이너 객체를 얻기
		SecurityContext securityContext = SecurityContextHolder.getContext();
		
		// 인증 정보 객체를 얻기
		Authentication authentication = securityContext.getAuthentication();
		*/
		
		// 사용자 아이디 얻기 
		String mid = authentication.getName();
		
		// 사용자 권한(롤) 리스트 얻기
		List<String> authorityList = new ArrayList<>();
		for(GrantedAuthority authority : authentication.getAuthorities()) {
			authorityList.add(authority.getAuthority());
		}
		
		//사용자가 로그인한 PC의 IP 주소 얻기
		WebAuthenticationDetails wad = (WebAuthenticationDetails) authentication.getDetails();
		String ip = wad.getRemoteAddress();
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("mid", mid);
		jsonObject.put("mrole", authorityList);
		jsonObject.put("ip", ip);
		String json = jsonObject.toString();
		return json;
	}
}
