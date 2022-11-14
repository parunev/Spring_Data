package com.example.spring_game_store.utils;

import com.example.spring_game_store.domain.entities.User;
import com.example.spring_game_store.exceptions.AccountNotLoggedInException;
import com.example.spring_game_store.exceptions.AnotherAccountInUseException;
import com.example.spring_game_store.exceptions.InsufficientPermissionException;

public class Checking {

    public static void checkIfAnotherUserIsLogged(User currentUser) {
        if (currentUser != null) {
            throw new AnotherAccountInUseException();
        }
    }

    public static void checkIfLoggedAndAdmin(User currentUser) {
        if (currentUser == null) {
            throw new AccountNotLoggedInException("Cannot operate while logged out!");
        }

        if (!currentUser.isAdmin()) {
            throw new InsufficientPermissionException();
        }
    }

    public static void isLogged(User currentUser) {
        if (currentUser == null) {
            throw new AccountNotLoggedInException("Cannot operate while logged out!");
        }
    }
}
