package com.example.json_exercise.domain.services.SeedService;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface SeedService {
    void seedUsers() throws IOException;
    void seedCategories() throws IOException;
    void seedProducts() throws IOException;

    default void seedAll() throws IOException {
        seedUsers();
        seedCategories();
        seedProducts();
    }
}
