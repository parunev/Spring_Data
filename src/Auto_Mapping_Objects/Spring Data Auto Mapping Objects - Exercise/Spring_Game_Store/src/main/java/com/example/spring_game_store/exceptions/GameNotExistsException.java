package com.example.spring_game_store.exceptions;

public class GameNotExistsException extends RuntimeException {
    public GameNotExistsException() {
        super("Game doesn't exist!");
    }

    public GameNotExistsException(String message) {
        super(message);
    }
}
