package com.practicum.accounts.controller;

import com.practicum.accounts.dto.cash.CashRequest;
import com.practicum.accounts.dto.transfer.TransferRequest;
import com.practicum.accounts.exception.NoSuchAccountException;
import com.practicum.accounts.exception.NotEnoughMoneyException;
import com.practicum.accounts.service.MoneyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/money")
public class MoneyController {

    private final MoneyService moneyService;

    @PostMapping("/transfer")
    public void transferMoney(@RequestBody TransferRequest transferRequest, JwtAuthenticationToken authentication) throws NoSuchAccountException, NotEnoughMoneyException {
        if(transferRequest.fromAccountId().equals(authentication.getName())) {
            moneyService.transferMoney(transferRequest);
        }
    }

    @GetMapping("/")
    public void cashOut(@RequestBody CashRequest request, JwtAuthenticationToken authentication) throws NoSuchAccountException, NotEnoughMoneyException {
        moneyService.getMoney(authentication.getName(), request.amount());
    }

    @PutMapping("/")
    public void cashIn(@RequestBody CashRequest request, JwtAuthenticationToken authentication) throws NoSuchAccountException {
        moneyService.putMoney(authentication.getName(), request.amount());
    }

}
