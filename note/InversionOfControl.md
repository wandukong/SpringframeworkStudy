# IoC(Inversion of Control)
>제어의 역전이라는 의미로, 말 그대로 메소드나 객체의 호출작업을 개발자가 결정하는 것이 아니라, 외부에서 결정되는 것을 의미한다.
- 스프링 IoC 컨테이너가 관리하는 객체들을 Bean 이라고 부른다.
- 스프링은 이러한 Bean들의 의존성을 관리하고, 객체를 만들어 준다.
- WebApplicationContext : 스프링에서 사용하는 IOC 컨테이너

## 💙XML 설정 방식
```xml
<bean [id|name]=“name”class=“package…ClassName”/>
```
- 웹 애플리케이션 구동시 자동으로 객체가 생성되고 관리
- id 또는 name의 값으로 객체를 관리한다.
- 주로 외부 라이브러리 클래스를 이용할 경우 XML로 관리 빈 설정
- 기본 생성자 필요

## 💚Annotatoin  설정 방식
```java
[@Controller(“name”) | @Service(“name”) |
@Repository(“name”) | @Component(“name”)]
public class ClassName { … }
```
- 웹 애플리케이션 구동시 자동으로 객체가 생성되고 관리
- name이 주어지지 않으면 클래스 이름의 첫자를 소문자로 한 이름을 사용
- 기본 생성자 필요

### Annotation 인식
```xml
<context:component-scan base-package=“com.mycompany”/>
```
- base-package="" : Annotation을 찾을 시작 패키지(하위 패키지 모두 검색)
<hr />

### 관리 빈 생성에 포함될 Annotation 지정
```xml
<context:include-filter>
```
- 관리 빈 생성에 포함될 Annotation 지정
```xml
<!-- dispatcher servlet 설정 xml --!>
<context:component-scan base-package="com.mycompany.webapp" use-default-filters="false">
	<context:include-filter type="annotation" expression="org.springframework.stereotype.Service" />
	<context:include-filter type="annotation" expression="org.springframework.stereotype.Repository" />
</context:component-scan>
```
- 각 webApplication에서 @Service, @Repository가 적용된 클래스만 객체로 관리한다.
<hr />

### 관리 빈 생성에 제외될 Annotation 지정
```xml
<context:exclude-filter>
```		
```xml
<!-- root webApplicationcontext 설정 xml --!>
<context:component-scan	base-package="com.mycompany.webapp">
	<context:exclude-filter type="annotation" expression="org.springframework.stereotype.Service"/>
	<context:exclude-filter type="annotation" expression="org.springframework.stereotype.Repository"/>
</context:component-scan>
```
- root webApplicationcontext에서 @Service @Repository가 적용된 클래스를 객체로 관리하지 않는다.

