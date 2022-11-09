package com.example.springintro.repository;

import com.example.springintro.model.BookSummary;
import com.example.springintro.model.BookTitleAndPrice;
import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByReleaseDateAfter(LocalDate releaseDateAfter);

    List<Book> findAllByReleaseDateBefore(LocalDate releaseDateBefore);

    List<Book> findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(String author_firstName, String author_lastName);

    @Query("SELECT b.title FROM Book AS b WHERE b.ageRestriction = :ageRestriction")
    List<String> findAllTitlesByAgeRestriction(AgeRestriction ageRestriction);

    @Query(" SELECT b.title FROM Book AS b WHERE b.editionType = :editionType AND b.copies < :copies")
    List<String> findAllTitleByEditionAndCopiesLessThan(EditionType editionType, int copies);

    @Query("SELECT b.title AS title, b.price AS price FROM Book b WHERE b.price < :lower OR b.price > :upper")
    List<BookTitleAndPrice> findAllTitlesAndPricesByPriceLowerThanAndHigherThan(BigDecimal lower, BigDecimal upper);

    @Query("SELECT b.title FROM Book AS b WHERE YEAR (b.releaseDate) NOT LIKE :year")
    List<String> findAllBooksTitlesNotReleasedIn(int year);

    List<Book> findAllByTitleContaining(String str);

    @Query("SELECT b FROM Book AS b JOIN b.author a WHERE a.lastName LIKE :str%")
    List<Book> findAllBooksTitlesByAuthorsLastNameStartingWith(String str);

    @Query("SELECT COUNT(b) FROM Book b WHERE CHAR_LENGTH(b.title) > :length")
    long getCountOfBooksWithTitleLongerThan(long length);

    @Query("SELECT b.title AS title, b.editionType AS editionType, b.ageRestriction AS ageRestriction, b.price AS price" +
            " FROM Book b" +
            " WHERE b.title = :title")
    List<BookSummary> getBookSummaryByTitle(String title);

    @Modifying
    @Transactional
    @Query("UPDATE Book AS b SET b.copies = b.copies + :number WHERE b.releaseDate > :date")
    int updateCopiesAfter(@Param(value = "date") LocalDate date, @Param(value = "number") int number);

    @Transactional
    int deleteByCopiesLessThan(int number);

    @Procedure("usp_total_books")
    int callUspTotalBooks(@Param(value = "first_name") String firstName, @Param(value = "last_name") String lastName);
}
