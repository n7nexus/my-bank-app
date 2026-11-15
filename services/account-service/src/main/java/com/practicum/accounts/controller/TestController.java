package com.practicum.accounts.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

//    @Value("${custom.message}")
//    private String message;

    @GetMapping("/")
    @PreAuthorize("hasRole('SERVICE')")
    public String getPerson() {
        return "OK: ";// + message;
    }
}
