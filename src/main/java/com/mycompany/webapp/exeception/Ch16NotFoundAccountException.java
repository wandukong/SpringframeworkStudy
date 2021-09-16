package com.mycompany.webapp.exeception;

public class Ch16NotFoundAccountException extends RuntimeException {
	
	public Ch16NotFoundAccountException() {
		
	}
	public Ch16NotFoundAccountException(String message) {
		super(message);
	}
}
