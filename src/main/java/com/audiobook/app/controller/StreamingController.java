package com.audiobook.app.controller;

import com.audiobook.app.enums.SubscriptionStatus;
import com.audiobook.app.model.Book;
import com.audiobook.app.model.User;
import com.audiobook.app.service.BookService;
import com.audiobook.app.service.StorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Duration;
import java.util.Map;

@RestController
@RequestMapping("/stream")
public class StreamingController {

	private final BookService bookService;
	private final StorageService storageService;

	public StreamingController(BookService bookService, StorageService storageService) {
		this.bookService = bookService;
		this.storageService = storageService;
	}

	@GetMapping("/books/{bookId}")
	public ResponseEntity<?> stream(@PathVariable Long bookId,
									@AuthenticationPrincipal User user) {
		Book book = bookService.findById(bookId);

		if (book.getAudioFileKey() == null) {
			return ResponseEntity.badRequest().body(Map.of("error", "Book has no audio file"));
		}

		if (book.isPremium() && user.getSubscriptionStatus() != SubscriptionStatus.ACTIVE) {
			return ResponseEntity.status(403).body(Map.of("error", "Active subscription required"));
		}

		String url = storageService.generateDownloadUrl(book.getAudioFileKey(), Duration.ofMinutes(15));
		return ResponseEntity.ok(Map.of("url", url, "expiresInSeconds", "900"));
	}

	@GetMapping("/books/{bookId}/read")
	public ResponseEntity<?> read(@PathVariable Long bookId,
								  @AuthenticationPrincipal User user) {
		Book book = bookService.findById(bookId);

		if (book.getTextFileKey() == null) {
			return ResponseEntity.badRequest().body(Map.of("error", "Book has no reading file"));
		}

		if (book.isPremium() && user.getSubscriptionStatus() != SubscriptionStatus.ACTIVE) {
			return ResponseEntity.status(403).body(Map.of("error", "Active subscription required"));
		}

		String url = storageService.generateDownloadUrl(book.getTextFileKey(), Duration.ofMinutes(15));
		return ResponseEntity.ok(Map.of("url", url, "expiresInSeconds", "900"));
	}
}
