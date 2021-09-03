
# Data Delivery

## 🔴객체 사용 범위의 종류
#### Request 범위
- 해당 request 범위에서만 유지
```java
@RequestMapping("/saveData")
public String saveData(HttpServletRequest request) {
	request.setAttribute("requestData", "자바");
	return "ch07/readData";
}
```
<hr/>

#### Session 범위
- 브라우저가 켜져있는 동안 유지
```java
HttpSession session = request.getSession();
session.setAttribute("sessionData", "자바스크립트");
```
<hr/>

#### Application 범위
- 서버가 켜져있는 동안 유지
```java
ServletContext application = request.getServletContext();
application.setAttribute("applicationData", "스프링프레임워크");
```
## 🟠HttpServletRequest 
> HttpServletRequest 객체에 속성을 추가한다.

**.setAttribute("속성이름","속성값")**
```java
@RequestMapping("/saveData")
public String saveData(HttpServletRequest request) {
	request.setAttribute("requestData", "자바");
	return "ch07/readData";
}
```

## 🟡ModelAndView
> ModelAndView 객체를 만들어서 반환한다.
 
**.addObject("속성이름","속성값")** : model에 속성 추가   
**.setViewName("뷰이름")** : model을 건내줄 view(jsp)   
```java
@GetMapping("/objectSaveAndRead2")
public ModelAndView objectSaveAndRead2() {
	Ch07Member member = new Ch07Member();
	member.setName("홍길동");
	member.setAge(25);
	ModelAndView mav = new ModelAndView();
	mav.addObject("member", member); // request 범위에 저장
	mav.setViewName("ch07/objectRead"); // 넘겨줄 뷰
	return mav;
}
```



## 🟢Model parameter
> parameter에 Model 객체를 추가하여 사용한다

**.addAttribute("속성이름","속성값")**
```java
@GetMapping("/objectSaveAndRead3")
public String objectSaveAndRead3(Model model) {
	Ch07Member member = new Ch07Member();
	member.setName("홍길동");
	member.setAge(25);
	member.setJob("개발자");
	model.addAttribute("member", member);
	return "ch07/objectRead";
}
```

## 🔵@ModelAttribute("속성이름")
> 메소드 위에 @ModelAttribute를 추가하여 사용한다.   
> 해당 메소드가 실행되면 return 되는 값을  model의 속성 이름으로 사용한다.   
> 상위 경로에 속하는 경로가 호출 될때마다 해당 Model 객체가 생성된다.   
```java
@ModelAttribute("colors") // 상위 경로인 /ch07 속하는 경로가 호출될때마다 실행됨. request 범위에 저장
public String[] getColors() {
	logger.info("getColors 실행");
	String[] colors = {"red", "Green", "Blue", "Yellow", "Pink"};
	return colors;
}
```

## 🟣@ModelAttribute을 이용해서 매개 변수 값 전달
> 매개변수 앞에 @ModelAttribute을 붙이면 해당 매개 변수가 model 객체가 된다.   
> return 되는 jsp로 model 객체를 보내서, 해당 jsp에서  사용할 수 있다.   
- modelAttribute에 괄호를 사용하지 않으면, 클래스 이름의 첫글자를 소문자로 바꾼 이름으로 사용할 수 있다.   

```java
@GetMapping("/argumentSaveAndRead1")
public String argumentSaveAndRead1(@ModelAttribute("kind") String kind, @ModelAttribute("sex") String sex){ 
	// 해당 매개변수의 이름과 값을 request 범위에 저장한다.
	return "ch07/argumentRead1";
}
	
@GetMapping("/argumentSaveAndRead2")
public String argumentSaveAndRead2(@ModelAttribute Ch07Cloth cloth) { 
	return "ch07/argumentRead2";
}
```

## 🟤JSP에서 전달된 Data 사용하기
**EL(Expression Language)를 사용한다.**
- 객체 형태로 넘길 때는, getter가 존재해야 값을 불러올 수 있다.
```html
<p>kind: ${kind}</p>
<p>sex: ${sex}</p>

<!-- getXXX()에서 return되는 값이 온다. 무조건 getter로 받아오기 떼문에 dto에 getter가 있어야한다. -->
<p>kind: ${ch07Cloth.kind}</p>
<p>sex: ${ch07Cloth.sex}</p>
```
<hr />

### jstl 사용하기
- **begin** : 시작
- **end** : 끝
- **step** : 증감수
- **var** : 변수
```html
<c:forEach begin="1" end="5" step="1" var="i">
	<tr>
		<th scope="row">${i}</th>
		<td>제목${i}</td>
	</tr>
</c:forEach>
````
- **var** : 변수
- **items** : 배열 혹은 리스트
```html
<c:forEach var="board" items="${boardList}">
	<tr>
		<td scope="row">${board.no}</td>
		<td>${board.title}</td>
		<td>${board.content}</td>
		<td>${board.writer}</td>
		<td><fmt:formatDate value="${board.date}" pattern="yyyy-mm-dd" /></td>
	</tr>
</c:forEach>
```
- **${status.index}** :  0부터의 순서 
- **${status.count}** : 1부터의 순서
- **${status.first}** : 현재 루프가 처음인지 반환 
- **${status.last}** : 현재 루프가 마지막인지 반환 

```html
<c:forEach var="lang" items="${langs}" varStatus="status">
	<c:if test="${status.first} ">
		<tbody>
	</c:if>

	<tr>
		<th scope="row">${status.count}</th>
		<!-- count1: 1부터 시작하여 일씩 증가됨, index: 0 부터 시작 -->
		<td>${lang}</td>
	</tr>
	<c:if test="${status.last} ">
		</tbody>
	</c:if>
</c:forEach>
```