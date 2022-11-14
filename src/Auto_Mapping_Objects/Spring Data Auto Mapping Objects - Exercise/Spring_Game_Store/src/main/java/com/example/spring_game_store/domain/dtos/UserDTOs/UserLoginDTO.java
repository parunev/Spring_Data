package com.example.spring_game_store.domain.dtos.UserDTOs;

import com.example.spring_game_store.exceptions.WrongCredentialsException;
import com.example.spring_game_store.utils.Validations;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginDTO {
    private String email;
    private String password;

    public UserLoginDTO(String[] commandParts) {
        this.email = commandParts[1];
        this.password = commandParts[2];
        validate();
    }

    private void validate(){
        if (!Validations.isValid(this.email, this.password)){
            throw new WrongCredentialsException("Invalid email/password");
        }
    }
}
