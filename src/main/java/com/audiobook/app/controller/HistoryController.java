package com.audiobook.app.controller;

import com.audiobook.app.model.ListeningHistory;
import com.audiobook.app.model.User;
import com.audiobook.app.service.HistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController
@RequestMapping("/history")
public class HistoryController {

	private final HistoryService historyService;

	public HistoryController(HistoryService historyService) {
		this.historyService = historyService;
	}

	@GetMapping
	public ResponseEntity<List<ListeningHistory>> findMine(@AuthenticationPrincipal User user) {
		return ResponseEntity.ok(historyService.findByUserId(user.getId()));
	}

	@PostMapping("/{bookId}")
	public ResponseEntity<ListeningHistory> record(@AuthenticationPrincipal User user,
												   @PathVariable Long bookId) {
		return ResponseEntity.status(201).body(historyService.record(user.getId(), bookId));
	}
}
