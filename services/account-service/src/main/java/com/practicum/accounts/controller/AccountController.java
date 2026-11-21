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
    public AccountInfo createOrUpdateAccount(@RequestBody AccountUpdateInfo accountInfo, JwtAuthenticationToken authentication) {
        return accountService.createOrUpdateAccount(authentication.getToken().getClaimAsString("preferred_username"), accountInfo);
    }

    @GetMapping("/{accountId}")
    public AccountInfo getAccountInfo(@PathVariable("accountId") String accountId) {
        return accountService.getAccountInfo(accountId);
    }

    @GetMapping("/")
    public List<AccountShortInfo> getAccountList() {
        return accountService.getAccountList();
    }
}
