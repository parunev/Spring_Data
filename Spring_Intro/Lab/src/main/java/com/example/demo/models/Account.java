package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private BigDecimal balance;

    //an account can be owned by a single person
    @ManyToOne(optional = false)
    private User user;

    public Account(BigDecimal balance) {
        this.balance = balance;
    }

    public Account(BigDecimal balance, User user) {
        this.balance = balance;
        this.user = user;
    }

    public Account() {}
}
