package com.example.json_exercise.domain.dtos.categoryDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryProductSummaryDTO {

    private String categoryName;
    private Long productsCount;
    private Double averagePrice;
    private BigDecimal totalRevenue;
}
