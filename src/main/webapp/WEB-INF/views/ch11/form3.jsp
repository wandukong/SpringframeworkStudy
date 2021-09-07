<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp"%>

<div class="card m-2">
	<div class="card-header">DTO 객체의 필드값을 양식의 드롭다운리스트(checkbox 태그) 세팅
	</div>
	<div class="card-body">
		 <form method="post" action="form3">
		 	<div class="ml-2">
				<c:forEach var="lang" items="${languageList}" varStatus="status">
					<span >
						<input class="ml-2" type="checkbox" id="lang${status.count}" name="mlanguage" value="${lang}" 
							<c:forEach var="temp" items="${member.mlanguage}" >
								<c:if test="${temp == lang }">checked</c:if>
							</c:forEach> 
						/>
						<label for="lang${status.count}">${lang}</label>&nbsp;
					</span>
				</c:forEach>
			</div>
			<button type="submit" class="btn btn-primary">제출</button>
		</form>
		
		
		<%-- css주기가 힘들어짐 --%>
		 <form:form modelAttribute="member" class="mt-3">
			<div>
				<form:checkboxes items="${languageList}" path="mlanguage" cssClass="ml-3 mr-1"/>　	
			</div>
			<button type="submit" class="btn btn-primary">제출</button>
		</form:form> 
		
		 <form:form modelAttribute="member" class="mt-3">
			<div>
				<form:checkboxes items="${skillList}" path="mskill" cssClass="ml-3 mr-1" itemValue="code" itemLabel="label"/>　	
			</div>
			<button type="submit" class="btn btn-primary">제출</button>
		</form:form> 
	</div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp"%>