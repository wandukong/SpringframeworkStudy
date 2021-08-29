<%@ page contentType="text/html; charset=UTF-8"%>

<%@ include file="/WEB-INF/views/common/header.jsp"%>

<div class="card m-2">
	<div class="card-header">게시물 작성</div>
	<div class="card-body">
		<%--<form method="post" action="<%=application.getContextPath()%>/ch02/boardwrite"> --%>
		<form method="post"
			action="${pageContext.request.contextPath}/ch02/boardwrite">
			<div class="form-group">
				<label for="exampleInputEmail1">제목</label> <input type="text"
					class="form-control" id="title">
			</div>
			<div class="form-group">
				<label for="exampleInputEmail1">내용</label>
				<textarea class="form-control" id="content"></textarea>
			</div>
			<button type="submit" class="btn btn-primary btn-sm">저장</button>
		</form>
	</div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp"%>