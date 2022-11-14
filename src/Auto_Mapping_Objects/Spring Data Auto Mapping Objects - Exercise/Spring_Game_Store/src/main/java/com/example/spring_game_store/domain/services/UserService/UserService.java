package com.example.spring_game_store.domain.services.UserService;

import com.example.spring_game_store.domain.dtos.GameDTOs.AddGameDTO;
import com.example.spring_game_store.domain.dtos.UserDTOs.UserLoginDTO;
import com.example.spring_game_store.domain.dtos.UserDTOs.UserRegistrationDTO;
import com.example.spring_game_store.domain.entities.Game;
import com.example.spring_game_store.domain.entities.User;
import com.example.spring_game_store.exceptions.GameAlreadyExistsException;

import java.util.Set;

public interface UserService {
    void register(UserRegistrationDTO registerDTO);

    User login(UserLoginDTO loginDTO);

    User logout();

    Game addGame(AddGameDTO addGameDTO) throws GameAlreadyExistsException;

    Game editGame(long id, String[] args);

    Game deleteGame(long id);

    String showOwnedGames();

    Game addItem(String title);

    Game removeItem(String title);

    Set<Game> buyItems();
}
