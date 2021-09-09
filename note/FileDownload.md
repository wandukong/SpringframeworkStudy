
# File Download

## 🏁Download
```html
<a href="fileDownload?fileNo=1" class="btn btn-info btn-sm">파일 다운로드</a>
```

```java
@GetMapping("/fileDownload")
@ResponseBody
public void fileDownload(int fileNo, HttpServletResponse response, @RequestHeader("User-Agent") String userAgent)
		throws IOException {
	// fileNo을 이용해서 db에서 파일 정보를 가져오기, 밑에 3줄 db에서 가져왔다고 가정
	String contentType = "img/jpeg";
	String savedName = "1630656700227-눈내리는마을.jpg";
	String originalFileName = "눈내리는마을.jpg";
	///////////////////////////////////////////////////////////////

	response.setContentType(contentType); // response body의 데이터 형식 설정

	// 브라우저별로 한글 파일명을 변환
	if (userAgent.contains("Trident") || userAgent.contains("MSIE")) {
		// IE
		originalFileName = URLEncoder.encode(originalFileName, "UTF-8");
	} else {
		// 크롬, 엣지, 사파리
		originalFileName = new String(originalFileName.getBytes("UTF-8"), "ISO-8859-1");
	}

	// 파일 첨부로 다운로드하도록 설정, 브라우저가 해석하지 못하는 파일을 다운로드 하게 한다.
	response.setHeader("Content-Disposition", "attachment; filename=\"" + originalFileName + "\"");

	// 파일로부터 데이터를 읽는 입력스트림 생성
	String filePath = "C:/hyundai_it&e/upload_files/" + savedName; // 파일을 불러올 경로
	InputStream is = new FileInputStream(filePath);

	// 응답 바디에 출력하는 출력 스트림 얻기
	OutputStream os = response.getOutputStream();

	// 입력스트림 -> 출력 스트림
	FileCopyUtils.copy(is, os); // 단위대로 읽고 쓴다.
	is.close();
	os.flush();
	os.close();
}
```