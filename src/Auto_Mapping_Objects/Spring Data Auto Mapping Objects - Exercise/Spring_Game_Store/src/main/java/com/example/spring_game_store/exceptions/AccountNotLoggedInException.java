package com.example.spring_game_store.exceptions;

public class AccountNotLoggedInException extends RuntimeException {
    public AccountNotLoggedInException() {
        super("You are not logged in!");
    }

    public AccountNotLoggedInException(String message) {
        super(message);
    }
}
