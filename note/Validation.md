
# Validation

## 🎁Validator 구현하기

#### Validation Dependency 추가하기
**pom.xml**
```xml
<!-- 유효성 검사를 위한 dependency -->
<dependency>
	<groupId>javax.validation</groupId>
	<artifactId>validation-api</artifactId>
	<version>2.0.1.Final</version>
</dependency>
```
<hr />

#### Validator 생성하기
Validator 인터페이스를 구현하여 만든다.
각 필드마다 혹은 form마다 구현할 수 있다.
```java
public class Ch04MemeberJoinFormValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		...
		//return [true/false];
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		...
	}
}
```
<hr/>

 **supports(Class<?> clazz)**
 - 유효성 검사가 가능한지를 판별해준다.
 - 파라미터로 넘어오는 객체가 유효성 검사를 할 수 있는 객체이면 true 반환, 그렇지 않으면 false 반환
- supports()가 true를 반환해야지만, validate()가 호출된다.
- 최소한 자식 클래스의 객체여야 유효하다.
- isAssignableFrom() : 파라미터로 넘어오는  객체가 유효한 객체인지 확인한다.
```java
Boolean check = 클래스이름.class.isAssignableFrom(clazz);
```
<hr/>

**validate(Object target, Errors errors)**
- 유효성 검사를 하는 메소드이다.
- 유효성 검사를 통과하지 못하면, Errors 객체에 등록한다.
<hr/>

**Errors**
- 유효성 검사의 결과와 발생한 에러를 가지고 있는 객체이다.
- .rejectValue() : 어떤 필드에서 에러가 발생하였고, 어떤 파라미터를 넘기고, 어떤 메세지를 보여줄지 Errors 객체에 에러 정보를 추가한다.
```java
errors.rejectValue("필드","에러메세지");
errors.rejectValue("필드","에러메세지", new Object[] {파라미터}, "defaultMessage");
```
<hr/>

```java
public class Ch04MemeberJoinFormValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> clazz) {
		logger.info("supports 실행");
		boolean check = Ch04Member.class.isAssignableFrom(clazz); 
		return check;
	}

	@Override
	public void validate(Object target, Errors errors) {
		logger.info("validate 실행");
		Ch04Member member =(Ch04Member) target;
		
		// mid 검사
		if(member.getMid()==null || member.getMid().trim().equals("")) {
			errors.rejectValue("mid","errors.mid.required"); 
		}else if(member.getMid().length() < 4){
			errors.rejectValue("mid","errors.mid.minlength",new Object[] {4}, ""); 
		}
		
		// mpassword 검사
		if(member.getMpassword()==null || member.getMpassword().trim().equals("")) {
			errors.rejectValue("mpassword","errors.mpassword.required"); 
		}else if(member.getMpassword().length() < 8){
			errors.rejectValue("mpassword","errors.mpassword.minlength",new Object[] {8}, ""); 
		}
		
		// memail 검사
		if(member.getMemail()==null || member.getMemail().trim().equals("")) {
			errors.rejectValue("memail","errors.memail.required");
		}else{
			String regExp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
			Pattern pattern = Pattern.compile(regExp);
			if(!pattern.matcher(member.getMemail()).matches()) {
				errors.rejectValue("memail","errors.memail.invalid"); 
			}
		}
		
		//mtel 검사
		if(member.getMtel()==null || member.getMtel().trim().equals("")) {
			errors.rejectValue("mtel","errors.mtel.required"); 
		}else{
			String regExp = "^\\d{3}-\\d{3,4}-\\d{4}$";
			Pattern pattern = Pattern.compile(regExp);
			if(!pattern.matcher(member.getMtel()).matches()) {
				errors.rejectValue("mtel","errors.mtel.invalid"); 
			}
		}
	}
}
```
**에러 properties 파일**   
ch04_error_ko.xml
```
errors.mid.required=회원 아이디는 필수 입력값입니다.
errors.mid.minlength=회원 아이디는 {0}자 이상 입력해야 합니다.

errors.mpassword.required=회원 비밀번호는 필수 입력값입니다.
errors.mpassword.minlength=회원 비밀번호는 {0}자 이상 입력해야 합니다.

errors.memail.required=회원 이메일은 필수 입력값입니다.
errors.memail.invalid=회원 이메일이 유효하지 않습니다.

errors.mtel.required=회원 전화번호는 필수 입력값입니다.
errors.mtel.invalid=회원 전화번호가 유효하지 않습니다.
```

## 🎉Validator 등록하기

**@Valid**
- 해당 클래스의 멤버변수들을 유효성 검사하도록 한다.
- 스프링은 기본적으로 클래스의 앞글자를 소문자로 바꾸어 해당 클래스의 객체를 내부적으로 관리하여, 같은 클래스를 사용하는 경우 같은 유효성 검사를 하게한다.
<hr />

**@ModelAttribute("새롭게지어줄이름")**
- 같은 클래스의 객체들을 서로 다른 이름으로 관리하기 위해 새로운 이름을 지어준다.
- ex) 로그인 vs 회원가입
<hr />

**@initBinder("객체이름")**
- 특정 모델 객체에만 바인딩 또는 validator 설정을 적용하고 싶은 경우  사용한다.
<hr />

**BindingResult**
- 유효성 검사의 결과와 발생한 에러를 가지고 있는 객체이다.
- .hasErrors() : 에러가 발생했는지 true/false 반환 
- Errors 클래스의 자식 클래스이다.
<hr/>

**WebDataBinder**
-  web request parameter에서 JavaBean 객체로의 데이터 바인딩을 위한 특수 DataBinder입니다.
- DataBinder : 대상 객체에 속성 값을 설정해주는 binder이다. 
- .setValidator(validator객체) : binder에 유효성 검사를 등록한다. 
- .addValidators(validator객체1, ...) : binder에 유효성 검사를 여러개 추가한다. 
```java
@InitBinder("joinForm")
public void joinFormSetValidate(WebDataBinder binder) {
	logger.info("실행");
	//binder.setValidator(new Ch04MemeberJoinFormValidator());
	
	binder.addValidators(
		new Ch04MemberIdValidator(),
		new Ch04MemberPasswordValidator(),
		new Ch04MemberEmailValidator(),
		new Ch04MemberTelValidator()
	);
}
	
@PostMapping("/join")
public String join(@ModelAttribute("joinForm") @Valid Ch04Member member, BindingResult bindingResult){  
	logger.info("join 실행");
	if(bindingResult.hasErrors()) {
		logger.info("다시 입력폼 제공 + 에러 메시지");
		return "ch04/content";
	}
	logger.info("정상 요청 처리 후 응답 제공");
	return "redirect:/ch04/content"; 
}
```