package com.example.json_exercise.domain.services.ProductService;

import com.example.json_exercise.domain.dtos.productDTOs.ProductDTO;
import com.example.json_exercise.domain.dtos.productDTOs.ProductInRangeWithNoBuyerDTO;
import com.example.json_exercise.domain.repositories.ProductRepository;
import com.example.json_exercise.domain.services.ProductService.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.example.json_exercise.constants.Paths.PRODUCTS_WITH_NO_BUYERS_IN_RANGE_JSON_PATH;
import static com.example.json_exercise.constants.Utils.MODEL_MAPPER;
import static com.example.json_exercise.constants.Utils.writeJsonIntoFile;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public List<ProductInRangeWithNoBuyerDTO> findAllByPriceBetweenAndBuyerIsNullOrderByPrice(BigDecimal low, BigDecimal high) throws IOException {

        List<ProductInRangeWithNoBuyerDTO> products = this.productRepository.findAllByPriceBetweenAndBuyerIsNullOrderByPrice(low, high)
                .orElseThrow(NoSuchElementException::new)
                .stream()
                .map(product -> MODEL_MAPPER.map(product, ProductDTO.class))
                .map(ProductDTO::toProductInRangeWithNoBuyerDTO)
                .collect(Collectors.toList());

        writeJsonIntoFile(products, PRODUCTS_WITH_NO_BUYERS_IN_RANGE_JSON_PATH);
        return products;
    }
}
