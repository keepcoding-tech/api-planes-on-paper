package com.keepcoding.api_planes_on_paper.storage;

import com.keepcoding.api_planes_on_paper.models.GameplayModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameplayRepository extends JpaRepository<GameplayModel, Long> {
	@Query("SELECT id FROM GameplayModel WHERE id.accessToken = ?1")
	Optional<GameplayModel> findPrivateGameByAccessToken(String accessToken);
}
