package com.mycompany.webapp.dto;

import lombok.Data;

@Data
public class Ch11Skill {
	private int code;
	private String label;
	
	public Ch11Skill() {
		
	}
	
	public Ch11Skill(int code, String label) {
		this.code = code;
		this.label = label;
	}
}
