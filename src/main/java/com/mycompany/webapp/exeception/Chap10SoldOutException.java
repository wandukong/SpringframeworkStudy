package com.mycompany.webapp.exeception;

public class Chap10SoldOutException extends RuntimeException{

	public Chap10SoldOutException() {
		super("품절");
	}
	
	public Chap10SoldOutException(String message) {
		super(message);
	}
}
