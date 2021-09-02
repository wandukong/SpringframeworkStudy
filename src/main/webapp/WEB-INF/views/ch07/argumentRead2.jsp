<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ include file="/WEB-INF/views/common/header.jsp"%>

<div class="card m-2">
	<div class="card-header">
		매개변수 값 읽기2
	</div>
	<div class="card-body">
		<p>kind: ${ch07Cloth.kind}</p>
		<p>sex: ${ch07Cloth.sex}</p>
	</div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp"%>