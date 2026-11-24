package com.practicum.transfer.controller;

import com.practicum.transfer.dto.CashInfo;
import com.practicum.transfer.dto.TransferRequest;
import com.practicum.transfer.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TransferController {
    private final TransferService transferService;

    @PostMapping("/")
    @PreAuthorize("hasRole('USER') && hasAuthority('transfer.write')")
    public CashInfo transfer(@RequestBody TransferRequest request, JwtAuthenticationToken authentication) {
        if(request.fromAccountId().equals(authentication.getToken().getClaimAsString("preferred_username"))) {
            return transferService.transfer(request);
        }
        throw new UnsupportedOperationException();
    }
}
