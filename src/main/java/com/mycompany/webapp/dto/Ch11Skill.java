package com.mycompany.webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // 기본 생성자 생성
@AllArgsConstructor // 모든 멤버변수를 매개변수로 가지는 생성자 생성
public class Ch11Skill {
	private int code;
	private String label;
	
	/*
	public Ch11Skill() {
		
	}
	public Ch11Skill(int code, String label) {
		this.code = code;
		this.label = label;
	}*/
}
