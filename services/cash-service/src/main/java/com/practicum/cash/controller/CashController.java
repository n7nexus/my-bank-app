package com.practicum.cash.controller;

import com.practicum.cash.client.AccountClient;
import com.practicum.cash.dto.CashInfo;
import com.practicum.cash.dto.CashRequest;
import com.practicum.cash.service.CashService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CashController {

    private final AccountClient accountClient;
    private final CashService service;

    @PostMapping("/")
    @PreAuthorize("hasRole('USER') && hasAuthority('cash.ops')")
    public CashInfo editCash(@RequestBody CashRequest request, JwtAuthenticationToken authentication) {
        if(request.accountId().equals(authentication.getToken().getClaimAsString("preferred_username"))) {
            return service.cashEdit(request);
        }
        throw new UnsupportedOperationException();
    }

}
