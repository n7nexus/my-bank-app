package com.practicum.accounts.controller;

import com.practicum.accounts.dto.cash.CashInfo;
import com.practicum.accounts.dto.cash.CashRequest;
import com.practicum.accounts.dto.transfer.TransferRequest;
import com.practicum.accounts.exception.NoSuchAccountException;
import com.practicum.accounts.exception.NotEnoughMoneyException;
import com.practicum.accounts.service.MoneyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/money")
public class MoneyController {

    private final MoneyService moneyService;

    @PostMapping("/transfer")
    @PreAuthorize("hasRole('SERVICE') && hasAuthority('accounts.write')")
    public CashInfo transferMoney(@RequestBody TransferRequest transferRequest, JwtAuthenticationToken authentication) throws NoSuchAccountException, NotEnoughMoneyException {
        moneyService.transferMoney(transferRequest);
        return moneyService.getCashInfo(transferRequest.fromAccountId());
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('SERVICE') && hasAuthority('accounts.write')")
    public CashInfo cash(@RequestBody CashRequest request, JwtAuthenticationToken authentication) throws NoSuchAccountException, NotEnoughMoneyException {
        switch (request.action()) {
            case GET -> moneyService.getMoney(request.accountId(), request.amount());
            case PUT -> moneyService.putMoney(request.accountId(), request.amount());
        }

        return moneyService.getCashInfo(request.accountId());
    }

}
