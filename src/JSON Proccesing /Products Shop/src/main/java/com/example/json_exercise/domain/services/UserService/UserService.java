package com.example.json_exercise.domain.services.UserService;

import com.example.json_exercise.domain.dtos.userDTOs.UsersWithProductsWrapperDTO;
import com.example.json_exercise.domain.dtos.userDTOs.UsersWithSoldProductsDTO;

import java.io.IOException;
import java.util.List;

public interface UserService {

List<UsersWithSoldProductsDTO> findAllBySellingProductsBuyerIsNotNullOrderBySellingProductsBuyerFirstName() throws IOException;

    UsersWithProductsWrapperDTO usersAndProducts() throws IOException;
}
