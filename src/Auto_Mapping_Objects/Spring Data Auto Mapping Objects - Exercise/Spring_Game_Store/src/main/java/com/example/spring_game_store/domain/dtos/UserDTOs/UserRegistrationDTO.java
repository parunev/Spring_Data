package com.example.spring_game_store.domain.dtos.UserDTOs;

import com.example.spring_game_store.exceptions.WrongCredentialsException;
import com.example.spring_game_store.utils.Validations;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationDTO {

    private String email;
    private String password;
    private String confirmPassword;
    private String fullName;

    public UserRegistrationDTO(String[] args) {
        this.email = args[1];
        this.password = args[2];
        this.confirmPassword = args[3];
        this.fullName = args[4];
        validate();
    }

    private void validate(){
        if (!this.password.equals(this.confirmPassword)){
            throw new WrongCredentialsException("Passwords don't match");
        }

        if (!Validations.isValidEmail(this.email)){
            throw new WrongCredentialsException("Invalid email");
        }

        if (!Validations.isValidPassword(this.password)) {
            throw new WrongCredentialsException("Invalid password");
        }
    }
}
