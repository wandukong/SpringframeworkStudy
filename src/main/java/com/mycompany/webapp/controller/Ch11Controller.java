package com.mycompany.webapp.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mycompany.webapp.dto.Ch11City;
import com.mycompany.webapp.dto.Ch11Member;
import com.mycompany.webapp.dto.Ch11Skill;

@Controller
@RequestMapping("/ch11")
public class Ch11Controller {
	private static final Logger logger = LoggerFactory.getLogger(Ch10Controller.class);

	@RequestMapping("/content")
	public String content() {
		logger.info("ch11 실행");
		return "ch11/content";
	}

	/*
	@RequestMapping("/form1")
	public String form1(Model model) {
		logger.info("ch11 실행");
		String defaultNation = "한국";
		model.addAttribute("defaultNation", defaultNation);
		return "ch11/form1";
	}
	*/

	@GetMapping("/form1")
	public String form1(@ModelAttribute("member") Ch11Member member) { // @ModelAttribute: request범위에 member 객체가 생성됨
		logger.info("form1 get 실행");
		member.setMnation("한국"); // 기본 값을 제공하기 위해 사용. 매개변수 없어도 된다.
		return "ch11/form1";
	}

	@PostMapping("/form1")
	public String handleForm1(Ch11Member member) { // @ModelAttribute: request범위에 member 객체가 생성됨
		logger.info("handleForm1 실행");
		logger.info("mid: " + member.getMid());
		logger.info("mname: " + member.getMname());
		logger.info("mpassword: " + member.getMpassword());
		logger.info("mnation: " + member.getMnation());
		return "redirect:/ch11/form1";
	}

	@GetMapping(value = "/form2")
	public String form2(@ModelAttribute("member") Ch11Member member, Model model) {
		logger.info("form2 실행");

		List<String> typeList = new ArrayList<>();
		typeList.add("일반회원");
		typeList.add("기업회원");
		typeList.add("헤드헌터회원");
		model.addAttribute("typeList", typeList);

		member.setMtype("헤드헌터회원"); // 기본 선택 항목을 설정

		List<String> jobList = new ArrayList<>();
		jobList.add("학생");
		jobList.add("개발자");
		jobList.add("디자이너");
		model.addAttribute("jobList", jobList);

		member.setMjob("개발자");

		List<Ch11City> cityList = new ArrayList<>();
		cityList.add(new Ch11City(1, "서울"));
		cityList.add(new Ch11City(2, "부산"));
		cityList.add(new Ch11City(3, "제주도"));
		model.addAttribute("cityList", cityList);

		member.setMcity(3);

		return "ch11/form2";
	}

	@PostMapping("/form2")
	public String handleForm2(@ModelAttribute("member") Ch11Member member) { // jsp 보내주기 위해서 ModelAttribute 사용
		logger.info("handleForm2 실행");
		logger.info("mtype: " + member.getMtype());
		logger.info("mjob: " + member.getMjob());
		logger.info("mcity: " + member.getMcity());
		return "redirect:/ch11/content";
	}

	@GetMapping("/form3")
	public String form3(@ModelAttribute("member") Ch11Member member, Model model) {
		logger.info("form3 실행");

		List<String> languageList = new ArrayList<>();
		languageList.add("C");
		languageList.add("Python");
		languageList.add("Java");
		languageList.add("JavaScript");
		model.addAttribute("languageList", languageList);

		member.setMlanguage(new String[] { "Java", "JavaScript" });

		List<Ch11Skill> skillList = new ArrayList<>();
		skillList.add(new Ch11Skill(1, "SpringFramework"));
		skillList.add(new Ch11Skill(2, "SpringBoot"));
		skillList.add(new Ch11Skill(3, "Vue"));
		model.addAttribute("skillList", skillList);

		member.setMskill(new int[] { 1, 3 });

		return "ch11/form3";
	}

	@PostMapping("/form3")
	public String handleForm3(@ModelAttribute("member") Ch11Member member) {
		logger.info("handleForm3 실행");

		if (member.getMlanguage() != null) {
			for (String lang : member.getMlanguage()) 
				logger.info("lang : " + lang);
		}

		if (member.getMskill() != null)
			System.out.println("mskill: " + Arrays.toString(member.getMskill()));

		return "redirect:/ch11/content";
	}

	@GetMapping("/form4")
	public String form4(@ModelAttribute("member") Ch11Member member, Model model) {
		logger.info("form4 실행");

		List<String> jobList = new ArrayList<>();
		jobList.add("학생");
		jobList.add("개발자");
		jobList.add("디자이너");
		model.addAttribute("jobList", jobList);

		member.setMjob("개발자");

		List<Ch11City> cityList = new ArrayList<>();
		cityList.add(new Ch11City(1, "서울"));
		cityList.add(new Ch11City(2, "부산"));
		cityList.add(new Ch11City(3, "제주도"));
		model.addAttribute("cityList", cityList);

		member.setMcity(3);

		return "ch11/form4";
	}
	
	@PostMapping("/form4")
	public String handleForm4(@ModelAttribute("member") Ch11Member member) {
		logger.info("handleForm4 실행");

		logger.info("job : " + member.getMjob());
		logger.info("city : " + member.getMcity());

		return "redirect:/ch11/content";
	}

	@GetMapping("/form5")
	public String form5(@ModelAttribute("member") Ch11Member ch11member) {
		logger.info("form5 실행");
		return "ch11/form5";
	}
}