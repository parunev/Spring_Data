package com.example.springintro.repository;

import com.example.springintro.model.entity.Author;
import com.example.springintro.model.AuthorCopies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("SELECT a FROM Author a ORDER BY a.books.size DESC")
    List<Author> findAllByBooksSizeDESC();

    List<Author> findAllByFirstNameEndingWith(String endStr);

    @Query("SELECT a.firstName AS firstName, a.lastName AS lastName, SUM(b.copies) AS bookCopies" +
            " FROM Author a" +
            " JOIN a.books b" +
            " GROUP BY a" +
            " ORDER BY bookCopies DESC")
    List<AuthorCopies> findAllAuthorsWithCopiesCountOrderByCopiesDesc();
}
