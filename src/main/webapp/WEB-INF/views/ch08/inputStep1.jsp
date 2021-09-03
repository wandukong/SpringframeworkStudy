<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp"%>

<div class="card m-2">
	<div class="card-header">1단계 입력</div>
	<div class="card-body">
		<form method="post" action="inputStep2">
			<div class="input-group">
				<div class="input-group-prepend">
					<span class="input-group-text">data1</span>
				</div>
				<input type="text" id="data1" name="data1" class="form-control"
					value="${inputForm.data1}">
			</div>

			<div class="input-group">
				<div class="input-group-prepend">
					<span class="input-group-text">data2</span>
				</div>
				<input type="text" id="data2" name="data2" class="form-control"
					value="${inputForm.data2}">
			</div>
			<input class="btn btn-primary btn-sm mt-2" type="submit" value="다음단계" />
		</form>
	</div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp"%>