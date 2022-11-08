package com.example.demo.services;

import com.example.demo.enums.AgeRestriction;
import com.example.demo.enums.EditionType;
import com.example.demo.models.Author;
import com.example.demo.models.Book;
import com.example.demo.models.Category;
import com.example.demo.repositories.AuthorRepository;
import com.example.demo.repositories.BookRepository;
import com.example.demo.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SeedServiceImpl implements SeedService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    @Autowired
    public SeedServiceImpl(AuthorRepository authorRepository, BookRepository bookRepository, CategoryRepository categoryRepository, AuthorService authorService, CategoryService categoryService) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    private static final String RESOURCE_PATH = "src\\main\\resources\\files\\";
    private static final String AUTHORS_RESOURCE_PATH = RESOURCE_PATH + "authors.txt";
    private static final String BOOKS_RESOURCE_PATH = RESOURCE_PATH + "books.txt";
    private static final String CATEGORIES_RESOURCE_PATH = RESOURCE_PATH + "categories.txt";


    @Override
    public void seedAuthors() throws IOException {
        Files.readAllLines(Path.of(AUTHORS_RESOURCE_PATH))
                .stream()
                .filter(line -> !line.isBlank())
                .map(line -> line.split("\\s+"))
                .forEach(e -> authorRepository.save(new Author(e[0], e[1])));
    }

    @Override
    public void seedBooks() throws IOException {
        Files.readAllLines(Path.of(BOOKS_RESOURCE_PATH))
                .stream()
                .filter(line -> !line.isBlank())
                .forEach(line -> {
                    String[] data = line.split("\\s+");

                    Author author = authorService.getRandomAuthor();
                    EditionType editionType = EditionType.values()[Integer.parseInt(data[0])];
                    LocalDate releaseDate = LocalDate.parse(data[1],
                            DateTimeFormatter.ofPattern("d/M/yyyy"));
                    int copies = Integer.parseInt(data[2]);
                    BigDecimal price = new BigDecimal(data[3]);
                    AgeRestriction ageRestriction = AgeRestriction
                            .values()[Integer.parseInt(data[4])];
                    String title = Arrays.stream(data)
                            .skip(5)
                            .collect(Collectors.joining(" "));
                    Set<Category> categories = categoryService.getRandomCategories();


                    Book book = new Book(title, editionType, price, copies, releaseDate, ageRestriction, author, categories);
                    bookRepository.save(book);
                });
    }

    @Override
    public void seedCategories() throws IOException {
        Files.readAllLines(Path.of(CATEGORIES_RESOURCE_PATH))
                .stream()
                .filter(line -> !line.isBlank())
                .forEach(e -> categoryRepository.save(new Category(e)));
    }
}
