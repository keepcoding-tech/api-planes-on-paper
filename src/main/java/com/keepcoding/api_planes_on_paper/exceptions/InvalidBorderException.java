package com.keepcoding.api_planes_on_paper.exceptions;

public class InvalidBorderException extends Exception{
	private final String message;

	public InvalidBorderException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
