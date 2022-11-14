package com.example.spring_game_store.domain.services.ExecutorService;

public interface ExecutorService {
    String REGISTER_USER_COMMAND = "RegisterUser";
    String LOGIN_USER_COMMAND = "LoginUser";
    String LOGOUT_USER_COMMAND = "Logout";
    String ADD_GAME_COMMAND = "AddGame";
    String EDIT_GAME_COMMAND = "EditGame";
    String DELETE_GAME_COMMAND = "DeleteGame";
    String ALL_GAMES_COMMAND = "AllGames";
    String DETAIL_GAME_COMMAND = "DetailGame";
    String OWNED_GAMES_COMMAND = "OwnedGames";
    String ADD_ITEM_COMMAND = "AddItem";
    String REMOVE_ITEM_COMMAND = "RemoveItem";
    String BUY_ITEM_COMMAND = "BuyItem";

    String execute(String command);
}
