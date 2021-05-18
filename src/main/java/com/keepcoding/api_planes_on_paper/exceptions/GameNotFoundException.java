package com.keepcoding.api_planes_on_paper.exceptions;

public class GameNotFoundException extends Exception{
	private final String message;
	public GameNotFoundException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
