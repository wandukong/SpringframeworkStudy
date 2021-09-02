<%@ page contentType="text/html; charset=UTF-8"%>

<%@ include file="/WEB-INF/views/common/header.jsp"%>

<div class="card m-2">
	<div class="card-header">Controller/Data Delivery</div>
	<div class="card-body">
		<div class="card">
			<div class="card-header">객체(데이터) 사용 범위</div>
			<div class="card-body">
				<a href="saveData" class="btn btn-info btn-sm">데이터 저장</a>
				<a href="readData" class="btn btn-info btn-sm">데이터 읽기</a>
			</div>
		</div>
		
		<div class="card">
			<div class="card-header">EL(Expression Language)</div>
			<div class="card-body">
				<a href="objectSaveAndRead1" class="btn btn-info btn-sm">객체를 저장하고 읽기</a>
				<a href="objectSaveAndRead2" class="btn btn-info btn-sm">객체를 저장하고 읽기</a>
				<a href="objectSaveAndRead3" class="btn btn-info btn-sm">객체를 저장하고 읽기</a>
			</div>
		</div>
		
		<div class="card">
			<div class="card-header">JSTL(Java Standard Tag Library): 조건 처리, 반복처리</div>
			<div class="card-body">
				<a href="useJstl1" class="btn btn-info btn-sm">JSTL 사용하기</a>
				<a href="useJstl2" class="btn btn-info btn-sm">JSTL 사용하기</a>
			</div>
		</div>
		
		<div class="card">
			<div class="card-header">@ModelAttribute을 이용해서 데이터 전달</div>
			<div class="card-body">
				<a href="useModelAttribute1" class="btn btn-info btn-sm">@ModelAttribute로 전달</a>
				<a href="useModelAttribute2" class="btn btn-info btn-sm">@ModelAttribute로 전달</a>
			</div>
		</div>
		
		<div class="card">
			<div class="card-header">@ModelAttribute을 이용해서 매개 변수 값 전달</div>
			<div class="card-body">
				<a href="argumentSaveAndRead1?kind=suit&sex=woman" class="btn btn-info btn-sm">매개변수 값 전달</a>
				<a href="argumentSaveAndRead2?kind=shirts&sex=man" class="btn btn-info btn-sm">매개변수 값 전달</a>
			</div>
		</div>
	</div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp"%>