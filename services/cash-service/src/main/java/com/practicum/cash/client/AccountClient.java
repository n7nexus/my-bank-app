package com.practicum.cash.client;

import com.practicum.cash.dto.CashInfo;
import com.practicum.cash.dto.CashRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class AccountClient {
    public static final String ACCOUNT_MONEY_URI = "/money/";
    private final WebClient client;

    @CircuitBreaker(name = "accountClient", fallbackMethod = "accountFallback")
    public CashInfo cashEdit(CashRequest request) {
        return client.post()
                .uri(ACCOUNT_MONEY_URI)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(CashInfo.class)
                .block();
    }

    public String accountFallback(String accountId, Throwable throwable) {
        return "Ошибка";
    }
}
