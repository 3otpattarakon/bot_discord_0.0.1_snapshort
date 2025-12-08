package com.example.discord_web_bot.controller;

import com.example.discord_web_bot.dto.DiscordMessageRequest;
import com.example.discord_web_bot.service.DiscordWebhookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/discord")
public class DiscordController {

	private final DiscordWebhookService webhookService;

	public DiscordController(DiscordWebhookService webhookService) {
		this.webhookService = webhookService;
	}

	@PostMapping("/message")
	public ResponseEntity<?> postMessage(@RequestBody DiscordMessageRequest request) {
		if (request == null || request.getMessage() == null || request.getMessage().trim().isEmpty()) {
			// comment
			return ResponseEntity.badRequest().body("'message' is required in JSON body");
		}
		try {
			webhookService.sendMessage(request.getMessage());
			return ResponseEntity.ok("Message sent");
		} catch (IllegalStateException ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send message: " + ex.getMessage());
		}
	}

	@GetMapping("/message")
	public ResponseEntity<?> getMessage(
			@RequestParam(value = "message", required = false) String message,
			@RequestBody(required = false) DiscordMessageRequest requestBody
	) {
		String msg = message;
		if ((msg == null || msg.trim().isEmpty()) && requestBody != null) {
			msg = requestBody.getMessage();
		}

		if (msg == null || msg.trim().isEmpty()) {
			return ResponseEntity.badRequest().body("Provide 'message' as query param or JSON body");
		}

		try {
			webhookService.sendMessage(msg);
			return ResponseEntity.ok("Message sent");
		} catch (IllegalStateException ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send message: " + ex.getMessage());
		}
	}
}
