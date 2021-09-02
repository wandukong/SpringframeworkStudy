<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ include file="/WEB-INF/views/common/header.jsp"%>

<div class="card m-2">
	<div class="card-header">JSTL을 이용한 반복 처리</div>
	<div class="card-body">
		<h6>[지정한 횟수만큼 반복]</h6>
		<table class="table table-striped">
			<thead>
				<tr>
					<th scope="col">No</th>
					<th scope="col">Title</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach begin="1" end="5" step="1" var="i">
					<tr>
						<th scope="row">${i}</th>
						<td>제목${i}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<!-- 
		${status.index}   0부터의 순서 
		${status.count}   1부터의 순서
		${status.first}   현재 루프가 처음인지 반환 
		${status.last}    현재 루프가 마지막인지 반환  
		 -->

		<h6 class="mt-3">[배열의 항목 수만큼 반복]</h6>
		<table class="table table-striped">
			<thead>
				<tr>
					<th scope="col">No</th>
					<th scope="col">Language</th>
				</tr>
			</thead>

			<c:forEach var="lang" items="${langs}" varStatus="status">
				<c:if test="${status.first} ">
					<tbody>
				</c:if>

				<tr>
					<th scope="row">${status.count}</th>
					<!-- count1: 1부터 시작하여 일씩 증가됨, index: 0 부터 시작 -->
					<td>${lang}</td>
				</tr>
				<c:if test="${status.last} ">
					</tbody>
				</c:if>
			</c:forEach>
		</table>
	</div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp"%>