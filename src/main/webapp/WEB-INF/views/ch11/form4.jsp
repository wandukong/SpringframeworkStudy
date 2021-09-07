<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp"%>

<div class="card m-2">
	<div class="card-header">DTO 객체의 필드값을 양식의 드롭다운리스트(checkbox 태그) 세팅
	</div>
	<div class="card-body">
		 <form method="post" action="form4">
		 	<div class="ml-2">
				<c:forEach var="job" items="${jobList}" varStatus="status">
					<span >
						<input type="radio" id="job${status.count}" name="mjob" value="${job}" class="ml-2" 
								<c:if test="${member.mjob == job }">checked</c:if>
						/>
						<label for="lang${status.count}">${job}</label>&nbsp;
					</span>
				</c:forEach>
			</div>
			<button type="submit" class="btn btn-primary btn-sm">제출</button>
		</form>
		
		
		<%-- radio는 1개만 선택 --%>
		 <form:form modelAttribute="member" class="mt-3">
			<div>
				<form:radiobuttons items="${jobList}" path="mjob" cssClass="ml-3 mr-1"/>　	
			</div>
			<div>
				<form:radiobuttons items="${cityList}" path="mcity" cssClass="ml-3 mr-1" itemValue="code" itemLabel="label"/>　	
			</div>
			<button type="submit" class="btn btn-primary btn-sm">제출</button>
		</form:form> 
	</div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp"%>