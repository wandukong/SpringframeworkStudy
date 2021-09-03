package com.mycompany.webapp.controller;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.mycompany.webapp.dto.Ch08InputForm;

@Controller
@RequestMapping("/ch08")
@SessionAttributes({"inputForm"})
public class Ch08Controller {
	private static final Logger logger = LoggerFactory.getLogger(Ch08Controller.class);

	@RequestMapping("/content")
	public String content() {
		logger.info("ch08 실행");
		return "ch08/content";
	}

	// Ajax를 이용해서 실습
	@GetMapping(value = "/saveData", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String saveData(String name, HttpSession session) {
		logger.info("saveData 실행");
		logger.info("name : " + name);

		// HttpSession session = request.getSession(); 해당 방식으로도 session을 이용할 수 있다.
		session.setAttribute("name", name);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", "success");
		String json = jsonObject.toString(); // {"result" : "success"}

		return json;
	}

	@GetMapping(value = "/readData", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String readData(HttpSession session, @SessionAttribute String name) {
		logger.info("readData 실행");

		// 방법1
		// String name = (String)session.getAttribute("name");

		// 방법2
		// parameter에 @SessionAttribute String name 추가하기

		logger.info("name : " + name);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", name);
		String json = jsonObject.toString(); // {"name" : "홍길동"}
		return json;
	}

	@GetMapping("/login")
	public String loginForm() {
		logger.info("loginForm 실행");
		return "ch08/loginForm";
	}

	@PostMapping("/login")
	public String login(String mid, String mpassword, HttpSession session) {
		logger.info("login 실행");
		if (mid.equals("spring") && mpassword.equals("12345")) {
			session.setAttribute("sessionMid", mid);
		}
		return "redirect:/ch08/content";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		logger.info("login 실행");

		// 로그아웃 -> 세션 제거
		// 방법1 : 해당 세션 삭제
		session.removeAttribute("sessionMid");
		// 방법2 : 모든 세션 무효화
		// session.invalidate();

		return "redirect:/ch08/content";
	}

	@PostMapping(value = "/loginAjax", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String loginAjax(String mid, String mpassword, HttpSession session) {
		logger.info("loginAjax 실행");
		String result = "success";

		if (!mid.equals("spring")) {
			result = "wrongMid";
		} else if (!mpassword.equals("12345")) {
			result = "wrongMpassword";
		} else {
			result = "success";
			session.setAttribute("sessionMid", mid);
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", result);
		String json = jsonObject.toString();
		return json;
	}

	@GetMapping(value = "/logoutAjax", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String loginAjax(HttpSession session) {
		logger.info("logoutAjax 실행");

		session.invalidate();

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", "success");
		String json = jsonObject.toString();
		return json;
	}

	// @SessionAttributes에 존재하면 한번만 실행된다.// session에 해당 속성이름이 존재하지 않을 때, 한번실행한다.
	@ModelAttribute("inputForm")
	public Ch08InputForm getInputForm() {
		Ch08InputForm inputForm = new Ch08InputForm();
		return inputForm;
	}

	@GetMapping("/inputStep1")
	public String inputStep1(@ModelAttribute("inputForm") Ch08InputForm inputForm) {
		logger.info("inputStep1 실행");
		return "ch08/inputStep1";
	}

	@PostMapping("/inputStep2")
	public String inputStep2(@ModelAttribute("inputForm") Ch08InputForm inputForm) {
		logger.info("inputStep2 실행");
		logger.info("data1: " + inputForm.getData1());
		logger.info("data2: " + inputForm.getData2());
		logger.info("data3: " + inputForm.getData3());
		logger.info("data4: " + inputForm.getData4());
		return "ch08/inputStep2";
	}

	@PostMapping("/inputDone")
	public String inputDone(@ModelAttribute("inputForm") Ch08InputForm inputForm, SessionStatus sessionStatus) {
		logger.info("inputDone 실행");
		logger.info("data1: " + inputForm.getData1());
		logger.info("data2: " + inputForm.getData2());
		logger.info("data3: " + inputForm.getData3());
		logger.info("data4: " + inputForm.getData4());
		
		sessionStatus.setComplete(); // @SessionAttributes 에 선언되어 있는 모든 세션 제거
		//session.removeAttribute("inputForm"); 이렇게 사용하면 절대 안됨. 안지워짐.
		return "redirect:/ch08/content";
	}
}
