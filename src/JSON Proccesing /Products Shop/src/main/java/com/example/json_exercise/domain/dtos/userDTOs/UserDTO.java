package com.example.json_exercise.domain.dtos.userDTOs;

import com.example.json_exercise.domain.dtos.productDTOs.ProductBasicInfo;
import com.example.json_exercise.domain.dtos.productDTOs.ProductDTO;
import com.example.json_exercise.domain.dtos.productDTOs.ProductsSoldWithCountDTO;
import com.example.json_exercise.domain.entities.Product;
import com.example.json_exercise.domain.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String firstName;
    private String lastName;
    private int age;
    private Set<ProductDTO> sellingProducts;
    private Set<ProductDTO> boughtProducts;
    private Set<UserDTO> friends;

    public String getFullName(){
        return firstName + " " + lastName;
    }

    public UsersWithProductsDTO toUsersWithProductsDTO(){
        return new UsersWithProductsDTO(firstName, lastName, age, toProductsSoldWithCountDTO());
    }

    public ProductsSoldWithCountDTO toProductsSoldWithCountDTO(){
        return new ProductsSoldWithCountDTO(sellingProducts
                .stream()
                .map(this::toProductBasicInfo)
                .collect(Collectors.toList()));
    }

    public ProductBasicInfo toProductBasicInfo(ProductDTO productDTO){
        return new ProductBasicInfo(productDTO.getName(), productDTO.getPrice());
    }
}
