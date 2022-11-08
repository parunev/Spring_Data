package com.example.demo.services;

import com.example.demo.models.Category;
import com.example.demo.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    @Override
    public Set<Category> getRandomCategories() {
        long allCategoryCount = categoryRepository.count();

        Random random = new Random();

        int categoryCount = random.nextInt((int) allCategoryCount) + 1;

        Set<Integer> categoryIds = new HashSet<>();
        for (int i = 0; i < categoryCount; i++) {
            categoryIds.add(random.nextInt((int) allCategoryCount) + 1);
        }

        return new HashSet<>(categoryRepository.findAllById(categoryIds));
    }
}
