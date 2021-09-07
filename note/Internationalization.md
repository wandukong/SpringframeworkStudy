# Internationalization

## form에 적용하기
**ch11_internationalization_ko.properties**
```
join.form.mid=아이디
join.form.mname=이름
join.form.mpassword=비밀번호
join.form.mnation=국가
join.form.email=이메일
join.form.submit=가입
```

**bean 객체에 value 추가하기**
```xml
<bean id="messageSource"
     class="org.springframework.context.support.ResourceBundleMessageSource">   
     <property name="basenames">
        <list>
           ...
           <value>message/ch11_internationalization</value>
        </list>
     </property>
     <property name="defaultEncoding" value="UTF-8"/>
  </bean> 
```

**jsp에 적용하기**
```html
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%> <!-- 태그 라이브러리 추가--!>

<form:form method="post" modelAttribute="member">
	<div class="form-group">
		<label for="mid"><spring:message code="join.form.mid"/></label>
		<form:input type="text" class="form-control" path="mid" />
	</div>
	<div class="form-group">
		<label for="mname"><spring:message code="join.form.mname"/></label>
		<form:input type="text" class="form-control" path="mname"/>
	</div>
	<div class="form-group">
		<label for="mpassword"><spring:message code="join.form.mpassword"/></label>
		<form:password class="form-control" path="mpassword" />
	</div>
	<div class="form-group">
		<label for="mnation"><spring:message code="join.form.mnation"/></label>
		<form:input type="text" class="form-control" path="mnation" />
	</div>
	<button type="submit" class="btn btn-primary">Submit</button>
</form:form>
```