<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/views/common/header.jsp"%>

<div class="card m-2">
	<div class="card-header">
		DTO 객체(Command Object)와 폼 연결
	</div>
	<div class="card-body">
		<form:form method="post" modelAttribute="member"> <!--  method default: post --> <!--  modelAttribute 멤버 객체와 바인딩됨 -->  <!-- action을 써도되지만, 이 폼을 호출한 경로와 같은 경로로 post한다. -->
		  <div class="form-group">
		    <label for="mid">ID</label>
		    <form:input type="text" class="form-control" id="mid" path="mid" />
		  </div>
		  <div class="form-group">
		    <label for="mname">Name</label>
		    <form:input type="text" class="form-control" id="mname" path="mname"/>
		  </div>
		  <div class="form-group">
		    <label for="mpassword">Password</label>
		    <form:password class="form-control" id="mpassword" path="mpassword" />
		  </div>
		  <div class="form-group">
		    <label for="mnation">Nation</label>
		    <form:input type="text" class="form-control" id="mnation" path="mnation" />
		  </div>
		  <button type="submit" class="btn btn-primary">Submit</button>
		</form:form>
	</div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp"%>