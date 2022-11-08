package com.example.demo;

import com.example.demo.models.Author;
import com.example.demo.models.Book;
import com.example.demo.repositories.AuthorRepository;
import com.example.demo.repositories.BookRepository;
import com.example.demo.services.SeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final SeedService seedService;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public ConsoleRunner(SeedService seedService, BookRepository bookRepository, AuthorRepository authorRepository) {
        this.seedService = seedService;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        //seedService.seedDatabase();

        System.out.println("Pick task number:");
        int a = new Scanner(System.in).nextInt();

        switch(a) {
            case 1 -> _01_getAllBooksTitlesAfter2000();
            case 2 -> _02_getAllBooksAuthorsWithBooksBefore1990();
            case 3 -> _03_getAllAuthorsWithBookCountOrderedDesc();
            case 4 -> _04_getAllBooksByGeorgePowell();
        }
    }

    private void _01_getAllBooksTitlesAfter2000() {
        LocalDate year2000 = LocalDate.of(1999, 12 ,31);
        List<Book> allAfterReleaseDate = bookRepository.findAllByReleaseDateAfter(year2000);

        allAfterReleaseDate.forEach(e -> System.out.println(e.getTitle()));
    }

    private void _02_getAllBooksAuthorsWithBooksBefore1990() {
        LocalDate year1990 = LocalDate.of(1990, 1 ,1);
        List<Book> allAfterReleaseDate = bookRepository.findAllByReleaseDateBefore(year1990);

        allAfterReleaseDate.stream()
                .map(Book::getAuthor)
                .forEach(e -> System.out.println(e.getFirstName() + " " + e.getLastName()));
    }

    private void _03_getAllAuthorsWithBookCountOrderedDesc() {
        List<Author> allAuthors = authorRepository.findAll();

        allAuthors.stream().sorted((e1, e2) -> Long.compare(e2.getBooks().size(), e1.getBooks().size()))
                .forEach(e -> System.out.printf("%s %s - %d books%n", e.getFirstName(), e.getLastName(), e.getBooks().size()));
    }

    private void _04_getAllBooksByGeorgePowell() {
        Author author = authorRepository.findByFirstNameAndLastName("George", "Powell");
        List<Book> allByAuthor = bookRepository.findAllByAuthor(author);

        allByAuthor.stream()
                .sorted((o1, o2) -> {
                    int sortedResult = o2.getReleaseDate().compareTo(o1.getReleaseDate());
                    return sortedResult == 0 ? o1.getTitle().compareTo(o2.getTitle()) : sortedResult;
                })
                .forEach(e -> System.out.printf("%s - %s - %d copies%n", e.getTitle(), e.getReleaseDate(), e.getCopies()));

    }
}
