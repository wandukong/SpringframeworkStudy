<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/views/common/header.jsp"%>

<div class="card m-2">
	<div class="card-header">
		DTO 객체의 필드값을 양식의 드롭다운리스트(select 태그) 세팅
	</div>
	<div class="card-body">
	<%-- 
		<form method="post" action="form2">
		  <div class="form-group">
		    <label for="mtype">Type</label>
		    <select class="form-control" id="mtype" name="mtype">
		    	<c:forEach var="mtype" items="${typeList}">
					<option value="${mtype}"
						<c:if test="${member.mtype==mtype}">selected</c:if>
					>${mtype}</option>	
		    	</c:forEach>
		    </select>
		  </div>
		  
		 <div class="form-group">
		    <label for="mjob">Job</label>
		    <select class="form-control" id="mjob" name="mjob">
		    	<option value="">---선택하세요---</option>
		    	<c:forEach var="job" items="${jobList}">
					<option value="${job}"
						<c:if test="${member.mjob==job}">selected</c:if>
					>${job}</option>	
		    	</c:forEach>
		    </select>
		  </div>
		  
		  
		  <div class="form-group">
		    <label for="mcity">City</label>
		    <select class="form-control" id="mcity" name="mcity">
		    	<c:forEach var="city" items="${cityList}">
		    		<option value="${city.code}"
		    			<c:if test="${member.mcity==city.code}">selected</c:if>
		    		>${city.label}</option>
		    	</c:forEach>
		    </select>
		  </div>
		  <button type="submit" class="btn btn-primary">제출</button>
		</form>  
	 --%>    <%-- 3 : value와 보여지는 값이 다를때 --%>


		<form:form modelAttribute="member">
			<div class="form-group">
				<label for="mtype">Type</label>
				<form:select path="mtype" items="${typeList}" class="form-control" />
				<%--  items : 항목들 , 옵션태그에 저장된 값을 path에 저장함 modelAttribute의 멤버변수들 중하나 select의 path로 지정 --%>
			</div>

			<div class="form-group">
				<label for="mjob">Job</label>
				<form:select path="mjob" class="form-control">
					<option value="">---선택하세요---</option>
					<form:options items="${jobList}" />
				</form:select>
			</div>

			<div class="form-group">
				<label for="mcity">City</label>
				<form:select path="mcity" class="form-control" items="${cityList}" itemValue="code" itemLabel="label" />
			</div>

			<button type="submit" class="btn btn-primary">제출</button>
		</form:form>
	</div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp"%>