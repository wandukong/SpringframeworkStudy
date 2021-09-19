# AOP
> 여러 객체에 공통으로 적용할 수 있는 기능을 구분함으로써 재사용을 높여주는 프로그래밍 기법
> Aspect Oriented Programming

<img src="https://user-images.githubusercontent.com/47289479/133353926-7a4260ab-d7b2-4d4e-8b78-6339c55b40e2.png" width=400px/>

- 공통 코드를 외부에 별도로 작성하고, 비지니스 로직이 실행될 때 공통 코드를 적용해준다.
- 관점 지향 프로그래밍
	- 공통 관심사를 핵심 관심사로부터 분리해서  코드 중복을 해소
	- 핵심 관심사 수정 없이 공통 관심사만 별도로 변경할 수 있음
- 핵심 관심사 : Core
	- 수행해야 하는 업무 처리 기능
		- Ex) 조회/이체 서비스, 주문 서비스
- 공통(횡단) 관심사 : Cross-cutting
	- 모듈에 걸치는 전체적인 부가적 기능
		- Ex) 로그작성, 보안/인증, 트랜잭션, 예외처리

### 핵심용어
<img src="https://user-images.githubusercontent.com/47289479/133362231-d4bd0ee1-f3bb-45bc-98cb-9c385c3bde7b.png" width=600px/>

- Aspect : 공통 관심사
- Advice : 공통 코드 삽입 위치(핵심로직 전/후)
- JointPoint : 핵심로직의 호출 지점
- Pointcut : Aspect 를 적용할  핵심 코드(메소드)들을  지정
- Weaving : 공통 코드를 핵심로직에 적용하는 것

## 🏖환경 추가
####
**dispatcher/ch15_aop.xml**
```xml
<aop:aspectj-autoproxy/>
```
**pom.xml**
```xml
<!-- AOP -->
<!-- https://mvnrepository.com/artifact/org.aspectj/aspectjweaver -->
<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjweaver</artifactId>
    <version>1.9.4</version>
</dependency>
```

## 🏜PointCut 작성

> @어드바이스("execution(접근지정자 리턴타입 패키지경로.클래스.메소드(매개변수))")
```java
@Around("execution(public * com.mycompany.myapp.controller.Ch15Controller.get*(..))"
```
## 🏕Advice 종류
 
### @Before 
>핵심 로직을 호출하기 전에 공통 기능을 실행
- 같은 메소드에 여러 Aspect를 적용할 때, **@Order**를 사용하여 순서를 지정한다.
```java
@Component
@Aspect
@Order(2)
public class Ch15Aspect1Before {
	
	@Before("execution(public * com.mycompany.webapp.controller.Ch15Controller.before*(..))")
	public void method() {
		logger.info("실행");
	}
}

@Component
@Aspect
@Order(1)
public class Ch15Aspect2Before {
	
	@Before("execution(public * com.mycompany.webapp.controller.Ch15Controller.before*(..))")
	public void method() {
		logger.info("실행");
	}
}
```
<hr/>

### @After
> 예외 발생 여부와 상관없이 핵심 로직을 호출한 후에 공통 기능을 실행
```java
@Component
@Aspect
public class Ch15Aspect3After {
	
	@After("execution(public * com.mycompany.webapp.controller.Ch15Controller.after*(..))")
	public void method() {
		logger.info("실행");
	}
}
```
<hr/>


### @AfterReturning 
> 핵심 로직이 예외 없이 실행해야만 공통 기능을 실행
- return값을 받고 싶은 경우,  **returning** 속성을 이용한다.
	- returning 속성 값과 동일하게 메소드의 매개변수로 사용해야한다.
```java
@Component
@Aspect
public class Ch15Aspect4AfterReturning {
private static final Logger logger = LoggerFactory.getLogger(Ch15Aspect3After.class);
	
	@AfterReturning(
		pointcut="execution(public * com.mycompany.webapp.controller.Ch15Controller.afterReturning(..))",
		returning="returnValue"	// 리턴값 받아오기
	)
	public void method(String returnValue) { 
		logger.info("실행");
		logger.info("리턴값 : "+ returnValue);
	}
}
```
<hr />

### @AfterThrowing
> 핵심 로직이 예외를 발생시킬 때 공통 기능을 수행
- 주로 예외 처리 코드가 공통 기능이 된다.
- **throwing** 속성을 이용하여, 발생한 예외를 받아온다.
- 예외 타입은 Exception의 상위 클래스인 **Throwable** 타입이다.
```java
@Component
@Aspect
public class Ch15Aspect5AfterThrowing {

	@AfterThrowing(
		pointcut = "execution(public * com.mycompany.webapp.controller.Ch15Controller.afterThrowing(..))", 
		throwing = "e" // 에외
	)
	public void method(Throwable e) { // 위 코드의 returning 동일한 이름으로 매개변수로를 사용해야한다.
		logger.info("실행");
		logger.info("예외 메시지 : " + e.getMessage());
	}
}
```
<hr/>

### @Around 
> 핵심 로직을 호출하기 전과 후에 공통 기능을 실행
- 핵심 로직을 매개변수를 ProceedingJoinPoint타입의 객체로 받은 후, .proceed()를 이용하여 수행한다.
```java
@Component
@Aspect
public class Ch15Aspect6Around {

	@Around("execution(public * com.mycompany.webapp.controller.Ch15Controller.around*(..))")
	public Object method(ProceedingJoinPoint joinPoint) throws Throwable {
		// -------------------------------------------------------
		// 전처리 코드 실행
		// -------------------------------------------------------
		Object result = joinPoint.proceed();
		// -------------------------------------------------------
		// 후처리 코드 실행
		// -------------------------------------------------------
		return result;
	}
}
```

## Aspect Class에서 JSP로 데이터 넘기기
- HttpServletRequest와 HttpSession의 객체를 아래와 같이 얻는다. 
```java
ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
HttpServletRequest request = sra.getRequest(); 		// HttpServletRequest 객체 얻기
HttpSession session = request.getSession();	   		// HttpSession 객체 얻기
HttpServletResponse response = sra.getResponse(); 	// HttpServletResponse 객체 얻기
```