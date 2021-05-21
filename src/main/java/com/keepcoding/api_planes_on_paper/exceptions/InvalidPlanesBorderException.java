package com.keepcoding.api_planes_on_paper.exceptions;

public class InvalidPlanesBorderException extends Exception{
	private final String message;

	public InvalidPlanesBorderException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
