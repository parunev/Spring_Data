package com.example.json_exercise.domain.repositories;

import com.example.json_exercise.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM products_shop.users ORDER BY RAND() LIMIT 1"
            , nativeQuery = true)
    Optional<User> getRandomEntity();


    Optional<List<User>> findAllBySellingProductsBuyerIsNotNullOrderBySellingProductsBuyerFirstName();
}