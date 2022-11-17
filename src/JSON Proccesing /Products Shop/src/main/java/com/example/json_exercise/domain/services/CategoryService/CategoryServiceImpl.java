package com.example.json_exercise.domain.services.CategoryService;


import com.example.json_exercise.domain.dtos.categoryDTOs.CategoryProductSummaryDTO;
import com.example.json_exercise.domain.repositories.CategoryRepository;
import com.example.json_exercise.domain.services.CategoryService.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import static com.example.json_exercise.constants.Paths.CATEGORIES_BY_PRODUCTS_JSON_PATH;
import static com.example.json_exercise.constants.Utils.writeJsonIntoFile;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public List<CategoryProductSummaryDTO> getCategoriesSummary() throws IOException {
        final List<CategoryProductSummaryDTO> categoryProductSummaryDTOS = this.categoryRepository
                .getCategoriesSummary().orElseThrow(NoSuchElementException::new);

        writeJsonIntoFile(categoryProductSummaryDTOS, CATEGORIES_BY_PRODUCTS_JSON_PATH);

        return categoryProductSummaryDTOS;
    }
}
