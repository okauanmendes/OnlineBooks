package com.audiobook.app.service;

import com.audiobook.app.model.ListeningHistory;
import com.audiobook.app.repository.ListeningHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryService {

	private final ListeningHistoryRepository historyRepository;

	public HistoryService(ListeningHistoryRepository historyRepository) {
		this.historyRepository = historyRepository;
	}

	public List<ListeningHistory> findByUserId(Long userId) {
		return historyRepository.findByUserId(userId);
	}

	public ListeningHistory record(Long userId, Long bookId) {
		ListeningHistory history = new ListeningHistory();
		history.setUserId(userId);
		history.setBookId(bookId);
		return historyRepository.save(history);
	}
}
