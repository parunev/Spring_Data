package com.example.demo;

import com.example.demo.models.Account;
import com.example.demo.models.User;
import com.example.demo.repositories.AccountRepository;
import com.example.demo.services.AccountService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private UserService userService;
    private AccountService accountService;
    private AccountRepository accountRepository;

    @Autowired
    public ConsoleRunner(UserService userService, AccountService accountService, AccountRepository accountRepository) {
        this.userService = userService;
        this.accountService = accountService;
        this.accountRepository = accountRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        User user = new User("Martin", 23);
        userService.registerUser(user);

        Account account = new Account(new BigDecimal("2500"));
        account.setUser(user);

        accountRepository.save(account);
        user.getAccounts().add(account);

        accountService.createAccount(account);

        accountService.withdrawMoney(new BigDecimal("200"), account.getId());
        accountService.depositMoney(new BigDecimal("300"), account.getId());
    }
}
