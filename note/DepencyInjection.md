
# DI(Dependency Injection)
> 객체 실행에 필요한 다른 객체를 외부에서 주입하는 개념이다.   

<img src="https://user-images.githubusercontent.com/47289479/132450288-2116e6a2-d3b4-4145-b0da-f9735a79e661.png" width=400px /> <br/>
<img src="https://user-images.githubusercontent.com/47289479/132450511-45dab0c9-4251-4737-b2bf-e06952d7265a.png" width=800px />
#### <bean> 방법은 2가지 (생성자, setter 주입 방식) 사용가능

**생성자 주입 방식**
- 컨트롤러에서는 생성자 주입 방식을 사용할 수 없다.
	- 빈이 컨트롤러를 기본 생성자를 사용하여 컨트롤러 객체를 생성하기 때문에

```java
public Ch13Service1(Ch13BoardDao1 ch13BoardDao1) {
	this.ch13BoardDao1 = ch13BoardDao1;
}
```

**setter 주입 방식**


#### 어노테이션 방법은 3가지( 필드, 생성자, setter 주입 방식) 사용가능


## 🍄XML(\<bean>) 을 이용한 방법
- XML을 이용한 방법은 생성자와 Setter 주입 방식 2가지가 있다.

#### 생성자(constructor) 주입
```java
public Ch13Service1(Ch13BoardDao1 ch13BoardDao1) {
	this.ch13BoardDao1 = ch13BoardDao1;
}
```

- ref : 주입할 객체의 bean 이름 (id 혹은 name 참조) 
```xml
<bean name=“name” class=“package…ClassName”>
	<constructor-arg ref=“beanName”>
</bean>
```
```xml
<bean id="ch13BoardDao1" class="com.mycompany.webapp.dao.Ch13BoardDao1"/> <!-- IoC 관리 빈 생성 --!>

<bean id="ch13Service1" class="com.mycompany.webapp.service.Ch13Service1">  <!-- IoC 관리 빈 생성과 주입 --!>
	<constructor-arg ref="ch13BoardDao1"/>
</bean>
```
#### 프로퍼티(Setter) 주입
```java
public void setCh13BoardDao1(Ch13BoardDao1 ch13BoardDao1) { 
	this.ch13BoardDao1 = ch13BoardDao1;
}
```

```xml
<bean name=“name” class=“package…ClassName”>
	<property name=“propertyName”ref=“beanName”>
</bean>

<bean name=“name”  class=“package…ClassName” p:propertyName-ref=“beanName”>
```
- ref : 주입할 객체의 bean 이름 (id 혹은 name 참조) 
- name : setter 이름, setter이름에서 set을 제외한 첫글자를 소문자로 한 이름
```xml
<bean id="ch13BoardDao1" class="com.mycompany.webapp.dao.Ch13BoardDao1"/>
<bean id="ch13Service1" class="com.mycompany.webapp.service.Ch13Service1">
	<property name="ch13BoardDao1" ref="ch13BoardDao1"></property>
</bean>

<bean id="ch13Service1" class="com.mycompany.webapp.service.Ch13Service1" 
	p:ch13BoardDao1-ref="ch13BoardDao1"/>
```

## 🥕어노테이션 방법

### @Repository :  IoC 관리 bean인  Dao 객체를 만든다. 
```java
@Repository 
public class Ch13BoardDao2 {
	...
}
```

### @Service : IoC 관리 bean인 Service 객체를 만든다.

```java
@Service 
public class Ch13Service2 {
	...
}
```

### @Resource : 스프링이 관리하는 빈 객체를 자동으로 주입해준다. 
- @AutoWired와 동일하지만, 스프링이 관리하는 bean name으로 객체를 찾을 수 있어서 편리하다.
- @Resource는 생상자에는 적용할 수 없다.
<hr />

### 필드에 사용
- 필드에 자동으로 객체를 주입한다.
```java
@Resource 
private Ch13BoardDao2 ch13BoardDao2;
```

#### Setter에 사용
- 자동으로 setter를 호출하게 하여, 매개 변수에 자동으로 객체를 주입한다.
```java
@Resource 
public void setCh13BoardDao2(Ch13BoardDao2 ch13BoardDao2) { 
	this.ch13BoardDao2 = ch13BoardDao2;
}
```
### 생성자에 사용
- 거의 사용 안함

--------------------------------------------------------------------------------
## 🥜인터페이스 타입 주입
- 다형성을 실현시키기 위해 사용한다.
- 어떤 메소드에 대한 구현을  보장하기 위해서 사용한다.

### interface 생성
```java
public interface Ch13Service {
	public void method2();
}
```

### interface 구현
```java
@Service
public class Ch13Service3 implements Ch13Service {

	private static final Logger logger = LoggerFactory.getLogger(Ch13Service3.class);

	@Override
	public void method2() {
		logger.info("method2 실행");
	}
}
```

#### 타입으로 객체를 찾아서 주입
- 인터페이스를 구현한 것이 한개이면 type으로 주입한다.
```java
@Resource
private Ch13Service ch13Service; 
```

#### 이름으로 객체를 찾아서 주입
- 인터페이스를 구현한 것이 여러 개이면 타입으로 찾을 수 없다. name으로 찾아서 주입한다.
```java
@Resource(name="ch13Service4")
private Ch13Service ch13Service; 
```

## 🧅외부 프로퍼티 파일(*.properties)의 값 주입
> 자바 코드를 건드리지 않고, 여러 설정에 관한 값들을 불러올 수 있게 해준다.

### 프로퍼티 관리자 설정(spring/root/ch13_di.xml)
#### Properties 값 관리 설정
```xml
<context:property-placeholder location="classpath:properties/*.properties"/>
```
**ch13_di.properties**
```
service.prop1=10
service.prop2=10.5
service.prop3=false
service.prop4=문자열
```
<hr />

### XML 이용 주입(spring/root/ch13_di.xml)
#### Properties 값 주입
- 값을 주입할 때는 value 사용, 객체를 주입할 때는 ref 사용

```xml
<bean id="ch13Service5" class="com.mycompany.webapp.service.Ch13Service5">
	<constructor-arg value="${service.prop1}" index="0"></constructor-arg> <!-- 생성자 주입 --!>
	<constructor-arg value="${service.prop2}" index="1"></constructor-arg> <!-- 생성자 주입 --!>
	<property name="prop3" value="${service.prop3}"/> <!-- setter주입 --!>
</bean>
```
<hr />

### Annotation 이용 주입(spring/root/ch13_di.xml)
#### @Value("${}") 사용
```java
@Service
public class Ch13Service6 {

	private static final Logger logger = LoggerFactory.getLogger(Ch13Service6.class);

	private int prop1;
	private double prop2;
	private boolean prop3;
	@Value("${service.prop4}")
	private String prop4;
	
	public Ch13Service6(@Value("${service.prop1}") int prop1, @Value("${service.prop2}") double prop2) {
		logger.info("prop1 : " + prop1);
		logger.info("prop2 : " + prop2);
		this.prop1 = prop1;
		this.prop2 = prop2;
	}

	@Value("${service.prop3}")
	public void setProp3(boolean prop3) {
		logger.info("prop3 : " + prop3);
		this.prop3 = prop3;
	}
}
```


