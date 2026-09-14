package com.audiobook.app.service;

import com.audiobook.app.exception.ResourceNotFoundException;
import com.audiobook.app.model.Book;
import com.audiobook.app.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

	private final BookRepository bookRepository;

	public BookService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	public List<Book> findAll() {
		return bookRepository.findAll();
	}

	public Book findById(Long id) {
		return bookRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Book not found: " + id));
	}

	public Book create(Book book) {
		return bookRepository.save(book);
	}

	public Book update(Long id, Book book) {
		Book existingBook = findById(id);
		existingBook.setTitle(book.getTitle());
		return bookRepository.save(existingBook);
	}

	public void delete(Long id) {
		Book book = findById(id);
		bookRepository.delete(book);
	}
}
