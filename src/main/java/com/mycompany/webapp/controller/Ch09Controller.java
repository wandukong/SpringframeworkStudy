package com.mycompany.webapp.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
	public String fileUpload(String title, String desc, MultipartFile attach) throws IllegalStateException, IOException {
		logger.info("fileUpload 실행");
		
		//문자 파트 내용 읽기
		logger.info("title : " + title);
		logger.info("desc : " + desc);
		
		//파일 파트 내용 읽기
		logger.info("file originalname: " + attach.getOriginalFilename());
		logger.info("file contentType: " + attach.getContentType());
		logger.info("file size: " + attach.getSize());
		
		//파일 파트 데이터를 서버의 파일로 저장
		String savedname = new Date().getTime() + "-" + attach.getOriginalFilename();
		File file = new File("C:/hyundai_it&e/upload_files/"+ savedname);
		attach.transferTo(file);
		
		return "redirect:/ch09/content";
	}
	
	@PostMapping(value="/fileUploadAttach", produces="application/json; charset=UTF-8")
	@ResponseBody
	public String fileUploadAttach(String title, String desc, MultipartFile attach) throws IllegalStateException, IOException {
		logger.info("fileUploadAttach 실행");
		
		//문자 파트 내용 읽기
		logger.info("title : " + title);
		logger.info("desc : " + desc);
		
		//파일 파트 내용 읽기
		logger.info("file originalname: " + attach.getOriginalFilename());
		logger.info("file contentType: " + attach.getContentType());
		logger.info("file size: " + attach.getSize());
		
		//파일 파트 데이터를 서버의 파일로 저장
		String savedname = new Date().getTime() + "-" + attach.getOriginalFilename();
		File file = new File("C:/hyundai_it&e/upload_files/"+ savedname);
		attach.transferTo(file);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", "success");
		jsonObject.put("savedname", savedname);
		String json = jsonObject.toString();
		
		return json;
	}
	
	@GetMapping(value="/fileDownload", produces="image/jpeg")
	@ResponseBody
	public byte[] fileDownload(String savedname) throws IOException {
		String filePath = "C:/hyundai_it&e/upload_files/"+ savedname;
		InputStream is = new FileInputStream(filePath);
		byte[] data = IOUtils.toByteArray(is);
		return data;
	}
}
