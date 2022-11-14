package com.example.spring_game_store.utils;

import com.example.spring_game_store.domain.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Scanner;

@Configuration
public class Config {

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }

    @Bean
    public User user(){
        return new User();
    }

    @Bean
    public Scanner scanner(){
        return new Scanner(System.in);
    }
}
