package com.audiobook.app.controller;

import com.audiobook.app.model.Book;
import com.audiobook.app.model.User;
import com.audiobook.app.enums.Role;
import com.audiobook.app.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

	private final BookService bookService;

	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@GetMapping
	public ResponseEntity<List<Book>> findAll() {
		return ResponseEntity.ok(bookService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Book> findById(@PathVariable Long id) {
		return ResponseEntity.ok(bookService.findById(id));
	}

	@PostMapping
	public ResponseEntity<Book> create(@RequestBody Book book) {
		return ResponseEntity.status(201).body(bookService.create(book));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Book> update(@PathVariable Long id, @RequestBody Book book) {
		return ResponseEntity.ok(bookService.update(id, book));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		bookService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/{id}/audio")
	public ResponseEntity<Book> uploadAudio(@PathVariable Long id,
											@RequestPart("file") MultipartFile file,
											@AuthenticationPrincipal User user) {
		if (user.getRole() != Role.ADMIN) {
			return ResponseEntity.status(403).build();
		}

		return ResponseEntity.ok(bookService.uploadAudio(id, file));
	}

	@PostMapping("/{id}/text")
	public ResponseEntity<Book> uploadText(@PathVariable Long id,
										   @RequestPart("file") MultipartFile file,
										   @AuthenticationPrincipal User user) {
		if (user.getRole() != Role.ADMIN) {
			return ResponseEntity.status(403).build();
		}

		return ResponseEntity.ok(bookService.uploadText(id, file));
	}
}
