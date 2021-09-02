package com.mycompany.webapp.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/ch06")
public class Ch06Controller {
	private static final Logger logger = LoggerFactory.getLogger(Ch06Controller.class);

	public Ch06Controller() {
		// @controller을 붙이면, 해당 클래스가 객체(Bean)로 만들어지고, webApplicationContext가 관리하게 된다.
		//logger.info("생성자 실행");
	}

	@RequestMapping("/content")
	public String content() {
		logger.info("ch06 실행");
		return "ch06/content";
	}

	@RequestMapping("/forward")
	public String forward() {
		logger.info("forward 실행");
		return "ch06/forward";
	}

	@RequestMapping("/redirect")
	public String redirect() {
		logger.info("redirect 실행");
		return "redirect:/ch01/content";
	}

	@GetMapping("/getFragmentHtml")
	public String getFragmentHtml() {
		logger.info("getFragmentHtml 실행");
		return "ch06/fragmentHtml";
	}

	@GetMapping("/getJson1")
	public void getJson1(HttpServletResponse response) throws IOException {
		logger.info("getJson1 실행");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("fileName", "photo5.jpg");
		String json = jsonObject.toString();

		// 응답 http에 body 부분에 json을 포함
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter pw = response.getWriter();
		pw.println(json);
		pw.flush();
		pw.close();
	}

	@GetMapping(value="/getJson2", produces="application/json; charset=UTF-8") // json이 만들어진다는 것을 명시해줘야 한다.
	@ResponseBody
	public String getJson2() {
		logger.info("getJson2 실행");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("fileName", "photo6.jpg");
		String json = jsonObject.toString();
		return json; // view이름 리턴하는 것이 아니라 response body에 들어가는 내용이 return 된다.
	}
	
	
	// redirect는 AJAX 요청시에는 사용할 수 없음
	@GetMapping(value="/getJson3")
	public String getJson3() {
		logger.info("getJson3 실행");
		return "redirect:/";
	}
}
