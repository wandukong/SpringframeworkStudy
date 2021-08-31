
# Spring MVC

## ⚙How it works?


### Dispatcher Servlet
> client로부터 요청이 오면 tomcat과 같은 servlet-container가 요청을 받는데, 이때 제일 앞에서 서버로 들어오는 모든 요청을 처리하는 front-controller를 Spring에서 정의하였고, 이를 Dispatcher-Servlet이라고 한다.
-  공통 처리 작업을 Dispatcher Servlet이 처리한 후, 적절한 controller로 작업을 위임해주는 역할을 한다.
- Dispatcher Servlet은 보통 사용자가 처음으로 요청을 할 때 생성되지만, 서버가 시작할 때 미리 생생해둘 수도 있다.(load-on-startup)


### Dispatcher Servlet 처리 순서
<img src="https://user-images.githubusercontent.com/47289479/131480029-b495b6a0-fe8c-4836-8fff-9af0cb5f1794.png" width="500"/>

1. 클라이언트의 요청을 받는다.
2. Handler Mapping에게 해당 요청을 어떤 Controller가 담당하는지 찾는다.
3. 담당 Controller를 찾으면, Controller에게 작업 요청
4. 작업을 끝낸 Controller에게 출력할 view 이름 반환한다.
5. 뷰 리졸버에게 view 경로를 찾는다.
6. 처리 결과를 view에  보낸다.
7. 처리 결과가 포함된 view를 받는다.
8. 클라이언트에게 응답을 한다.

## 📃web.xml
> 웹 어플리케이션의 배포 설명자로, 각 어플리케이션의 환경을 설정하는 역할을 한다.
서버가 처음 로딩될 때 읽어들이고, 해당 환경설정을 tomcat에 적용하여 서버를 시작한다.

### dispatcher servlet 생성
- dispatcher servlet은 여러개 만들 수 있다.
- load-on-startup을 작성하면, was에 의해 처음 구동될때 **dispatcher servlet 객체를 미리 만들어** 놓아 실행속도를 높인다.
- contextConfigLocation은 다른 이름으로 사용할 수 없다.
```xml
<servlet>
		<servlet-name>appServlet</servlet-name>
		<servlet-class>com.mycompany.webapp.servlet.DispatcherServlet</servlet-class>
		<init-param>
			<param-name>contextConfigLocation</param-name> 
			<param-value>classpath:spring/appServlet1/*.xml</param-value> 
		</init-param>
		<init-param>
			<param-name>title</param-name>
			<param-value>shopping</param-value>
		</init-param>
	<load-on-startup>1</load-on-startup> 
</servlet>
```
- servlet-name : dispatcher servlet의 이름
- servlet-class : dispatcher servlet의 경로
- init-param : dispatcher servlet에게 넘겨줄 파라미터 정의
- param-name : 파라미터 이름
- param-value : 파라미터 값
- load-on-startup : dispatcher sevlet을 생성 및 초기화하는 순서를 정한다. 

### sevlet dispatcher 매핑
 클라이언트 요청 URL과 매핑시켜 사용한다.
```xml
<servlet-mapping>
	<servlet-name>appServlet</servlet-name> 
	<url-pattern>/</url-pattern>
</servlet-mapping>
```
- servlet-name : 매핑할 dispatcher servlet을 기술한다.
- url-pattern : 매핑할 url을 기술한다.



## 👨‍💻Dispatcher Servlet 구현해보기

### class 생성
HttpServlet을 상속받아 생성한다.
```java
public class DispatcherServlet extends HttpServlet {
	...
}
```

### 생성자 만들기
```java
public DispatcherServlet(){
	System.out.println("Dispatcher Servlet 객체 생성");
```

### 초기화 하기
dispatcher servlet은 초기화 작업을 생성자에서 하지 않고, **init()에서 초기화**한다.
```java
@Override
public void init(ServletConfig config) throws ServletException {
	System.out.println("Dispatcher Servlet 초기화");
	String contextConfigLocation= config.getInitParameter("contextConfigLocation");
	System.out.println("contextConfigLocation : "  + contextConfigLocation);
	
	String title = config.getInitParameter("title");
	this.title = title;
}
```

### request 처리하기
service는 클라이언트의 요청이 발생하면 실행된다.
service()는 클라이언트로부터 넘겨진 데이터의 method 를 보고 GET 메소드이면, doGet 메소드를, POST 이면, doPost 메소드를 호출한다.
```java
@Override
protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
	System.out.println("service 실행");
	String requestUri = request.getRequestURI(); 
	String viewName ="redirect:/";
	
	//request mapping 
	if(requestUri.startsWith("/ch01")) { 
		Ch01Controller ctrl = new Ch01Controller();
		viewName = ctrl.exec();
	}else if(requestUri.startsWith("/ch02")) {
		Ch02Controller ctrl = new Ch02Controller();
		viewName = ctrl.exec();
	}
	
	// 뷰 이름을 해석해서 redirect 할 것인지 forward 할 것인지를 결정
	if(viewName.startsWith("redirect:")) {
		String uri = viewName.split(":")[1];
		response.sendRedirect(uri);  
	}else {
		String prefix = "/WEB-INF/views/";
		String suffix = ".jsp";
		String jspPath = prefix + viewName + suffix;
		RequestDispatcher rd = request.getRequestDispatcher(jspPath);
		rd.forward(request, response);
	}
}
```
```java
package com.mycompany.webapp.controller;

public class Ch01Controller {
	public String exec() {
		System.out.println("Ch01Controller-exec()");
		return "ch01/content";
	}
}
```
```java
package com.mycompany.webapp.controller;

public class Ch02Controller {
	public String exec() {
		System.out.println("Ch02Controller-exec()");
		return "redirect:/";
	}
}
```