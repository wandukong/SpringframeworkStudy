<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp"%>

<div class="card m-2">
	<div class="card-header">AOP</div>
	<div class="card-body">
		<div class="card">
			<div class="card-header">Advice 테스트</div>
			<div class="card-body">
				<a href="before" class="btn btn-info btn-sm">@Before 테스트</a>
				<a href="after" class="btn btn-warning btn-sm">@After테스트</a>
				<a href="afterReturning" class="btn btn-success btn-sm">@AfterReturning 테스트</a>
				<a href="afterThrowing" class="btn btn-danger btn-sm">@AfterThrowing 테스트</a>
				<a href="around" class="btn btn-primary btn-sm">@Around 테스트</a>
			</div>
		</div>
		
		<div class="card">
			<div class="card-header">AOP 예제</div>
			<div class="card-body">
				<a href="runTimeCheck" class="btn btn-info btn-sm">요청 처리 시간 측정</a>
				<a href="javascript:getBoardList()" class="btn btn-success btn-sm">인증 여부 확인(html 응답)</a>
				<a href="javascript:getBoardList2()" class="btn btn-success btn-sm">인증 여부 확인(json 응답)</a>
				<hr/>
				<div>${methodName} 실행 시간 : ${howLong}</div>
				<hr/>
				<div id="boardList" class="mt-2">
					
				</div>
			</div>
			<script>
				function getBoardList(){
					$.ajax({
						url: "getBoardList"
					}).done(data=>{
						if(data.result=="authFail"){
							$("#boardList").html("로그인이 필요합니다.");
							window.location.href="login"
						}else{
							$("#boardList").html(data);
						}
					});
				}
				
				function getBoardList2(){
					$.ajax({
						url: "getBoardList2"
					}).done(data=>{ // {result:"success", boards: [{...}, {...}, ...]}
						if(data.result == "authFail"){
							$("#boardList").html("로그인이 필요합니다.");
							window.location.href="login"
						}else{
							let html = "";
							html += '<table class="table table-sm table-bordered container-fluid">';
							html += '<tr>';
							html += '<th class="col-sm-1">번호</th>';
							html += '<th class="col-sm-7">제목</th>';
							html += '<th class="col-sm-2">글쓴이</th>';
							html += '<th class="col-sm-2">날짜</th>';
							html +=	'</tr>';
							for(var board of data.boards){
								html +=	'<tr>';
								html +=	'<td>'+ board.bno + '</td>';
								html +=	'<td><a href="boardDetail?bno=' + board.bno +'">' + board.btitle +'</a></td>';
								html +=	'<td>' + board.mid + '</td>';
								html +=	'<td>' + board.bdate + '</td>';
								html +=	'</tr>';
							}
							html +=	'</table>';
							$("#boardList").html(html);
						}
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
	</div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp"%>