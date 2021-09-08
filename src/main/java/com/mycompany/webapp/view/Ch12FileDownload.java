package com.mycompany.webapp.view;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

@Component
public class Ch12FileDownload extends AbstractView{

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {  
		
		logger.info("ch12FileDownload 실행");
		
		// Map은 request 범위에 저장되어 있는 객체를 불러올수 있게 해준다.
		String fileName = (String) model.get("fileName");
		String userAgent = (String) model.get("userAgent");
		
		String contentType = request.getServletContext().getMimeType(fileName); // 확장자를 통해 mimeType을 얻음
		String savedName = fileName;
		String originalFileName = fileName;

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
		String filePath = "C:/hyundai_it&e/upload_files/" + savedName;
		InputStream is = new FileInputStream(filePath);

		// 응답 바디에 출력하는 출력 스트림 얻기
		OutputStream os = response.getOutputStream();

		// 입력스트림 -> 출력 스트림
		FileCopyUtils.copy(is, os); // 단위대로 읽고 쓴다.
		is.close();
		os.flush();
		os.close();
	}
}
