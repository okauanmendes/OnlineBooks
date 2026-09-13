package com.audiobook.app.controller;

import com.audiobook.app.model.Favorite;
import com.audiobook.app.model.User;
import com.audiobook.app.service.FavoriteService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController
@RequestMapping("/favorites")
public class FavoriteController {

	private final FavoriteService favoriteService;

	public FavoriteController(FavoriteService favoriteService) {
		this.favoriteService = favoriteService;
	}

	@GetMapping
	public ResponseEntity<List<Favorite>> findMine(@AuthenticationPrincipal User user) {
		return ResponseEntity.ok(favoriteService.findByUserId(user.getId()));
	}

	@PostMapping("/{bookId}")
	public ResponseEntity<Favorite> add(@AuthenticationPrincipal User user,
										@PathVariable Long bookId) {
		return ResponseEntity.status(201).body(favoriteService.add(user.getId(), bookId));
	}

	@DeleteMapping("/{bookId}")
	public ResponseEntity<Void> remove(@AuthenticationPrincipal User user,
									   @PathVariable Long bookId) {
		favoriteService.remove(user.getId(), bookId);
		return ResponseEntity.noContent().build();
	}
}
