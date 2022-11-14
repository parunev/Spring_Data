package com.example.spring_game_store.domain.dtos.GameDTOs;

import com.example.spring_game_store.exceptions.InvalidGameException;
import com.example.spring_game_store.utils.Validations;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class AddGameDTO {

    private String title;
    private BigDecimal price;
    private float size;
    private String trailer;
    private String thumbnailURL;
    private String description;
    private LocalDate releaseDate;

    public AddGameDTO(String[] args) {
        title = args[1];
        price = new BigDecimal(args[2]);
        size = Float.parseFloat(args[3]);
        trailer = args[4];
        thumbnailURL = args[5];
        description = args[6];
        releaseDate = LocalDate.parse(args[7], DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        validate();

        trailer = trailer.substring(trailer.length() - 11);
    }

    private void validate() {
        if (!Validations.isValidGame(title, price, size, trailer, thumbnailURL, description)) {
            throw new InvalidGameException();
        }
    }
}
