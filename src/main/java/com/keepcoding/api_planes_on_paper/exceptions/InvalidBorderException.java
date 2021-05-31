package com.keepcoding.api_planes_on_paper.exceptions;

public class InvalidBorderException extends Exception{
	public InvalidBorderException() { }

	public String getMessage() {
		return "the planes border is not valid";
	}
}
