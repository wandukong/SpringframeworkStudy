<%@ page contentType="text/html; charset=UTF-8"%>

<%@ include file="/WEB-INF/views/common/header.jsp"%>

<div class="card m-2">
	<div class="card-header">HTTP 정보 읽기 설정</div>
	<div class="card-body">
		<div class="card m-2">
			<div class="card-header">요청 HTTP 헤더 정보 읽기</div>
			<div class="card-body">
				<a class="btn btn-warning btn-sm" href="getHeaderValue">요청</a>
			</div>
		</div>
		
		<div class="card m-2">
			<div class="card-header">쿠키 생성 및 읽기</div>
			<div class="card-body">
				<a class="btn btn-primary btn-sm" href="createCookie">쿠키 생성</a>
				<a class="btn btn-info btn-sm" href="getCookie1">쿠키 읽기(서버)</a>
				<a class="btn btn-info btn-sm" href="getCookie2">쿠키 읽기(서버)</a>
				<a class="btn btn-danger btn-sm" onclick="getCookie()">쿠키 읽기(JavaScript)</a>
				<hr />
				<a class="btn btn-dark btn-sm" href="createJsonCookie">json 쿠키 생성</a>
				<a class="btn btn-dark btn-sm" href="getJsonCookie">json 쿠키 읽기</a>
				<hr />
				<a class="btn btn-dark btn-sm" href="createJwtCookie">JWT 쿠키 생성</a>
				<a class="btn btn-dark btn-sm" href="getJwtCookie">JWT 쿠키 읽기</a>
			</div>
			<script>
				function getCookie(){
					console.log(document.cookie);
				}
			</script>
		</div>
	</div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp"%>