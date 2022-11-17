package com.example.json_exercise.domain.services.ProductService;

import com.example.json_exercise.domain.dtos.productDTOs.ProductInRangeWithNoBuyerDTO;
import com.example.json_exercise.domain.entities.Product;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface ProductService{

    List<ProductInRangeWithNoBuyerDTO> findAllByPriceBetweenAndBuyerIsNullOrderByPrice(BigDecimal low, BigDecimal high) throws IOException;
}
