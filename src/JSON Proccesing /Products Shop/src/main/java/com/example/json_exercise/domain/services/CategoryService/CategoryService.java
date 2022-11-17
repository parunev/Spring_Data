package com.example.json_exercise.domain.services.CategoryService;

import com.example.json_exercise.domain.dtos.categoryDTOs.CategoryProductSummaryDTO;

import java.io.IOException;
import java.util.List;

public interface CategoryService {

    List<CategoryProductSummaryDTO> getCategoriesSummary() throws IOException;
}
