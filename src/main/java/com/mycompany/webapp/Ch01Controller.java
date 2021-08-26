package com.mycompany.webapp;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller // @RequestMapping 을 사용하려면, @Controller가 필요하다.
@RequestMapping("/ch01")
public class Ch01Controller {
	private static final Logger logger = LoggerFactory.getLogger(Ch01Controller.class);

	@RequestMapping("/content") // @RequestMapping : 클라이언트가 해당 url로 요청을 하면 해당 메소드를 실행시킨다. 
	public String home() {
		logger.info("실행");
		return "ch01/content"; // jsp 경로
	}

	@RequestMapping(value = "/favicon.ico", method = RequestMethod.GET)
	public void favicon(HttpServletRequest request, HttpServletResponse reponse) {
		try {
			reponse.sendRedirect("/resources/favicon.ico");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
