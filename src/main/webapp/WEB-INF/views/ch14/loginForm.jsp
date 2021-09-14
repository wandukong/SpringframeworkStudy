<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp"%>

<div class="card m-2">
	<div class="card-header">로그인</div>
	<div class="card-body">
		<c:if test="${error!=null}">
			<div class="alert alert-danger" role="alert">${error}</div>
		</c:if>
		<form method="post" action="login">
			<div class="input-group">
				<div class="input-group-prepend">
					<span class="input-group-text">mid</span>
				</div>
				<input type="text" name="mid" class="form-control">
			</div>
			<div class="input-group">
				<div class="input-group-prepend">
					<span class="input-group-text">mpassword</span>
				</div>
				<input type="text" name="mpassword" class="form-control">
			</div>
			<input class="btn btn-info" type="submit" value="로그인" />
		</form>
	</div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp"%>