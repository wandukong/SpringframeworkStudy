# Paging 처리

### Pager Class
```java
import lombok.Data;

@Data
public class Pager {
	private int totalRows;		//전체 행수
	private int totalPageNo;	//전체 페이지 수
	private int totalGroupNo;	//전체 그룹 수
	private int startPageNo;	//그룹의 시작 페이지 번호
	private int endPageNo;		//그룹의 끝 페이지 번호
	private int pageNo;			//현재 페이지 번호
	private int pagesPerGroup;	//그룹당 페이지 수
	private int groupNo;		//현재 그룹 번호
	private int rowsPerPage;	//페이지당 행 수 
	private int startRowNo;		//페이지의 시작 행 번호(1, ..., n)
	private int startRowIndex;	//페이지의 시작 행 인덱스(0, ..., n-1) for mysql
	private int endRowNo;		//페이지의 마지막 행 번호
	private int endRowIndex;	//페이지의 마지막 행 인덱스

	// 페이지당 행 수, 그룹별 페이지 수, 전체 데이터 수, 현재 페이지
	public Pager(int rowsPerPage, int pagesPerGroup, int totalRows, int pageNo) {
		this.rowsPerPage = rowsPerPage;
		this.pagesPerGroup = pagesPerGroup;
		this.totalRows = totalRows;
		this.pageNo = pageNo;

		totalPageNo = totalRows / rowsPerPage;
		if(totalRows % rowsPerPage != 0) totalPageNo++;
		
		totalGroupNo = totalPageNo / pagesPerGroup;
		if(totalPageNo % pagesPerGroup != 0) totalGroupNo++;
		
		groupNo = (pageNo - 1) / pagesPerGroup + 1; 
		
		startPageNo = (groupNo-1) * pagesPerGroup + 1;
		
		endPageNo = startPageNo + pagesPerGroup - 1;
		if(groupNo == totalGroupNo) endPageNo = totalPageNo;
		
		startRowNo = (pageNo - 1) * rowsPerPage + 1;
		startRowIndex = startRowNo - 1;
		endRowNo = pageNo * rowsPerPage;
		endRowIndex = endRowNo - 1; 
	}
}
```
<hr/>

### Controller
```java
@GetMapping("/boardList")
public String boardList(@RequestParam(defaultValue="1") int pageNo, Model model) {
	int totalRows = boardservice.getTotalBoardNum();
	
	Pager pager = new Pager(10, 5, totalRows, pageNo);
	// 페이지당 행 수, 그룹별 페이지 수, 전체 데이터 수, 현재 페이지
	
	List<Ch14Board> boards = boardservice.getBoards(pager);
	model.addAttribute("boards", boards);
	model.addAttribute("pager", pager);
	return "ch14/boardList";
}
```
<hr/>

### boardList.jsp
```html
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
```
<hr/>

### board.xml
```xml
<select id="selectByPage" parameterType="pager" resultType="board">
	<![CDATA[
		select rnum, bno, btitle, bdate, mid 
		from (
        		select rownum as rnum , bno, btitle, bdate, mid 
                from (	select bno, btitle, bdate, mid 
                        from board
                        order by bno desc )
        		where rownum <= #{endRowNo}
		)
		where rnum >= #{startRowNo}
	]]>
</select>
```