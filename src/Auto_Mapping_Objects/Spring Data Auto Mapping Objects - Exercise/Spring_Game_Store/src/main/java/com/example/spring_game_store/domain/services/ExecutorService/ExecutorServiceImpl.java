package com.example.spring_game_store.domain.services.ExecutorService;

import com.example.spring_game_store.domain.dtos.GameDTOs.AddGameDTO;
import com.example.spring_game_store.domain.dtos.UserDTOs.UserLoginDTO;
import com.example.spring_game_store.domain.dtos.UserDTOs.UserRegistrationDTO;
import com.example.spring_game_store.domain.entities.Game;
import com.example.spring_game_store.domain.entities.User;
import com.example.spring_game_store.domain.services.GameService.GameService;
import com.example.spring_game_store.domain.services.UserService.UserService;
import com.example.spring_game_store.exceptions.NoSuchOperationExists;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

import static com.example.spring_game_store.outputs.GameOutputs.*;
import static com.example.spring_game_store.outputs.UserOutputs.*;


@AllArgsConstructor
@Service
public class ExecutorServiceImpl implements ExecutorService{
    private final UserService userService;
    private final GameService gameService;

    @Override
    public String execute(String args) {
        String[] arguments = args.split("\\|");
        String cmd = arguments[0];

            return switch (cmd){
            case REGISTER_USER_COMMAND -> registerUser(arguments);
            case LOGIN_USER_COMMAND -> loginUser(arguments);
            case LOGOUT_USER_COMMAND -> logoutUser();
            case ADD_GAME_COMMAND -> addGame(arguments);
            case EDIT_GAME_COMMAND -> editGame(arguments);
            case DELETE_GAME_COMMAND -> deleteGame(arguments);
            case ALL_GAMES_COMMAND -> allGames();
            case DETAIL_GAME_COMMAND -> detailGame(arguments);
            case OWNED_GAMES_COMMAND -> ownedGames();
            case ADD_ITEM_COMMAND -> addItem(arguments);
            case REMOVE_ITEM_COMMAND -> removeItem(arguments);
            case BUY_ITEM_COMMAND -> buyItem();
            default -> throw new NoSuchOperationExists("Unexpected command: " + cmd);
        };
    }

    private String registerUser(String[] arguments) {
        UserRegistrationDTO urdto = new UserRegistrationDTO(arguments);
        this.userService.register(urdto);

        return String.format(REGISTERED_USER_MSG, urdto.getFullName());
    }

    private String loginUser(String[] arguments) {
        UserLoginDTO uldto = new UserLoginDTO(arguments);
        User login = this.userService.login(uldto);

        return String.format(LOGIN_USER_MSG, login.getFullName());
    }

    private String logoutUser() {
        User logout = this.userService.logout();
        return String.format(LOGOUT_USER_MSG, logout.getFullName());
    }

    private String addGame(String[] arguments) {
        AddGameDTO agdto = new AddGameDTO(arguments);
        Game game = userService.addGame(agdto);

        return String.format(ADDED_GAME_MSG, game.getTitle());
    }

    private String editGame(String[] arguments) {
        long toEdit = Long.parseLong(arguments[1]);
        Game game = this.userService.editGame(toEdit, arguments);

        return String.format(EDITED_GAME_MSG, game.getTitle());
    }

    private String deleteGame(String[] arguments) {
        long toDelete = Long.parseLong(arguments[1]);
        Game game = this.userService.deleteGame(toDelete);

        return String.format(DELETE_GAME_MSG, game.getTitle());
    }

    private String allGames() {
        return this.gameService.showAllGames();
    }

    private String detailGame(String[] arguments) {
        String title = arguments[1];

        return this.gameService.showDetailGame(title);
    }

    private String ownedGames() {
        return this.userService.showOwnedGames().isBlank() ? NO_GAMES_MSG : userService.showOwnedGames();
    }

    private String addItem(String[] arguments) {
        String title = arguments[1];

        Game game = this.userService.addItem(title);

        return String.format(ADDED_ITEM_MSG, game.getTitle());
    }

    private String removeItem(String[] arguments) {
        String title = arguments[1];

        Game game = this.userService.removeItem(title);

        return String.format(REMOVED_ITEM_MSG, game.getTitle());
    }

    private String buyItem() {
        Set<Game> games = this.userService.buyItems();

        return String.format(SUCCESSFULLY_BOUGHT_MSG
                , games.stream().map(e -> " -" + e.getTitle())
                .collect(Collectors.joining(System.lineSeparator())));
    }
}
