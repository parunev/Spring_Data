package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username",unique = true, nullable = false)
    private String username;

    @Column(name = "age",nullable = false)
    private int age;
    
    @OneToMany(mappedBy = "user", targetEntity = Account.class)
    private Set<Account> accounts;

    public User() {
        this.accounts = new HashSet<>();
    }

    public User(String username, int age, Set<Account> accounts) {
        this(username, age);
        this.accounts = accounts;
    }

    public User(String username, int age) {
        this();
        this.username = username;
        this.age = age;
    }
}
