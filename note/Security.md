
# Security
> Spring에서 어플리케이션의 인증과 권한 부여 및 보안 기능 등을 담당하는 프레임워크이다.
- **Authentication** : 인증,  자신이 누구라고 주장하는 사람을 확인하는 절차
- **Authorizatiom** : 인가, 권한 부여, 특정한 일을 하려고 할 때 그것을 허용하는 행위. 

### 인증 및 권한 부여 설정

- **\<security:http>** 태그 안에 작성한다.
```xml
<security:http>
	...
</security:http>
```

## 🍝환경 설정 
### dependency 추가
- 스프링 버전에 맞는 dependency 추가하기
```xml
<!-- spring security -->
<dependency>
	<groupId>org.springframework.security</groupId>
	<artifactId>spring-security-web</artifactId>
	<version>5.5.1</version>
</dependency>

<dependency>
	<groupId>org.springframework.security</groupId>
	<artifactId>spring-security-config</artifactId>
	<version>5.5.1</version>
</dependency>

<dependency>
	<groupId>org.springframework.security</groupId>
	<artifactId>spring-security-taglibs</artifactId>
	<version>5.5.1</version>
</dependency>
```
<hr/>

### web.xml
- **springSecurityFilterChain** : 필터 이름으로, 예약된 이름이라 수정하면 안된다. 
```xml
<!-- Security filter 등록 -->
<filter>
	<filter-name>springSecurityFilterChain</filter-name>
	<filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
</filter>
<filter-mapping>
	<filter-name>springSecurityFilterChain</filter-name>
	<url-pattern>/*</url-pattern>
</filter-mapping>

<!-- Spring Security 로그인 중복 방지를 위해 설정 -->
<listener>
	<listener-class>org.springframework.security.web.session.HttpSessionEventPublisher</listener-class>
</listener>
```

## 🍜보안 필터 체인 적용하지 않을 경로 설정
- 인증 절차가 필요없는 경로 설정 
- 보안 필터 체인을 적용하지 않는 요청 경로 
- 보안 필터 체인 설정보다 **먼저** 설정해야한다.
```xml
<security:http pattern="/resources/**" security="none"/>
```

## 🥘사이트간 요청 위조 방지(CSRF)
> Cross-Site Request Forgery
> 서버에서 허용한 요청이 맞는지 확인한다.
- true: csrf 비활성화 / false : csrf 활성화
```xml
<security:csrf disabled="false"/>
```
- form에  **\<input type="hidden" name="\${_crsf.parameterName}" value="\${_csrf.token}">** 을 추가하여 서버에 토큰 값을 넘겨준다.
```html
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
```
- security 태그 라이브러리를 사용할 수 있다.
```html
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<sec:csrfInput/>
```
- 해당 정보가 오지 않으면, 자기가 보낸 폼이 아니라는 것으로 알고, 403 error 발생
- logout도 _csrf의 토큰을 전달하기 위해 post 방식으로 요청
```html
<form method="post" action="${pageContext.request.contextPath}/logout" class="d-inline-block">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	<button class="btn btn-danger btn-sm">로그아웃</button> 
</form>
````

## 🍔로그인 설정
### 방법1 : 핸들러 사용 X
```xml
<security:form-login 
	login-page="/ch17/loginForm"  
	username-parameter="mid"
	password-parameter="mpassword"
	login-processing-url="/login" 
	always-use-default-target="false"
	default-target-url="/"
	authentication-failure-url="/ch17/loginForm"/>
```
- login-page : 로그인 입력 페이지
- user-name-parameter : 사용자가 입력하는 로그인 ID 파라미터 이름
- password-parameter : 사용자가 입력하는 로그인 PASSWORD 파라미터 이름
- login-processiong-url : 로그인 처리 url
	- 이미 정해져 있는 값으로, spring security가 처리해주는 경로이다.
- always-use-default-target : 로그인 성공 시 default-target-url로 갈 것인지 설정
- default-target-url : 로그인 성공시 이동할 경로
- authentication-failure-url: 로그인 실패시 이동할 경로
<hr />

### 방법2 : 핸들러 사용
```xml
<security:form-login 
	login-page="/ch17/loginForm"
	username-parameter="mid"
	password-parameter="mpassword"
	login-processing-url="/login" 
	authentication-success-handler-ref="authenticationSuccessHandler"
	authentication-failure-handler-ref="authenticationFailureHandler"/> 
```
- authentication-success-handler-ref : 로그인 성공 시 사용할 핸들러 지정
- authentication-failure-handler-ref= 로그인 실패 시 사용할 핸들러 지정
<hr />

### 인증 성공 핸들러 클래스 구현
- 로그인 성공 후, 페이지 이동 전에 처리할 로직을 작성할 수 있다.
- **SimpleUrlAuthenticationSuccessHandler 상속** :  로그인 성공 후, 무조건 defaultTargetURL으로 이동
- **SavedRequestAwareAuthenticationSuccessHandler 상속** : 로그인 성공 후, 사용자가 접근하고자 했던 페이지로 이동
```java
@Component
//public class Ch17AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
public class Ch17AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	private static final Logger logger = LoggerFactory.getLogger(Ch17AuthenticationSuccessHandler.class);
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		logger.info("onAuthenticationSuccess 실행");
		super.onAuthenticationSuccess(request, response, authentication);
	}
}
```
<hr />

### 인증 실패 핸들러 클래스 구현
- 로그인 실패 후, 페이지 이동 전에 처리할 로직을 작성할 수 잇다.
- **SimpleUrlAuthenticationFailureHandler 상속**
```java
public class Ch17AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler{
	private static final Logger logger = LoggerFactory.getLogger(Ch17AuthenticationFailureHandler.class);
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {

		logger.info("onAuthenticationFailure 실행");
		super.onAuthenticationFailure(request, response, exception);
	}
}
```
<hr />

### 핸들러 빈 등록
**root/security.xml**
- 인증 성공 및 실패 핸들러는 @Component을 사용하여 빈 객체로 등록되었다. 이로 인해 root-applicationContext 에서는 dispatcher-servlet에서 관리하는 빈을 직접 사용할 수 없다.
	- root-applicationContext에서 새로운 bean을 만들어 관리한다.
- \<property name="useForward" value="true"/>:  **포워드 방식**으로 가고 싶을 경우 설정
<!-- com.mycompany.webapp.security.Ch17AuthenticationSuccessHandler는 dispatcher sevlet이 관리 하기때문에 -->

```xml
<bean id="authenticationSuccessHandler" class="com.mycompany.webapp.security.Ch17AuthenticationSuccessHandler">
	<property name="alwaysUseDefaultTargetUrl" value="false"/>
	<property name="defaultTargetUrl" value="/" />
	<!-- <property name="useForward" value="true"/> -->
</bean>
<bean id="authenticationFailureHandler" class="com.mycompany.webapp.security.Ch17AuthenticationFailureHandler">
	<property name="defaultFailureUrl" value="/ch17/loginForm"/>
</bean>
```	
<hr />

### 인증 관리자 설정
- 입력한 id와 password를 DB와 비교해서 인증 처리를 한다.

```xml
<security:jdbc-user-service 
	id="jdbcUserService" 
	data-source-ref="dataSource" 
	users-by-username-query="select mid, mpassword, menabled from member where mid=?"
	authorities-by-username-query="select mid, mrole from member where mid=?"/>
<!--  
	사용자 정보 불러오기
	data-source-ref : 커넥션 풀 객체 지정
	users-by-username-query : 유저 정보 불러오기 
		아이디(mid), 비밀번호(password), 활성화여부(menabled) 순으로 세개의 정보를 꼭 가져와야한다. 
	group-authorities-by-username-query : 유저 권한(롤) 불러오기
-->	

<bean id="daoAuthenticationProvider" class="org.springframework.security.authentication.dao.DaoAuthenticationProvider">
	<property name="userDetailsService" ref="jdbcUserService"/>
	<property name="authoritiesMapper" ref="roleHierarchyAuthoritiesMapper"/>
</bean>
<!--
	인증 제공자 설정
	유저 정보를 불러올 객체(jdbcUserService)와
	권한 계층 맵퍼 객체(roleHierarchyAuthoritiesMapper) 주입
-->

<security:authentication-manager>
	<security:authentication-provider ref="daoAuthenticationProvider"/>
</security:authentication-manager>
<!-- 
	인증 관리자 설정
	인증 제공자를 부여한다.
-->
```
<hr />

### 인증 중복 방지
- max-sessions : 최대 로그인 수 
- error-if-maximum-exceeded : 최대 로그인 수를 벗어나면 에러 발생 여부 
	- true :새로운 로그인 차단, false : 이전 로그인 차단
- error-if-expired-url-exceeded : 인증이 만료되면 이동할 url

```xml
<security:session-management>
	<!-- <security:concurrency-control 
		max-sessions="1"
		error-if-maximum-exceeded="true" /> -->
	
	<security:concurrency-control 
		max-sessions="1"
		error-if-maximum-exceeded="false" 
		expired-url="/ch17/loginForm"/>
</security:session-management>
```


**loginform.jsp**
- error-if-maximum-exceeded="true" 인 경우, 이전 인증들을 보존한다.
- 새로운 인증에 대한 에러 메시지를 처리하기 위해 **SPRING_SECURITY_LAST_EXCEPTION** 객체 이용
```html
<c:if test="${SPRING_SECURITY_LAST_EXCEPTION !=null}">
	<div class="alert alert-danger" role="alert">
		<c:if test="${SPRING_SECURITY_LAST_EXCEPTION.message == 'Bad credentials'}">
			아이디 또는 비밀번호가 틀립니다.
		</c:if>
		<c:if test="${fn:contains(SPRING_SECURITY_LAST_EXCEPTION.message,'principal exceeded')}">
			인증 횟수가 초과되었습니다.
		</c:if>
	</div>
</c:if>
```

## 🍕로그아웃 설정

```xml
<security:logout
	logout-url="/logout"
	success-handler-ref="logoutSuccessHandler"/>
```
- logout-url : 로그아웃 처리 url
	- 이미 정해져 있는 값으로, spring security가 처리해주는 경로이다.
- success-handler-ref : 로그아웃 성공 시 사용할 핸들러 지정
<hr />

#### 인증 해제(로그아웃) 성공 핸들러 클래스 구현
- 로그아웃 성공 후, 페이지 이동 전에 처리할 로직을 작성할 수 잇다.
- **SimpleUrlLogoutSuccessHandler 상속**

```java
public class Ch17LogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler{
	private static final Logger logger = LoggerFactory.getLogger(Ch17LogoutSuccessHandler.class);
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		logger.info("onLogoutSuccess 실행");
		super.onLogoutSuccess(request, response, authentication);
	}
}
```
<hr />

#### 핸들러 빈 등록
```xml
<bean id="logoutSuccessHandler" class="com.mycompany.webapp.security.Ch17LogoutSuccessHandler">
	<!-- 로그아웃 성공 후 리다이렉트로 이동할 경로 -->
	<property name="defaultTargetUrl" value="/ch17/content"/>
</bean>
```

## 🍣권한 설정

### 요청 url 권한 설정
- 주어진 role에 속하는 사용자는 접근 허용
  - hasAuthority(‘role’), hasRole(‘role’)
- 주어진 role 목록 중에 한 role에 속하는 사용자는 접근 허용
	- hasAnyAuthority(String …​ authorities), hasAnyRole(String …​ roles)
- 인증(로그인)만 되었다면 접근 허용
	- isAuthenticated()
- 모두 허용
	- permitAll
- 모두 거부
	- denyAll
- 주어진 ip만 접근 허용
	- hasIpAddress('ip')

```xml
<security:intercept-url pattern="/ch17/admin*" access="hasRole('ROLE_ADMIN')"/>
<security:intercept-url pattern="/ch17/manager*" access="hasRole('ROLE_MANAGER')"/>
<security:inteept-url pattern="/ch17/user*" access="isAuthenticated()"/>
<security:intercept-url pattern="/**" access="permitAll"/>
```
<hr/>

### 권한 계층 설정
```xml
<bean id="roleHierarchyImpl" class="org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl">
	<property name="hierarchy">
		<value>
			ROLE_ADMIN > ROLE_MANAGER
			ROLE_MANAGER > ROLE_USER
		</value>
	</property>
</bean>

<bean id="roleHierarchyAuthoritiesMapper" class="org.springframework.security.access.hierarchicalroles.RoleHierarchyAuthoritiesMapper">
	<constructor-arg ref="roleHierarchyImpl"/>
</bean>
```
<hr/>

### 권한이 충족되지 않을 경우 보여줄 페이지 설정
```xml
<security:access-denied-handler error-page="/ch17/error403"/>
```

## 인증 정보 불러오기

#### Spring Security가 인증 정보를 저장하는 컨테이너 객체를 얻기

```java
SecurityContext securityContext = SequrityContextHolder.getConetxt(); 
```
<hr/>

#### 인증 정보 객체 얻기
```java
Authentication authentication = securityContext.getAuthentication();
```
<hr />

#### 인증 id 얻기
```java
String mid = authentication.getName();
```
<hr />

#### 권한 얻기
- 사용자의 권한이 여러 개일 수 있으니 **List**로 받아야한다.
```java
List<String> authorityList = new ArrayList<>();
for(GrantedAuthority authority : authentication.getAuthorities()) {
	authorityList.add(authority.getAuthority());
}
```
<hr />

#### client ip 얻기
```java
WebAuthenticationDetails wad = (WebAuthenticationDetails) authentication.getDetails();
String ip = wad.getRemoteAddress();	
```
<hr />

## 🥪비밀번호 암호화
- 비밀번호를 DB에 저장할 때, 어떤 알고리즘을 사용했는지 비밀번호에 명시하고 암호화된 비밀번호와 함께 저장해야 한다.
<img src="https://user-images.githubusercontent.com/47289479/133894557-f4d810c1-ebac-4a67-a084-f3b9784beb2c.png" width=600px/>
```java
String mpassword = member.getMpassword();
BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
mpassword = "{bcrypt}"+passwordEncoder.encode(mpassword);
member.setMpassword(mpassword);
```