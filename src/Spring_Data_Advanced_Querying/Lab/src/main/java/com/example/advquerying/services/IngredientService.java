package com.example.advquerying.services;

import com.example.advquerying.entities.Ingredient;

import java.util.List;

public interface IngredientService {
    List<Ingredient> selectByName(String name);

    List<Ingredient> selectByNames(List<String> names);
    void deleteByName(String name);

    void updateAllPrice();
}
