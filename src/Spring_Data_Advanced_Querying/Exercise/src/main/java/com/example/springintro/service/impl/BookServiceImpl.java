package com.example.springintro.service.impl;

import com.example.springintro.model.BookSummary;
import com.example.springintro.model.BookTitleAndPrice;
import com.example.springintro.model.entity.*;
import com.example.springintro.repository.BookRepository;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private static final String BOOKS_FILE_PATH = "src/main/resources/files/books.txt";

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedBooks() throws IOException {
        if (bookRepository.count() > 0) {
            return;
        }

        Files.readAllLines(Path.of(BOOKS_FILE_PATH))
                .forEach(row -> {
                    String[] bookInfo = row.split("\\s+");
                    Book book = createBookFromInfo(bookInfo);
                    bookRepository.save(book);
                });
    }

    @Override
    public List<Book> findAllBooksAfterYear(int year) {
        return bookRepository
                .findAllByReleaseDateAfter(LocalDate.of(year, 12, 31));
    }

    @Override
    public List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year) {
        return bookRepository
                .findAllByReleaseDateBefore(LocalDate.of(year, 1, 1))
                .stream()
                .map(book -> String.format("%s %s", book.getAuthor().getFirstName(),
                        book.getAuthor().getLastName()))
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName) {
       return bookRepository
                .findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(firstName, lastName)
                .stream()
                .map(book -> String.format("%s %s %d",
                        book.getTitle(),
                        book.getReleaseDate(),
                        book.getCopies()))
                .collect(Collectors.toList());
    }

    //P01 - Books Title By Age Restriction
    @Override
    public List<String> findAllBooksTitlesByAgeRestriction(String ageRestriction) {
        AgeRestriction ageRestrictionEnum = AgeRestriction.valueOf(ageRestriction.toUpperCase());

        return this.bookRepository.findAllTitlesByAgeRestriction(ageRestrictionEnum);
    }

    //P02 - Golden Books
    @Override
    public List<String> findAllBooksTitlesByEditionAndCopies(EditionType editionType, int copies) {
        return this.bookRepository.findAllTitleByEditionAndCopiesLessThan(editionType, copies);
    }

    //P03 - Books By Price
    @Override
    public List<BookTitleAndPrice> findAllBooksTitlesAndPricesByPriceLowerThanAndHigherThan(float lower, float upper) {
        BigDecimal lowerBD = BigDecimal.valueOf(lower);
        BigDecimal upperBD = BigDecimal.valueOf(upper);

        return this.bookRepository.findAllTitlesAndPricesByPriceLowerThanAndHigherThan(lowerBD, upperBD);
    }

    //P04 - Not Released Books
    @Override
    public List<String> findAllBooksTitlesNotReleasedIn(int year) {
        return this.bookRepository.findAllBooksTitlesNotReleasedIn(year);
    }

    //P05 - Books Released Before Date
    @Override
    public List<Book> findAllBooksReleasedBefore(String dateStr) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse(dateStr, dateTimeFormatter);

        return this.bookRepository.findAllByReleaseDateBefore(date);
    }

    //P07 - Books Search
    @Override
    public List<Book> findAllBooksByTitlesContaining(String str) {
        str = str.toLowerCase();

        return this.bookRepository.findAllByTitleContaining(str);
    }

    //P08 - Books Title Search
    @Override
    public List<Book> findAllBooksTitlesByAuthorsLastNameStartingWith(String str) {
        return this.bookRepository.findAllBooksTitlesByAuthorsLastNameStartingWith(str);
    }

    //P09 - Count Books
    @Override
    public long getCountOfBooksWithTitleLongerThan(long length) {
        return this.bookRepository.getCountOfBooksWithTitleLongerThan(length);
    }

    @Override
    public List<BookSummary> getBookSummaryByTitle(String title) {
        return this.bookRepository.getBookSummaryByTitle(title);
    }

    //P12 - Increase Book Copies
    @Override
    public int increaseBookCopiesByDateAfter(String dateStr, int number) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        LocalDate date = LocalDate.parse(dateStr, dateTimeFormatter);

        return this.bookRepository.updateCopiesAfter(date, number);
    }

    //P13 - Remove Books
    @Override
    public int deleteByCopiesLessThan(int number) {
        return this.bookRepository.deleteByCopiesLessThan(number);
    }

    //P14 - Stored Procedure
    @Override
    public int callUspTotalBooks(String firstName, String lastName) {
        return this.bookRepository.callUspTotalBooks(firstName, lastName);
    }

    private Book createBookFromInfo(String[] bookInfo) {
        EditionType editionType = EditionType.values()[Integer.parseInt(bookInfo[0])];
        LocalDate releaseDate = LocalDate
                .parse(bookInfo[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
        Integer copies = Integer.parseInt(bookInfo[2]);
        BigDecimal price = new BigDecimal(bookInfo[3]);
        AgeRestriction ageRestriction = AgeRestriction
                .values()[Integer.parseInt(bookInfo[4])];
        String title = Arrays.stream(bookInfo)
                .skip(5)
                .collect(Collectors.joining(" "));

        Author author = authorService.getRandomAuthor();
        Set<Category> categories = categoryService
                .getRandomCategories();

        return new Book(editionType, releaseDate, copies, price, ageRestriction, title, author, categories);
    }
}
