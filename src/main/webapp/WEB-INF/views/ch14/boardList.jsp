<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp"%>

<div class="card m-2">
	<div class="card-header">게시물 목록</div>
	<div class="card-body">
		<table class="table table-sm table-bordered container-fluid">
			<tr>	
				<th class="col-sm-1">번호</th>
				<th class="col-sm-7">제목</th>
				<th class="col-sm-2">글쓴이</th>
				<th class="col-sm-2">날짜</th>
			</tr>

			<c:forEach var="board" items="${boards}">
				<tr>
					<td>${board.bno}</td>
					<td><a href="boardDetail?bno=${board.bno}">${board.btitle}</a></td>
					<td>${board.mid}</td>
					<td><fmt:formatDate value="${board.bdate}"
							pattern="yyyy-MM-dd" /></td>
				</tr>
			</c:forEach>
			
			<tr>
				<td colspan="4" class="text-center">
					<div>
						<a class="btn btn-warning btn-sm" href="boardList?pageNo=1">처음</a>
						<c:if test="${pager.groupNo!=1}">
							<a class="btn btn-primary btn-sm" href="boardList?pageNo=${pager.startPageNo-1}">이전</a>
						</c:if>
						<c:forEach var="i" begin="${pager.startPageNo}" end="${pager.endPageNo}">
							<c:if test="${pager.pageNo !=i }">
								<a class="btn btn-outline-success btn-sm" href="boardList?pageNo=${i}">${i}</a>
							</c:if>
							<c:if test="${pager.pageNo ==i }">
								<a class="btn btn-success btn-sm" href="boardList?pageNo=${i}">${i}</a>
							</c:if>
						</c:forEach>
						
						<c:if test="${pager.groupNo!=pager.totalGroupNo}">
							<a class="btn btn-primary btn-sm" href="boardList?pageNo=${pager.endPageNo+1}">다음</a>
						</c:if>
						<a class="btn btn-warning btn-sm" href="boardList?pageNo=${pager.totalPageNo}">맨끝</a>
					</div>
				</td>
			</tr>
		</table>
		<div class="mt-2">
			<a href="boardWriteForm" class="btn btn-success btn-sm">새글쓰기</a>
		</div>
	</div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp"%>