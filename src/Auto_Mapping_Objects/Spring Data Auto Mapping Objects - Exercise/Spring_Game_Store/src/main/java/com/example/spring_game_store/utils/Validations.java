package com.example.spring_game_store.utils;

import com.example.spring_game_store.domain.entities.Game;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validations {
    private static final String EMAIL_REGEX = "^(?<user>[a-zA-Z]+[.-_]*[a-zA-Z]+)@(?<host>[A-Za-z]+\\.[A-Za-z]+)$";
    private static final String PASSWORD_REGEX = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,}";

    public static boolean isValidEmail(String email) {
        Pattern emailPattern = Pattern.compile(EMAIL_REGEX);
        Matcher emailMatcher = emailPattern.matcher(email);

        return emailMatcher.find();
    }

    public static boolean isValidPassword(String password) {
        Pattern passwordPattern = Pattern.compile(PASSWORD_REGEX);
        Matcher passwordMatcher = passwordPattern.matcher(password);

        return passwordMatcher.find();
    }

    public static boolean isValid(String email, String password) {
        return isValidEmail(email) && isValidPassword(password);
    }

    private static boolean isValidDescription(String description) {
        return description.length() >= 20;
    }

    private static boolean isValidThumbnailURL(String thumbnailURL) {
        return thumbnailURL.startsWith("http://") || thumbnailURL.startsWith("https://");
    }

    public static boolean isValidTrailer(String trailer) {
        return trailer.length() == 11 || trailer.startsWith("youtube", 12);
    }

    private static boolean isValidSize(float size) {
        return size > 0;
    }

    private static boolean isValidPrice(BigDecimal price) {
        return price.compareTo(BigDecimal.ZERO) > 0;
    }

    private static boolean isValidTitle(String title) {
        return Character.isUpperCase(title.charAt(0)) && title.length() >= 3 && title.length() <= 100;
    }


    public static boolean isValidGame(String title, BigDecimal price, float size, String trailer, String thumbnailURL, String description) {
        return isValidTitle(title) && isValidPrice(price) &&
                isValidSize(size) && isValidTrailer(trailer) &&
                isValidThumbnailURL(thumbnailURL) && isValidDescription(description);
    }

    public static boolean isValidGame2(Game game) {
        return isValidTitle(game.getTitle()) && isValidPrice(game.getPrice()) &&
                isValidSize(game.getSize()) && isValidTrailer(game.getTrailer()) &&
                isValidThumbnailURL(game.getThumbnailURL()) && isValidDescription(game.getDescription());
    }
}
