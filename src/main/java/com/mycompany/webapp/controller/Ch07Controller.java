package com.mycompany.webapp.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mycompany.webapp.dto.Ch07Board;
import com.mycompany.webapp.dto.Ch07City;
import com.mycompany.webapp.dto.Ch07Cloth;
import com.mycompany.webapp.dto.Ch07Member;

@Controller
@RequestMapping("/ch07")
public class Ch07Controller {
	private static final Logger logger = LoggerFactory.getLogger(Ch07Controller.class);

	@RequestMapping("/content")
	public String content() {
		logger.info("ch07 실행");
		return "ch07/content";
	}

	@RequestMapping("/saveData")
	public String saveData(HttpServletRequest request) {
		logger.info("saveData 실행");

		// Request 범위에 데이터 저장 - 해당 요청에서만 존재한다.
		request.setAttribute("requestData", "자바");

		// Session 범위에 데이터 저장 - 브라우저가 켜져있는 동안에만 존재한다.
		HttpSession session = request.getSession();
		session.setAttribute("sessionData", "자바스크립트");

		// Application 범위에 데이터를 저장
		ServletContext application = request.getServletContext();
		application.setAttribute("applicationData", "스프링프레임워크");

		return "ch07/readData";
	}

	@RequestMapping("/readData")
	public String readData() {
		logger.info("readData 실행");
		return "ch07/readData";
	}

	@GetMapping("/objectSaveAndRead1")
	public String objectSaveAndRead1(HttpServletRequest request) {
		logger.info("objectSaveAndRead1 실행");

		Ch07Member member = new Ch07Member();
		member.setName("홍길동");
		member.setAge(25);
		member.setJob("개발자");
		Ch07City city = new Ch07City();
		city.setName("서울");
		member.setCity(city);

		request.setAttribute("member", member);

		return "ch07/objectRead";
	}

	@GetMapping("/objectSaveAndRead2")
	public ModelAndView objectSaveAndRead2() {
		logger.info("objectSaveAndRead2 실행");

		Ch07Member member = new Ch07Member();
		member.setName("홍길동");
		member.setAge(25);
		member.setJob("개발자");
		Ch07City city = new Ch07City();
		city.setName("서울");
		member.setCity(city);

		ModelAndView mav = new ModelAndView();
		mav.addObject("member", member); // request 범위에 저장
		mav.setViewName("ch07/objectRead"); // 넘겨줄 뷰

		return mav;
	}

	@GetMapping("/objectSaveAndRead3")
	public String objectSaveAndRead3(Model model) {
		logger.info("objectSaveAndRead3 실행");

		Ch07Member member = new Ch07Member();
		member.setName("홍길동");
		member.setAge(25);
		member.setJob("개발자");
		Ch07City city = new Ch07City();
		city.setName("서울");
		member.setCity(city);

		model.addAttribute("member", member);

		return "ch07/objectRead";
	}

	@GetMapping("/useJstl1")
	public String useJstl1(Model model) {
		logger.info("useJstl1 실행");

		String[] languages = { "Java", "JavaScript", "Spring Framework", "Vue" };
		model.addAttribute("langs", languages);

		return "ch07/useJstl1";
	}

	@GetMapping("/useJstl2")
	public String useJstl2(Model model) {
		logger.info("useJstl2 실행");

		List<Ch07Board> list = new ArrayList<Ch07Board>();
		for (int i = 1; i <= 5; i++) 
			list.add(new Ch07Board(i, "제목" + i, "내용" + i, "글쓴이" + i, new Date()));
		model.addAttribute("boardList",list);
		return "ch07/useJstl2";
	}
	
	@ModelAttribute("colors") // /ch07 안에 속하는 모든 경로가 호출될때 실행됨. request 범위에 저장
	public String[] getColors() {
		logger.info("getColors 실행");
		String[] colors = {"red", "Green", "Blue", "Yellow", "Pink"};
		return colors;
	}

	@GetMapping("/useModelAttribute1")
	public String useModelAttribute1() {
		logger.info("useModelAttribute1 실행");
		return "ch07/useModelAttribute";
	}
	
	@GetMapping("/useModelAttribute2")
	public String useModelAttribute2() {
		logger.info("useModelAttribute2 실행");
		return "ch07/useModelAttribute";
	}
	
	@GetMapping("/argumentSaveAndRead1")
	public String argumentSaveAndRead1(@ModelAttribute("kind") String kind, @ModelAttribute("sex") String sex){ // 해당 매개변수의 이름과 값을 request 범위에 저장한다.
		logger.info("argumentSaveAndRead1 실행");
		logger.info(kind +" " + sex);
		return "ch07/argumentRead1";
	}
	
	@GetMapping("/argumentSaveAndRead2")
	public String argumentSaveAndRead2(@ModelAttribute Ch07Cloth cloth) { // modelAttribute에 괄호를 사용하지 않으면 클래스 이름의 첫글자를 소문자로 바꾼 이름으로 사용할 수 있다.
		logger.info("argumentSaveAndRead2 실행");
		
		logger.info("kind: " + cloth.getKind());
		logger.info("sex: " + cloth.getSex());
		
		return "ch07/argumentRead2";
	}
}