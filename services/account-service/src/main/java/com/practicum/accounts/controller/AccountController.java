package com.practicum.accounts.controller;

import com.practicum.accounts.dto.account.AccountInfo;
import com.practicum.accounts.dto.account.AccountShortInfo;
import com.practicum.accounts.dto.account.AccountUpdateInfo;
import com.practicum.accounts.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/")
    public void createOrUpdateAccount(@RequestBody AccountUpdateInfo accountInfo, JwtAuthenticationToken authentication) {
        accountService.createOrUpdateAccount(authentication.getName(), accountInfo);
    }

    @GetMapping("/{login}")
    public AccountInfo getGetAccountResponseDto(@PathVariable("login") String login) {
        return accountService.getAccountInfo(login);
    }

    @GetMapping("/")
    public List<AccountShortInfo> getAccountList() {
        return accountService.getAccountList();
    }
}
