package com.example.spring_game_store.domain.services.GameService;

import com.example.spring_game_store.domain.dtos.GameDTOs.DetailsGameDTO;
import com.example.spring_game_store.domain.entities.Game;
import com.example.spring_game_store.domain.repositories.GameRepository;
import com.example.spring_game_store.exceptions.GameAlreadyExistsException;
import com.example.spring_game_store.exceptions.GameNotExistsException;
import com.example.spring_game_store.exceptions.InvalidGameException;
import com.example.spring_game_store.exceptions.NoSuchOperationExists;
import com.example.spring_game_store.utils.Validations;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class GameServiceImpl implements GameService{

    private GameRepository gameRepository;

    @Override
    public void addGame(Game game) {
        Optional<Game> byTitle = gameRepository.findByTitle(game.getTitle());
        if (byTitle.isPresent()){
            throw new GameAlreadyExistsException();
        }
        gameRepository.save(game);
    }

    @Override
    public Game editGame(long id, String[] args) {
        Optional<Game> gameById = this.gameRepository.findById(id);

        if (gameById.isEmpty()) {
            throw new GameNotExistsException();
        }

        Game game = gameById.get();

        args = Arrays.stream(args).skip(2).toArray(String[]::new);


        for (String arg : args) {
            String[] input = arg.split("=");
            String toEditArg = input[1];

            switch (input[0]) {
                case "title" -> game.setTitle(toEditArg);
                case "price" -> game.setPrice(new BigDecimal(toEditArg));
                case "size" -> game.setSize(Float.parseFloat(toEditArg));
                case "trailer" -> {
                    if (!Validations.isValidTrailer(toEditArg)) {
                        throw new InvalidGameException();
                    }

                    toEditArg = toEditArg.substring(toEditArg.length() - 11);
                    game.setTrailer(toEditArg);
                }
                case "thumbnailURL" -> game.setThumbnailURL(toEditArg);
                case "description" -> game.setDescription(toEditArg);
                case "releaseDate" -> {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-uuuu");
                    game.setReleaseDate(LocalDate.parse(toEditArg, formatter));
                    game.setReleaseDate(LocalDate.parse(toEditArg, formatter));
                }

                default -> throw new NoSuchOperationExists();
            }

            if (!Validations.isValidGame2(game)) {
                throw new InvalidGameException();
            }
        }

        gameRepository.save(game);
        return game;
    }

    @Override
    public Game deleteGame(long idToDelete) {
        Optional<Game> deleteGame = this.gameRepository.findById(idToDelete);

        if(deleteGame.isEmpty()) {
            throw new GameNotExistsException();
        }

        Game game = deleteGame.get();

        this.gameRepository.deleteById(idToDelete);

        return game;
    }

    @Override
    public String showAllGames() {
        return this.gameRepository.findAllBy().stream().map(e -> e.getTitle() + " " + e.getPrice())
                .collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    public String showDetailGame(String title) {
        return this.gameRepository.findAllByTitle(title)
                .stream().map(DetailsGameDTO::toString).collect(Collectors.joining());
    }

    @Override
    public Game findByTitle(String title) {
        Optional<Game> byTitle = this.gameRepository.findByTitle(title);

        if (byTitle.isEmpty()) {
            throw new GameNotExistsException();
        }

        return byTitle.get();
    }
}
