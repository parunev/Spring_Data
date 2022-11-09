package com.example.springintro.service;

import com.example.springintro.model.BookSummary;
import com.example.springintro.model.BookTitleAndPrice;
import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.EditionType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;

import java.io.IOException;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> findAllBooksAfterYear(int year);

    List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year);

    List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName);

    List<String> findAllBooksTitlesByAgeRestriction(String ageRestriction);

    List<String> findAllBooksTitlesByEditionAndCopies(EditionType editionType, int copies);

    List<BookTitleAndPrice> findAllBooksTitlesAndPricesByPriceLowerThanAndHigherThan(float lower, float upper);

    List<String> findAllBooksTitlesNotReleasedIn(int year);

    List<Book> findAllBooksReleasedBefore(String dateStr);

    List<Book> findAllBooksByTitlesContaining(String str);

    List<Book> findAllBooksTitlesByAuthorsLastNameStartingWith(String str);

    long getCountOfBooksWithTitleLongerThan(long length);

    List<BookSummary> getBookSummaryByTitle(String title);

    int increaseBookCopiesByDateAfter(String dateStr, int number);

    int deleteByCopiesLessThan(int number);

    int callUspTotalBooks(String firstName, String lastName);
}
