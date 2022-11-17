package com.example.json_exercise.domain.dtos.productDTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductsSoldWithCountDTO {
    private Integer count;
    private List<ProductBasicInfo> products;

    public ProductsSoldWithCountDTO(List<ProductBasicInfo> products) {
        this.products = products;
        this.count = this.products.size();
    }
}
