package com.example.json_exercise.domain.dtos.userDTOs;

import com.example.json_exercise.domain.dtos.productDTOs.ProductsSoldWithCountDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsersWithProductsDTO {
    private String firstName;
    private String lastName;
    private Integer age;
    private ProductsSoldWithCountDTO products;
}
