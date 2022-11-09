package com.example.advquerying.repositories;

import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ShampooRepository extends JpaRepository<Shampoo, Long> {

    //ако вместо findBy има findByDistinct това означава, че ще вземем само абсолютно уникалните
    List<Shampoo> findByBrand(String brand); //select * from shampoo where brand = ?
    List<Shampoo> findByBrandAndSize(String brand, Size size); // select * from shampoo where brand = ? and size = ?
    List<Shampoo> findBySizeOrderByIdDesc(Size parsed); // select * from shampoo where size = ? order by id
    @Query("SELECT s FROM Shampoo AS s " + "JOIN s.ingredients AS i" + " WHERE i.name = :ingredient")
    List<Shampoo> findByIngredient(String ingredient);
    // възможно е използването на @Param анотация
    // т.е. бихме могли да напишем :name и да стане @Param("name) String ingredient
    @Query("SELECT s FROM Shampoo AS s" + " JOIN s.ingredients AS i" + " WHERE i.name IN :ingredients")
    List<Shampoo> findByIngredients(List<String> ingredients);

    List<Shampoo> findBySizeOrLabelId(Size parsed, long labelId);

    List<Shampoo> findByPriceGreaterThanOrderByPriceDesc(BigDecimal price);

    long countByPriceLessThan(BigDecimal price);

    @Query("SELECT s FROM Shampoo AS s " + " WHERE s.ingredients.size < :count")
    List<Shampoo> findByIngredientCountLessThan(int count);
}
