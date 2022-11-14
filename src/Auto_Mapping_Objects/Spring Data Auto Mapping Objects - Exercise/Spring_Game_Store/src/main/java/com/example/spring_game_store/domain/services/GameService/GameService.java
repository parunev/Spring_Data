package com.example.spring_game_store.domain.services.GameService;

import com.example.spring_game_store.domain.entities.Game;

public interface GameService {
    void addGame(Game game);

    Game editGame(long id, String[] commandParts);

    Game deleteGame(long idToDelete);

    String showAllGames();

    String showDetailGame(String title);

    Game findByTitle(String title);
}
