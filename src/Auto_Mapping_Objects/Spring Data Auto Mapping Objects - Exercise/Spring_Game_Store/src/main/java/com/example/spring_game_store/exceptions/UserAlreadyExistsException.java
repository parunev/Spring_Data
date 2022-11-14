package com.example.spring_game_store.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException() {
        super("Can't register with the same email!");
    }

}
