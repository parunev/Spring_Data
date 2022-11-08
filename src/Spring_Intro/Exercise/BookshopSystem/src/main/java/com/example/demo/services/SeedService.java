package com.example.demo.services;

import java.io.IOException;

public interface SeedService {
    void seedAuthors() throws IOException;
    void seedBooks() throws IOException;
    void seedCategories() throws IOException;

    default void seedDatabase() throws IOException {
        seedCategories();
        seedAuthors();
        seedBooks();
    }
}
