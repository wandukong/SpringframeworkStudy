# Exception 
- 예외 처리를 일괄적으로 처리하기 위해, 예외 처리 클래스를 작성한다.
- 예외 처리를 하지 않고 발생하는 Exception은 모두 RuntimeException이다.

## 🌌Controller 예외 처리 클래스 작성
- @Component와 @ControllerAdvice 적용
	- @Component : 스프링이 해당 클래스를 객체로 생성해서 관리하도록 설정한다. 컴파일 시 객체 생성됨.
	- @ControllerAdvice : 모든 컨트롤러에게 영향을 주도록 설정한다.
- 예외별로 처리하는 메소드에 @ExceptionHandler 적용

```java
@Component
@ControllerAdvice
public class Ch10ExceptionHandler(){

	@ExceptionHandler
	public String handleRuntimeException(RuntimeException e){
		return "error/500";
	}
}
```

## 🌍Exception Custom

### Exception 클래스를 상속받는 클래스 생성
 - RuntimeException을 상속 받으면, 에러가 발생하는 메소드에 throws를 붙이지 않아도 된다.
```java
public class Chap10SoldOutException extends RuntimeException{

	public Chap10SoldOutException() {
		super("품절");
	}
	public Chap10SoldOutException(String message) {
		super(message);
	}
}
```
```java
@Component
@ControllerAdvice
public class Ch10ExceptionHandler(){

	@ExceptionHandler
	public String handleSoldOutException(Chap10SoldOutException e){
		return "error/soldout";
	}
}
```
```java
@Controller
@RequestMapping("/ch10")
public class Ch10Controller {
	@RequestMapping("/handlingException5")
	public String handlingException5() { // throws RuntimeException 생략함
		int stock=0;
		if(stock==0) {
			throw new Chap10SoldOutException("상품의 재고가 없습니다.");
		}
		return "ch10/content";
	}
}
```



## 🪐경로를 찾지 못하는 Exception 처리하는 방법
- 컨트롤러에 도달하지 않기 때문에, Controller에서 처리 불가능
- 404 Error가 발생했을 경우를 말한다.

#### web.xml
```xml
<!-- 404 에러 처리 -->
<error-page>
	<error-code>404</error-code>
	<location>/WEB-INF/views/error/404.jsp</location>
</error-page>
``` 
