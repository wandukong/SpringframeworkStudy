
# JWT
> Json Web Token의 약자로, 선택적 서명 및 선택적 암호화한 토큰이다.

<img src="https://user-images.githubusercontent.com/47289479/131851257-5b7f038c-8221-4afb-aa29-191dcd67ad44.png" width=400px />

- header, payload, signature로 구성된다.
	- header : 토큰 종류와 해시 알고리즘 정보 포함
		- alg와 typ로 구성, alg는 서명시 사용하는 알고리즘, typ는 token type이다.
	- payload : 토큰에 담을 정보, 담은 정보의 한 조각을 '클레임(claim)'이라고 부른다. 클레임의 종류로는 registered, public, private 클레임이 존재한다.
	- signature : 암호화된 서명(위조여부 확인용)이다. 
		- header의 인코딩값, payload의 인코딩값을 합친 후, 주어진 비밀키로 해쉬하여 생성한다.


- 회원인증, 정보교류 등에 사용한다.
	- 유저가 로그인을 하면, 서버는 유저의 정보에 기반한 토큰을 발급하여 유저에게 전달해준다. 그 후, 유저가 서버에 요청을 할 때 마다 JWT를 포함하여 전달한다. 서버가 클라이언트에게서 요청을 받을때 마다, 해당 토큰이 유효하고 인증됐는지 검증을 하고, 유저가 요청한 작업에 권한이 있는지 확인하여 작업을 처리한다. 서버측에서는 유저의 세션을 유지 할 필요가 없습니다. 즉, 유저가 로그인되어있는지 안되어있는지 신경 쓸 필요가 없고, 유저가 요청을 했을때 토큰만 확인하면 되니, 세션 관리가 필요 없어서 서버 자원을 많이 아낄 수 있다.
	- JWT는 두 개체 사이에서 안정성있게 정보를 교환할 수 있다. 정보가 sign이 됐기 때문에 정보를 보낸사람이 바뀌었는지, 정보가 도중에 조작되었는지 검증할 수 있다.

## 🎸JWT 라이브러리 등록
#### pom.xml에 jwt dependency 추가하기
```xml
<dependency>
	<groupId>io.jsonwebtoken</groupId>
	<artifactId>jjwt</artifactId>
	<version>0.9.1</version>
</dependency>
```

## 🥁JWT 생성하여 암호화하기
### JwtBuilder 생성하기
```java
JwtBuilder builder = Jwts.builder();
```
<hr />

### Header 설정
**.setHeaderParam("name","value");**
```java
bulder.setHeaderParam("alg","HS256");
bulder.setHeaderParam("typ","JWT"); 
```
<hr />

### Payload 설정
**.claim("name","value");**
```
builder.claim("userid", userid);
builder.claim("useremail", useremail);
builder.claim("username", username);
```
<hr />

### Signature 설정
**.signWith("sign알고리즘","비밀키");**
- sign알고리즘에 header의 alg값과 같은 값을 넣는다.
- 비밀키는 바이트형태로 인코딩한다.
```java
String secretKey = "abc12345";
builder.signWith(SignatureAlgorithm.HS256, secretKey.getBytes("UTF-8")); 
```
<hr />

### 만료기간 설정
**.setExpiration(millisecond)**
```java
builder.setExpiration(new Date(new Date().getTime() + 1000*60*30)); // 지금으로부터 30분
// new Date().getTime() 1970년 자정부터 지금까지의 milliseconds초
```
<hr />

### String으로 압축
**.compat()**
```java
String jwt = builder.compact(); // 압축
```
<hr />

## 📯JWT 복호화
### JwtParser 생성하기
```java
JwtParser parser = Jwts.parser();
```
<hr />

### parser 비밀키 설정하기
**.setSingingKey("시크릿키");**
```java
String secretKey = "abc12345"
paser.setSingingKey(secretKey.getBytes("UTF-8));
```
<hr />

### claim 받아오기
```java
Jws<Claims> jws = parser.parseClaimsJws(jwt); 
Claims claims= jws.getBody();
String userid = claims.get("userid", String.class);
String useremail = claims.get("useremail", String.class);
String username = claims.get("username", String.class);
```


## 🎻JWT로 쿠키 사용하기
```java
Cookie cookie = new Cookie("jwt",jwt);
reponse.addCookie(cookie);
```

## 🎺JWT 쿠키 받기

```java
@GetMapping("/getJwtCookie")
public String getJwtCookie(@CookieValue String jwt) throws Exception {

	JwtParser parser = Jwts.parser();
	String secretKey = "abc12345";
	parser.setSigningKey(secretKey.getBytes("UTF-8"));

	Jws<Claims> jws = parser.parseClaimsJws(jwt);

	Claims claims= jws.getBody();

	String userid = claims.get("userid", String.class);
	String useremail = claims.get("useremail", String.class);
	String username = claims.get("username", String.class);
	
	return "redirect:/ch05/content";
}
```