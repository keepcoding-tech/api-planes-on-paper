package com.keepcoding.api_planes_on_paper.storage;

import com.keepcoding.api_planes_on_paper.models.private_game.PrivateGameplay;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrivateGameplayRepository extends JpaRepository<PrivateGameplay, Long> {
	@Query("SELECT id FROM PrivateGameplay WHERE id.accessToken = ?1")
	Optional<PrivateGameplay> findPrivateGameByAccessToken(String accessToken);
}
