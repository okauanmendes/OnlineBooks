package com.audiobook.app.controller;

import com.audiobook.app.model.Category;
import com.audiobook.app.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

	private final CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@GetMapping
	public ResponseEntity<List<Category>> findAll() {
		return ResponseEntity.ok(categoryService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Category> findById(@PathVariable Long id) {
		return ResponseEntity.ok(categoryService.findById(id));
	}

	@PostMapping
	public ResponseEntity<Category> create(@RequestBody Category category) {
		return ResponseEntity.status(201).body(categoryService.create(category));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Category> update(@PathVariable Long id, @RequestBody Category category) {
		return ResponseEntity.ok(categoryService.update(id, category));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		categoryService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
