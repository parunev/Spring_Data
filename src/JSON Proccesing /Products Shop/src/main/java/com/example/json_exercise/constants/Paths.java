package com.example.json_exercise.constants;

import java.nio.file.Path;

public enum Paths {
    ;
    public static final Path USER_JSON_PATH =
            Path.of("src", "main", "resources", "db_content", "users.json");

    public static final Path CATEGORY_JSON_PATH =
            Path.of("src", "main", "resources", "db_content", "categories.json");

    public static final Path PRODUCT_JSON_PATH =
            Path.of("src", "main", "resources", "db_content", "products.json");

    public static final Path PRODUCTS_WITH_NO_BUYERS_IN_RANGE_JSON_PATH =
            Path.of("src", "main", "resources", "outputs", "products-in-range.json");

    public static final Path USERS_WITH_SOLD_PRODUCTS_JSON_PATH =
            Path.of("src", "main", "resources", "outputs", "users-sold-products.json");

    public static final Path CATEGORIES_BY_PRODUCTS_JSON_PATH =
            Path.of("src", "main", "resources", "outputs", "categories-by-products.json");

    public static final Path USERS_AND_PRODUCTS_JSON_PATH =
            Path.of("src", "main", "resources", "outputs", "users-and-products.json");

}
