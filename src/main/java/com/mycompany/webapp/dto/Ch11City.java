package com.mycompany.webapp.dto;

import lombok.Data;

@Data
public class Ch11City {
	private int code;
	private String label;
	
	public Ch11City() {
		
	}
	
	public Ch11City(int code, String label) {
		this.code = code;
		this.label = label;
	}
}
