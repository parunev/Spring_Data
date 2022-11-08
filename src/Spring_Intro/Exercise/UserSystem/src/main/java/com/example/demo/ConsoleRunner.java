package com.example.demo;

import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ConsoleRunner implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
       try {
           // invalid pass: User user = new User("asd", "asdas", "dfsf@gmail.com", "adsd", "asdsad");
           // invalid email: User user = new User("asd", "asdas", "dfsf", "adsd", "asdsad");

           User user = new User("test1", "wrong", "info@softuni-bulgaria.org", "t", "t");
           userRepository.save(user);
       } catch (IllegalArgumentException e) {
           System.out.println(e.getMessage());
       }
    }
}
