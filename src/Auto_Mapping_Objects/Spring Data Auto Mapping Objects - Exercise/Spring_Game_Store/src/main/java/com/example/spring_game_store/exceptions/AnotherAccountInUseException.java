package com.example.spring_game_store.exceptions;

public class AnotherAccountInUseException extends RuntimeException {
    public AnotherAccountInUseException() {
        super("Another user is logged in!");
    }
}
