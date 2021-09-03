<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ include file="/WEB-INF/views/common/header.jsp"%>

<div class="card m-2">
	<div class="card-header">Session Support</div>
	<div class="card-body">
		<div class="card">
			<div class="card-header">세션 원리: JSESSIONID 쿠키</div>
			<div class="card-body">
				<p>서버: 세션 객체 생성->JSESSIONID 쿠키 발생</p>
				<p>브라우저: JSESSIONID 쿠키 전송 -> 세션 객체 찾음 -> 세션 이용</p>
				<p>
					<a href="javascript:saveData()" class="btn btn-primary btn-sm">세션에 데이터 저장</a> 
					<a href="javascript:readData()"class="btn btn-primary btn-sm">세션 데이터 읽기</a>
				</p>
			</div>
			<script>
				function saveData(){
					$.ajax({
						url:"saveData",
						method:"get",
						data: {name:"홍길동"}
					}).done(data =>{
						console.log(data);
					});
				}
				
				function readData(){
					$.ajax({
						url:"readData"
					}).done(data =>{
						console.log(data);
						console.log(data.name);
					});
				}
			</script>
		</div>
		<div class="card mt-2">
			<div class="card-header">from을 통한 로그인 처리</div>
			<div class="card-body">
				<c:if test="${sessionMid == null}">
					<a class="btn btn-success btn-sm" href="login">로그인</a>
				</c:if>
				<c:if test="${sessionMid != null}">
					<a class="btn btn-danger btn-sm" href="logout">로그아웃</a>
				</c:if>
			</div>
		</div>

		<div class="card mt-2">
			<div class="card-header">Ajax를 통한 로그인 처리</div>
			<div class="card-body">
				<c:if test="${sessionMid == null}">
					<form>
						<div class="input-group">
							<div class="input-group-prepend">
								<span class="input-group-text">mid</span>
							</div>
							<input id="mid" type="text" name="mid" class="form-control"
								autocomplete= /> <span id="mid-error" class="error"></span>
						</div>
						<div class="input-group">
							<div class="input-group-prepend">
								<span class="input-group-text">mpassword</span>
							</div>
							<input id="mpassword" type="password" name="mpassword"
								class="form-control"> <span id="mpassword-error"
								class="error"></span>
						</div>
					</form>
				</c:if>
				<div class="mt-2">
					<c:if test="${sessionMid == null}">
						<a class="btn btn-success btn-sm" href="javascript:login()">로그인</a>
					</c:if>
					<c:if test="${sessionMid != null}">
						<a class="btn btn-danger btn-sm" href="javascript:logout()">로그아웃</a>
					</c:if>
				</div>
				<script>
					function login(){
						let mid = $("#mid").val();
						let mpassword = $("#mpassword").val();
						$.ajax({
							url: "loginAjax",
							method:"post",
							data:{mid,mpassword}
						}).done(data=>{
							// data = {result:"success"}  로그인 성공
							// data = {result:"wrongMid"} id 다름
							// data = {result:"wrongMpassword"} password 다름
							
							const midError= $("#mid-error");
							const mpasswordError = $("#mpassword-error");
							midError.html("");
							mpasswordError.html("");
							
							if(data.result==="success"){
								// 현재 페이지 전체를 다시 서버에서 받아오도록 함 -> 모든 로그인버튼 없애기 위해서
								window.location.reload();
							}else if(data.result==="wrongMid"){
								midError.html("아이디가 잘못됨");
							}else if(data.result==="wrongMpassword"){
								mpasswordError.html("비밀번호가 잘못됨");
							}
						});
					}
					
					function logout(){
						$.ajax({
							url: "logoutAjax",
						}).done(()=>{
							window.location.reload();
						});
					}
				</script>
			</div>
		</div>
		
		<div class="card mt-2">
			<div class="card-header">@SessionAttributes를 이용한 다단계 입력처리</div>
			<div class="card-body">
				<a href="inputStep1" class="btn btn-info btn-sm">입력하기</a>
			</div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/common/footer.jsp"%>