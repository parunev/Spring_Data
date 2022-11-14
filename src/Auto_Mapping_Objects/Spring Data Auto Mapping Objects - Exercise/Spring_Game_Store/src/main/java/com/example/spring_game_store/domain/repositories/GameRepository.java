package com.example.spring_game_store.domain.repositories;

import com.example.spring_game_store.domain.dtos.GameDTOs.DetailsGameDTO;
import com.example.spring_game_store.domain.entities.Game;
import com.example.spring_game_store.domain.entities.interfaces.TitlePriceGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    Optional<Game> findByTitle(String title);
    Set<TitlePriceGame> findAllBy();
    Set<DetailsGameDTO> findAllByTitle(String title);
}
