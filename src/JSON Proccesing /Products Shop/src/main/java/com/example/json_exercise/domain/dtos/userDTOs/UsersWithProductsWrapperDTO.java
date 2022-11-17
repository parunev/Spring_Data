package com.example.json_exercise.domain.dtos.userDTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UsersWithProductsWrapperDTO {

    private long usersCount;
    private List<UsersWithProductsDTO> users;

    public UsersWithProductsWrapperDTO(List<UsersWithProductsDTO> users) {
        this.users = users;
        this.usersCount = this.users.size();
    }
}
