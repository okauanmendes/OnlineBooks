package com.audiobook.app.service;

import com.audiobook.app.exception.ResourceNotFoundException;
import com.audiobook.app.model.Book;
import com.audiobook.app.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.List;

@Service
public class BookService {

	private final BookRepository bookRepository;
	private final StorageService storageService;

	public BookService(BookRepository bookRepository, StorageService storageService) {
		this.bookRepository = bookRepository;
		this.storageService = storageService;
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
		existingBook.setAudioFileKey(book.getAudioFileKey());
		existingBook.setTextFileKey(book.getTextFileKey());
		existingBook.setDurationSeconds(book.getDurationSeconds());
		existingBook.setPremium(book.isPremium());
		return bookRepository.save(existingBook);
	}

	public void delete(Long id) {
		Book book = findById(id);
		bookRepository.delete(book);
	}

	public Book uploadAudio(Long id, MultipartFile file) {
		if (file.isEmpty()) {
			throw new IllegalArgumentException("Audio file cannot be empty");
		}

		Book book = findById(id);
		String filename = Paths.get(file.getOriginalFilename() == null
				? "audio-file"
				: file.getOriginalFilename()).getFileName().toString();
		String key = "books/" + id + "/" + UUID.randomUUID() + "-" + filename;

		try {
			storageService.upload(key, file);
		} catch (IOException exception) {
			throw new IllegalStateException("Could not read audio file", exception);
		}

		if (book.getAudioFileKey() != null) {
			storageService.delete(book.getAudioFileKey());
		}

		book.setAudioFileKey(key);
		return bookRepository.save(book);
	}

	public Book uploadText(Long id, MultipartFile file) {
		if (file.isEmpty()) {
			throw new IllegalArgumentException("Reading file cannot be empty");
		}

		Book book = findById(id);
		String filename = Paths.get(file.getOriginalFilename() == null
				? "reading-file"
				: file.getOriginalFilename()).getFileName().toString();
		String key = "books/" + id + "/text/" + UUID.randomUUID() + "-" + filename;

		try {
			storageService.upload(key, file);
		} catch (IOException exception) {
			throw new IllegalStateException("Could not read book file", exception);
		}

		if (book.getTextFileKey() != null) {
			storageService.delete(book.getTextFileKey());
		}

		book.setTextFileKey(key);
		return bookRepository.save(book);
	}
}
