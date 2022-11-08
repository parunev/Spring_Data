package com.example.demo.services;

import com.example.demo.models.Account;

import java.math.BigDecimal;

public interface AccountService {
    void withdrawMoney(BigDecimal money, Long id);
    void depositMoney(BigDecimal money, Long id);

    void createAccount(Account account);
}
