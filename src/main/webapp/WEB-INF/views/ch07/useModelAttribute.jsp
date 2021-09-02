<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="/WEB-INF/views/common/header.jsp"%>

<div class="card m-2">
	<div class="card-header">
		
	</div>
	<div class="card-body">
		<ul>
			<c:forEach var="color" items="${colors}">
				<li>${color}</li>
			</c:forEach>
		</ul>
	</div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp"%>