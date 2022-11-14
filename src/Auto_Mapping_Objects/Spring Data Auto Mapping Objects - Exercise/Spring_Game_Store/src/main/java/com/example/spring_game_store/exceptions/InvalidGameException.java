package com.example.spring_game_store.exceptions;

public class InvalidGameException extends RuntimeException {
    public InvalidGameException() {
        super("Game doesn't match the criteria!");
    }

}
