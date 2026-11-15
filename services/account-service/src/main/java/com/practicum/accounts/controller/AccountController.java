package com.practicum.accounts.controller;

import com.practicum.accounts.dto.GetAccountResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class AccountController {

    @PostMapping("/")
    public void createOrUpdateAccount() {

    }

    @GetMapping("/")
    public GetAccountResponseDto getGetAccountResponseDto() {
        return new GetAccountResponseDto("Denis", LocalDate.of(1999, 10, 9), BigDecimal.valueOf(100000));
    }
}
