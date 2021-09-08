# BeanNameResolver
> 컨트롤러가 리턴한 뷰 이름을 JSP 경로로 해석하지 않고 빈 이름으로 해석

- **@Component 을 사용하여, 해당 클래스를 객체로 만들어서, 여러 컨트롤러에서 응답으로 받을 수 있도록 한다.**
- **AbstractView를 상속받아 클래스를 만든다.**
- **해당 클래스의 앞글자를 소문자로 바꾼 이름이 객체 이름이다.**
- 컨트롤러에서도 응답을 만들 수 있는데, 이 응답을 만들 수 잇는 컨트롤러가 여러 개일때 유용하다.
- 객체로 만들어 놓으면 공통으로 사용할 수 있다. -->
- 객체 이름을 리턴하면, dispacther Servlet은 해당 이름을 가진 객체로 요청한다. 

## 💭설정 파일 (dispatcher servlet)
```xml
<bean class="org.springframework.web.servlet.view.BeanNameViewResolver">
	<property name="order" value="0"/> <!-- 우선 순위 결정 -->
</bean>

<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
	<property name="order" value="1" /> <!-- 우선 순위 결정 -->
	<property name="prefix" value="/WEB-INF/views/" />
	<property name="suffix" value=".jsp" />
</bean>
```
- **우선 순위**를 부여하여, BeanNameViewResolver를 통해서 해당 빈 이름이 없으면,  InternalResourceViewResolver를 통해 jsp를 찾는다.

## 🗯 객체로 만들 클래스 작성

```java
@Component
public class Ch12FileDownload extends AbstractView{
	
	private static final Logger looger = LoggerFactory.getLogger(Ch12FileListView.class);

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// Map은 request 범위에 저장되어 있는 객체를 불러올수 있게 해준다.
		String fileName = (String) model.get("fileName");
		String userAgent = (String) model.get("userAgent");
		
		response.setContentType("application/json; charset=UTF-8;");
		PrintWriter pw = response.getWriter();
		
		// 보내줄 응답 바디를 response에 출력한다.
		
		pw.print(json);
		pw.flush();
		pw.close();
	}
}
```

## 💬컨트롤러에서 사용하기
```java
@GetMapping("/fileDownload")
public String fileDownload(@ModelAttribute("fileName") String fileName, @ModelAttribute("userAgent") @RequestHeader("User-Agent") String userAgent)
		throws IOException {
	return "ch12FileDownload";
}
```