<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%-- 태그 라이브러리 지시자 --%>

<%@ include file="/WEB-INF/views/common/header.jsp"%>

<div class="card m-2">
	<div class="card-header">Browser 1차 유효성 검사</div>
	<div class="card-body">
		<div class="card m-2">
			<div class="card-header">Post 방식으로 요청</div>
			<div class="card-body">
				<%-- name으로 들어가는 값이 param의 이름이 된다. --%>
				<form id="form0" method="post" onsubmit=checkData(this) name="form">
					<div class="input-group">
						<div class="input-group-prepend">
							<span class="input-group-text">ID</span>
						</div>
						<input type="text" name="param1" class="form-control"> <span
							class="param1-error text-danger">*</span>
					</div>
					<div class="input-group">
						<div class="input-group-prepend">
							<span class="input-group-text">Phone</span>
						</div>
						<input type="text" name="param2" class="form-control"> <span
							class="param2-error text-danger">*</span>
					</div>
					<div class="input-group">
						<div class="input-group-prepend">
							<span class="input-group-text">E-mail</span>
						</div>
						<input type="text" name="param3" class="form-control"> <span
							class="param3-error text-danger">*</span>
					</div>
					<div class="input-group">
						<div class="input-group-prepend">
							<span class="input-group-text">param4</span>
						</div>
						<div class="btn-group btn-group-toggle" data-toggle="buttons">
							<label class="btn btn-secondary active"> <input
								type="radio" name="param4" value="true"> true
							</label> <label class="btn btn-secondary"> <input type="radio"
								name="param4" value="false"> false
							</label>
						</div>
					</div>
					<div class="input-group">
						<div class="input-group-prepend">
							<span class="input-group-text">Date</span>
						</div>
						<input type="date" name="param5" class="form-control"> <span
							class="param5-error text-danger">*</span>
					</div>
					<input class="btn btn-info btn-sm mt-2" type="submit" value="요청" />
				</form>
			</div>
			<script>
				function checkData(obj){
					event.preventDefault(); // form의 제출 기능을 off
					
					let checkResult = true;
					
					let param1 = form.param1.value;
					const param1Error = document.querySelector("#form0 .param1-error");
					if(param1===""){
						param1Error.innerHTML = "필수 입력 사항";
						checkResult = false;
					}else{
						if(param1.length<8 || param1.length > 15){
							param1Error.innerHTML = "8자 이상, 15자 이하로 입력";
							checkResult = false;
						}else {
							param1Error.innerHTML = "*"
							checkResult = true;
						}
					}
					
					let param2 = form.param2.value;
					const param2Error = document.querySelector("#form0 .param2-error");
					if(param2 === ""){
						param2Error.innerHTML = "필수 입력 사항";
						checkResult = false;
					}else{
						const pattern = /(010|011)-[0-9]{3,4}-[0-9]{4}/i; // i는 대소문자 구문x
						const result = pattern.test(param2); // test() : 정규표현식에 일치하면 true
						if(result === false){
							param2Error.innerHTML = "전화번호 형식이 아닙니다.";
							checkResult = false;
						}else {
							param2Error.innerHTML = "*"
							checkResult = true;
						}
					}
					
					let param3 = form.param3.value;
					const param3Error = document.querySelector("#form0 .param3-error");
					if(param3 === ""){
						param3Error.innerHTML = "필수 입력 사항";
						checkResult = false;
					}else{
						const pattern = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
						const result = pattern.test(param3);
						if(result === false){
							param3Error.innerHTML = "이메일 형식이 아닙니다.";
							checkResult = false;
						}else {
							param3Error.innerHTML = "*"
							checkResult = true;
						}
					}
					
					let param5 = form.param5.value;
					const param5Error = document.querySelector("#form0 .param5-error");
					if(param5 === ""){
						param5Error.innerHTML = "필수 입력 사항";
						checkResult = false;
					}else{
						param5Error.innerHTML = "*"
						checkResult = true;
					}
					
					if(checkResult){
						form.submit();
					}
				}
			</script>
		</div>

		<div class="card m-2">
			<div class="card-header">Ajax로 요청</div>
			<div class="card-body">
				<form method="post" name="form1" id="form1">
					<div class="input-group">
						<div class="input-group-prepend">
							<span class="input-group-text">ID</span>
						</div>
						<input type="text" id="param1" name="param1" class="form-control">
						<span class="param1-error text-danger">*</span>
					</div>
					<div class="input-group">
						<div class="input-group-prepend">
							<span class="input-group-text">Phone</span>
						</div>
						<input type="text" id="param2" name="param2" class="form-control">
						<span class="param2-error text-danger">*</span>
					</div>
					<div class="input-group">
						<div class="input-group-prepend">
							<span class="input-group-text">E-mail</span>
						</div>
						<input type="text" id="param3" name="param3" class="form-control">
						<span class="param3-error text-danger">*</span>
					</div>
					<div class="input-group">
						<div class="input-group-prepend">
							<span class="input-group-text">param4</span>
						</div>
						<div class="btn-group btn-group-toggle" data-toggle="buttons">
							<label class="btn btn-secondary active"> <input
								type="radio" name="param4" value="true"> true
							</label> <label class="btn btn-secondary"> <input type="radio"
								name="param4" value="false"> false
							</label>
						</div>
					</div>
					<div class="input-group">
						<div class="input-group-prepend">
							<span class="input-group-text">Date</span>
						</div>
						<input type="date" id="param5" name="param5" class="form-control">
							<span class="param5-error text-danger">*</span>
					</div>
				</form>
				<button class="btn btn-danger btn-sm  mt-2" onclick="requestPost()">POST
					방식 요청</button>
			</div>
			<script>
				function requestPost(){
					event.preventDefault(); // form의 제출 기능을 off
					
					let checkResult = true;
					
					let param1 = form1.param1.value;
					const param1Error = document.querySelector("#form1 .param1-error")
					if(param1===""){
						param1Error.innerHTML = "필수 입력 사항";
						checkResult = false;
					}
					else {
						if(param1.length < 8 || param1.length > 15){
							param1Error.innerHTML = "8자 이상, 15이하로 입력";
							checkResult = false;
						}else{
							param1Error.innerHTML = "*"
							checkResult = true;
						}
					}
					
					let param2 = form1.param2.value;
					const param2Error = document.querySelector("#form1 .param2-error");
					if(param2 === ""){
						param2Error.innerHTML = "필수 입력 사항";
						checkResult = false;
					}else{
						const pattern = /(010|010)-[0-9]{3,4}-[0-9]{4}/i;
						let result = pattern.test(param2);
						if(result === false){
							param2Error.innerHTML = "전화번호 형식이 아닙니다.";
							checkResult = false;
						}else {
							param2Error.innerHTML = "*"
							checkResult = true;
						}
					}
					
					let param3 = form1.param3.value;
					const param3Error = document.querySelector("#form1 .param3-error");
					if(param3 === ""){
						param3Error.innerHTML = "필수 입력 사항";
						checkResult = false;
					}else{
						const pattern = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
						const result = pattern.test(param3);
						if(result === false){
							param3Error.innerHTML = "이메일 형식이 아닙니다.";
							checkResult = false;
						}else {
							param3Error.innerHTML = "*"
							checkResult = true;
						}
					}
					
					let param5 = form1.param5.value;
					const param5Error = document.querySelector("#form1 .param5-error");
					if(param5 === ""){
						param5Error.innerHTML = "필수 입력 사항";
						checkResult = false;
					}else{
						param5Error.innerHTML = "*"
						checkResult = true;
					}
					
					if(checkData){
						/* 서버로 보내기
						$.ajax({
							url:"method1",
							method:"post",
							data:{
								param1,
								param2,
								param3,
								param4,
								param5
							}
						})
						.done(()=>{});
						*/
					}
				}
			</script>
		</div>
	</div>
	<div class="card m-2">
		<div class="card-header">서버측 유효성 검사</div>
		<div class="card-body">
			<div class="card m-2">
				<div class="card-header">회원 가입 폼</div>
				<div class="card-body">

					<form method="post" action="join">
						<div class="input-group">
							<div class="input-group-prepend">
								<span class="input-group-text">mid</span>
							</div>
							<input type="text" name="mid" class="form-control"
								value="${joinForm.mid}" autocomplete="user-name">
							<form:errors cssClass="text-danger" path="joinForm.mid" />
							<%-- member 변수와 errorCode를 모두 같게 하여, form:erros의 path에 넣는다. --%>
						</div>
						<div class="input-group">
							<div class="input-group-prepend">
								<span class="input-group-text">mpassword</span>
							</div>
							<input type="password" name="mpassword" class="form-control"
								value="${joinForm.mpassword}" autocomplete="current-password">
							<form:errors cssClass="text-danger" path="joinForm.mpassword" />
						</div>
						<div class="input-group">
							<div class="input-group-prepend">
								<span class="input-group-text">memail</span>
							</div>
							<input type="text" name="memail" class="form-control"
								value="${joinForm.memail}">
							<form:errors cssClass="text-danger" path="joinForm.memail" />
						</div>
						<div class="input-group">
							<div class="input-group-prepend">
								<span class="input-group-text">mtel</span>
							</div>
							<input type="text" name="mtel" class="form-control"
								value="${joinForm.mtel}">
							<form:errors cssClass="text-danger" path="joinForm.mtel" />
						</div>
						<input class="btn btn-info mt-2" type="submit" value="가입" />
					</form>

				</div>
			</div>
		</div>
	</div>

	<div class="card m-2">
		<div class="card-header">서버측 유효성 검사</div>
		<div class="card-body">
			<div class="card m-2">
				<div class="card-header">로그인 폼</div>
				<div class="card-body">
					<form method="post" action="login">
						<div class="input-group">
							<div class="input-group-prepend">
								<span class="input-group-text">mid</span>
							</div>
							<input type="text" name="mid" class="form-control"
								value="${loginForm.mid}" autocomplete="user-name">
							<form:errors cssClass="error" path="loginForm.mid" />
						</div>
						<div class="input-group">
							<div class="input-group-prepend">
								<span class="input-group-text">mpassword</span>
							</div>
							<input type="password" name="mpassword" class="form-control"
								value="${loginForm.mpassword}" auto>
							<form:errors cssClass="error" autocomplete="current-password"/>
						</div>
						<input class="btn btn-info mt-2" type="submit" value="로그인"  />
					</form>
				</div>
			</div>
		</div>
	</div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp"%>