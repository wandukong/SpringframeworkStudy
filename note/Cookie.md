
# Cookie
> server가 client에게 전송하는 작은 데이터 조각
- client에 대한 상태 정보를 저장하기 위해 사용된다.
- http 헤더에 추가되어 key-value 쌍의 형태로 전달된다.
- 사용자가 쿠키의 내용을 볼 수 있고, 마음대로 수정할 수 있다
- 쿠키는 클라이언트의 컴퓨터에 저장된다.
```
- Connectionless: client가 request를 보내면 server가 response를 한 뒤 접속을 끊는다.
- Stateless: server가 client에 관한 정보를 유지하지 않는다.
```
- 이러한 이유로 인해 request간의 정보를 공유할 수 없다. 

## 🍪Cookie 생성하기
쿠키는 key-value 쌍의 형태로 생성한다.
```java
Cookie cookie = new Cookie("key", "value");
```

## 🍩Cookie 속성 추가하기
- .setPath("경로") : cookie를 들고갈 경로를 설정한다.
- .setMaxAge(시간) : cookie가 유효한 시간을 설정한다. 초단위
- .setHttpOnly(true/false) : 스크립트 언어에서 접근하지 못하도록 설정
- .setSecure(true/false) : secure https 연결에서만 cookie가 전송될 것인지의 여부
```java
cookie.setPath("/ch05"); // 쿠키를 들고갈 경로를 설정해 준다. 보통 / 으로 설정한다.
cookie.setMaxAge(30 * 60); // 브라우저 종료되더라도 30분동안 쿠키 유지
cookie.setHttpOnly(true); // 자바스크립트를 포함한 스크립트 언어에서 접근하지 못하도록 설정.
cookie.setSecure(true); // https:// 만 전송
```

## 🎂Cookie 전달하기
생성된 쿠키를 **HttpServletResponse** 객체에 추가하여 전달한다.
- .addCookie(쿠키이름)
```java
public String createCookie(HttpServletResponse response) {
	// 쿠키 생성 및 속성 설정 코드
	...
	response.addCookie(cookie);
	return "ch05/content";
}
```
## 🍰Cookie 받기
전달받은 쿠키를 **HttpServletRequest** 객체로부터 전달받는다.

방법 1: **@CookieValue 어노테이션**을 사용하여 전달받는다
```java
@GetMapping("/getCookie1")
public String getCookie1(@CookieValue String useremail) {
// Cookie 이름이랑 매개변수랑 다를 경우 @CookieValue("useremail") String uemail 로 사용가능
	logger.info("useremail : " + useremail);
	return "redirect:/ch05/content";
}
```

방법 2 : **request.getCookies()** 를 사용하여 배열 형태로 전달 받는다.

```java
@GetMapping("/getCookie2")
public String getCookie2(HttpServletRequest request) {
	Cookie[] cookies = request.getCookies();
	return "redirect:/ch05/content";
}
```

## 🧁JSON 쿠키 생성
**json dependency를 pom.xml에 추가**
```xml
<dependency>
	<groupId>org.json</groupId>
	<artifactId>json</artifactId>
	<version>20210307</version>
</dependency>
```
```java
@GetMapping("/createJsonCookie")
public String createJsonCookie(HttpServletResponse response) throws UnsupportedEncodingException {

	JSONObject jsonObject = new JSONObject();
	jsonObject.put("userid", "fall");
	jsonObject.put("useremail", "fall@company.com");
	jsonObject.put("username", "홍길동");
	String json = jsonObject.toString();
	
	json = URLEncoder.encode(json, "UTF-8"); // utf-8 형태를 ASCII 형태로 인코딩
	
	Cookie cookie = new Cookie("user", json);
	response.addCookie(cookie);

	return "redirect:/ch05/content";
}
```

## 🍫JSON 쿠키 받기
```java
@GetMapping("/getJsonCookie")
public String getJsonCookie(@CookieValue String user) {
	JSONObject jsonObject = new JSONObject(user); // json 형태로 변환
	logger.info("userid : " + jsonObject.getString("userid")); // json value 얻어오기
	logger.info("useremail : " + jsonObject.getString("useremail"));
	logger.info("username : " + jsonObject.getString("username	"));
	return "redirect:/ch05/content";
}
```