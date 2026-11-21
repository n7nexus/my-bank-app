package com.practicum.accounts.service;

import com.practicum.accounts.dto.cash.CashInfo;
import com.practicum.accounts.dto.transfer.TransferRequest;
import com.practicum.accounts.exception.NoSuchAccountException;
import com.practicum.accounts.exception.NotEnoughMoneyException;
import com.practicum.accounts.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class MoneyService {

    private AccountRepository repository;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void putMoney(String login, BigDecimal amount) throws NoSuchAccountException {
        var account = repository.getAccountByAccountId(login);
        if(account != null) {
            var current = account.getMoney();
            account.setMoney(current.add(amount));
            repository.save(account);
        } else {
            throw new NoSuchAccountException("Account with login = " + login + " doesn't exist!");
        }
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void getMoney(String login, BigDecimal amount) throws NotEnoughMoneyException, NoSuchAccountException {
        var account = repository.getAccountByAccountId(login);
        if(account != null) {
            var current = account.getMoney();
            if(current.compareTo(amount) > 0) {
                account.setMoney(current.subtract(amount));
                repository.save(account);
            } else {
                throw new NotEnoughMoneyException("Account with login = " + login + " doesn't have enough money!");
            }
        } else {
            throw new NoSuchAccountException("Account with login = " + login + " doesn't exist!");
        }
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = {NoSuchAccountException.class, NotEnoughMoneyException.class})
    public void transferMoney(TransferRequest request) throws NoSuchAccountException, NotEnoughMoneyException {
        getMoney(request.fromAccountId(), request.amount());
        putMoney(request.toAccountId(), request.amount());
    }

    public CashInfo getCashInfo(String accountId) {
        return
    }
}
