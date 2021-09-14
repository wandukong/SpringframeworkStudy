package com.mycompany.webapp.dto;

import java.util.Date;

import lombok.Data;

@Data
public class Ch14Board {
	private int bno;
	private String btitle;
	private String bcontent;
	private Date bdate;
	private String mid;
}
