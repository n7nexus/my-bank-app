package com.practicum.transfer.client;

import com.practicum.transfer.dto.CashInfo;
import com.practicum.transfer.dto.TransferRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class AccountClient {
    public static final String ACCOUNT_MONEY_URI = "/money/";
    private final WebClient client;

    @CircuitBreaker(name = "accountClient", fallbackMethod = "accountFallback")
    public CashInfo transfer(TransferRequest transferRequest) {
        return client.post()
                .uri(ACCOUNT_MONEY_URI + "transfer")
                .bodyValue(transferRequest)
                .retrieve()
                .bodyToMono(CashInfo.class)
                .block();
    }

    public ResponseEntity<String> accountFallback(Throwable throwable) {
        return ResponseEntity.internalServerError().body(throwable.getMessage());
    }
}
