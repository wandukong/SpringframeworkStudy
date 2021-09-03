package com.mycompany.webapp.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/ch09")
public class Ch09Controller {
	private static final Logger logger = LoggerFactory.getLogger(Ch09Controller.class);

	@RequestMapping("/content")
	public String content() {
		logger.info("ch09 실행");
		return "ch09/content";
	}

	@PostMapping("/fileUpload")
	public String fileUpload(String title, String desc, MultipartFile attach)
			throws IllegalStateException, IOException {
		logger.info("fileUpload 실행");

		// 문자 파트 내용 읽기
		logger.info("title : " + title);
		logger.info("desc : " + desc);

		// 파일 파트 내용 읽기
		logger.info("file originalname: " + attach.getOriginalFilename());
		logger.info("file contentType: " + attach.getContentType());
		logger.info("file size: " + attach.getSize());

		// 파일 파트 데이터를 서버의 파일로 저장
		String savedname = new Date().getTime() + "-" + attach.getOriginalFilename();
		File file = new File("C:/hyundai_it&e/upload_files/" + savedname);
		attach.transferTo(file);

		return "redirect:/ch09/content";
	}

	@PostMapping(value = "/fileUploadAttach", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String fileUploadAttach(String title, String desc, MultipartFile attach)
			throws IllegalStateException, IOException {
		logger.info("fileUploadAttach 실행");

		// 문자 파트 내용 읽기
		logger.info("title : " + title);
		logger.info("desc : " + desc);

		// 파일 파트 내용 읽기
		logger.info("file originalname: " + attach.getOriginalFilename());
		logger.info("file contentType: " + attach.getContentType());
		logger.info("file size: " + attach.getSize());

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

	// 응답 바디의 데이터 형식고정
	/* 
	@GetMapping(value="/fileDownload", produces="images/jpeg")
	@ResponseBody
	public byte[] fileDownload(String savedname) throws IOException {
		String filePath = "C:/hyundai_it&e/upload_files/"+ savedname;
		InputStream is = new FileInputStream(filePath);
		byte[] data = IOUtils.toByteArray(is); // 파일의 크기가 크더라도 불러옴 -> 좋은 코드가 아님
		return data;
	}
	*/

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

		// 파일 첨부로 다운로드하도록 설정, 브라우저가 해석하지 못하는 파일을 다운로드하게한다.
		response.setHeader("Content-Disposition", "attachment; filename=\"" + originalFileName + "\"");

		// 파일로부터 데이터를 읽는 입력스트림 생성
		String filePath = "C:/hyundai_it&e/upload_files/" + savedName;
		InputStream is = new FileInputStream(filePath);

		// 응답 바디에 출력하는 출력 스트림 얻기
		OutputStream os = response.getOutputStream();

		// 입력스트림 -> 출력 스트림
		FileCopyUtils.copy(is, os); // 1mb 읽고, 1mb 출력. 단위로 읽고 쓴다.
		is.close();
		os.flush();
		os.close();
	}
}
