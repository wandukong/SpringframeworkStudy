<%@ page contentType="text/html; charset=UTF-8" %>

<%@ include file="/WEB-INF/views/common/header.jsp"%>

<div class="card m-2">
	<div class="card-header">
		각 범위에서 데이터 읽기
	</div>
	<div class="card-body">
		<p>Request 범위에서 읽기: ${requestData }</p> <!--EL :  Expressions Language  -->
		<p>Session 범위에서 읽기: ${sessionData }</p>
		<p>Application 범위에서 읽기: ${applicationData }</p>
	</div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp"%>