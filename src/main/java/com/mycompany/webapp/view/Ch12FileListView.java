package com.mycompany.webapp.view;

import java.io.File;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;


@Component
public class Ch12FileListView extends AbstractView{
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		logger.info("ch12FileListView 실행");
		
		String fileDir = "C:/hyundai_it&e/upload_files"; // 파일의 총 수 및 파일 이름 목록 얻기
		File file = new File(fileDir);
		String[] fileList = file.list();
		int totalFileNum = fileList.length;
		
		response.setContentType("application/json; charset=UTF-8;");
		
		PrintWriter pw = response.getWriter();
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("totalFileNum", totalFileNum);
		
		JSONArray jsonArray = new JSONArray(); // 배열 형태 만들기
		
		for(String fileName : fileList) {
			jsonArray.put(fileName);
		}
		jsonObject.put("fileList", jsonArray);
		
		String json = jsonObject.toString();
		pw.print(json);
		pw.flush();
		pw.close();
	}
}
