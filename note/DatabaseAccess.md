# Database Access

## 📘JDBC
> Java 응용프로그램이 DBMS에 연결하여 데이터베이스를 일관된 방법으로 이용할 수 있도록 만든 표준 API(인터페이스와 클래스)를 말한다.

<img src="https://user-images.githubusercontent.com/47289479/132978401-b151fddb-cdd7-4f0f-8b8e-ea23613b1463.png" width=700px/>

```xml
<!-- JDBC -->
<!-- JDBC를 지원해주는 고급(진화된) 라이브러리-->
<dependency>
	<groupId>org.springframework</groupId>
	<artifactId>spring-jdbc</artifactId>
	<version>${org.springframework-version}</version>
</dependency>
```

## 📕JDBC Driver
> Java 응용 프로그램이 데이터베이스와 상호 작용할 수 있도록하는 소프트웨어 구성 요소입니다.
- DB 연결의 주체이다.
- JDBC 드라이버가 있어야 DB에 접근할 수 있다.

```xml
<!-- JDBC Driver -->
<dependency>
	<groupId>com.oracle.database.jdbc</groupId>
	<artifactId>ojdbc8</artifactId>
	<version>19.3.0.0</version>
</dependency>
```

## 📙DBCP

> DataBase Connection Pool의 약자이며, 데이터베이스와 애플리케이션을 효율적으로 연결하는  **커넥션 풀 라이브러리**를 말한다.
- Connection 객체를 관리하는 컨테이너이다.
- DataSource 인터페이스를 사용한다.
- 데이터베이스에서 데이터를 불러올 때, 가장 오랜 시간이 걸리는 부분이 DB와 연결하는 부분이다.
	-  매 요청마다 DB연결을 하면 시간이 너무 오래 걸린다.
- DBCP는 WAS가 실행되면서  **미리 일정량의 DB Connection 객체를 생성하고 Pool이라는 공간에 저장**한다.
	- 클라이언트가 요청을 했을 때 데이터베스가 필요한 경우, 연결된 객체를 통해서 SQL을 전송한다.
	- IP, PORT, 사용자계정(ID,PASSWORD ), DB 이름이 포함됨
-  저장된 DB Connection 객체는 요청에 따라 필요할 때마다 Pool에서 가져다 쓰고 반환할 수 있다.

```xml
<!-- dbcp2 -->
<dependency>
	<groupId>org.apache.commons</groupId>
	<artifactId>commons-dbcp2</artifactId>
	<version>2.9.0</version>
</dependency>
```

```xml
<bean id="dataSource" class="org.apache.commons.dbcp2.BasicDataSource">
	<property name="driverClassName" value="oracle.jdbc.OracleDriver"/>
	<property name="url" value="jdbc:oracle:thin:@로컬호스트:포트번호:orcl"/> 
	<property name="username" value="사용자계정"/>
	<property name="password" value="비밀번호"/>
	
	<property name="initialSize" value="2" />
	<property name="maxTotal" value="3"/>
	<property name="maxIdle" value="2"/> 
</bean>
```
- name에 setter 메소드의 set을 제외한 첫글자를 소문자로 바꾼 값이 들어간다. 
- initialSize : 커네션풀이 만들어질 때 연결 객체 생성 갯수
- maxTotal : 최대 연결 객체 생성 갯수
- maxIdle : 최소 몇개를 남겨 놓고 닫을 것인지 설정, 일반적으로 initialSize와 동일하게 놓는다.


## 📒Controller / Service / Dao
<img src="https://user-images.githubusercontent.com/47289479/133029723-dbe8dd28-f2f3-4291-baba-7a49f4a5fb6b.png" width=600px />

### Controller
> 클라이언트에서 요청이 들어올 때, 해당 요청을 수행할 비지니스 로직을 제어(분리)하는 객체이다.
- 데이터 유효성 검사 수행
```java
@Resource
private Ch14MemeberService memberService;

@GetMapping("/join")
public String joinForm() {
	return "ch14/joinForm";
}

@PostMapping("/join")
public String join(Ch14Member member, Model model) {
	member.setMenabled(1);
	member.setMrole("ROLE_USER");
	JoinResult jr = memberService.join(member);
	if(jr == JoinResult.SUCCESS) {
		return "redirect:/ch14/content";
	}else if(jr == JoinResult.DUPLICATED) {
		model.addAttribute("error", "중복된 아이디가 있습니다.");
		return "ch14/joinForm";
	}else { // 예외 발생시
		model.addAttribute("error", "회원 가입이 실패되었습니다. 다시 시도해 주세요.");
		return "ch14/joinForm";
	}
}
```

<hr />

### Service
> 나뉘어진 비지니스 로직을 처리하는 객체이다.
- controller가 보낸 client의 요청을 기능 위주로 처리한다.
	- ex) join/login, order/detailOrder, deposit/withdraw
```java
public enum JoinResult {
	SUCCESS, FAIL, DUPLICATED
}

public JoinResult join(Ch14Member member) { // 회원 가입을 처리하는 비지니스 로직
	try {
		// 이미 가입된 아이디인지 확인
		Ch14Member dbMember = memberDao.selectByMid(member.getMid());

		// DB에 회원 정보를 저장
		if (dbMember == null) {
			try {
				memberDao.insert(member);
				return JoinResult.SUCCESS;
			} catch (Exception e) {
				return JoinResult.FAIL;
			}

		} else{
			return JoinResult.DUPLICATED;
		}
	}catch(Exception e) {
		e.printStackTrace();
		return JoinResult.FAIL;
	}
}
```
<hr />

### Dao
> Data Access Object의 준말로, DB를 사용해 데이터를 조회 및 조작하는 기능을 전담하는 객체이다.
- sql문을 수행한다.
	- select, update, insert, delete
### *jdbc api를 이용할 때  dao로 구분하게 되면, 예외처리가 많아지면 코드가 지저분해진다.*
### => MyBatis 사용
-	mybatis는 try ~catch를 구현하지 않게 한다. RuntimeException으로 예외를 발생시킨다. 
- RuntimeException 에러가 발생할 수 있는데, 이 예외를 service에서 잡는다. 
- 절대 spring에선 dao의 예외 처리를 하지 않는다 .
	- dao에서 예외처리를 하면, **transaction** 처리를 못하게된다.
```java
@Resource 
SqlSessionTemplate sqlSessionTemplate;
	
public void insert(Ch14Member member) {
	sqlSessionTemplate.insert("member.insert", member); // mapper의namespace.namespace안의id값
}

public Ch14Member selectByMid(String mid) {
	return sqlSessionTemplate.selectOne("member.selectByMid", mid);
}
```

## 📗MyBatis
> 자바의 JDBC를 이용한 퍼시스턴스 프레임워크입니다. SQL쿼리들을 따로 XML파일로 작성하여 프로그램 코드와 SQL문을 관리하기 용이하다.
> 마이바티스는 개발자가 지정한 SQL, 저장프로시저 그리고 몇가지 고급 매핑을 지원하는 퍼시스턴스 프레임워크이다. 
- 마이바티스는 JDBC로 처리하는 상당부분의 코드와 파라미터 설정 및 결과 매핑을 대신해준다. 
- 마이바티스는 데이터베이스 레코드에 원시타입과 Map 인터페이스 그리고 자바 POJO 를 설정해서 매핑하기 위해 XML과 애노테이션을 사용할 수 있다.

### pom.xml에 dependency 추가
```xml
<!--spring에서 mybatis를 지원하게 해준다.  -->
<dependency>
	<groupId>org.mybatis</groupId>
	<artifactId>mybatis-spring</artifactId>
	<version>2.0.6</version>
</dependency>

<!- mybatis -->
<dependency>
	<groupId>org.mybatis</groupId>
	<artifactId>mybatis</artifactId>
	<version>3.5.7</version>
</dependency>
```
<hr />

### root application-context bean 추가
#### Connection Pool 객체를 SqlSessionFactoryBean 안에 주입하고, SqlSessionFactoryBean 객체를 SqlSessionTemplate 안에 주입한다.
```xml
<!-- Connection Pool--!>
<bean id="dataSource" class="org.apache.commons.dbcp2.BasicDataSource">
	<property name="driverClassName" value="oracle.jdbc.OracleDriver"/>
	<property name="url" value="jdbc:oracle:thin:@kosa1.iptime.org:50108:orcl"/>
	<property name="username" value="spring"/>
	<property name="password" value="oracle"/>
	
	<property name="initialSize" value="2" />
	<property name="maxTotal" value="3"/>
	<property name="maxIdle" value="2"/> 
</bean>

<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
	<!-- 연결 객체 설정 : 커넥션 풀 주입--!>
	<property name="dataSource" ref="dataSource"/> 
	<!-- mybatis 관련 설정--!>
	<property name="configLocation" value="classpath:mybatis/mapper-config.xml"/> 
	<!-- mapper 위치 설정 --!>
	<property name="mapperLocations" value="classpath:mybatis/mapper/*.xml"></property> 
</bean>

<bean id="sqlSessionTemplate" class="org.mybatis.spring.SqlSessionTemplate">
	<constructor-arg ref="sqlSessionFactory" />
</bean>
```
<hr />

### mapper-config.xml
- typeAliases :  클래스에 alias(별명)를 지어주어, resultType 혹은 requestType에 사용한다 -->
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN" "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
	<typeAliases>
		<typeAlias alias="member" type="com.mycompany.webapp.dto.Ch14Member"/> 
	</typeAliases>
</configuration>
```
<hr />

### Mapper xml 파일
> 프로그램 객체를 DB 테이블과 작업을 하도록 SQL과 매핑을 정의한 XML 파일
- parameterType, resultType
	- 	기본 타입은 앞에 언더바를 설정 
		- int => _int 
		- double => _double 
	-  레퍼런스 타입은 앞글자를 소문자로 바꾼다
		- String => string 
		- Integer => integer 
	- 클래스 이름은 config 설정 파일에서 설정한 alias를 사용할 수 있다.
	
#### member.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="member">
	<insert id="insert" parameterType="member">
		insert into member 
		(mid, mname, mpassword, menabled, mrole) 
		values (#{mid}, #{mname}, #{mpassword}, #{menabled}, #{mrole}) <!-- getter이름 -->
	</insert>
	
	<select id="selectByMid" parameterType="string" resultType="member"> <!-- 파라미터타입과 리턴타입을 지정해준다. -->
		select mid, mname, mpassword, menabled, mrole
		from member
		where mid=#{value}
	</select>
</mapper>
```