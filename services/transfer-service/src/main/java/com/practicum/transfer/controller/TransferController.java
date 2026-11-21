package com.practicum.transfer.controller;

import com.practicum.transfer.client.AccountClient;
import com.practicum.transfer.dto.CashInfo;
import com.practicum.transfer.dto.TransferRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transfer")
@RequiredArgsConstructor
public class TransferController {
    private final AccountClient accountClient;

    @PostMapping("/")
    @PreAuthorize("hasRole('USER')")
    public CashInfo transfer(@RequestBody TransferRequest request, JwtAuthenticationToken authentication) {
        if(request.fromAccountId().equals(authentication.getToken().getClaimAsString("preferred_username"))) {
            return accountClient.transfer(request);
        }
        throw new UnsupportedOperationException();
    }
}
