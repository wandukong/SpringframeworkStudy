package com.mycompany.webapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

public class Ch13Service5 {

	private static final Logger logger = LoggerFactory.getLogger(Ch13Service5.class);

	private int prop1;
	private double prop2;
	private boolean prop3;
	private String prop4;
	
	public Ch13Service5(int prop1, double prop2) {
		logger.info("Ch13Service5 생성자 실행");
		logger.info("prop1 : " + prop1);
		logger.info("prop2 : " + prop2);

		this.prop1 = prop1;
		this.prop2 = prop2;
	}

	public void setProp3(boolean prop3) {
		logger.info("setProp3 실행");

		logger.info("prop3 : " + prop3);
		this.prop3 = prop3;
	}
}
