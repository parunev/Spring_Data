package com.example.json_exercise.domain.repositories;

import com.example.json_exercise.domain.dtos.categoryDTOs.CategoryProductSummaryDTO;
import com.example.json_exercise.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "SELECT * FROM products_shop.categories ORDER BY RAND() LIMIT 1"
            , nativeQuery = true)
    Optional<Category> getRandomEntity();

    @Query("SELECT new com.example.json_exercise.domain.dtos.categoryDTOs.CategoryProductSummaryDTO" +
            "(c.name, COUNT(p), AVG(p.price), SUM(p.price)) FROM Product p JOIN p.categories c GROUP BY c")
    Optional<List<CategoryProductSummaryDTO>> getCategoriesSummary();
}
