package com.practicum.accounts.client;

import com.practicum.accounts.dto.notification.Notification;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class NotificationClient {
//    public static final String ACCOUNT_MONEY_URI = "/notific/";
    private final WebClient client;

    @CircuitBreaker(name = "notificationClient", fallbackMethod = "accountFallback")
    public void send(Notification request) {
        client.post()
//            .uri(ACCOUNT_MONEY_URI)
            .bodyValue(request)
            .retrieve()
            .toBodilessEntity()
            .block();
    }

    public String accountFallback(String accountId, Throwable throwable) {
        return "Ошибка";
    }
}
