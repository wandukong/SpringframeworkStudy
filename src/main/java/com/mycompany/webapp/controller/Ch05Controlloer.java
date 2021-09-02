package com.mycompany.webapp.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Controller
@RequestMapping("/ch05")
public class Ch05Controlloer {
	private static final Logger logger = LoggerFactory.getLogger(Ch05Controlloer.class);

	@RequestMapping("/content")
	public String content() {
		logger.info("ch05 실행");
		return "ch05/content";
	}

	@GetMapping("/getHeaderValue")
	public String getHeaderValue(HttpServletRequest request) {
		// HttpServletRequest 헤더에 대한 모든 정보를 얻을 수 있다.
		logger.info("getHeaderValue 실행");

		logger.info("method : " + request.getMethod());
		logger.info("requestURI : " + request.getRequestURI());
		logger.info("client IP : " + request.getRemoteAddr());
		logger.info("contextRoot : " + request.getContextPath());

		String userAgent = request.getHeader("User-Agent");
		logger.info("User-Agent : " + userAgent);

		// Client OS
		if (userAgent.contains("Windows NT")) {
			logger.info("client OS : Windows");
		} else if (userAgent.contains("Macintosh")) {
			logger.info("client OS : macOS");
		}

		// Client Browser
		if (userAgent.contains("Edg")) {
			logger.info("client Browser : Edge");
		} else if (userAgent.contains("Trident")) {
			logger.info("client Browser : IE11");
		} else if (userAgent.contains("Chrome")) {
			logger.info("client Browser : Chrome");
		} else if (userAgent.contains("Safari")) {
			logger.info("client Browser : Safari");
		}

		return "redirect:/ch05/content";
	}

	@GetMapping("/createCookie")
	public String createCookie(HttpServletResponse response) {
		logger.info("createCookie 실행");

		Cookie cookie = new Cookie("useremail", "seungwan37@naver.com");
		cookie.setPath("/ch05"); // 쿠키를 들고갈 경로를 설정해 준다. 보통 / 으로 설정한다.
		cookie.setMaxAge(30 * 60); // 브라우저 종료되더라도 30분동안 쿠키 유지, default: session, 브라우저 종료되면 없어짐
		cookie.setHttpOnly(false); // 자바스크립트를 포함한 스크립트 언어에서 접근하지 못하도록 설정.
		cookie.setSecure(true); // https://만 전송

		response.addCookie(cookie);

		return "ch05/content";
	}

	// name : cookie 이름
	// value : cookie 값
	// secure: secure HTTPS 연결에서만 Cookie가 전송될 것인지의 여부.
	// TRUE면 secure connection이 존재할 때만 Cookie가 보내진다. Default는 FALSE다.
	// domain : Cookie의 domain name이다.
	// path : 유효한 Cookie 경로
	// HTTPOnly: TRUE로 설정되면 HTTP 프로토콜을 통해서만 Cookie를 이용할 수 있다.
	// 자바 스크립트와 같은 스크립팅 언어로는 접근이 안된다. Default는 FALSE다.
	// Expires: Cookie가 언제 파기될 지 명시한다. 초 단위이며, 60*60*24*30은 30일 뒤에 파기된다는 것을 나타낸다. 이
	// 값이 생략되거나 0으로 설정되면 Session이 끝날 때 파기된다.

	@GetMapping("/getCookie1")
	public String getCookie1(@CookieValue String useremail) {
		// Cookie 이름이랑 매개변수랑 다를 경우 @CookieValue("useremail") String uemail 로 사용가능
		logger.info("getCookie1 실행");
		logger.info("useremail : " + useremail);
		return "redirect:/ch05/content";
	}

	@GetMapping("/getCookie2")
	public String getCookie2(HttpServletRequest request) {
		logger.info("getCookie2 실행");
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			String cookieName = cookie.getName();
			String cookieValue = cookie.getValue();
			if (cookieName.equals("useremail")) {
				logger.info("useremail : " + cookieValue);
				break;
			}
		}
		return "redirect:/ch05/content";
	}

	@GetMapping("/createJsonCookie")
	public String createJsonCookie(HttpServletResponse response) throws UnsupportedEncodingException {
		logger.info("createJsonCookie 실행");

		// String json = "{\"userid\":\"fall\", \"useremail\":\"fall@company.com\",
		// \"username\":\"홍길동\"}";

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("userid", "fall");
		jsonObject.put("useremail", "fall@company.com");
		jsonObject.put("username", "홍길동");
		String json = jsonObject.toString();

		logger.info("json 인코딩 전 :" + json);
		json = URLEncoder.encode(json, "UTF-8");
		logger.info("json 인코딩 후 :" + json);

		Cookie cookie = new Cookie("user", json);
		response.addCookie(cookie);

		return "redirect:/ch05/content";
	}

	@GetMapping("/getJsonCookie")
	public String getJsonCookie(@CookieValue String user) {
		logger.info("getJsonCookie 실행");
		logger.info("user : " + user);

		JSONObject jsonObject = new JSONObject(user);
		logger.info("userid : " + jsonObject.getString("userid")); // json value 얻어오기
		logger.info("useremail : " + jsonObject.getString("useremail"));
		logger.info("username : " + jsonObject.getString("username	"));

		return "redirect:/ch05/content";
	}

	@GetMapping("/createJwtCookie")
	public String createJwtCookie(HttpServletResponse response) throws Exception {
		logger.info("createJwtCookie 실행");

		String userid = "fall";
		String useremail = "fall@mycompany.com";
		String username = "홍길동";

		JwtBuilder builder = Jwts.builder();

		// header 설정
		builder.setHeaderParam("alg", "HS256");
		builder.setHeaderParam("typ", "JWT");

		// 유효기간 설정
		builder.setExpiration(new Date(new Date().getTime() + 1000*60*30)); // 지금으로부터 30분
		// new Date().getTime() 1970년 자정부터 지금까지의 milliseconds초

		// payload(claim) 설정
		builder.claim("userid", userid);
		builder.claim("useremail", useremail);
		builder.claim("username", username);
 
		// signature(서명) 설정
		String secretKey = "abc12345";
		builder.signWith(SignatureAlgorithm.HS256, secretKey.getBytes("UTF-8")); // header의 alg과 같은 값을 넣는다.
		
		String jwt = builder.compact(); // 압축

		logger.info("jwt : " + jwt);
		
		Cookie cookie = new Cookie("jwt", jwt);
		response.addCookie(cookie);
		
		return "redirect:/ch05/content";
	}

	@GetMapping("/getJwtCookie")
	public String getJwtCookie(@CookieValue String jwt) throws Exception {
		logger.info("getJwtCookie 실행");
		
		logger.info("jwt : " + jwt);
		JwtParser parser = Jwts.parser();
		
		String secretKey = "abc12345";
		parser.setSigningKey(secretKey.getBytes("UTF-8"));
		
		Jws<Claims> jws = parser.parseClaimsJws(jwt);
		
		Claims claims= jws.getBody();
		
		String userid = claims.get("userid", String.class);
		String useremail = claims.get("useremail", String.class);
		String username = claims.get("username", String.class);
		
		logger.info("userid : " + userid); // json value 얻어오기
		logger.info("useremail : " + useremail);
		logger.info("username : " + username);


		return "redirect:/ch05/content";
	}
}
