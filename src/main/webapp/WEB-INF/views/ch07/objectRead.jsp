<%@ page contentType="text/html; charset=UTF-8" %>

<%@ include file="/WEB-INF/views/common/header.jsp"%>

<div class="card m-2">
	<div class="card-header">
		EL을 사용해서 Object 안에 데이터를 출력
	</div>
	<div class="card-body">
		<p>이름: ${member.name} </p> <!-- getXXX()에서 return되는 값이 온다. 무조건 getter로 받아오기 떼문에 dto에 getter가 있어야한다. -->
		<p>나이: ${member.age} </p>
		<p>직업: ${member.job}</p>
		<p>도시: ${member.city.name}</p>
	</div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp"%>