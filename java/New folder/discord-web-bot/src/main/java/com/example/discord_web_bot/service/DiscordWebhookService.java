package com.example.discord_web_bot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

@Service
public class DiscordWebhookService {

	private final RestTemplate restTemplate = new RestTemplate();

	@Value("${discord.webhook.url:}")
	private String webhookUrl;

	public DiscordWebhookService() {
	}

	public void sendMessage(String message) {
		if (webhookUrl == null || webhookUrl.trim().isEmpty()) {
			throw new IllegalStateException("discord.webhook.url is not configured in application.properties");
		}

		Map<String, String> payload = Collections.singletonMap("content", message);
		try {
			ResponseEntity<String> resp = restTemplate.postForEntity(webhookUrl, payload, String.class);
		} catch (RestClientException ex) {
			throw new RuntimeException("Failed to send message to Discord webhook", ex);
		}
	}
}
