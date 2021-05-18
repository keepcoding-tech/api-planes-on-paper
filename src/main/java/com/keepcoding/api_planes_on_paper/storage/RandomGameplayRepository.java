package com.keepcoding.api_planes_on_paper.storage;

import com.keepcoding.api_planes_on_paper.models.random_game.RandomGameplay;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RandomGameplayRepository extends JpaRepository<RandomGameplay, Long> { }
