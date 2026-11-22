package com.practicum.accounts.handler;

import com.practicum.accounts.exception.NoSuchAccountException;
import com.practicum.accounts.exception.NotEnoughMoneyException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AccountsExceptionHandler {

    @ExceptionHandler( NotEnoughMoneyException.class )
    public ResponseEntity<String> handleNotEnoughMoneyException() {
        return ResponseEntity.badRequest().body("На счете недостаточно денег!");
    }

    @ExceptionHandler( NoSuchAccountException.class )
    public ResponseEntity<String> handleNoSuchAccountException() {
        return ResponseEntity.badRequest().body("Аккаунта не существует!");
    }
}
