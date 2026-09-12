package com.audiobook.app.repository;

import com.audiobook.app.model.ListeningHistory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ListeningHistoryRepository extends JpaRepository<ListeningHistory, Long> {
	List<ListeningHistory> findByUserId(Long userId);
}
