package com.example.spring_game_store.exceptions;

public class NoSuchAccountException extends RuntimeException{

    public NoSuchAccountException(String message) {
        super(message);
    }
}
