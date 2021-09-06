package com.mycompany.webapp.dto;

import lombok.Data;

@Data // Lombok의 @Data를 사용하면 getter/setter/equal/tostring() 등 다양한 메소드를 자동으로 생성해준다.
public class Ch11Member {
	private String mid;
	private String mname;
	private String mpassword;
	private String mnation;
	private String mtype;
	private String mjob;
	private int mcity;
	private String[] mlanguage;
	private String[] mskill;
}