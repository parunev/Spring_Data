package com.example.demo.services;

import com.example.demo.models.Account;
import com.example.demo.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountServiceImpl implements AccountService{
    private AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void withdrawMoney(BigDecimal money, Long id) {
        Account account = accountRepository.getReferenceById(id);

        BigDecimal currentBalance = account.getBalance();
        BigDecimal newBalance = currentBalance.subtract(money);

        //operation to check BigDecimals if they're negative or positive number
        if (newBalance.compareTo(BigDecimal.ONE) < 0){
            throw new IllegalArgumentException("Cannot withdraw negative amount of money");
        }

        account.setBalance(newBalance);
    }

    @Override
    public void depositMoney(BigDecimal money, Long id) {
        Account account = accountRepository.getReferenceById(id);

        BigDecimal currentBalance = account.getBalance();
        BigDecimal newBalance = currentBalance.add(money);

        if (money.compareTo(BigDecimal.ONE) < 0){
            throw new IllegalArgumentException("Cannot deposit negative amount of money");
        }

        account.setBalance(newBalance);
    }

    public void createAccount(Account account){
        this.accountRepository.save(account);
    }
}
