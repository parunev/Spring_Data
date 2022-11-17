package com.example.json_exercise.domain.dtos.productDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ProductImportDTO {

    private String name;
    private BigDecimal price;
}
