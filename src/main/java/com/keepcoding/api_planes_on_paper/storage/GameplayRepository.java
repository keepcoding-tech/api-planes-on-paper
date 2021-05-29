package com.keepcoding.api_planes_on_paper.storage;

import com.keepcoding.api_planes_on_paper.models.GameplayModel;

import com.keepcoding.api_planes_on_paper.models.GameplayStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface GameplayRepository extends JpaRepository<GameplayModel, Long> {
	@Query(
			"SELECT gp FROM GameplayModel gp " +
			"WHERE gp.gameID = :gameID"
	)
	Optional<GameplayModel> findGameplayByGameID(@Param("gameID") String gameID);

	@Transactional
	@Query(
			"SELECT gp FROM GameplayModel gp " +
			"WHERE gp.accessToken IS NULL " +
			"AND gp.gameStatus = :gameStatus"
	)
	Optional<GameplayModel> joinRandomGame(@Param("gameStatus") GameplayStatus gameStatus);

	@Transactional
	@Query(
			"SELECT gp FROM GameplayModel gp " +
			"WHERE gp.accessToken = :accessToken " +
			"AND gp.gameStatus = :gameStatus"
	)
	Optional<GameplayModel> joinPrivateGame(
			@Param("accessToken") String accessToken,
			@Param("gameStatus") GameplayStatus gameStatus);

	@Query(
			"SELECT gp FROM GameplayModel gp " +
			"WHERE gp.accessToken = :accessToken"
	)
	Optional<GameplayModel> findExistentToken(@Param("accessToken") String accessToken);

	@Modifying
	@Query(
			"DELETE FROM GameplayModel gp " +
			"WHERE gp.gameID = :gameID"
	)
	void deleteGameplayByGameID(@Param("gameID") String gameID);
}
