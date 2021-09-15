package com.mycompany.webapp.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mycompany.webapp.dto.Ch14Board;
import com.mycompany.webapp.dto.Ch14Member;
import com.mycompany.webapp.dto.Pager;
import com.mycompany.webapp.service.Ch14BoardService;
import com.mycompany.webapp.service.Ch14MemberService;
import com.mycompany.webapp.service.Ch14MemberService.LoginResult;

@Controller
@RequestMapping("/ch15")
public class Ch15Controller {
	
	private static final Logger logger = LoggerFactory.getLogger(Ch15Controller.class);

	@RequestMapping("/content")
	public String content() {
		logger.info("content 실행");
		return "ch15/content";
	}
	
	@RequestMapping("/before")
	public String before() {
		logger.info("before 실행");
		return "redirect:/ch15/content";
	}
	

	@RequestMapping("/after")
	public String afterXXX() {
		logger.info("afterXXX 실행");
		return "redirect:/ch15/content";
	}
	
	@RequestMapping("/afterReturning")
	public String afterReturning() {
		logger.info("afterReturning 실행");
		return "redirect:/ch15/content";
	}
	
	@RequestMapping("/afterThrowing")
	public String afterThrowing() {
		logger.info("afterThrowing 실행");
		if(true) {
			throw new RuntimeException("테스트 예외입니다.");
		}
		return "redirect:/ch15/content";
	}
	
	@RequestMapping("/around")
	public String around() {
		logger.info("around 실행");
		return "redirect:/ch15/content";
	}
	
	@Resource
	private Ch14BoardService boardService;
	
	@RequestMapping("/runTimeCheck")
	public String runTimeCheck() {
		logger.info("runTimeCheck 실행");
		Pager pager = new Pager(10, 5, boardService.getTotalBoardNum(), 1);
		List<Ch14Board> boards = boardService.getBoards(pager);
		return "redirect:/ch15/content";
	}
	@RequestMapping("/authCheck")
	public String authCheck() {
		logger.info("authCheck 실행");
		return "redirect:/ch15/content";
	}
	
	@GetMapping("/login")
	public String loginForm( ) {
		return "ch15/loginForm";
	}
	
	@Resource
	private Ch14MemberService memberService;
	
	@PostMapping("/login")
	public String login(Ch14Member member, Model model, HttpSession session) {
		logger.info("login 실행");
		LoginResult result = memberService.login(member);
		if(result == LoginResult.SUCCESS) {
			session.setAttribute("sessionMid", member.getMid());
			return "redirect:/";
		}else if(result == LoginResult.WRONG_ID) {
			model.addAttribute("error", "아이디가 존재하지 않습니다.");
			return "ch15/loginForm";
		}else if(result == LoginResult.WRONG_PASSWORD) {
			model.addAttribute("error", "패스워드가 틀립니다.");
			return "ch15/loginForm";
		}else {
			model.addAttribute("error", "로그인에 실패하였습니다. 다시 시도해 주세요.");
			return "ch15/loginForm";
		}
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		logger.info("logout 실행");
		session.removeAttribute("sessionMid");
		return "redirect:/ch15/content";
	}
	
	@GetMapping("/getBoardList")
	public String getBoardList(Model model) {
		logger.info("getBoardList 실행");
		Pager pager = new Pager(5, 5, boardService.getTotalBoardNum(), 1);
		List<Ch14Board> boards = boardService.getBoards(pager);
		model.addAttribute("boards", boards);
		return "ch15/boardList";
	}
}
