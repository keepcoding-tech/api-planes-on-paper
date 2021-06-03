package com.keepcoding.api_planes_on_paper.service.util;

import java.util.Random;

// returns a unique string representing the access token for a private gameplay room
public class AccessTokenGenerator {
	public String accessToken() {
		final String characters = "0AaBbCc1DdEeF2fGgHh3IiJjK4kLlMmN5nOoPpQqRr6SsTtU7uVvWw8XxYyZz9";
		final int tokenLength = 6;

		String accessToken = "";
		Random random = new Random();

		for (int i = 0; i < tokenLength; i++) {
			final char aux = characters.charAt(random.nextInt(characters.length()));
			accessToken = accessToken + aux;
		}

		return accessToken;
	}
}
