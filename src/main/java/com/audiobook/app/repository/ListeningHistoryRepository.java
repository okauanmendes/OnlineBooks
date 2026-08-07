package com.audiobook.app.repository;

import com.audiobook.app.model.ListeningHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListeningHistoryRepository extends JpaRepository<ListeningHistory, Long> {
}
