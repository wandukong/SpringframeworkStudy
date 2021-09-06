
# Session 
> 웹 사이트의 여러 페이지에 걸쳐 사용되는 사용자 정보를 저장하는 기술   
> 사용자가 브라우저를 닫아 서버와의 연결을 끝내는 시점까지를 의미한다.
- 세션은 서비스가 돌아가는 서버 측에 데이터를 저장하고, 세션의 키값만을 클라이언트 측에 남겨둔다.
- 브라우저는 필요할 때마다 이 키값을 이용하여 서버에 저장된 데이터를 사용하게 된다.
- 쿠키는 클라이언트 측의 컴퓨터에 모든 데이터를 저장한다
- 세션도 쿠키를 이용한다.
- 보통 30분 동안 아무 요청을 보내지 않으면, 서버는 해당 브라우저가 더 이상 이용하지 않는다고 판단하여
세션을 지운다.

#### 동작 순서
1. 처음 브라우저 실행시, WAS는 브라우저가 jsessionid 쿠키를 들고오는지 확인
2. 쿠키가 없으면, 이 브라우저가 사용할 수 있는 새로운 세션 객체를 만든다.
3. 세션 객체에 식별명을 지정하고, 그것을 브라우저에게 응답과 함께 보낸다.
4. 브라우저는 jsessionid의 식별명을 저장하고 있다가, 다시 서버로 요청할 때 가지고 간다.
5. 서버는 식별명을 세션 저장소에서 찾아보고, 해당 실병명에 맞는 세션 객체를 이용한다.


## 🎮Session 추가하기

#### HttpSession 객체의 .setAttribute("이름","값")

```java
HttpSession session = request.getSession();
session.setAttribute("name","홍길동");
```
```java
public String saveData(String name, HttpSession session) {
	session.setAttribute("name", name);
	...
}
```

## 🕹Session 읽기
####  @SessionAttribute
```java
public String readData(@SessionAttribute String name) {
	...
}
```
#### HttpSession 객체의 .getAttribute("이름")

```java
public String readData(HttpSession session) {
	String name = (String)session.getAttribute("name");
	...
}
```
## 🎰Session 삭제하기
#### HttpSession 객체의 .removeAttribute("이름") 
```java
public String logout(HttpSession session) {
	//하나의 세션 지우기
	session.removeAttribute("sessionMid");
	...
}
```
#### HttpSession 객체의 .invalidate()
```java
public String logout(HttpSession session) {
	//모든 세션 무효화
	session.invalidate();
	...
}
```
<hr/>
<hr/>

### HttpSession은 web app에서 지속적으로 유지 되어야할 사용자 데이터를 저장할 때 사용한다. ex) 로그인 정보
### @SessionAttributes는 임시직으로 데이터를 유지할 때 사용한다.

✔ **저장/읽기/제거 방법이 다르기  때문에 데이터를  저장/읽기/제거할 때 두 방법을 섞어 사용하지 않는 것이 좋다.**



# @SessionAttributes
> 화면과 화면 사이에 임시적으로 데이터를 유지할 때 사용 ex) 단계별 입력 폼 작성   
> @SessionAttributes({"객체1", "객체2", ...}) 
- @SessionAttributes 안에 선언된 객체들을 session 객체로 인식한다.

```java
@SessionAttributes({"inputForm"})
public class Ch08Controller {
}
```

## ⚽Session 객체 저장
- session 객체가 이미 생성되어 있으면, 기존 객체를 사용한다.
- 만약 해당 객체가 session에 존재 하지 않을 때, 메소드가 한번 실행된다.
```java
@ModelAttribute("객체이름")
public Object getObject(){
	return object;
}
```
```java
@ModelAttribute("inputForm")
public Ch08InputForm getInputForm() {
	Ch08InputForm inputForm = new Ch08InputForm();
	return inputForm;
}
```
## 🏀Session 객체 읽기
- session 객체가 이미 생성되어 있으면, 기존 객체를 사용한다.
- 만약 해당 객체가 session에 존재 하지 않을 때, 예외 발생
```java
@RequestMapping("/url")
public String method(@ModelAttribute("객체이름") Object obj){
	...
}
```
```java
@GetMapping("/inputStep1")
public String inputStep1(@ModelAttribute("inputForm") Ch08InputForm inputForm) {
	return "ch08/inputStep1";
}

@PostMapping("/inputStep2")
public String inputStep2(@ModelAttribute("inputForm") Ch08InputForm inputForm) {
	return "ch08/inputStep2";
}

@PostMapping("/inputDone")
public String inputDone(@ModelAttribute("inputForm") Ch08InputForm inputForm) {
	logger.info("data1: " + inputForm.getData1()); // /inputStep1에서 입력함
	logger.info("data2: " + inputForm.getData2()); // /inputStep1에서 입력함
	logger.info("data3: " + inputForm.getData3()); // /inputStep2에서 입력함
	logger.info("data4: " + inputForm.getData4());.// /inputStep2에서 입력함
	return "redirect:/ch08/content";
}
```

## 🏈Session 객체 삭제
#### SessionStatus의 setComplete()
- 작업을 완료하고 난 후, 사용한 객체를 모두 지운다.
- HttpSession의 메소드를 사용하지 않는다.
```java
@PostMapping("/inputDone")
public String inputDone(@ModelAttribute("inputForm") Ch08InputForm inputForm, SessionStatus sessionStatus) {
	sessionStatus.setComplete(); // @SessionAttributes 에 선언되어 있는 모든 세션 제거
	//session.removeAttribute("inputForm"); 이렇게 사용하면 절대 안됨. 안지워짐.
	return "redirect:/ch08/content";
}

```