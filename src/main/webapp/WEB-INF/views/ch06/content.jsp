<%@ page contentType="text/html; charset=UTF-8" %>

<%@ include file="/WEB-INF/views/common/header.jsp"%>

<div class="card m-2">
	<div class="card-header">
		Controller/Forward & Redirect
	</div>
	<div class="card-body">
		<a href="forward" class="btn btn-info btn-sm">JSP 포워드</a>
		<a href="redirect" class="btn btn-info btn-sm">홈으로 리다이렉트</a>
		<hr/>
		<p>[AJAX 요청은 리다이렉트를 하면 안됨]</p>
		<a href="javascript:ajax1()" class="btn btn-info btn-sm">AJAX 요청(HTML)</a>
		<a href="javascript:ajax2()" class="btn btn-info btn-sm">AJAX 요청(JSON)</a>
		<a href="javascript:ajax3()" class="btn btn-info btn-sm">AJAX 요청(JSON)</a>
		<a href="javascript:ajax4()" class="btn btn-danger btn-sm">AJAX 요청(리다이렉트)-안됨</a>
		<div id="content" class="mt-2">
		
		</div>
		<script>
			function ajax1(){
				$.ajax({
					url:"getFragmentHtml",
					method:"get"
				}).done((data)=>{
					$("#content").html(data);
				});
			}
			
			function ajax2(){
				$.ajax({
					url:"getJson1",
					method:"get"
				}).done((data)=>{
					$("#content").html(
						"<img src='${pageContet.request.contextPath}/resources/images/" + 
						data.fileName + "'width='200px'/>");
				});
			}
			
			function ajax3(){
				$.ajax({
					url:"getJson2",
					method:"get"
				}).done((data)=>{
					console.log(data);
					$("#content").html(
						"<img src='${pageContet.request.contextPath}/resources/images/" + 
						data.fileName + "'width='200px'/>");
				});
			}
			
			function ajax4(){
				$.ajax({
					url:"getJson3",
					method:"get"
				}).done((data)=>{
					$("#content").html(
						"<img src='${pageContet.request.contextPath}/resources/images/" + 
						data.fileName + "'width='200px'/>");
				});
			}
		</script>
	</div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp"%>