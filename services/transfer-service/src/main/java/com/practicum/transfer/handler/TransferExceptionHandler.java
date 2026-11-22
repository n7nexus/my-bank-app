package com.practicum.transfer.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@RestControllerAdvice
public class TransferExceptionHandler {
    @ExceptionHandler( WebClientResponseException.class )
    public ResponseEntity<String> handleWebClientResponseException(WebClientResponseException e) {
        return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
    }
}