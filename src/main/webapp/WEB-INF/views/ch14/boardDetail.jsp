<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp"%>

<div class="card m-2">
	<div class="card-header"></div>
	<div class="card-body">
		<div>
			<div>
				<div>
					<span>번호:</span> <span>${board.bno}</span> <br /> <span>제목:</span>
					<span>${board.btitle}</span> <br /> <span>글쓴이:</span> <span>${board.mid}</span>
					<br /> <span class="title">날짜:</span> <span><fmt:formatDate
							value="${board.bdate}" pattern="yyyy-MM-dd HH.mm.ss" /></span> <br />
				</div>
				<div>
					<span class="title">내용:</span> <br />
					<textarea style="width: 100%" readonly>${board.bcontent}</textarea>
				</div>
				<a class="btn btn-success btn-sm mt-2" href="boardList">목록</a>
				<a class="btn btn-warning btn-sm mt-2" href="boardUpdateForm?bno=${board.bno}">수정</a>
				<a class="btn btn-danger btn-sm mt-2" href="boardDelete?bno=${board.bno}">삭제</a>
			</div>
		</div>
	</div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp"%>