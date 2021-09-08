
# File Upload with multipart
> **Multipart란?**

-   웹 클라이언트가 요청을 보낼 때,  **http 프로토콜**의  **바디**  부분에 데이터를 여러 부분으로 나눠서 보내는 것.
-   웹 클라이언트가 서버에게 파일을 업로드할 때, http 프로토콜의 바디 부분에 파일정보를 담아서 전송을 하는데, 파일을 한번에 여러개 전송을 하면 body 부분에 파일이 여러개의 부분으로 연결되어 전송된다. 이렇게 여러 부분으로 나뉘어서 전송되는 것을 **Multipart data**라고 한다.

<img src="https://user-images.githubusercontent.com/47289479/132018367-2182c766-4651-4e85-a437-d1429947479e.png" width="500px"/>

## ✨사용환경 설정

#### dependency 추가
```xml
<!-- json -->
<dependency>
	<groupId>org.json</groupId>
	<artifactId>json</artifactId>
	<version>20210307</version>
</dependency>

<!-- Common-FileUpload -->
<dependency>
	<groupId>commons-fileupload</groupId>
	<artifactId>commons-fileupload</artifactId>
	<version>1.4</version>
</dependency>
```
#### bean 추가

```xml
<!-- 파일 업로드를 위한 MultipartResolver 객체 등록 -->
<bean id="multipartResolver"   
 	class="org.springframework.web.multipart.commons.CommonsMultipartResolver"/>
```


## 🎆Upload 
```java
@PostMapping(value = "/fileUploadAttach", produces = "application/json; charset=UTF-8")
@ResponseBody
public String fileUploadAttach(String title, String desc, MultipartFile attach)
		throws IllegalStateException, IOException {

	logger.info("title : " + title);
	logger.info("desc : " + desc);

	// 파일 파트 내용 읽기
	logger.info("file originalname: " + attach.getOriginalFilename()); // photo12.jpg
	logger.info("file contentType: " + attach.getContentType()); // image/jpeg
	logger.info("file size: " + attach.getSize()); // 107447

	// 파일 파트 데이터를 서버의 파일로 저장
	String savedname = new Date().getTime() + "-" + attach.getOriginalFilename();
	File file = new File("C:/hyundai_it&e/upload_files/" + savedname);
	attach.transferTo(file);

	JSONObject jsonObject = new JSONObject();
	jsonObject.put("result", "success");
	jsonObject.put("savedname", savedname);
	String json = jsonObject.toString();

	return json;
}
```
```html
<form method="post" enctype="multipart/form-data" action="fileUpload"> 
	<input type="text" class="form-control" id="title" name="title" placeholder="파일 제목">
	<input type="text" class="form-control" id="desc" name="desc" placeholder="파일 설명">
	<input type="file" class="form-control-file" id="attach" name="attach" multiple>
</form>
```
```javascript
function fileUpload(){
	const title = $("#title").val();
	const desc = $("#desc").val();
	const attach = document.querySelector("#attach").files[0]; // files 속성은 여러 파일 불러옴
	
	//  multipart/form-data
	const formData = new FormData();
	formData.append("title",title);
	formData.append("desc",desc);
	formData.append("attach",attach);

	$.ajax({
		url : "fileUploadAttach",
		method : "post",
		data : formData,
		cache: false, // 큰파일을 보내기 때문에 브라우저에서 캐쉬로 저장할 필요가 없다
		processData: false, // ajax가 가공하지 않고 있는 그대로 보낸다.
		contentType: false, // 개별적인 contentType이 있기 때문에 전체적으로 줄 필요가 없다.
	}).done((data)=>{
		console.log(data);
		if(data.result === "success"){
			window.alert("파일 전송 성공");
		}
	});
}
```