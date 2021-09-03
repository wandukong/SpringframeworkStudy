<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp"%>

<div class="card m-2">
	<div class="card-header">2단계 입력</div>
	<div class="card-body">
		<form method="post" action="inputDone">
			<div class="input-group">
				<div class="input-group-prepend">
					<span class="input-group-text">data3</span>
				</div>
				<input type="text" id="data3" name="data3" class="form-control"
					value="${inputForm.data3}">
			</div>

			<div class="input-group">
				<div class="input-group-prepend">
					<span class="input-group-text">data4</span>
				</div>
				<input type="text" id="data4" name="data4" class="form-control"
					value="${inputForm.data4}">
			</div>
			<a class="btn btn-warning btn-sm mt-2" href="inputStep1">이전단계</a> 
			<input class="btn btn-primary btn-sm mt-2" type="submit" value="입력완료" />
		</form>
	</div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp"%>