package com.example.spring_game_store.exceptions;

public class InsufficientPermissionException extends RuntimeException {
    public InsufficientPermissionException() {
        super("You don't have permission to perform this action!");
    }

}
