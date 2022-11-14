package com.example.spring_game_store.domain.dtos.GameDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class DetailsGameDTO {
    private final String title;
    private final BigDecimal price;
    private final String description;
    private final LocalDate releaseDate;

    @Override
    public String toString() {
        return String.format("Title: %s%n" +
                "Price: %.2f%n" +
                "Description: %s%n" +
                "Release date: %s", title, price, description, releaseDate);
    }
}
