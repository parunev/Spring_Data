package com.example.springintro.service;

import com.example.springintro.model.AuthorCopies;
import com.example.springintro.model.entity.Author;

import java.io.IOException;
import java.util.List;

public interface AuthorService {
    void seedAuthors() throws IOException;

    Author getRandomAuthor();

    List<String> getAllAuthorsOrderByCountOfTheirBooks();

    List<Author> findAllAuthorsByNamesEndingWith(String endStr);

    List<AuthorCopies> findAllAuthorsWithCopiesCountOrderByCopiesDesc();
}
