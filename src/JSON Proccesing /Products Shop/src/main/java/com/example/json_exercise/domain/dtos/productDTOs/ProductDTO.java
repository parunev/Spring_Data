package com.example.json_exercise.domain.dtos.productDTOs;

import com.example.json_exercise.domain.dtos.categoryDTOs.CategoryDTO;
import com.example.json_exercise.domain.dtos.userDTOs.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private String name;
    private BigDecimal price;
    private UserDTO buyer;
    private UserDTO seller;
    private Set<CategoryDTO> categories;

    public ProductInRangeWithNoBuyerDTO toProductInRangeWithNoBuyerDTO(){
        return new ProductInRangeWithNoBuyerDTO(name, price, seller.getFullName());
    }

}
