# Spring Form Tag Library 
> 폼 태그 라이브러리를 사용하면, 폼에 데이터를 바인딩하거나 에러메세지 처리 등을 간편하게 할 수 있다.

## 🦓태그 라이브러리 추가
```html
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
```

## 🐭기본 구성
```html
<form:form ModelAttribute="모델명" method="get|post" action="수행처리할url">
 ```
 - ModelAttribute : 양식의 초기값을 가지고 있는 객체 이름
 - method : post가 default 값으로, 생략가능
 - action : 폼에 작성된 데이터를 처리할 url, default 값은 해당 폼을 요청한 url값으로 한다.
 
### 태그 속성 
 ** id : label 태그와 연동
 ** name  : 서버에 전송되는 변수명
 ** value : 서버로 전송되는 값
 
## 🐷drop down List (select) dto 객체와 바인딩
> **\<form:select> 사용**

### 1. 기본적인 drop down list 
<img src="https://user-images.githubusercontent.com/47289479/132237507-e27a753e-b1e9-4b5e-884b-d934b7ab470b.png" width=300px/>

```java
@GetMapping(value="/form2")
public String form2(@ModelAttribute("member") Ch11Member member, Model model) {

	List<String> typeList = new ArrayList<>();
	typeList.add("일반회원");
	typeList.add("기업회원");
	typeList.add("헤드헌터회원");
	model.addAttribute("typeList", typeList);
	
	member.setMtype("헤드헌터회원"); // 기본 선택 항목을 설정
	return "ch11/form2";
}
```

- Spring Tag Library 사용 전
  - forEach 문으로, mebmer 객체가 기본값으로 설정한 값을 골라낸다.
```html
<form method="post" action="form2">
  <div class="form-group">
    <label for="mtype">Type</label>
    <select class="form-control" id="mtype" name="mtype">
    	<c:forEach var="mtype" items="${typeList}">
			<option value="${mtype}"
				<c:if test="${member.mtype==mtype}">selected</c:if>
			>${mtype}</option>	
    	</c:forEach>
    </select>
  </div>
</form> 
```
- Spring Tag Libary  사용 후
	- forEach을 하지 않아도, 기본값으로 설정해준다.
	- items : 보여줄 option 값을 담은 객체
	- path :  저장된 값을 path에 저장함, 모델 객체의 멤버변수들 중 하나, path는 name과 id 속성을 자동으로 생성한다.
```html
<form:form modelAttribute="member">
	<div class="form-group">
		<label for="mtype">Type</label>
		<form:select path="mtype" items="${typeList}" class="form-control" />
	</div>
</form:form>
```
<hr />

### 2.  '--선택하세요--'  포함된 drop down list 
<img src="https://user-images.githubusercontent.com/47289479/132239088-bb1898ba-b2b8-480b-913c-65787726c637.png" width=300px/>

```java
@GetMapping(value="/form2")
public String form2(@ModelAttribute("member") Ch11Member member, Model model) {

	List<String> jobList = new ArrayList<>();
	jobList.add("학생");
	jobList.add("개발자");
	jobList.add("디자이너");
	model.addAttribute("jobList", jobList);
	
	member.setMjob("개발자");
	return "ch11/form2";
}
```

- Spring Tag Library 사용 전
```html
<form method="post" action="form2">
	 <div class="form-group">
	    <label for="mjob">Job</label>
	    <select class="form-control" id="mjob" name="mjob">
	    	<option value="">---선택하세요---</option>
	    	<c:forEach var="job" items="${jobList}">
				<option value="${job}"
					<c:if test="${member.mjob==job}">selected</c:if>
				>${job}</option>	
	    	</c:forEach>
	    </select>
	  </div>
</form> 
```
- Spring Tag Libary  사용 후
	- \<form:option> 태그를  추가적으로 사용한다.
```html
<form:form modelAttribute="member">
	<div class="form-group">
		<label for="mjob">Job</label>
		<form:select path="mjob" class="form-control">
			<option value="">---선택하세요---</option>
			<form:options items="${jobList}" />
		</form:select>
	</div>
</form:form>
```
<hr />

### 3. value와 label이 다른 drop down list 
<img src="https://user-images.githubusercontent.com/47289479/132239563-a22435ee-004a-4e99-89de-8eb238293f91.png" width=300px/>

<img src="https://user-images.githubusercontent.com/47289479/132239593-5bfaebe3-f0c5-459e-aaf2-e2d5782dfe15.png" width=300px/>

```java
@GetMapping(value="/form2")
public String form2(@ModelAttribute("member") Ch11Member member, Model model) {

	List<Ch11City> cityList = new ArrayList<>();
	cityList.add(new Ch11City(1,"서울"));
	cityList.add(new Ch11City(2,"부산"));
	cityList.add(new Ch11City(3,"제주도"));
	model.addAttribute("cityList", cityList);
	
	member.setMcity(3);
	return "ch11/form2";
}
```

- Spring Tag Library 사용 전
```html
<form method="post" action="form2">
	<div class="form-group">
		<label for="mcity">City</label>
		<select class="form-control" id="mcity" name="mcity">
			<c:forEach var="city" items="${cityList}">
				<option value="${city.code}"
					<c:if test="${member.mcity==city.code}">selected</c:if>
				>${city.label}</option>
			</c:forEach>
		</select>
	</div>
</form> 
```
- Spring Tag Libary  사용 후
	- itemValue와 itemLabel 속성을 사용한다. items에 들어가는 객체의 멤버 변수이다.
```html
<form:form modelAttribute="member">
	<div class="form-group">
		<label for="mcity">City</label>
		<form:select path="mcity" class="form-control" 
					items="${cityList}" itemValue="code" itemLabel="label" />
	</div>
</form:form>
```

## 🐰Checkbox 와 dto 객체와 바인딩
> **\<form:checkboxes> 사용**

### 1. 기본적인 checkbox
<img src="https://user-images.githubusercontent.com/47289479/132241392-43cc0092-2174-46e1-b12a-9aa5cf957d43.png" width=300px/>
<img src="https://user-images.githubusercontent.com/47289479/132241430-6bbc06ca-5248-4c06-bb68-0673eced561d.png" width=800px/>

```java
@GetMapping("/form3")
	public String form3(@ModelAttribute("member") Ch11Member member, Model model) {
		
		List<String> languageList = new ArrayList<>();
		languageList.add("C");
		languageList.add("Python");
		languageList.add("Java");
		languageList.add("JavaScript");
		model.addAttribute("languageList", languageList);
		
		member.setMlanguage(new String[] {"Java", "JavaScript"});
		return "ch11/form3";
	}
```

- Spring Tag Library 사용 전
	- 배열에 요소가 포함되었는지 확인을 위해, 이중 forEach문을 사용했다.
	- 각 check option의 id와 label의 for속성을 status.count로 처리하였다.
```html
<form method="post" action="form3">
	<c:forEach var="lang" items="${languageList}" varStatus="status">
		<div class="form-check form-check-inline">
			<input class="form-check-input" type="checkbox" id="lang${status.count}" name="mlanguage" value="${lang}"
				<c:forEach var="temp" items="${member.mlanguage}" >
					<c:if test="${temp == lang }">checked</c:if>
				</c:forEach> 
			/> 
			<label class="form-check-label" for="lang${status.count}">${lang}</label>
		</div>
	</c:forEach>
</form> 
```
- Spring Tag Libary  사용 후
	- 각 check option의 id와 label의 for속성을 path로 지정된 값에  자동으로 숫자를 하나씩 붙여준다.
	- 기본값으로 설정한 값들을 자동으로 checked 해준다.
```html
 <form:form modelAttribute="member">
	<div>
		<form:checkboxes items="${languageList}" path="mlanguage" class="ml-2 mr-1"/>
	</div>
</form:form> 
```
<hr />

### 2. value와 label이 다른 checkbox
<img src="https://user-images.githubusercontent.com/47289479/132241392-43cc0092-2174-46e1-b12a-9aa5cf957d43.png" width=300px/>
<img src="https://user-images.githubusercontent.com/47289479/132241430-6bbc06ca-5248-4c06-bb68-0673eced561d.png" width=800px/>

```java
@GetMapping("/form3")
	public String form3(@ModelAttribute("member") Ch11Member member, Model model) {
		
		List<Ch11Skill> skillList = new ArrayList<>();
		skillList.add(new Ch11Skill(1, "SpringFramework"));
		skillList.add(new Ch11Skill(2, "SpringBoot"));
		skillList.add(new Ch11Skill(3, "Vue"));
		model.addAttribute("skillList", skillList);

		member.setMskill(new int[] { 1, 3 });
		return "ch11/form3";
	}
```

- Spring Tag Libary  사용
	- itemValue와 itemLabel 속성을 사용한다. items에 들어가는 객체의 멤버 변수이다.
	- 기본값으로 설정한 값들을 자동으로 checked 해준다.
```html
<form:form modelAttribute="member" class="mt-3">
	<div>
		<form:checkboxes items="${skillList}" path="mskill" cssClass="ml-3 mr-1" itemValue="code" itemLabel="label"/>　	
	</div>
	<button type="submit" class="btn btn-primary">제출</button>
</form:form> 
```

## 🐸radioButtons 와 dto 객체와 바인딩
> **\<form:radiobuttons> 사용**
- checkboxes와 사용 방법이 동일하다. radioButton은 한 개만 선택한다.
- Spring Tag Libary  사용
```html
<form:form modelAttribute="member" class="mt-3">
	<div>
		<form:radiobuttons items="${jobList}" path="mjob" cssClass="ml-3 mr-1"/>　	
	</div>
	<div>
		<form:radiobuttons items="${cityList}" path="mcity" cssClass="ml-3 mr-1" itemValue="code" itemLabel="label"/>　	
	</div>
	<button type="submit" class="btn btn-primary btn-sm">제출</button>
</form:form> 
```