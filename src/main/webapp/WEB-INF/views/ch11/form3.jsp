<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp"%>

<div class="card m-2">
	<div class="card-header">DTO 객체의 필드값을 양식의 드롭다운리스트(checkbox 태그) 세팅
	</div>
	<div class="card-body">
		 <form>
			<c:forEach var="lang" items="${languageList}" varStatus="status">
				<div class="form-check form-check-inline">
					<input class="form-check-input" type="checkbox" id="lang${status.count}" name="mlanguage" value="${lang}"
						<c:forEach var="temp" items="${member.mlanguage}" >
							<c:if test="${temp == lang }">checked</c:if>
						</c:forEach> 
					/> 
					<label class="form-check-label" for="lang${status.count}">${lang}</label>
				</div>
			</c:forEach>
		</form>
		
		
		
		<%-- css주기가 힘들어짐 --%>
		<%-- <form:form modelAttribute="member">
			<div>
				<form:checkboxes items="${languageList}" path="mlanguage" class="ml-2 mr-1"/>
			</div>
		</form:form> --%>
		
	</div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp"%>