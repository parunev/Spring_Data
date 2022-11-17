package com.example.json_exercise.domain.dtos.productDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductBasicInfo {
    private String name;
    private BigDecimal price;
}
