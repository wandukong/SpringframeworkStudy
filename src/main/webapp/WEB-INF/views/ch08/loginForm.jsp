<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ include file="/WEB-INF/views/common/header.jsp"%>

<div class="card m-2">
	<div class="card-header">Login Form</div>
	<div class="card-body">
		<form method="post" action="login">
			<div class="input-group">
				<div class="input-group-prepend">
					<span class="input-group-text">mid</span>
				</div>
				<input type="text" name="mid" class="form-control" />
			</div>
			<div class="input-group">
				<div class="input-group-prepend">
					<span class="input-group-text">mpassword</span>
				</div>
				<input type="password" name="mpassword" class="form-control" >
			</div>
			<div class="mt-2">
				<input class="btn btn-success btn-sm" type="submit" value="로그인" />
				<input class="btn btn-warning btn-sm" type="reset" value="초기화" autocomplete="current-password" />
				<a class="btn btn-danger btn-sm" href="content">취소</a>
			</div>
		</form>
	</div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp"%>