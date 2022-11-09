package com.example.advquerying.services;

import com.example.advquerying.entities.Shampoo;

import java.math.BigDecimal;
import java.util.List;

public interface ShampooService {
    List<Shampoo> findByBrand(String brand);

    List<Shampoo> findByBrandAndSize(String brand, String size);

    List<Shampoo> findBySize(String size);

    List<Shampoo> findByIngredient(String ingredient);

    List<Shampoo> findByIngredients(List<String> ingredients);

    List<Shampoo> findBySizeOrLabelId(String size, long labelId);

    List<Shampoo> findWithPriceGreaterThan(String price);

    long countWithPriceLowerThan(String price);

    List<Shampoo> findWithIngredientCountLessThan(int count);

}
