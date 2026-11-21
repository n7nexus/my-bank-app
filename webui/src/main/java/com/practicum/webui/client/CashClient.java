package com.practicum.webui.client;

import com.practicum.webui.dto.cash.CashAction;
import com.practicum.webui.dto.cash.CashInfo;
import com.practicum.webui.dto.cash.CashRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CashClient {
    public static final String CASH_URI = "/cash/";
    private final WebClient client;

    @CircuitBreaker(name = "accountClient", fallbackMethod = "accountFallback")
    public CashInfo editCash (CashRequest cashRequest) {
        return client.post()
                .uri(CASH_URI)
                .bodyValue(cashRequest)
                .retrieve()
                .bodyToMono(CashInfo.class)
                .block();
    }

    public String accountFallback() {
        return "Ошибка";
    }
}
