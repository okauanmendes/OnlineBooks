package com.audiobook.app.service;

import com.audiobook.app.model.Favorite;
import com.audiobook.app.repository.FavoriteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {

	private final FavoriteRepository favoriteRepository;

	public FavoriteService(FavoriteRepository favoriteRepository) {
		this.favoriteRepository = favoriteRepository;
	}

	public List<Favorite> findByUserId(Long userId) {
		return favoriteRepository.findByUserId(userId);
	}

	public Favorite add(Long userId, Long bookId) {
		return favoriteRepository.findByUserIdAndBookId(userId, bookId)
				.orElseGet(() -> {
					Favorite favorite = new Favorite();
					favorite.setUserId(userId);
					favorite.setBookId(bookId);
					return favoriteRepository.save(favorite);
				});
	}

	public void remove(Long userId, Long bookId) {
		favoriteRepository.findByUserIdAndBookId(userId, bookId)
				.ifPresent(favoriteRepository::delete);
	}
}
