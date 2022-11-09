package com.example.springintro;

import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.EditionType;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import com.example.springintro.tools.Reader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.naming.OperationNotSupportedException;
import java.io.IOException;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;

    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Choose task:");
        int taskNum = Reader.readInt();

        switch (taskNum) {
            case 1 -> { //works
                //seedData();
                String ageRestriction = Reader.readString();
                printAllBooksTitlesByAgeRestriction(ageRestriction);
            }
            case 2 -> printAllBooksTitlesByEditionAndCopies(EditionType.GOLD, 5000); //works
            case 3 -> printAllBooksTitlesAndPricesByPriceLowerThanAndHigherThan(5, 40); //works
            case 4 -> { //works
                int year = Reader.readInt();
                printAllBooksTitlesNotReleasedIn(year);}
            case 5 -> { //works
                String dateStr = Reader.readString();
                printAllBooksReleasedBefore(dateStr);}
            case 6 -> { //works
                String endStr = Reader.readString();
                printAllAuthorNamesEndingWith(endStr);}
            case 7 -> { //works
                String str = Reader.readString();
                printAllBooksTitlesContaining(str);}
            case 8 -> { //works
                String str = Reader.readString();
                printAllBooksTitlesWrittenByAuthorsLastNameStartingWith(str);}
            case 9 -> { //works
                long length = Reader.readLong();
                printNumberOfBooksWithTitleLongerThan(length);}
            case 10 -> printTotalBookCopiesByAuthorOrderByTotalCopiesDesc(); //works
            case 11 -> { //works
                String title = Reader.readString();
                printBookSummaryByTitle(title);}
            case 12 -> { //works ## day ####
                String dateStr = Reader.readString();
                int number = Reader.readInt();
                increaseBookCopies(dateStr, number);}
            case 13 -> { //it works, but I'm not sure if database is updating
                int number = Reader.readInt();
                removeBooks(number);}
            case 14 -> { //technically should work, but in my situation is not working :D
                String[] names = Reader.readString().split("\\s+");
                useStoredProcedure(names[0], names[1]);}

            default -> throw new OperationNotSupportedException("No such task!");
        }
    }

    private void useStoredProcedure(String firstName, String lastName) {
        int count = this.bookService.callUspTotalBooks(firstName, lastName);

        System.out.printf("%s %s has written %d books%n", firstName, lastName, count);
    }

    private void removeBooks(int number) {
        int deleted = this.bookService.deleteByCopiesLessThan(number);

        System.out.println("Delete books count: " + deleted);
    }

    private void increaseBookCopies(String dateStr, int number) {
        int updatedCount = this.bookService.increaseBookCopiesByDateAfter(dateStr, number);

        System.out.printf("%d books are released after 12 Oct 2005, so total of %d book copies were added%n",
                updatedCount, updatedCount * number);
    }

    private void printBookSummaryByTitle(String title) {
        this.bookService.getBookSummaryByTitle(title)
                .forEach(e -> System.out.printf("%s %s %s %.2f%n",
                        e.getTitle(), e.getEditionType(), e.getAgeRestriction(), e.getPrice()));
    }

    private void printTotalBookCopiesByAuthorOrderByTotalCopiesDesc() {
        this.authorService.findAllAuthorsWithCopiesCountOrderByCopiesDesc()
                .forEach(e -> System.out.printf("%s %s - %d%n",
                        e.getFirstName(), e.getLastName(), e.getBookCopies()));
    }

    private void printNumberOfBooksWithTitleLongerThan(long length) {
        System.out.printf("There are %d books with longer title than %d symbols%n",
                this.bookService.getCountOfBooksWithTitleLongerThan(length), length);
    }

    private void printAllBooksTitlesWrittenByAuthorsLastNameStartingWith(String str) {
        this.bookService.findAllBooksTitlesByAuthorsLastNameStartingWith(str)
                .forEach(e -> System.out.printf("%s (%s %s)%n",
                        e.getTitle(), e.getAuthor().getFirstName(), e.getAuthor().getLastName()));
    }

    private void printAllBooksTitlesContaining(String str) {
        this.bookService.findAllBooksByTitlesContaining(str)
                .forEach(e -> System.out.println(e.getTitle()));
    }

    private void printAllAuthorNamesEndingWith(String endStr) {
        this.authorService.findAllAuthorsByNamesEndingWith(endStr)
                .forEach(e -> System.out.printf("%s %s%n", e.getFirstName(), e.getLastName()));
    }

    private void printAllBooksReleasedBefore(String dateStr) {
        this.bookService.findAllBooksReleasedBefore(dateStr)
                .forEach(e -> System.out.printf("%s %s %.2f%n",
                        e.getTitle(), e.getEditionType(), e.getPrice()));
    }

    private void printAllBooksTitlesNotReleasedIn(int year) {
        this.bookService.findAllBooksTitlesNotReleasedIn(year)
                .forEach(System.out::println);
    }

    private void printAllBooksTitlesAndPricesByPriceLowerThanAndHigherThan(float lower, float upper) {
        this.bookService.findAllBooksTitlesAndPricesByPriceLowerThanAndHigherThan(lower, upper)
                .forEach(e -> System.out.printf("%s - $%.2f%n", e.getTitle(), e.getPrice()));
    }

    private void printAllBooksTitlesByEditionAndCopies(EditionType editionType, int copies) {
        this.bookService.findAllBooksTitlesByEditionAndCopies(editionType, copies)
                .forEach(System.out::println);
    }

    public void printAllBooksTitlesByAgeRestriction(String ageRestriction) {
        this.bookService.findAllBooksTitlesByAgeRestriction(ageRestriction)
                .forEach(System.out::println);
    }

    private void printALlBooksByAuthorNameOrderByReleaseDate(String firstName, String lastName) {
        bookService
                .findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(firstName, lastName)
                .forEach(System.out::println);
    }

    private void printAllAuthorsAndNumberOfTheirBooks() {
        authorService
                .getAllAuthorsOrderByCountOfTheirBooks()
                .forEach(System.out::println);
    }

    private void printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(int year) {
        bookService
                .findAllAuthorsWithBooksWithReleaseDateBeforeYear(year)
                .forEach(System.out::println);
    }

    private void printAllBooksAfterYear(int year) {
        bookService
                .findAllBooksAfterYear(year)
                .stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();
    }
}
