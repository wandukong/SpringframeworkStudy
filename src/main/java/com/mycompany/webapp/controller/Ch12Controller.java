package com.mycompany.webapp.controller;

import java.io.IOException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ch12")
public class Ch12Controller {
	private static final Logger logger = LoggerFactory.getLogger(Ch12Controller.class);

	@RequestMapping("/content")
	public String content() {
		logger.info("ch12 실행");
		return "ch12/content";
	}
	
	@GetMapping("/fileList")
	public String fileList() throws IOException {
		logger.info("fileList 실행");
		return "ch12FileListView";
	}
	
	@GetMapping("/fileDownload")
	public String fileDownload(@ModelAttribute("fileName") String fileName, @ModelAttribute("userAgent") @RequestHeader("User-Agent") String userAgent)
			throws IOException {
		logger.info("fileDownload 실행");
		return "ch12FileDownload";
	}
}
