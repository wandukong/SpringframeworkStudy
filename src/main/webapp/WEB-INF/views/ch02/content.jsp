<%@ page contentType="text/html; charset=UTF-8"%>

<%@ include file="/WEB-INF/views/common/header.jsp"%>

<div class="card m-2">
	<div class="card-header">Controller/RequestMapping</div>
	<div class="card-body">
		<div class="card m-2">
			<div class="card-header">요청 방식별 메소드 매핑</div>
			<div class="card-body">
				<button class="btn btn-primary btn-sm" onclick="requestGet()">Get</button>
				<button class="btn btn-success btn-sm" onclick="requestPost()">Post</button>
				<button class="btn btn-warning btn-sm" onclick="requestPut()">Put</button>
				<button class="btn btn-danger btn-sm" onclick="requestDelete()">Delete</button>
			</div>
			<script>
				function requestGet() {
					$.ajax({
						url:"method",
						method:"GET"
					})
					.done((data)=>{})
				}

				function requestPost() {
					$.ajax({
						url:"method",
						method:"POST"
					})
					.done((data)=>{})
				}

				function requestPut() {
					$.ajax({
						url:"method",
						method:"PUT"
					})
					.done((data)=>{})
				}

				function requestDelete() {
					$.ajax({
						url:"method",
						method:"DELETE"
					})
					.done((data)=>{})
				}
			</script>
		</div>

		<div class="card m-2">
			<div class="card-header">ModelAndView 리턴</div>
			<div class="card-body">
				<a class="btn btn-primary btn-sm" href="${pageContext.request.contextPath}/ch02/modelandview">요청</a>
			</div>
		</div>

		<div class="card m-2">
			<div class="card-header">Redirect</div>
			<div class="card-body">
				<%-- <form action="/ch02/login1" method="POST"> --%>
				<form action="${pageContext.request.contextPath}/ch02/login2" method="POST">
					<div class="form-group">
						<label for="exampleInputEmail1">Email address</label> <input
							type="email" class="form-control" id="exampleInputEmail1"
							aria-describedby="emailHelp" autocomplete="additional-name"> <small id="emailHelp"
							class="form-text text-muted">We'll never share your email
							with anyone else.</small>
					</div>
					<div class="form-group">
						<label for="exampleInputPassword1">Password</label> <input
							type="password" class="form-control" id="exampleInputPassword1" autocomplete="current-password">
					</div>
					<div class="form-group form-check">
						<input type="checkbox" class="form-check-input" id="exampleCheck1">
						<label class="form-check-label" for="exampleCheck1">Check
							me out</label>
					</div>
					<button type="submit" class="btn btn-primary">로그인</button>
				</form>
				<hr/>
				
				<%-- a로 request요청은 무조건 get방식이다. --%>
				<a class="btn btn-info btn-sm" href="boardlist">게시물 목록</a>
				<a class="btn btn-success btn-sm" href="boardwriteform">게시물 작성</a>
			</div>
		</div>
	</div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp"%>