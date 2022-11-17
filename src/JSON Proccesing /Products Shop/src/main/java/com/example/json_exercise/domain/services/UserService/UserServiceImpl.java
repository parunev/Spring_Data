package com.example.json_exercise.domain.services.UserService;

import com.example.json_exercise.domain.dtos.userDTOs.UserDTO;
import com.example.json_exercise.domain.dtos.userDTOs.UsersWithProductsDTO;
import com.example.json_exercise.domain.dtos.userDTOs.UsersWithProductsWrapperDTO;
import com.example.json_exercise.domain.dtos.userDTOs.UsersWithSoldProductsDTO;
import com.example.json_exercise.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.example.json_exercise.constants.Paths.USERS_AND_PRODUCTS_JSON_PATH;
import static com.example.json_exercise.constants.Paths.USERS_WITH_SOLD_PRODUCTS_JSON_PATH;
import static com.example.json_exercise.constants.Utils.MODEL_MAPPER;
import static com.example.json_exercise.constants.Utils.writeJsonIntoFile;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UsersWithSoldProductsDTO> findAllBySellingProductsBuyerIsNotNullOrderBySellingProductsBuyerFirstName() throws IOException {
        final List<UsersWithSoldProductsDTO> usersWithSoldProductsDTOS = this.userRepository
                .findAllBySellingProductsBuyerIsNotNullOrderBySellingProductsBuyerFirstName()
                .orElseThrow(NoSuchElementException::new)
                .stream().map(user -> MODEL_MAPPER.map(user, UsersWithSoldProductsDTO.class))
                .collect(Collectors.toList());

        writeJsonIntoFile(usersWithSoldProductsDTOS, USERS_WITH_SOLD_PRODUCTS_JSON_PATH);

        return usersWithSoldProductsDTOS;
    }

    @Override
    public UsersWithProductsWrapperDTO usersAndProducts() throws IOException {

        final List<UsersWithProductsDTO> usersAndProducts = this.userRepository
                .findAllBySellingProductsBuyerIsNotNullOrderBySellingProductsBuyerFirstName()
                .orElseThrow(NoSuchElementException::new)
                .stream().map(user -> MODEL_MAPPER.map(user, UserDTO.class))
                .map(UserDTO::toUsersWithProductsDTO).toList();

        final UsersWithProductsWrapperDTO usersWithProductsWrapperDTO = new UsersWithProductsWrapperDTO(usersAndProducts);

        writeJsonIntoFile(usersWithProductsWrapperDTO, USERS_AND_PRODUCTS_JSON_PATH);

        return usersWithProductsWrapperDTO;
    }
}
