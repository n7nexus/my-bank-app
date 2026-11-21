package com.practicum.cash.controller;

import com.practicum.cash.client.AccountClient;
import com.practicum.cash.dto.CashInfo;
import com.practicum.cash.dto.CashRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cash")
@RequiredArgsConstructor
public class CashController {

    private final AccountClient accountClient;

    @PostMapping("/")
    @PreAuthorize("hasRole('USER')")
    public CashInfo editCash(@RequestBody CashRequest request, JwtAuthenticationToken authentication) {
        if(request.accountId().equals(authentication.getToken().getClaimAsString("preferred_username"))) {
            return accountClient.cashEdit(request);
        }
        throw new UnsupportedOperationException();
    }

}
