package com.example.spring_game_store.domain.services.UserService;

import com.example.spring_game_store.domain.dtos.GameDTOs.AddGameDTO;
import com.example.spring_game_store.domain.dtos.UserDTOs.UserLoginDTO;
import com.example.spring_game_store.domain.dtos.UserDTOs.UserRegistrationDTO;
import com.example.spring_game_store.domain.entities.Game;
import com.example.spring_game_store.domain.entities.Order;
import com.example.spring_game_store.domain.entities.User;
import com.example.spring_game_store.domain.repositories.OrderRepository;
import com.example.spring_game_store.domain.repositories.UserRepository;
import com.example.spring_game_store.domain.services.GameService.GameService;
import com.example.spring_game_store.exceptions.*;
import com.example.spring_game_store.utils.Checking;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private ModelMapper mp;
    private final UserRepository userRepository;
    private final GameService gameService;
    private final OrderRepository orderRepository;
    private User currentUser;

    public UserServiceImpl(UserRepository userRepository, GameService gameService, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.gameService = gameService;
        this.orderRepository = orderRepository;
        this.currentUser = null;
    }

    @Override
    public void register(UserRegistrationDTO arg) {
        Checking.checkIfAnotherUserIsLogged(currentUser);

        Optional<User> doesUserExists = this.userRepository.findByEmail(arg.getEmail());

        if (doesUserExists.isPresent()) {
            throw new UserAlreadyExistsException();
        }

        mp = new ModelMapper();
        User user = mp.map(arg, User.class);

        if (this.userRepository.count() == 0) {
            user.setAdmin(true);
        }

        this.userRepository.save(user);

    }

    @Override
    public User login(UserLoginDTO loginDTO) {
        Checking.checkIfAnotherUserIsLogged(currentUser);

        Optional<User> byEmailAndPassword = this.userRepository
                .findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());

        if (byEmailAndPassword.isEmpty()) {
            throw new WrongCredentialsException("Invalid email / password");
        }

        User user = byEmailAndPassword.get();

        this.currentUser = user;
        return user;
    }

    @Override
    public User logout() {
        if (currentUser == null) {
            throw new AccountNotLoggedInException();
        }

        User user = this.currentUser;
        this.currentUser = null;

        return user;
    }

    @Override
    public Game addGame(AddGameDTO addGameDTO) throws GameAlreadyExistsException {
        Checking.checkIfLoggedAndAdmin(this.currentUser);

        mp = new ModelMapper();
        Game game = mp.map(addGameDTO, Game.class);

        this.gameService.addGame(game);

        return game;
    }

    @Override
    public Game editGame(long id, String[] args) {
        Checking.checkIfLoggedAndAdmin(this.currentUser);

        return this.gameService.editGame(id, args);
    }

    @Override
    public Game deleteGame(long id) {
        Checking.checkIfLoggedAndAdmin(this.currentUser);

        return this.gameService.deleteGame(id);
    }

    @Override
    public String showOwnedGames() {
        Checking.isLogged(this.currentUser);

        return this.currentUser.getGames().stream().map(Game::getTitle)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    public Game addItem(String title) {
        Checking.isLogged(this.currentUser);

        Game toAdd = this.gameService.findByTitle(title);

        if (currentUser.getGames().contains(toAdd)) {
            throw new GameAlreadyExistsException("Game already owned!");
        }

        if (currentUser.getShoppingCart().contains(toAdd)) {
            throw new GameAlreadyExistsException("Game is already added in shopping cart!");
        }

        currentUser.addToShoppingCart(toAdd);

        return toAdd;
    }

    @Override
    public Game removeItem(String title) {
        Checking.isLogged(this.currentUser);

        Game toRemove = this.gameService.findByTitle(title);

        if (currentUser.getShoppingCart().contains(toRemove)) {
            throw new GameNotExistsException("Game isn't in shopping cart!");
        }

        currentUser.removeFromShoppingCart(toRemove);

        return toRemove;
    }

    @Override
    public Set<Game> buyItems() {
        Checking.isLogged(this.currentUser);

        Set<Game> shoppingCart = new HashSet<>(currentUser.getShoppingCart());

        if (shoppingCart.isEmpty()) {
            throw new GameNotExistsException("Empty cart!");
        }

        Order order = new Order(currentUser, currentUser.getShoppingCart());
        orderRepository.save(order);

        currentUser.clearShoppingCart();
        return shoppingCart;
    }
}
