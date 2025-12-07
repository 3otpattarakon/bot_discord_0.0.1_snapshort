package com.example.discord_web_bot.dto;

public class DiscordMessageRequest {
	private String message;

	public DiscordMessageRequest() {
	}

	public DiscordMessageRequest(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

